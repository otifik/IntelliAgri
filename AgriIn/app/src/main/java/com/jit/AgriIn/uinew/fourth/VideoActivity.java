package com.jit.AgriIn.uinew.fourth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.app.MyApp;
import com.jit.AgriIn.ys.ui.realplay.EZRealPlayActivity;
import com.videogo.constant.IntentConsts;
import com.videogo.exception.BaseException;
import com.videogo.openapi.bean.EZCameraInfo;
import com.videogo.openapi.bean.EZDeviceInfo;
import com.videogo.ui.cameralist.SelectCameraDialog;
import com.videogo.ui.util.EZUtils;
import com.videogo.util.LogUtil;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yuanyuan on 2019/3/11.
 */

public class VideoActivity extends BaseActivity implements SelectCameraDialog.CameraItemClick{

    protected static final String TAG = "OneVideoFragment";
    public final static int REQUEST_CODE = 100;
    public final static int RESULT_CODE = 101;


    public final static int TAG_CLICK_PLAY = 1;
    public final static int TAG_CLICK_REMOTE_PLAY_BACK = 2;
    public final static int TAG_CLICK_SET_DEVICE = 3;
    public final static int TAG_CLICK_ALARM_LIST = 4;

    private int mClickType;

    @BindView(R.id.rec_baike)
    XRecyclerView mRecCamera;

    private YSVideoAdapter mAdapter;
    List<EZDeviceInfo> mList = new ArrayList<>();
    private List<String> mPondStrList = new ArrayList<>();
    private List<String> mMacStrList = new ArrayList<>();
    private boolean isCameraOpen = false;


    private String mToken;
    List<EZDeviceInfo> result = null;

    @Override
    public void init() {
        if (getIntent() != null){
            mToken = getIntent().getStringExtra("token");
            if (mToken != null){
                MyApp.getOpenSDK().setAccessToken(mToken);
                getCameraInfo();
            }
        }
    }

    public void getCameraInfo(){
        Observable.create((ObservableOnSubscribe<List<EZDeviceInfo>>) emitter -> {

            try {
                result = MyApp.getOpenSDK().getDeviceList(0, 10);
            } catch (BaseException e) {
                e.printStackTrace();
            }


            emitter.onNext(result);
            emitter.onComplete();
            mRecCamera.refreshComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(infoList -> mList =infoList,
                        throwable -> UIUtils.showToast(throwable.getMessage()));
    }


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_video;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }


    @Override
    public void initData() {
        initAdapter();
    }


    private void initAdapter() {
        mRecCamera.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new YSVideoAdapter(R.layout.item_video,mList);

        mRecCamera.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getCameraInfo();
            }

            @Override
            public void onLoadMore() {
                // 加载更多
//                mPage++;
//                mPresenter.queryExpertInfo(mPage);
                mRecCamera.noMoreLoading();
            }
        });
        mRecCamera.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {
//        iBtnPlay.setOnClickListener(view -> {
//            Intent intent = null;
//            intent = new Intent(getActivity(), EZRealPlayActivity.class);
//            intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
//            intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
//            startActivityForResult(intent, REQUEST_CODE);
//        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                EZDeviceInfo ezDeviceInfo = mList.get(position-1);
//                EZCameraInfo cameraInfo = EZUtils.getCameraInfoFromDevice(ezDeviceInfo, 0);
//                Intent intent = null;
//                intent = new Intent(getActivity(), EZRealPlayActivity.class);
//                intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
//                intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, ezDeviceInfo);
//                startActivityForResult(intent, REQUEST_CODE);

                if (ezDeviceInfo.getCameraNum() <= 0 || ezDeviceInfo.getCameraInfoList() == null || ezDeviceInfo.getCameraInfoList().size() <= 0) {
                    LogUtil.d(TAG, "cameralist is null or cameralist size is 0");
                    return;
                }
                if (ezDeviceInfo.getCameraNum() == 1 && ezDeviceInfo.getCameraInfoList() != null && ezDeviceInfo.getCameraInfoList().size() == 1) {
                    LogUtil.d(TAG, "cameralist have one camera");
                    final EZCameraInfo cameraInfo = EZUtils.getCameraInfoFromDevice(ezDeviceInfo, 0);
                    if (cameraInfo == null) {
                        return;
                    }

                    Intent intent = new Intent(VideoActivity.this, EZRealPlayActivity.class);
                    intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
                    intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, ezDeviceInfo);
                    startActivityForResult(intent, REQUEST_CODE);
                    return;
                }
                SelectCameraDialog selectCameraDialog = new SelectCameraDialog();
                selectCameraDialog.setEZDeviceInfo(ezDeviceInfo);
                selectCameraDialog.setCameraItemClick(VideoActivity.this);
                selectCameraDialog.show(getFragmentManager(), "onPlayClick");
            }
        });
    }







    @Override
    public void onCameraItemClick(EZDeviceInfo deviceInfo, int camera_index) {
        EZCameraInfo cameraInfo = null;
        Intent intent = null;

        cameraInfo = EZUtils.getCameraInfoFromDevice(deviceInfo, camera_index);
        if (cameraInfo == null) {
            return;
        }

        intent = new Intent(VideoActivity.this, EZRealPlayActivity.class);
        intent.putExtra(IntentConsts.EXTRA_CAMERA_INFO, cameraInfo);
        intent.putExtra(IntentConsts.EXTRA_DEVICE_INFO, deviceInfo);
        startActivityForResult(intent, REQUEST_CODE);
    }
}
