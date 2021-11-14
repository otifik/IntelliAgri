package com.jit.AgriIn.ui.activity.culture;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.LogFileBean;
import com.jit.AgriIn.model.response.CultureLogResponse;
import com.jit.AgriIn.ui.presenter.culture.LogFileAtPresenter;
import com.jit.AgriIn.ui.view.culture.LogFileAtView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.ResHelper;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.widget.DropDownView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-11 14:31:34.
 * Describe:
 */

public class LogFileCsActivity extends BaseActivity<LogFileAtView, LogFileAtPresenter> implements LogFileAtView {


    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.llToolbarTitle)
    LinearLayout mLlToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.llDownload)
    LinearLayout mLlDownload;
    @BindView(R.id.ivDnLog)
    ImageView mIvDnLog;
    @BindView(R.id.llLogAll)
    LinearLayout mLlLogAll;
    @BindView(R.id.llTable)
    LinearLayout mLlTable;
    @BindView(R.id.llSave)
    LinearLayout mLlSave;
    @BindView(R.id.flToolBar)
    FrameLayout mFlToolBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.drop_down_view)
    DropDownView mDropDownView;

    private TextView mTvFileName;
    private ImageView mArrowImage;
    private LinearLayout mLlFileType;
    private int selectPosition = 0;
    private int prePosition = -1;
    private View expandedView;
    private View collapsedView;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<LogFileBean,BaseViewHolder> mAdapter ;
    private List<LogFileBean> mLogFileBeanList = new ArrayList<>();
    private TextView mTvFileType;

    private File file;
    private String mFileName;
    private String mFileType;
    private String mFileSavePath;
    private List<CultureLogResponse> mMList;
    private final DropDownView.DropDownListener dropDownListener = new DropDownView.DropDownListener() {
        @Override
        public void onExpandDropDown() {
            // 刷新 ----
            ObjectAnimator.ofFloat(mArrowImage, View.ROTATION.getName(), 180).start();
        }

        @Override
        public void onCollapseDropDown() {
            ObjectAnimator.ofFloat(mArrowImage, View.ROTATION.getName(), -180, 0).start();
        }
    };

    @Override
    protected void init() {
        if (getIntent() != null){
            mFileName = getIntent().getExtras().getString(AppConstant.EXTRA_DOWNLOAD_NAME);
            mMList = (List<CultureLogResponse>)getIntent().getExtras().getSerializable(AppConstant.EXTRA_DOWNLOAD_LIST);
        }
        mLogFileBeanList.add(new LogFileBean(AppConstant.FileSaveType.XLS,AppConstant.FileSaveType.DES_FOR_XLS));
        mLogFileBeanList.add(new LogFileBean(AppConstant.FileSaveType.CSV,AppConstant.FileSaveType.DES_FOR_CVS));
        mLogFileBeanList.add(new LogFileBean(AppConstant.FileSaveType.TXT,AppConstant.FileSaveType.DES_FOR_TXT));
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_log_file_cs;
    }

    @Override
    protected LogFileAtPresenter createPresenter() {
        return new LogFileAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText(R.string.title_insert_file_name);
        mLlSave.setVisibility(View.VISIBLE);
        setupDropView();
    }

    private void setupDropView() {
        collapsedView = LayoutInflater.from(this).inflate(R.layout.drop_log_file_head, null, false);
        expandedView = LayoutInflater.from(this).inflate(R.layout.drop_body_rv, null, false);
        mTvFileName = collapsedView.findViewById(R.id.tvFileName);
        mArrowImage = collapsedView.findViewById(R.id.arrowImage);
        mLlFileType = collapsedView.findViewById(R.id.llFileType);
        mTvFileType = collapsedView.findViewById(R.id.tvFileType);
        mRecyclerView = expandedView.findViewById(R.id.recyclerView);
        setAdapter();
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.setBackgroundColor(ResHelper.getColor(R.color.white));
        mDropDownView.setHeaderView(collapsedView);
        mDropDownView.setExpandedView(expandedView,frameLayout);
        mDropDownView.setDropDownListener(dropDownListener);
        if (!TextUtils.isEmpty(mFileName)){
            mTvFileName.setText(mFileName);
        }
        mTvFileName.setOnClickListener(v -> {
            if (mDropDownView.isExpanded()){
                mDropDownView.collapseDropDown();
            }
            Bundle bundle = new Bundle();
            bundle.putString(AppConstant.CONTENT_FORM_FILE_LOG, mTvFileName.getText().toString().trim());
            jumpToActivityForResult(EditInfoActivity.class, bundle, AppConstant.RECODE_EDIT_FROM_LOG_FILE);
        });

    }

    private void setAdapter() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<LogFileBean, BaseViewHolder>(R.layout.list_item_stand_drop_down,mLogFileBeanList) {
            @Override
            protected void convert(BaseViewHolder helper, LogFileBean item) {
                helper.itemView.setBackground (ResHelper.getDrawable(R.drawable.selector_item_select_state));
                helper.itemView.setSelected(helper.getAdapterPosition() == selectPosition);
                helper.setText(R.id.tvTitle,item.getFileType());
                helper.setText(R.id.tvStatus,item.getDes());
            }
        };

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (position != selectPosition) {
                prePosition = selectPosition;
                selectPosition = position;
                mAdapter.notifyItemChanged(prePosition);
                mAdapter.notifyItemChanged(selectPosition);
                mTvFileType.setText(mLogFileBeanList.get(selectPosition).getFileType());
            }
            mDropDownView.collapseDropDown();
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mLlSave.setOnClickListener(v -> {
            if (mDropDownView.isExpanded()) {
                mDropDownView.collapseDropDown();
            }
            LogFileCsActivity.this.saveFile();
        });
    }

    private void saveFile() {
        mFileName = mTvFileName.getText().toString().trim();
        mFileType = mTvFileType.getText().toString().trim();
        /* 遍历文件夹检查是否存在该文件 ============= */
        // 判断SDK是否存在-----
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (!sdCardExist) {
            UIUtils.showToast("保存失败,不存在SD卡!");
            return;
        }
        sdDir = Environment.getExternalStorageDirectory();
        // 保存在 appcloud路径下了
        file = new File(sdDir.toString()
                + File.separator + AppConstant.PS_SAVE_DIR + File.separator + AppConstant.RECORD);
        makeDir(file);
        if (hasThisFile(mFileName+mFileType)){
            // 提示用户进行覆盖
            showMaterialDialog("保存文件", "同名文件已存在,确认覆盖?"
                    , getString(R.string.ensure), getString(R.string.cancel)
                    , (dialog, which) -> doExport(), (dialog, which) -> dialog.cancel());
        }else {
            doExport();
        }
    }

    /**
     *  判断手机SD卡是否存在该文件
     * */
    private boolean hasThisFile(String fileName) {
        boolean has = false;
        File[] listFiles = file.listFiles();
        for (int i=0;i<listFiles.length;i++){
            File file =  listFiles[i];
            if (fileName.equals(file.getName())){
                has = true;
                break;
            }
        }
        return has;
    }

    public  void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    private void doExport(){
        mPresenter.doExport(file.toString(),mFileName,mFileType,mMList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case AppConstant.RECODE_EDIT_FROM_LOG_FILE:
                    mTvFileName.setText(data.getStringExtra(AppConstant.RESULT_FROM_EDIT));
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void exportFileSuccess(String info) {
        UIUtils.showToast(info);
        jumpToActivity(LogDdListActivity.class);
        finish();
    }

    @Override
    public void exportFileFailure(String error) {
        UIUtils.showToast(error);
    }
}
