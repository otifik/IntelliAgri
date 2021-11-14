package com.jit.AgriIn.ui.activity.config;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.ConfigUpdateBean;
import com.jit.AgriIn.model.request.ConfigActionRequest;
import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.model.response.RobotMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.presenter.config.ConfigUpdateAtPresenter;
import com.jit.AgriIn.ui.view.config.ConfigUpdateAtView;
import com.jit.AgriIn.widget.MyDialog;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/09/05.
 * discription:配置金科智农修改界面
 * 这里的更新和添加还是挺相似的 ----
 */
public class ConfigUpdateActivity extends BaseActivity<ConfigUpdateAtView, ConfigUpdateAtPresenter> implements ConfigUpdateAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.stvPondChoose)
    SuperTextView mStvPondChoose;
    @BindView(R.id.stvRobotChoose)
    SuperTextView mStvRobotChoose;
    @BindView(R.id.stvCruiseMode)
    SuperTextView mStvCruiseMode;
    @BindView(R.id.stvCameraDepth)
    SuperTextView mStvCameraDepth;
    @BindView(R.id.stvSensorDepth)
    SuperTextView mStvSensorDepth;
    @BindView(R.id.stvCruiseSpeed)
    SuperTextView mStvCruiseSpeed;
    @BindView(R.id.stvCruiseNum)
    SuperTextView mStvCruiseNum;
    @BindView(R.id.stvReturnCruiseSpeed)
    SuperTextView mStvReturnCruiseSpeed;
    @BindView(R.id.stvDrive)
    SuperTextView mStvDrive;
    @BindView(R.id.stvFeedName)
    SuperTextView mStvFeedName;
    @BindView(R.id.stvFeedWeight)
    SuperTextView mStvFeedWeight;
    @BindView(R.id.stvFeedSpeed)
    SuperTextView mStvFeedSpeed;
    @BindView(R.id.llFeed)
    LinearLayout mLlFeed;
    @BindView(R.id.stvDrugName)
    SuperTextView mStvDrugName;
    @BindView(R.id.stvDrugWeight)
    SuperTextView mStvDrugWeight;
    @BindView(R.id.stvDrugSpeed)
    SuperTextView mStvDrugSpeed;
    @BindView(R.id.llDrug)
    LinearLayout mLlDrug;
    @BindView(R.id.rb_xia)
    RadioButton mRbXia;
    @BindView(R.id.rb_yu)
    RadioButton mRbYu;
    @BindView(R.id.rb_xie)
    RadioButton mRbXie;
    @BindView(R.id.radioCategory)
    RadioGroup mRadioCategory;
    @BindView(R.id.rb_mode_1)
    RadioButton mRbMode1;
    @BindView(R.id.rb_mode_2)
    RadioButton mRbMode2;
    @BindView(R.id.rb_mode_3)
    RadioButton mRbMode3;
    @BindView(R.id.rgMode)
    RadioGroup mRgMode;

    private boolean mDriveState = false;
    private List<String> mSpeedList;
    private List<String> mDrugKindList;
    private List<String> mFeedKindList;
    private List<String> mFCrusieModeList;
    private int mPondSelectPosition = -1;
    private int mRobotSelectPosition = -1;

    private int cruiseSpeedIndex = -1;
    private int returnSpeedIndex = -1;
    private int feedSpeedIndex = -1;
    private int medicineSpeedIndex = -1;
    private int cruiseMode = 0;
    private List<String> mPondStrList = new ArrayList<>();
    private List<String> mRobotStrList = new ArrayList<>();
    private List<PondMainResponse> mPondBeanList = new ArrayList<>();
    private List<RobotMainResponse> mRobotBeanList = new ArrayList<>();
    private ConfigMainResponse mConfigBean;
    private int mIndex;


    private int mCurrentPage;
    private int mMaxPage;
    private boolean isFirst;
    private int robotIDSelected = -1;
    private BaseQuickAdapter<RobotMainResponse, BaseViewHolder> mRobotAdapter;
    private String category = "虾";
    private int type = 0;
    @Override
    protected void init() {
        mSpeedList = Arrays.asList(getResources().getStringArray(R.array.array_cruise_speed));
        mDrugKindList = Arrays.asList(getResources().getStringArray(R.array.array_drug_kind));
        mFeedKindList = Arrays.asList(getResources().getStringArray(R.array.array_feed_kind));
        mFCrusieModeList = Arrays.asList(getResources().getStringArray(R.array.array_cruise_mode));
        if (getIntent() != null) {
            mConfigBean = (ConfigMainResponse) getIntent().getSerializableExtra(AppConstant.BUNDLE_CONFIG_BEAN);
            mIndex = getIntent().getIntExtra(AppConstant.EXTRA_ITEM_INDEX, -1);
        }
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_config_update;
    }

    @Override
    protected ConfigUpdateAtPresenter createPresenter() {
        return new ConfigUpdateAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(getString(R.string.save));
        mTvToolbarTitle.setText("配置修改");
        refreshView();
    }

    /**
     * 刷新View
     */
    private void refreshView() {
        if (mConfigBean == null) {
            return;
        }
        mStvPondChoose.setRightString(mConfigBean.getPound_name());
        mStvRobotChoose.setRightString(mConfigBean.getRobert_name());
        category = mConfigBean.getCategory();
        switch (mConfigBean.getCategory()){
            case "虾":
                mRbXia.setChecked(true);
                break;
            case "鱼":
                mRbYu.setChecked(true);
                break;
            case "螃蟹":
                mRbXie.setChecked(true);
                break;
                default:
                    break;
        }
        type = mConfigBean.getType();
        switch (type){
            case 0:
                mRbMode1.setChecked(true);
                break;
            case 1:
                mRbMode2.setChecked(true);
                break;
            case 2:
                mRbMode3.setChecked(true);
                break;
                default:
                    break;
        }
        mStvCameraDepth.setRightString(String.valueOf(mConfigBean.getCamera_depth()));
        mStvSensorDepth.setRightString(String.valueOf(mConfigBean.getSensor_depth()));
        mStvCruiseSpeed.setRightString(mSpeedList.get(mConfigBean.getCruise_velocity()));
        mStvReturnCruiseSpeed.setRightString(mSpeedList.get(mConfigBean.getReturn_velocity()));
        mStvCruiseNum.setRightString(String.valueOf(mConfigBean.getCircle()));
        mStvDrive.setSwitchIsChecked(mConfigBean.getBird_status() == 1);
        cruiseSpeedIndex = mConfigBean.getCruise_velocity();
        returnSpeedIndex = mConfigBean.getReturn_velocity();
        cruiseMode = mConfigBean.getType();
        mStvFeedName.setRightString(mConfigBean.getFeed_name());
        mStvFeedWeight.setRightString(String.valueOf(mConfigBean.getFeed_weight()));
        mStvFeedSpeed.setRightString(mSpeedList.get(mConfigBean.getFeed_velocity()));
        mStvDrugName.setRightString(mConfigBean.getMedicine_name());
        mStvDrugWeight.setRightString(String.valueOf(mConfigBean.getMedicine_weight()));
        mStvDrugSpeed.setRightString(mSpeedList.get(mConfigBean.getMedicine_velocity()));
        medicineSpeedIndex = mConfigBean.getMedicine_velocity();

        robotIDSelected = mConfigBean.getRobert_id();
    }

    @Override
    protected void initData() {

        /**
         * 初始化生产单元等信息
         */
        mPresenter.loadPondWithRobot();

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(view -> onBackPressed());

        mStvCameraDepth.setOnSuperTextViewClickListener(superTextView -> showCameraDepthDialog());

        mStvDrive.setSwitchCheckedChangeListener((compoundButton, b) -> mDriveState = b);

        mStvCruiseMode.setOnSuperTextViewClickListener(superTextView -> showCruiseModeDialog());

        mStvCruiseNum.setOnSuperTextViewClickListener(superTextView -> showCruiseNumDialog());

        mStvReturnCruiseSpeed.setOnSuperTextViewClickListener(superTextView -> showReturnCruiseDialog());

        mStvCruiseSpeed.setOnSuperTextViewClickListener(superTextView -> showCruiseSpeedDialog());

        mStvSensorDepth.setOnSuperTextViewClickListener(superTextView -> showSensorDepthDialog());

        mStvFeedName.setOnSuperTextViewClickListener(superTextView -> showFeedNameDialog());

        mStvFeedWeight.setOnSuperTextViewClickListener(superTextView -> showFeedWeightDialog());

        mStvFeedSpeed.setOnSuperTextViewClickListener(superTextView -> showFeedSpeedDialog());

        mStvDrugName.setOnSuperTextViewClickListener(superTextView -> showDrugNameDialog());

        mStvDrugWeight.setOnSuperTextViewClickListener(superTextView -> showDrugWeightDialog());

        mStvDrugSpeed.setOnSuperTextViewClickListener(superTextView -> showDrugSpeedDialog());

        mTvPublishNow.setOnClickListener(v -> doSubmit());

        mStvPondChoose.setOnSuperTextViewClickListener(superTextView -> showPondList());

        mStvRobotChoose.setOnSuperTextViewClickListener(superTextView -> showPageRobotList());

        mRgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mRbMode1.getId()){
                    type = 0;
                }else if (checkedId == mRbMode2.getId()){
                    type = 1;
                }else if (checkedId == mRbMode3.getId()){
                    type = 2;
                }
            }
        });

        mRadioCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == mRbXia.getId()){
                    category = "虾";
                }else if (checkedId == mRbYu.getId()){
                    category = "鱼";
                }else if (checkedId == mRbXie.getId()){
                    category = "螃蟹";
                }
            }
        });
    }

    private void showPageRobotList() {
        /*  弹出dialog,键入密码进行验证*/
        mCurrentPage = 1;
        isFirst = true;
        View rgScsView = LayoutInflater.from(this).inflate(R.layout.dialog_show_robot, null);
        MyDialog robotDialog = new MyDialog(this, R.style.MyDialog);
        Window window = robotDialog.getWindow();
        window.setGravity(Gravity.CENTER);
        window.setWindowAnimations(R.style.BottomDialog);
        window.getDecorView().setPadding(50, 0, 50, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        robotDialog.setContentView(rgScsView);
        TextView tvCancel = robotDialog.findViewById(R.id.tvCancel);
        RecyclerView recyclerView = robotDialog.findViewById(R.id.recyclerView);
        initDialogRecyclerView(recyclerView, robotDialog);
        tvCancel.setOnClickListener(v -> {
            mRobotBeanList.clear();
            robotDialog.dismiss();
        });
        refreshDialog();
        robotDialog.show();

    }

    private void refreshDialog() {
        mPresenter.queryAllRobotPage(mCurrentPage);
    }

    private void initDialogRecyclerView(RecyclerView recyclerView, Dialog dialog) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRobotAdapter = new BaseQuickAdapter<RobotMainResponse, BaseViewHolder>(R.layout.item_robot_select, mRobotBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, RobotMainResponse item) {
                helper.setText(R.id.tvText, item.getNumber());
            }
        };
        mRobotAdapter.setOnLoadMoreListener(() -> new Handler().postDelayed(() -> {
            if (mCurrentPage > mMaxPage) {
                mRobotAdapter.loadMoreEnd();
            } else {
                mPresenter.queryAllRobotPage(mCurrentPage);
            }
        }, 1000), recyclerView);

        mRobotAdapter.setOnItemClickListener((adapter, view, position) -> {
            RobotMainResponse robotMainResponse = mRobotBeanList.get(position);
            mStvRobotChoose.setRightString(robotMainResponse.getNumber());
            robotIDSelected = robotMainResponse.getId();
            dialog.dismiss();
        });

        recyclerView.setAdapter(mRobotAdapter);
    }

    /**
     * 进行更新的提交咯----哈哈
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    private void doSubmit() {
        if (mPondBeanList == null || mPondBeanList.size() == 0) {
            UIUtils.showToast("生产单元信息缺失");
            return;
        }

        int pondID = mPondBeanList.get(mPondSelectPosition).getId();
        int robotID = robotIDSelected;
        ConfigActionRequest configActionRequest = new ConfigActionRequest();
        configActionRequest.setPound_id(pondID);
        configActionRequest.setRobert_id(robotID);
        configActionRequest.setType(type);
        configActionRequest.setCategory(category);

        if (cruiseSpeedIndex == -1) {
            UIUtils.showToast(getString(R.string.input_cruise_speed));
            return;
        }

        if (returnSpeedIndex == -1) {
            UIUtils.showToast(getString(R.string.input_return_speed));
            return;
        }
        configActionRequest.setCamera_depth(Float.parseFloat(mStvCameraDepth.getRightString().trim()));
        configActionRequest.setSensor_depth(Float.parseFloat(mStvSensorDepth.getRightString().trim()));
        configActionRequest.setCruise_velocity(cruiseSpeedIndex);
        configActionRequest.setReturn_velocity(returnSpeedIndex);
        configActionRequest.setCircle(Integer.parseInt(mStvCruiseNum.getRightString().trim()));
        configActionRequest.setBird_status(mDriveState ? 0 : 1);
        String feedName = mStvFeedName.getRightString().trim();
        if (feedName.startsWith(getString(R.string.onClick))) {
            UIUtils.showToast(getString(R.string.input_feed_name));
            return;
        }

        String feedWeight = mStvFeedWeight.getRightString().trim();
        if (feedWeight.startsWith(getString(R.string.onClick))) {
            UIUtils.showToast(getString(R.string.input_feed_weight));
            return;
        }

        if (feedSpeedIndex == -1) {
            UIUtils.showToast(getString(R.string.input_feed_speed));
            return;
        }
        configActionRequest.setFeed_name(feedName);
        configActionRequest.setFeed_weight(Float.parseFloat(feedWeight));
        configActionRequest.setFeed_velocity(feedSpeedIndex);


        String drugName = mStvDrugName.getRightString().trim();
        if (drugName.startsWith(getString(R.string.onClick))) {
            UIUtils.showToast(getString(R.string.input_medicine_name));
            return;
        }
        String drugWeight = mStvDrugWeight.getRightString().trim();
        if (drugWeight.startsWith(getString(R.string.onClick))) {
            UIUtils.showToast(getString(R.string.input_medicine_weight));
            return;
        }

        if (medicineSpeedIndex == -1) {
            UIUtils.showToast(getString(R.string.input_medicine_speed));
            return;
        }

        configActionRequest.setMedicine_name(drugName);
        configActionRequest.setMedicine_weight(Float.parseFloat(drugWeight));
        configActionRequest.setMedicine_velocity(medicineSpeedIndex);
        mPresenter.updateConfig(mConfigBean.getId(), configActionRequest);

    }


    private void showPondList() {
        new MaterialDialog.Builder(this)
                .title("生产单元")
                .items(mPondStrList)
                .itemsCallback((dialog, view, which, text) -> {
                    mPondSelectPosition = which;
                    mStvPondChoose.getRightTextView().setText(text);
                })
                .positiveText(android.R.string.cancel)
                .show();
    }

    private void showRobotList() {
        new MaterialDialog.Builder(this)
                .title("机器人编号")
                .items(mRobotStrList)
                .itemsCallback((dialog, view, which, text) -> {
                    mRobotSelectPosition = which;
                    mStvRobotChoose.getRightTextView().setText(text);
                })
                .positiveText(android.R.string.cancel)
                .show();
    }


    private void showCruiseModeDialog() {
        int index;
        String str = mStvCruiseMode.getRightString();
        index = Arrays.asList(getResources().getStringArray(R.array.array_cruise_mode)).indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.cruise_mode_title))
                .items(R.array.array_cruise_mode)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            cruiseMode = which;
                            mStvCruiseMode.setRightString(text);
                            switchMode(which);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    private void switchMode(int which) {
        switch (which) {
            case 0:
                mLlDrug.setVisibility(View.GONE);
                mLlFeed.setVisibility(View.GONE);
                break;
            case 1:
                mLlFeed.setVisibility(View.VISIBLE);
                mLlDrug.setVisibility(View.GONE);
                break;
            case 2:
                mLlDrug.setVisibility(View.VISIBLE);
                mLlFeed.setVisibility(View.VISIBLE);
                break;
            default:
                mLlDrug.setVisibility(View.GONE);
                mLlFeed.setVisibility(View.GONE);
                break;
        }
    }

    public void showCameraDepthDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.camera_depth_title))
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .positiveText(R.string.submit)
                .input(
                        null,
                        mStvCameraDepth.getRightString(),
                        false,
                        (dialog, input) -> mStvCameraDepth.setRightString(input))
                .show();
    }

    public void showSensorDepthDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.sensor_depth_title))
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .positiveText(R.string.submit)
                .input(
                        null,
                        mStvSensorDepth.getRightString(),
                        false,
                        (dialog, input) -> mStvSensorDepth.setRightString(input))
                .show();
    }

    public void showCruiseSpeedDialog() {
        int index;
        String str = mStvCruiseSpeed.getRightString();
        index = mSpeedList.indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.cruise_speed_title))
                .items(R.array.array_cruise_speed)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            cruiseSpeedIndex = which;
                            mStvCruiseSpeed.setRightString(text);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    public void showReturnCruiseDialog() {
        int index;
        String str = mStvReturnCruiseSpeed.getRightString();
        index = mSpeedList.indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.return_cruise_num_title))
                .items(R.array.array_cruise_speed)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            returnSpeedIndex = which;
                            mStvReturnCruiseSpeed.setRightString(text);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    public void showCruiseNumDialog() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.cruise_num_title))
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .positiveText(R.string.submit)
                .input(
                        null,
                        mStvCruiseNum.getRightString(),
                        false,
                        new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                mStvCruiseNum.setRightString(input);
                            }
                        })
                .show();
    }


    public void showFeedNameDialog() {
        int index;
        String str = mStvFeedName.getRightString();
        index = mFeedKindList.indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.drug_name_title))
                .items(R.array.array_feed_kind)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            mStvFeedName.setRightString(text);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    public void showFeedWeightDialog() {
        String preFill = null;
        String str = mStvFeedWeight.getRightString();
        if (!str.contains(getString(R.string.contains_input))) {
            preFill = str;
        }
        new MaterialDialog.Builder(this)
                .title(getString(R.string.feed_weight_title))
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .positiveText(R.string.submit)
                .input(
                        null,
                        preFill,
                        false,
                        (dialog, input) -> mStvFeedWeight.setRightString(input))
                .show();
    }

    public void showFeedSpeedDialog() {
        int index;
        String str = mStvReturnCruiseSpeed.getRightString();
        index = mSpeedList.indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.feed_speed_title))
                .items(R.array.array_cruise_speed)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            feedSpeedIndex = which;
                            mStvFeedSpeed.setRightString(text);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    public void showDrugSpeedDialog() {
        int index;
        String str = mStvReturnCruiseSpeed.getRightString();
        index = mSpeedList.indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.drug_speed_title))
                .items(R.array.array_cruise_speed)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            medicineSpeedIndex = which;
                            mStvDrugSpeed.setRightString(text);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    public void showDrugNameDialog() {
        int index;
        String str = mStvDrugName.getRightString();
        index = mDrugKindList.indexOf(str);
        new MaterialDialog.Builder(this)
                .title(getString(R.string.drug_name_title))
                .items(R.array.array_drug_kind)
                .itemsCallbackSingleChoice(
                        index,
                        (dialog, view, which, text) -> {
                            mStvDrugName.setRightString(text);
                            return true;
                        })
                .positiveText(R.string.submit)
                .show();
    }

    public void showDrugWeightDialog() {
        String preFill = null;
        String str = mStvDrugWeight.getRightString();
        if (!str.contains(getString(R.string.contains_input))) {
            preFill = str;
        }
        new MaterialDialog.Builder(this)
                .title(getString(R.string.drug_weight_num_title))
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .positiveText(R.string.submit)
                .input(
                        null,
                        preFill,
                        false,
                        (dialog, input) -> mStvDrugWeight.setRightString(input))
                .show();
    }


    @Override
    public void onBackPressedSupport() {
        showMaterialDialog(getString(R.string.info_tips),
                getString(R.string.update_exit),
                getString(R.string.dialog_ensure),
                getString(R.string.dialog_cancel),
                (dialog, which) -> {
                    dialog.dismiss();
                    super.onBackPressedSupport();
                }, (dialog, which) -> dialog.dismiss());
    }

    @Override
    public void updateConfigSuccess(ConfigMainResponse configMainResponse) {
        ConfigUpdateBean configUpdateBean = new ConfigUpdateBean();
        configUpdateBean.setIndex(mIndex);
        configUpdateBean.setConfigMainResponse(configMainResponse);
        mRxManager.post(AppConstant.RX_UPDATE_CONFIG, configUpdateBean);
        finish();
    }

    @Override
    public void updateConfigFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getPondInfoSuccess(List<PondMainResponse> pondBeanList, List<String> pondStrList) {
        mPondStrList.clear();
        mPondStrList.addAll(pondStrList);
        mPondBeanList.clear();
        mPondBeanList.addAll(pondBeanList);
        if (pondStrList != null && pondBeanList.size() != 0 && mConfigBean != null) {
            mPondSelectPosition = mPondStrList.indexOf(mConfigBean.getPound_name());
        }
    }

    @Override
    public void getPondInfoFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void queryMyRobotSuccess(RobotPageResponse robotPageResponse) {
        mMaxPage = robotPageResponse.getPages();
        if (robotPageResponse.getPageNum() <= robotPageResponse.getPages()) {
            mCurrentPage++;
        }
        if (isFirst) {
            mRobotBeanList.clear();
            mRobotBeanList.addAll(robotPageResponse.getList());
            mRobotAdapter.setNewData(mRobotBeanList);
            isFirst = false;
        } else {
            if (mRobotAdapter.isLoading()) {
                mRobotAdapter.loadMoreComplete();
            }
            // 可以加载 那么页数就得+1了
            mRobotBeanList.addAll(robotPageResponse.getList());
            mRobotAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void queryMyRobotFailure(String error) {
        UIUtils.showToast(error);
    }


}
