package com.jit.AgriIn.uinew.fourth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.util.PictureSHelper;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.image.GlideLoaderUtils;
import com.zxl.baselib.util.ui.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author zxl on 2018/08/29.
 *         discription: 修改塘口配置
 */
public class SelfInfoActivity extends BaseActivity<SelfInfotView,SelfInfoPresenter> implements SelfInfotView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.img_avater)
    CircleImageView imgAvater;
    @BindView(R.id.etRealName)
    EditText etRealName;
    @BindView(R.id.etUserName)
    EditText etUserName;
    @BindView(R.id.etTel)
    EditText etTel;
    @BindView(R.id.etAddr)
    EditText etAddr;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etType)
    EditText etType;

    public List<LocalMedia> mSingleSelectList = new ArrayList<>();


    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_selfinfo;
    }

    @Override
    protected SelfInfoPresenter createPresenter() {
        return new SelfInfoPresenter(this);
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("修改资料");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText("保存");

        if (UserCache.getUserRole().contains("USER")){
            mPresenter.requestSelfInfo(UserCache.getUserUsername(), AppConstant.Role_User);
        }else if (UserCache.getUserRole().contains("EXPERT")){
            mPresenter.requestSelfInfo(UserCache.getUserUsername(), AppConstant.Role_Expert);
        }

    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressedSupport());

        mTvPublishNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });

        // 设置单张图片的点击事件
        imgAvater.setOnClickListener(view -> PictureSHelper.getInstance().
                chooseSinglePictureEvent(SelfInfoActivity.this, mSingleSelectList, PictureConfig.SINGLE));

    }

    private void checkData() {
        String reaName = etRealName.getText().toString();
        if (TextUtils.isEmpty(reaName)){
            UIUtils.showToast("请填写真实姓名");
            return;
        }

        String userName = etUserName.getText().toString();
        if (TextUtils.isEmpty(userName)){
            UIUtils.showToast("请填写用户名");
            return;
        }

        String tel = etTel.getText().toString();
        if (TextUtils.isEmpty(tel)){
            UIUtils.showToast("请填写手机号");
            return;
        }

        String addr = etAddr.getText().toString();
        if (TextUtils.isEmpty(addr)){
            UIUtils.showToast("请填写养殖地址");
            return;
        }

        String email = etEmail.getText().toString();
        if (TextUtils.isEmpty(email)){
            UIUtils.showToast("请填写邮箱");
            return;
        }

        String type = etType.getText().toString();
        if (TextUtils.isEmpty(type)){
            UIUtils.showToast("请填写养殖类型");
            return;
        }


        if (!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
        }else {
            if (UserCache.getUserRole().contains("USER")){
                mPresenter.updateUserInfo(UserCache.getUserUser_id(),reaName,userName,tel,addr,email,type,AppConstant.Role_User);
            }else if (UserCache.getUserRole().contains("EXPERT")){
                mPresenter.updateUserInfo(UserCache.getUserUser_id(),reaName,userName,tel,addr,email,type,AppConstant.Role_Expert);
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.SINGLE:
                    mSingleSelectList = PictureSelector.obtainMultipleResult(data);
                    // 执行P层的上传图片
                    mPresenter.doPostHeadImage(mSingleSelectList.get(0).getPath());
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void querySelfInfoSuccess(SelfInfoBean responses) {
        Gson gson = new Gson();
        String s = gson.toJson(responses);
        Log.e("个人信息","个人信息" + s);
        if (responses.getRealname() != null){
            if (!responses.getRealname().equals("string")){
                etRealName.setText(responses.getRealname());
            }
        }

        if (responses.getUsername() != null){
            if (!responses.getUsername().equals("string")){
                etUserName.setText(responses.getUsername());
            }
        }

        if (responses.getTel() != null){
            if (!responses.getTel().equals("string")){
                etTel.setText(responses.getTel());
            }
        }

        if (responses.getCompany() != null){
            if (!responses.getCompany().equals("string")){
                etAddr.setText(responses.getCompany());
            }
        }

        if (responses.getEmail() != null){
            if (!responses.getEmail().equals("string")){
                etEmail.setText(responses.getEmail());
            }
        }

        if (responses.getMajor() != null){
            if (!responses.getMajor().equals( "string")){
                etType.setText(responses.getMajor());
            }
        }

        GlideLoaderUtils.display(SelfInfoActivity.this, imgAvater, UserCache.getUserImage());
    }

    @Override
    public void querySelfInfoFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void updateInfoSuccess(BaseResponse response) {
        UIUtils.showToast("修改成功");
//        UserCache.setRealName(etRealName.getText().toString());
        finish();
    }

    @Override
    public void updateInfoFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    public void postHeadSuccess(String imagePath) {
        UIUtils.showToast("头像上传成功");
        UserCache.setUserImage(imagePath);
        GlideLoaderUtils.display(this, imgAvater, mSingleSelectList.get(0).getPath());
        mSingleSelectList.clear();
    }

    @Override
    public void postHeadFailure(String error) {
        UIUtils.showToast(error);
    }
}
