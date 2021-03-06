package com.jit.AgriIn.ys.devicemgt;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jit.AgriIn.app.MyApp;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.exception.BaseException;
import com.videogo.openapi.bean.EZDeviceUpgradeStatus;
import com.videogo.openapi.bean.EZDeviceVersion;
import com.videogo.util.LogUtil;
import com.videogo.widget.TitleBar;

import java.util.Timer;
import java.util.TimerTask;

public class EZUpgradeDeviceActivity extends Activity {
    private final static String TAG = "EZUpgradeDeviceActivity";
    private final static int TIMER_PERIODS = 3*1000;
    private TitleBar mTitleBar;
    LinearLayout mUpgradeLL = null;
    LinearLayout mUpgradeProgressLL = null;
    Button mUpgradeButton = null;
    TextView mProgressTextView = null;
    TextView mVersionDescTextView = null;

    String mDeviceSerial;
    private EZDeviceVersion mVersion = null;
    private EZDeviceUpgradeStatus mUpgradeStatus = null;
    private Timer mTimer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ezviz.ezopensdk.R.layout.activity_ezupgrade_device);
        initTitleBar();
        find_views();
        init_views();
    }

    private void find_views() {
        mUpgradeLL = (LinearLayout) findViewById(ezviz.ezopensdk.R.id.ezupgrade_ll_btn);
        mUpgradeProgressLL = (LinearLayout) findViewById(ezviz.ezopensdk.R.id.ezupgrade_ll_progress);
        mUpgradeButton = (Button) findViewById(ezviz.ezopensdk.R.id.ezupgrade_button);
        mProgressTextView = (TextView) findViewById(ezviz.ezopensdk.R.id.ezupgrade_progress_text);
        mVersionDescTextView = (TextView) findViewById(ezviz.ezopensdk.R.id.ezupgrade_text_version_desc);
    }

    private void init_views() {
        mUpgradeButton.setEnabled(false);
        mDeviceSerial = getIntent().getStringExtra("deviceSerial");
        LogUtil.i(TAG, "init_views: serial:" + mDeviceSerial);
        mUpgradeButton.setVisibility(View.VISIBLE);
        mProgressTextView.setVisibility(View.VISIBLE);
//        checkUpgradeStatusPeriodical();
        showVersionViewOnce();
        mProgressTextView.setText("????????????: 0" + "%");

        mUpgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUpgrade();
            }
        });
    }

    private void initTitleBar() {
        mTitleBar = (TitleBar) findViewById(ezviz.ezopensdk.R.id.title_bar);
        mTitleBar.addBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTitleBar.setTitle(ezviz.ezopensdk.R.string.ez_device_upgrade);
    }

    private void showButtonTab() {
        mUpgradeLL.setVisibility(View.VISIBLE);
        mUpgradeButton.setEnabled(true);
        mUpgradeProgressLL.setVisibility(View.GONE);
    }
    private void showProgressTab() {
        mUpgradeLL.setVisibility(View.GONE);
        mUpgradeProgressLL.setVisibility(View.VISIBLE);
    }
    private void showUpgradeSuccess() {
        showButtonTab();
        mUpgradeButton.setEnabled(false);
        mUpgradeButton.setText("????????????");
    }
    private void showUpgradeFailure() {
        showButtonTab();
        mUpgradeButton.setEnabled(false);
        mUpgradeButton.setText("????????????");
    }
    private void showIsLatestVersion() {
        showButtonTab();
        mUpgradeButton.setEnabled(true);
        mUpgradeButton.setText("??????");
    }
    private void showIsLatestVersion2() {
        showButtonTab();
        mUpgradeButton.setEnabled(true);
        mUpgradeButton.setText("??????????????????");
    }

    private int mStatus = -2;
    private void checkUpgradeStatusPeriodical() {
        final Runnable runit = new Runnable() {
            @Override
            public void run() {

                    LogUtil.i(TAG, "checkUpgradeStatusPeriodical: status: " + mStatus);
                    switch (mStatus) {
                        case 0://????????????
                            showProgressTab();
                            mProgressTextView.setText("????????????: " + mUpgradeStatus.getUpgradeProgress() + "%");
                            mTimer = new Timer();
                            mTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    checkUpgradeStatusPeriodical();
                                }
                            }, TIMER_PERIODS);
                            break;
                        case 1://????????????
                            if(mUpgradeStatus != null && mUpgradeStatus.getUpgradeProgress() == 100) {
                                Toast.makeText(EZUpgradeDeviceActivity.this, "?????????????????????????????????", Toast.LENGTH_SHORT).show();
                                mProgressTextView.setText("?????????????????????????????????: " + mUpgradeStatus.getUpgradeProgress() + "%");
                            }
                            showProgressTab();
                            break;
                        case 2://????????????
//                            showUpgradeSuccess();
                            showButtonTab();
                            break;
                        case 3://????????????
                            showUpgradeFailure();
                            break;
                        case -1://????????????
                            showIsLatestVersion();
                            break;
                        default:
                            showIsLatestVersion();
                            break;
                    }
            }
        };

        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mUpgradeStatus = MyApp.getOpenSDK().getDeviceUpgradeStatus(mDeviceSerial);
                    mStatus = mUpgradeStatus.getUpgradeStatus();
                    LogUtil.i(TAG, "checkUpgradeStatusPeriodical: status: " + mStatus);
                } catch (BaseException e) {
                    e.printStackTrace();

                    ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                    LogUtil.debugLog(TAG, errorInfo.toString());

                    return;
                }
                runOnUiThread(runit);
            }
        });
        thr.start();
    }

    private void startUpgrade() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MyApp.getOpenSDK().upgradeDevice(mDeviceSerial);
                    mTimer = new Timer();
                    mTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            checkUpgradeStatusPeriodical();
                        }
                    }, TIMER_PERIODS);
                } catch (BaseException e) {
                    e.printStackTrace();

                    ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                    LogUtil.debugLog(TAG, errorInfo.toString());
                }
            }
        });
        thr.start();
    }

    private void showVersionViewOnce() {
        LogUtil.i(TAG, "Enter showVersionViewOnce: ");
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mVersion = MyApp.getOpenSDK().getDeviceVersion(mDeviceSerial);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // ????????????
                            if (mVersion != null) {
                                mVersionDescTextView.setText(mVersion.getUpgradeDesc());
                                checkUpgradeStatusPeriodical();
                            }

                        }
                    });
                } catch (BaseException e) {
                    e.printStackTrace();
                    ErrorInfo errorInfo = (ErrorInfo) e.getObject();
                    LogUtil.debugLog(TAG, errorInfo.toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(EZUpgradeDeviceActivity.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        });
        thr.start();
    }
}
