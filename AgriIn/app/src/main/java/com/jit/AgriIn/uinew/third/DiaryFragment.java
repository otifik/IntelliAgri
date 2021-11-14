package com.jit.AgriIn.uinew.third;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jit.AgriIn.R;
import com.jit.AgriIn.model.bean.MainBean;
import com.jit.AgriIn.ui.activity.expert.ExpertInfoActivity;
import com.zxl.baselib.ui.base.BaseFragment;
import com.zxl.baselib.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by yuanyuan on 2019/3/11.
 */

public class DiaryFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ivSquare)
    ImageView ivSquare;

    private BaseQuickAdapter<MainBean,BaseViewHolder> mAdapter;
    private List<MainBean> mMainBeanList = new ArrayList<>();

    @Override
    public void init() {
//        mMainBeanList.add(new MainBean(R.mipmap.diary_diary,"设置"));
        mMainBeanList.add(new MainBean(R.mipmap.diary_expert,"手动巡航"));
        mMainBeanList.add(new MainBean(R.mipmap.diary_knowledge,"自动巡航"));
//        mMainBeanList.add(new MainBean(R.mipmap.diary_pond,"视频监控"));
//        mMainBeanList.add(new MainBean(R.mipmap.diary_robot,"联系专家"));
//        mMainBeanList.add(new MainBean(R.mipmap.diary_robotservice,"数据报告"));
    }

    public static DiaryFragment newInstance() {
        
        Bundle args = new Bundle();
        
        DiaryFragment fragment = new DiaryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_diary;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void initView(View rootView) {
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        initAdapter();
    }

    private void initAdapter() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        if (mAdapter == null){
            mAdapter = new BaseQuickAdapter<MainBean, BaseViewHolder>(R.layout.item_main_log,mMainBeanList) {
                @Override
                protected void convert(BaseViewHolder helper, MainBean item) {
                    ImageView ivImage =  helper.getView(R.id.image);
                    Glide.with(getActivity()).load(item.getImageSource()).into(ivImage);
//                    helper.setText(R.id.text,item.getImageDes());
                }
            };
        }
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (position){
                case 0:
                    jumpToActivity(ExpertInfoActivity.class);
                    break;
                case 1:
                    jumpToActivity(ZhishiActivity.class);
                    break;
//                case 2:
//
//                    break;
//                case 3:
//                    jumpToActivity(PondMainActivity.class);
//                    break;
//                case 4:
//                    jumpToActivity(RobotManageActivity.class);
//                    break;
//                case 5:
//                    jumpToActivity(RepairListActivity.class);
//                default:
//                    break;
            }
        });


        ivSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpToActivity(SquareActivity.class);
            }
        });
    }
}
