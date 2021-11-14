package com.jit.AgriIn.uinew.first;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.xrecyclerview.XRecyclerView;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.response.OrgResponse;
import com.jit.AgriIn.model.response.RealResponse;
import com.jit.AgriIn.uinew.first.data.query.RealDataPresenter;
import com.jit.AgriIn.uinew.first.data.query.RealDataView;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.util.app.SharePreferenceUtils;
import com.zxl.baselib.util.ui.UIUtils;
import com.zxl.baselib.widget.CustomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yuanyuan on 2019/3/11.
 */

public class RealDataFragment extends BaseFragment<RealDataView, RealDataPresenter> implements RealDataView {
    @BindView(R.id.rvPond)
    XRecyclerView rvReal;

    @BindView(R.id.stvName)
    TextView stvTypeName;
    @BindView(R.id.typecCard)
    CardView typeCard;

    private String mtypeName = "";
    private View mSenOrCtrlView;
    private CustomDialog mSenOrCtrlDialog;

    int mCellId;

    private BaseQuickAdapter<RealResponse, BaseViewHolder> mAdapter;
    private List<RealResponse> mRealResponsee = new ArrayList<>();

    private List<OrgResponse> mOrgResponsee = new ArrayList<>();

    @Override
    public void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_hand_cruise;
    }

    @Override
    protected RealDataPresenter createPresenter() {
        return new RealDataPresenter((BaseActivity) getActivity());
    }

    @Override
    public void initView(View rootView) {
        mCellId = SharePreferenceUtils.getInstance(getActivity()).getInt("cellId",-100);
        mPresenter.getOrgData(mCellId);

        initAdapter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    private void initAdapter() {
        rvReal.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mAdapter = new BaseQuickAdapter<RealResponse, BaseViewHolder>(R.layout.item_realdata,mRealResponsee
        ) {
            @Override
            protected void convert(BaseViewHolder helper, RealResponse item) {
                SuperTextView stvName = helper.getView(R.id.stvName);

                for (int i=0;i<mOrgResponsee.size();i++){
                    if (mOrgResponsee.get(i).getSnpid() == item.getSnpid()){
                        stvName.setLeftString(mOrgResponsee.get(i).getTypeCHN());
                        stvName.setRightString(item.getValue() + mOrgResponsee.get(i).getSuffix());
                        helper.setText(R.id.tvType,"(" + mOrgResponsee.get(i).getType() +")");
                    }
                }





            }
        };


        rvReal.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //设置的刷新
//                mPage = 1;
                mPresenter.getOrgData(mCellId);
            }

            @Override
            public void onLoadMore() {
                rvReal.noMoreLoading();
            }
        });



        rvReal.setAdapter(mAdapter);
        // 设置空布局
        View emptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_pond_main, null, false);
        TextView tvEmptyDes =  emptyView.findViewById(R.id.tvEmptyDes);
        tvEmptyDes.setText("尚未配置传感器");
        mAdapter.setEmptyView(emptyView);

    }

    @Override
    public void getRealDataSuccess(List<RealResponse> realResponseList) {
        if (realResponseList == null){
            UIUtils.showToast("设备不在线");
        }else {
            mRealResponsee.clear();
            mRealResponsee.addAll(realResponseList);

            mAdapter.notifyDataSetChanged();
        }

        // 刷新成功
        rvReal.refreshComplete();
    }

    @Override
    public void getRealDataFailure(String error) {
        UIUtils.showToast(error);
        rvReal.refreshComplete();
    }

    @Override
    public void getOrgDataSuccess(List<OrgResponse> orgResponseList) {
        mOrgResponsee.clear();
        mOrgResponsee.addAll(orgResponseList);
        mPresenter.getRealData(mCellId);

        mtypeName = "";
        for (int i =0;i<mOrgResponsee.size();i++){
            mtypeName = mtypeName + mOrgResponsee.get(i).getTypeCHN() +"(" +  mOrgResponsee.get(i).getSuffix()    + ")    ";
        }

        stvTypeName.setText(mtypeName);
    }

    @Override
    public void getOrgDataFailure(String error) {
        UIUtils.showToast(error);
    }


}
