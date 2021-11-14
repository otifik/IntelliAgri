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
import com.jit.AgriIn.model.request.ConfigActionRequest;
import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.model.response.RobotMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.ui.presenter.config.ConfigAddAtPresenter;
import com.jit.AgriIn.ui.view.config.ConfigAddAtView;
import com.jit.AgriIn.widget.MyDialog;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author zxl on 2018/09/05.
 * discription: 配置水产设备界面
 * 大体上使用ScrollView 采用线性布局的方式分条列出来,
 * 通过MaterialDiaglog 进行数据的显示和选择 避免直接在布局控件里面输入。
 */
public class ConfigAddActivity extends BaseActivity<ConfigAddAtView, ConfigAddAtPresenter> implements ConfigAddAtView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.stvCameraDepth)
    SuperTextView mStvCameraDepth;
    @BindView(R.id.stvSensorDepth)
    SuperTextView mStvSensorDepth;
    @BindView(R.id.stvCruiseSpeed)
    SuperTextView mStvCruiseSpeed;
    @BindView(R.id.stvCruiseNum)
    SuperTextView mStvCruiseNum;
    @BindView(R.id.stvDrive)
    SuperTextView mStvDrive;
    @BindView(R.id.stvFeedName)
    SuperTextView mStvFeedName;
    @BindView(R.id.stvFeedWeight)
    SuperTextView mStvFeedWeight;
    @BindView(R.id.stvFeedSpeed)
    SuperTextView mStvFeedSpeed;
    @BindView(R.id.stvDrugName)
    SuperTextView mStvDrugName;
    @BindView(R.id.stvDrugWeight)
    SuperTextView mStvDrugWeight;
    @BindView(R.id.stvDrugSpeed)
    SuperTextView mStvDrugSpeed;
    @BindView(R.id.stvCruiseMode)
    SuperTextView mStvCruiseMode;
    @BindView(R.id.stvReturnCruiseSpeed)
    SuperTextView mStvReturnCruiseSpeed;
    @BindView(R.id.stvPondChoose)
    SuperTextView mStvPondChoose;
    @BindView(R.id.stvRobotChoose)
    SuperTextView mStvRobotChoose;
    @BindView(R.id.llFeed)
    LinearLayout mLlFeed;
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
    private int mPondSelectPosition = -1;
    private int mRobotSelectPosition = -1;

    private int cruiseSpeedIndex = -1;
    private int returnSpeedIndex = -1;
    private int feedSpeedIndex = -1;
    private int medicineSpeedIndex = -1;
    private int cruiseMode = 0;
    private List<String> mPondStrList = new ArrayList<>();
    private List<String> mRobotStrList = new ArrayList<>();
    private List<RobotMainResponse> mRobotBeanList = new ArrayList<>();

    private List<PondMainResponse> mPondBeanList = new ArrayList<>();


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
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_config_add;
    }

    @Override
    protected ConfigAddAtPresenter createPresenter() {
        return new ConfigAddAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加设备配置");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(getString(R.string.save));
        // 初始化为巡航模式
        //switchMode(cruiseMode);
        //mStvCruiseMode.setRightString("巡航");
        mRbXia.setChecked(true);
        mRbMode1.setChecked(true);
    }

    @Override
    protected void initData() {
        mPresenter.loadPondWithRobot();
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

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

        mStvPondChoose.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                showPondList();
            }
        });

        mStvRobotChoose.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                showPageRobotList();
            }
        });

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
                break;
            case 1:
                break;
            case 2:
                break;
            default:
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

    /**
     * 进行数据的保持
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    private void doSubmit() {
        if (mPondSelectPosition == -1) {
            UIUtils.showToast("暂无可操作生产单元，请先进行添加");
            return;
        }

        if (robotIDSelected == -1) {
            UIUtils.showToast("暂无可操作机器人，请先进行添加");
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
            UIUtils.showToast(getString(R.string.input_cruise_mode));
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
        configActionRequest.setBird_status(mDriveState ? 1 : 0);

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
        mPresenter.addConfig(configActionRequest);
    }

    @Override
    public void addConfigSuccess(ConfigMainResponse configMainResponse) {
        mRxManager.post(AppConstant.RX_ADD_CONFIG, configMainResponse);
        finish();
    }

    @Override
    public void addConfigFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getPondInfoSuccess(List<PondMainResponse> pondBeanList, List<String> pondStrList) {
        mPondStrList.clear();
        mPondStrList.addAll(pondStrList);
        mPondBeanList.clear();
        mPondBeanList.addAll(pondBeanList);
        if (pondStrList != null && pondBeanList.size() != 0) {
            mStvPondChoose.getRightTextView().setText(pondStrList.get(0));
            mPondSelectPosition = 0;
        }
    }

    @Override
    public void getPondInfoFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void getRobotInfoSuccess(RobotPageResponse robotPageResponse) {
        if (robotPageResponse.getList() != null && robotPageResponse.getList().size() != 0) {
            RobotMainResponse robotMainResponse = robotPageResponse.getList().get(0);
            mStvRobotChoose.setRightString(robotMainResponse.getNumber());
            robotIDSelected = robotMainResponse.getId();
        }
    }


    @Override
    public void getRobotInfoFailure(String error) {
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
