package com.jit.AgriIn.ui.presenter.culture;

import android.annotation.SuppressLint;

import com.jit.AgriIn.api.ApiRetrofit;
import com.jit.AgriIn.commom.AppConstant;
import com.jit.AgriIn.model.response.CultureLogResponse;
import com.jit.AgriIn.ui.view.culture.LogFileAtView;
import com.jit.AgriIn.util.ExcelUtils;
import com.zxl.baselib.ui.base.BaseActivity;
import com.zxl.baselib.ui.base.BasePresenter;
import com.zxl.baselib.util.time.TimeUtil;
import com.zxl.baselib.util.ui.UIUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author crazyZhangxl on 2018/10/11.
 * Describe:
 */
public class LogFileAtPresenter extends BasePresenter<LogFileAtView> {
    private String mFileSavePath;
    private String mFileName;
    private String mFileType;
    private List<CultureLogResponse> mMList = new ArrayList<>();
    private static String[] title = { "日期","天气","1","2","3","4","5","6",
            "合计","PH","溶解氧","温度","用药情况","备注"};
    private ArrayList<ArrayList<String>> recordList;

    public LogFileAtPresenter(BaseActivity context) {
        super(context);
    }
    @SuppressLint("CheckResult")
    public void doExport(String filePath, String fileName, String fileType, List<CultureLogResponse> mList) {
        getView().showLoadingDialog();
        mFileName = fileName;
        mFileType = fileType;
        mFileSavePath = filePath + File.separator+fileName+fileType;
        mMList = mList;
        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
            if (AppConstant.FileSaveType.XLS.equals(fileType)) {
                exportExcel(emitter);
            } else if (AppConstant.FileSaveType.CSV.equals(fileType)) {
                exportCvs(emitter);
            } else if (AppConstant.FileSaveType.TXT.equals(fileType)) {
                exportTxt(emitter);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(aBoolean -> {
                    if (aBoolean){
                        return ApiRetrofit.getInstance().addDownloadLog(mFileName+mFileType);
                    }
                    return null;
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    getView().hideLoadingDialog();
                    if (response != null && response.getCode() == AppConstant.REQUEST_SUCCESS){
                        getView().exportFileSuccess(String.format("文件已存储在/SD卡/%s/%s/%s%s"
                                , AppConstant.PS_SAVE_DIR, AppConstant.RECORD, mFileName, mFileType));
                    }else {
                        getView().exportFileFailure(response.getMsg());
                    }
                }, throwable -> {
                    getView().hideLoadingDialog();
                    getView().exportFileFailure(throwable.getLocalizedMessage());
                });
    }

    private void exportTxt(ObservableEmitter<Boolean> emitter) {
        StringBuilder sb = new StringBuilder();
        for (String aTitle : title) {
            sb.append(aTitle).append("\t");
        }
        sb.append("\n");
        for (CultureLogResponse bean:mMList){
            sb.append(TimeUtil.getMyTimeDay(bean.getDate())).append("\t")
                    .append(bean.getWeather()).append("\t")
                    .append(String.valueOf(bean.getCount1())).append("\t")
                    .append(String.valueOf(bean.getCount2())).append("\t")
                    .append(String.valueOf(bean.getCount3())).append("\t")
                    .append(String.valueOf(bean.getCount4())).append("\t")
                    .append(String.valueOf(bean.getCount5())).append("\t")
                    .append(String.valueOf(bean.getCount6())).append("\t")
                    .append(String.valueOf(bean.getCount_total())).append("\t")
                    .append(String.valueOf(bean.getPh())).append("\t")
                    .append(String.valueOf(bean.getO2())).append("\t")
                    .append(String.valueOf(bean.getTemperature())).append("\t")
                    .append(bean.getNano2()).append("\t")
                    .append(bean.getAlkali()).append("\t")
                    .append(bean.getMedicine()).append("\t")
                    .append(bean.getRemark()).append("\t")
                    .append("\n");
        }
        File saveCSV = new File(mFileSavePath);
        try {
            if(!saveCSV.exists()) {
                boolean createOk = saveCSV.createNewFile();
                if (!createOk){
                    UIUtils.showToast("创建文件失败!");
                    return;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveCSV));
            writer.write(sb.toString());
            writer.close();
            emitter.onNext(true);
            emitter.onComplete();
        } catch (IOException e) {
            e.printStackTrace();
            emitter.onNext(false);
            emitter.onError(e);
        }
    }

    private void exportCvs(ObservableEmitter<Boolean> emitter) {
        StringBuilder sb = new StringBuilder();
        for (int i=0 ;i<title.length-1;i++) {
            sb.append(title[i]).append("\t").append(",");
        }
        sb.append(title[title.length-1]).append("\t").append("\n");
        for (CultureLogResponse bean:mMList){
            sb.append(TimeUtil.getMyTimeDay(bean.getDate())).append("\t").append(",")
                    .append(bean.getWeather()).append("\t").append(",")
                    .append(String.valueOf(bean.getCount1())).append("\t").append(",")
                    .append(String.valueOf(bean.getCount2())).append("\t").append(",")
                    .append(String.valueOf(bean.getCount3())).append("\t").append(",")
                    .append(String.valueOf(bean.getCount4())).append("\t").append(",")
                    .append(String.valueOf(bean.getCount5())).append("\t").append(",")
                    .append(String.valueOf(bean.getCount6())).append("\t").append(",")
                    .append(String.valueOf(bean.getCount_total())).append("\t").append(",")
                    .append(String.valueOf(bean.getPh())).append("\t").append(",")
                    .append(String.valueOf(bean.getO2())).append("\t").append(",")
                    .append(String.valueOf(bean.getTemperature())).append("\t").append(",")
                    .append(bean.getNano2()).append("\t").append(",")
                    .append(bean.getAlkali()).append("\t").append(",")
                    .append(bean.getMedicine()).append("\t").append(",")
                    .append(bean.getRemark()).append("\t")
                    .append("\n");
        }
        File saveCSV = new File(mFileSavePath);
        try {
            if(!saveCSV.exists()) {
                boolean createOk = saveCSV.createNewFile();
                if (!createOk){
                    UIUtils.showToast("创建文件失败!");
                    return;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(saveCSV));
            writer.write(sb.toString());
            writer.close();
            emitter.onNext(true);
            emitter.onComplete();
        } catch (IOException e) {
            e.printStackTrace();
            emitter.onNext(false);
            emitter.onError(e);
        }
    }

    private void exportExcel(ObservableEmitter<Boolean> emitter) {
        ExcelUtils.initExcel(mFileSavePath,title,mFileName,emitter);
        ExcelUtils.writeObjListToExcel(getRecordData(), mFileSavePath, mContext,emitter);
    }

    private  ArrayList<ArrayList<String>> getRecordData() {
        recordList = new ArrayList<>();
        for (int i = 0; i <mMList.size(); i++) {
            CultureLogResponse bean = mMList.get(i);
            ArrayList<String> beanList = new ArrayList<>();
            beanList.add(TimeUtil.getMyTimeDay(bean.getDate()));
            beanList.add(bean.getWeather());
            beanList.add(String.valueOf(bean.getCount1()));
            beanList.add(String.valueOf(bean.getCount2()));
            beanList.add(String.valueOf(bean.getCount3()));
            beanList.add(String.valueOf(bean.getCount4()));
            beanList.add(String.valueOf(bean.getCount5()));
            beanList.add(String.valueOf(bean.getCount6()));
            beanList.add(String.valueOf(bean.getCount_total()));
            beanList.add(String.valueOf(bean.getPh()));
            beanList.add(String.valueOf(bean.getO2()));
            beanList.add(String.valueOf(bean.getTemperature()));
            beanList.add(bean.getMedicine());
            beanList.add(bean.getRemark());
            recordList.add(beanList);
        }
        return recordList;
    }
}
