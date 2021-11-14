package com.jit.AgriIn.uinew.role_admin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.bean.TermUpdateBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.response.TermResponse;
import com.jit.AgriIn.model.response.TypeTermResponse;
import com.jit.AgriIn.ui.activity.user.ChangePwdActivity;
import com.jit.AgriIn.ui.activity.user.LoginActivity;
import com.zxl.baselib.baseapp.AppManager;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RoleAdminActivity extends BaseActivity<TermView,TermPresenter> implements TermView{
    @BindView(R.id.ivToolbarNavigation)
    ImageView ivToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View vToolbarDivision;
    @BindView(R.id.ibAddMenu)
    ImageButton ibAddMenu;
    @BindView(R.id.ibMoreMenu)
    ImageButton ibMoreMenu;
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;
    @BindView(R.id.rvPond)
    XRecyclerView rvPond;

    private View mExitView;
    private CustomDialog mExitDialog;

    private View mSenOrCtrlView;
    private CustomDialog mSenOrCtrlDialog;

//    private View mAddView;
//    private CustomDialog mAddDialog;

    int mPosition;
    private boolean mIsFirst = true;

    /**
     * 用于保存当前获取页
     */
    private int mPage;

    private BaseQuickAdapter<TermResponse, BaseViewHolder> mPondMainAdapter;
    private List<TermResponse> mTermResponse = new ArrayList<>();
    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_guding;
    }

    @Override
    protected TermPresenter createPresenter() {
        return new TermPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        ivToolbarNavigation.setVisibility(View.GONE);
        ibAddMenu.setVisibility(View.VISIBLE);
        ibMoreMenu.setVisibility(View.VISIBLE);
        tvToolbarTitle.setText("管理员配置");
        setAdapter();
    }

    private void setAdapter() {
        rvPond.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mPondMainAdapter = new BaseQuickAdapter<TermResponse, BaseViewHolder>(R.layout.item_term,mTermResponse
        ) {
            @Override
            protected void convert(BaseViewHolder helper, TermResponse item) {

                if (item.getDeveui() != null){
                    helper.setText(R.id.tvDeveui,item.getDeveui());
                }

                if (item.getName() != null){
                    helper.setText(R.id.tvName,item.getName());
                }

                if (item.getProduct() != null){
                    helper.setText(R.id.tvPro,item.getProduct());
                }

                helper.setText(R.id.tvStatus,String.valueOf(item.getStatus()));

                helper.setText(R.id.tvType,String.valueOf(item.getType()));

                if (item.getUsername() != null){
                    helper.setText(R.id.tvUserName,item.getUsername());
                }

            }
        };


        rvPond.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
                mPage = 1;
                mPresenter.getAllTerms(mPage);
            }

            @Override
            public void onLoadMore() {
                // 加载更多
                mPage++;
                mPresenter.getAllTerms(mPage);
            }
        });



        rvPond.setAdapter(mPondMainAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(this).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("请添加终端配置");
        mPondMainAdapter.setEmptyView(emptyView);

    }





    @Override
    protected void initData() {
        mPage = 1;
        mPresenter.getAllTerms(mPage);

        // 监听增加塘口信息
        mRxManager.on(AppConstant.RX_ADD_POND, (Consumer<TermResponse>) pondMainResponse -> {
            mTermResponse.add(pondMainResponse);
            mPondMainAdapter.notifyDataSetChanged();
        });

        mRxManager.on(AppConstant.RX_UPDATE_POND, (Consumer<TermUpdateBean>) gudingUpdateBean -> {
            mTermResponse.set(gudingUpdateBean.getItemPosition()-1,gudingUpdateBean.getTermResponse());
            mPondMainAdapter.notifyItemChanged(gudingUpdateBean.getItemPosition());
        });
    }

    @Override
    protected void initListener() {
        ibAddMenu.setOnClickListener(view -> jumpToActivity(TermAddActivity.class));

        ibMoreMenu.setOnClickListener(view -> {
            if (mExitView == null){
                mExitView = View.inflate(mContext,R.layout.dialog_exit_expert,null);
                mExitDialog = new CustomDialog(mContext,mExitView,R.style.MyDialog);
                mExitView.findViewById(R.id.tvExitAccount).setOnClickListener(v1 -> {
                    mExitDialog.dismiss();
                    doLogout();
                });

                mExitView.findViewById(R.id.tvExitApp).setOnClickListener(v12 -> {
                    mExitDialog.dismiss();
                    AppManager.getAppManager().finishAllActivity();
                });

                mExitView.findViewById(R.id.tvChangePwd).setOnClickListener(v13 -> {
                    mExitDialog.dismiss();
                    jumpToActivity(ChangePwdActivity.class);
                });
            }
            mExitDialog.show();
        });

        /**
         * 条目点击事件
         */
        mPondMainAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mPosition = position;
                TermResponse termResponse = mTermResponse.get(position - 1);

                if (termResponse.getType() == 1){
                    if (mSenOrCtrlView == null){
                        mSenOrCtrlView = View.inflate(mContext,R.layout.dialog_senorctrl_choose,null);
                        mSenOrCtrlDialog = new CustomDialog(mContext,mSenOrCtrlView,R.style.MyDialog);
                        mSenOrCtrlView.findViewById(R.id.tvSen).setOnClickListener(v1 -> {
                            mSenOrCtrlDialog.dismiss();
                            jumpToPondShow(mPosition - 1);
                        });

                        mSenOrCtrlView.findViewById(R.id.tvCtrl).setOnClickListener(v12 -> {
                            mSenOrCtrlDialog.dismiss();
                            jumpToCtrlShow(mPosition - 1);
                        });
                    }
                    mSenOrCtrlDialog.show();
                }else {
                    jumpToPondShow(position - 1);
                }


            }
        });

        /**
         * 条目长按事件
         */
        mPondMainAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            mPosition = position-1;
            showChooseView(position-1);
            return false;
        });


    }

    private void showChooseView(int itemPosition) {
        Dialog dialog = new Dialog(this,R.style.MyDialog);
        View choose = LayoutInflater.from(this).inflate(R.layout.dialog_choose, null);
        dialog.setCancelable(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.AnimBottom);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.setContentView(choose);
        setViewAndListener(dialog,choose,itemPosition);
        dialog.show();

    }


    /**
     * 设置弹出框的点击事件等
     * @param dialog
     * @param choose
     * @param itemPosition
     */
    private void setViewAndListener(Dialog dialog, View choose, int itemPosition) {
        View vEdit =  choose.findViewById(R.id.tvChooseEdit);
        View vCancel =  choose.findViewById(R.id.tvChooseCancel);
        View vDelete = choose.findViewById(R.id.tvChooseDel);
        vEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                jumpToPondEdit(itemPosition);
            }
        });

        vCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        vDelete.setOnClickListener(view -> {
            dialog.dismiss();
            mPresenter.deleteTerm(String.valueOf(mTermResponse.get(itemPosition).getId()));
        });
    }


    private void jumpToCtrlShow(int itemPosition){
        TermResponse pondMainResponse = mTermResponse.get(itemPosition);
        Log.e("--brfore--","--brfore--" + String.valueOf(pondMainResponse.getId())  + "--itemPosition--" + itemPosition);
        Intent intent = new Intent(this, EquipListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    private void jumpToPondShow(int itemPosition){
        TermResponse pondMainResponse = mTermResponse.get(itemPosition);
        Intent intent = new Intent(this, SenorListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }

    public void jumpToPondEdit(int itemPosition){
        TermResponse pondMainResponse = mTermResponse.get(itemPosition);
        Intent intent = new Intent(this, TermUpdateActivity.class);
        intent.putExtra(AppConstant.EXTRA_ITEM_INDEX,itemPosition+1);
        intent.putExtra(AppConstant.EXTRA_IS_FROM_POND_MAIN,true);
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.BUNDLE_POND_BEAN,pondMainResponse);
        intent.putExtras(bundle);
        jumpToActivity(intent);
    }













    @SuppressLint("CheckResult")
    private void doLogout() {
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            UserCache.clear();
            emitter.onNext(true);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                    if (aBoolean){
                        AppManager.getAppManager().finishAllActivity();
                        jumpToActivityAndClearTask(LoginActivity.class);
                    }
                });
    }

    @Override
    public void getTermSuccess(TypeTermResponse typeTermResponse) {
        Log.e("rizhi","获取日志成功");

        if (mIsFirst){
            if (typeTermResponse != null){
                mTermResponse.clear();
                mTermResponse.addAll(typeTermResponse.getList());
                mPondMainAdapter.notifyDataSetChanged();
            }
            mIsFirst = false;
        }else {
            if (mPage == 1){
                if (typeTermResponse != null){
                    mTermResponse.clear();
                    mTermResponse.addAll(typeTermResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    // 刷新成功
                    rvPond.refreshComplete();
                }

            }else {
                if (typeTermResponse !=null && typeTermResponse.getList() != null && typeTermResponse.getList().size() >0){
                    mTermResponse.addAll(typeTermResponse.getList());
                    mPondMainAdapter.notifyDataSetChanged();
                    rvPond.refreshComplete();
                }else {
                    rvPond.noMoreLoading();
                }
            }
        }
    }

    @Override
    public void getTermFailure(String error) {
        UIUtils.showToast(error);
        rvPond.refreshComplete();
        if (mIsFirst){
            mIsFirst = false;
        }

        if (mPage > 1) {
            mPage--;
        }
    }

    @Override
    public void deleteTermSuccess() {
        remove(mPosition);
    }

    @Override
    public void deleteTermFailure(String error) {
        UIUtils.showToast(error);
    }

    public void remove(int position) {
        mTermResponse.remove(position);
        mPondMainAdapter.notifyItemRemoved(position+1);
        mPondMainAdapter.notifyItemRangeChanged(position+1,mTermResponse.size()-position);
    }
}
