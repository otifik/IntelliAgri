package com.jit.AgriIn.ui.activity.culture;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.DownLogResponse;
import com.jit.AgriIn.ui.presenter.culture.LogDdListAtPresenter;
import com.jit.AgriIn.ui.view.culture.LogDdListAtView;
import com.luck.picture.lib.decoration.RecycleViewDivider;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.util.window.DisplayHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author crazyZhangxl on 2018-10-11 14:32:19.
 * Describe:
 */

public class LogDdListActivity extends BaseActivity<LogDdListAtView,LogDdListAtPresenter> implements LogDdListAtView {

    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.llToolbarTitle)
    LinearLayout mLlToolbarTitle;
    @BindView(R.id.llDownload)
    LinearLayout mLlDownload;
    @BindView(R.id.ivDnLog)
    ImageView mIvDnLog;
    @BindView(R.id.flToolBar)
    FrameLayout mFlToolBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rcDdLog)
    RecyclerView mRcDdLog;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;
    @BindView(R.id.llLogAll)
    LinearLayout mLlLogAll;
    @BindView(R.id.llSave)
    LinearLayout mLlSave;
    @BindView(R.id.viewEmpty)
    View mViewEmpty;
    @BindView(R.id.cbAll)
    CheckBox mCbAll;
    @BindView(R.id.btnDel)
    Button mBtnDel;
    @BindView(R.id.rlBottom)
    RelativeLayout mRlBottom;
    private boolean isCanEditor = false;
    private BaseQuickAdapter<DownLogResponse , BaseViewHolder> mAdapter;
    private List<DownLogResponse> mList = new ArrayList<>();

    @Override
    protected void init() {
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_log_dd_list;
    }

    @Override
    protected LogDdListAtPresenter createPresenter() {
        return new LogDdListAtPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("下载记录");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText("编辑");
        setAdapter();
    }

    private void setAdapter() {
        mRcDdLog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRcDdLog.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, DisplayHelper.dp2px(this, 0.5f),
                ContextCompat.getColor(this, R.color.bg_line_2)));
        mAdapter = new BaseQuickAdapter<DownLogResponse , BaseViewHolder>(R.layout.item_log_file_list, mList) {
            @Override
            protected void convert(BaseViewHolder helper, DownLogResponse  item) {
                helper.setText(R.id.tvFileName,item.getDownlogname());
                helper.setText(R.id.tvFileTime,item.getDownlogtime());
                View view = helper.getView(R.id.rlDel);
                CheckBox cb = helper.getView(R.id.checkbox);
                if (isCanEditor) {
                    view.setVisibility(View.VISIBLE);
                    cb.setChecked(item.isChecked());
                    /* 通过cb的改变来设置值 */
                    cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        mList.get(helper.getAdapterPosition()).setChecked(isChecked);
                        checkAndSetEnable();
                        checkAllCheckState();
                    });
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        };
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            if (isCanEditor) {
                CheckBox cb = view.findViewById(R.id.checkbox);
                cb.setChecked(!cb.isChecked());
            } else {
                checkFileStateAndShow(mList.get(position).getDownlogname());
            }
        });
        mRcDdLog.setAdapter(mAdapter);
    }

    /* =============== 检查是否含有该日志,并打开管理器进行查看===================*/
    private void checkFileStateAndShow(String logName) {
        // check
        defExists(logName);
        // show
    }

    @Override
    protected void initData() {
        mPresenter.queryAllLog();
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> {
            if (mList.size() == 0){
                UIUtils.showToast("无日志可编辑!");
                return;
            }

            if (!isCanEditor) {
                mTvPublishNow.setText("取消");
                mViewEmpty.setVisibility(View.VISIBLE);
                mRlBottom.setVisibility(View.VISIBLE);
            } else {
                mTvPublishNow.setText("编辑");
                mViewEmpty.setVisibility(View.GONE);
                mRlBottom.setVisibility(View.GONE);
                /* 全部设置为不可选*/
                setNoneChoose();
                mCbAll.setChecked(false);
            }
            isCanEditor = !isCanEditor;
            mAdapter.notifyDataSetChanged();
        });


        mCbAll.setOnClickListener(v -> {
            boolean isChecked = mCbAll.isChecked();
            if (isChecked){
                setAllChoose();
                mAdapter.notifyDataSetChanged();
            }else {
                setNoneChoose();
                mAdapter.notifyDataSetChanged();
            }
        });

        /* 确实是是删除的问题*/
        mBtnDel.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            for (int i=0;i<mList.size();i++){
                if (mList.get(i).isChecked()) {
                    sb.append(String.valueOf(mList.get(i).getId())).append("-");
                }
            }
            mPresenter.deleleteSmLogs(sb.substring(0,sb.lastIndexOf("-")));
        });

    }

    private void checkEditorState(){
        if (mList.size() == 0){
            mTvPublishNow.setText("编辑");
            mViewEmpty.setVisibility(View.GONE);
            mRlBottom.setVisibility(View.GONE);
            isCanEditor = !isCanEditor;
        }
    }

    /* 设置全选 ---- */
    private void setAllChoose() {
        for (DownLogResponse bean : mList) {
            bean.setChecked(true);
        }
    }

    private void setNoneChoose() {
        for (DownLogResponse bean : mList) {
            bean.setChecked(false);
        }
    }

    private void checkAndSetEnable(){
        boolean isEnable = false;
        for (DownLogResponse bean : mList){
            if (bean.isChecked()){
                isEnable = true;
                break;
            }
        }
        mBtnDel.setEnabled(isEnable);
    }

    private void checkAllCheckState(){
        if (isAllCheck()){
            mCbAll.setChecked(true);
        }else {
            mCbAll.setChecked(false);
        }

    }

    private boolean isAllCheck(){
        boolean isAllChecked = true;
        for (DownLogResponse fileHyBean :mList){
            if (!fileHyBean.isChecked()){
                isAllChecked = false;
                break;
            }
        }
        return isAllChecked;
    }


    private void defExists(String fileName){

        File file = new File(Environment.getExternalStorageDirectory().toString()
                + File.separator + AppConstant.PS_SAVE_DIR + File.separator + AppConstant.RECORD
                +File.separator + fileName);
        if (file.exists()){
            openFileByPath(this,file.getPath());
        }else {
            com.zxl.baselib.util.ui.UIUtils.showToast("本地不存在该文件或者已被删除了!");
        }
    }

    /**
     * 根据路径打开文件
     * @param context 上下文
     * @param path 文件路径
     */
    public void openFileByPath(Context context, String path) {
        if(context==null||path==null) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        try {
            //设置intent的data和Type属性
            intent.setDataAndType(Uri.fromFile(new File(path)), "text/html");
            //跳转
            context.startActivity(intent);
        } catch (Exception e) { //当系统没有携带文件打开软件，提示
            Toast.makeText(context, "无法打开该格式文件!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void queryDdListSuccess(List<DownLogResponse> downLogResponseList) {
        mList.clear();
        mList.addAll(downLogResponseList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void queryDdListFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void delDdListSuccess() {
        // 成功再删除撒
        for (int i=0;i<mList.size();i++){
            if (mList.get(i).isChecked()) {
                mList.remove(i);
                i--;
            }
        }
        mBtnDel.setEnabled(false);
        mCbAll.setChecked(false);
        checkEditorState();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void delDdListFailure(String error) {
        UIUtils.showToast(error);
    }
}
