package com.jit.AgriIn.uinew.second.input;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jit.AgriIn.R;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.InputResponse;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.luck.picture.lib.tools.ScreenUtils;
import com.zxl.baselib.bean.response.BaseResponse;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.util.NetworkHelper;
import com.zxl.baselib.util.ui.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class InputAddActivity extends BaseActivity<InputAddView,InputAddPresenter> implements InputAddView {
    @BindView(R.id.ivToolbarNavigation)
    ImageView mIvToolbarNavigation;
    @BindView(R.id.vToolbarDivision)
    View mVToolbarDivision;
    @BindView(R.id.tvToolbarTitle)
    TextView mTvToolbarTitle;
    @BindView(R.id.tv_publish_now)
    TextView mTvPublishNow;

    @BindView(R.id.inputType)
    TextView mInputType;
    @BindView(R.id.inputName)
    EditText mInputName;
    @BindView(R.id.manufacturer)
    EditText mManufacturer;
    @BindView(R.id.remarks)
    EditText mRemarks;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;

    //TODO:需要修复的BUG：选择完图片后，再次进入PictureSelector不会勾选已选图片（交给你了）
    private InputAddPictureAdapter mAdapter;

    private List<String> type = new ArrayList();

    List<LocalMedia> selectList = null;

    private final InputAddPictureAdapter.onAddPicClickListener onAddPicClickListener = new InputAddPictureAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            PictureSelector.create(InputAddActivity.this)
                    .openGallery(PictureMimeType.ofImage())
                    .maxSelectNum(3)
                    .isCamera(false)//TODO:后面加一个拍照功能
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };


    @Override
    public void addInputSuccess(BaseResponse baseResponse) {
        if (baseResponse != null) {
            mRxManager.post(AppConstant.RX_ADD_INPUT, baseResponse);
            UIUtils.showToast("添加成功");
            Log.e("test",baseResponse.toString());
        } else {
            UIUtils.showToast("请勿重复添加");
        }

        finish();
    }

    @Override
    public void addInputFailure(String error) {
        UIUtils.showToast(error);
    }

    @Override
    protected void init() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_input_add;
    }

    @Override
    protected InputAddPresenter createPresenter() {
        return new InputAddPresenter(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mTvToolbarTitle.setText("添加投入品");
        mTvPublishNow.setVisibility(View.VISIBLE);
        mTvPublishNow.setText(R.string.submit);

        mInputType.setOnClickListener(v ->
            new MaterialDialog.Builder(this)
                    .title("投入品类型")
                    .items(type)
                    .itemsCallback(new MaterialDialog.ListCallback() {
                        @Override
                        public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                            mInputType.setText(text);
                        }
                    })
                    .positiveText(android.R.string.cancel)
                    .show()
        );

        FullyGridLayoutManager manager = new FullyGridLayoutManager(this,
                4, GridLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(manager);
        mRecycler.addItemDecoration(new GridSpacingItemDecoration(4,
                ScreenUtils.dip2px(this, 8), false));
        mAdapter = new InputAddPictureAdapter(this,onAddPicClickListener);
        if (savedInstanceState != null && savedInstanceState.getParcelableArrayList("selectorList") != null) {
            mAdapter.setList(savedInstanceState.getParcelableArrayList("selectorList"));
        }
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((v,position) -> {
            List<LocalMedia> selectList =  mAdapter.getData();
            PictureSelector.create(InputAddActivity.this)
                    .themeStyle(R.style.picture_default_style) // xml设置主题
                    .openExternalPreview(position, selectList);
        });
    }

    @Override
    protected void initData() {
        type.clear();
        type.add("饲料");
        type.add("调水剂");
    }

    @Override
    protected void initListener() {
        mIvToolbarNavigation.setOnClickListener(v -> onBackPressed());

        mTvPublishNow.setOnClickListener(v -> requestSubmit());
    }

    private void requestSubmit() {
        checkFormatData();
    }

    private void checkFormatData(){
        String inputType = mInputType.getText().toString();
        if(TextUtils.isEmpty(inputType)){
            UIUtils.showToast("请选择投入品类型");
            return;
        }

        String inputName = mInputName.getText().toString();
        if(TextUtils.isEmpty(inputName)){
            UIUtils.showToast("请输入投入品名称");
            return;
        }

        String manufacturer = mManufacturer.getText().toString();
        if(TextUtils.isEmpty(manufacturer)){
            UIUtils.showToast("请输入生产商");
        }

        if (selectList == null){
            UIUtils.showToast("至少上传一张图片");
            return;
        }

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(LocalMedia lm: selectList){
            File file = new File(lm.getPath());
            Log.d("test",lm.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/x-markdown;charset=utf-8"), file);
            builder.addFormDataPart("pictures",file.getName(),requestBody);
        }

        List<MultipartBody.Part> parts = builder.build().parts();


        String remarks = mRemarks.getText().toString();

        if(!NetworkHelper.isNetworkAvailable(this)){
            UIUtils.showToast(getString(R.string.no_net));
            return;
        }else {
            doInputAddCommit(inputType,inputName,manufacturer,parts,remarks);
        }
    }

    private void doInputAddCommit(String inputType, String inputName, String manufacturer, List<MultipartBody.Part> pictures, String remarks){
        mPresenter.addInput(inputType,inputName,manufacturer,pictures,remarks);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //图片选择回调
        if(requestCode == PictureConfig.CHOOSE_REQUEST && resultCode == RESULT_OK){
            selectList = PictureSelector.obtainMultipleResult(data);
            mAdapter.setList(selectList);
            mAdapter.notifyDataSetChanged();
            Log.e("test",selectList.get(0).getPath());
        }
    }
}