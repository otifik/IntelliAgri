package com.jit.AgriIn.api;

import com.google.gson.Gson;
import com.jit.AgriIn.model.bean.AnswerBean;
import com.jit.AgriIn.model.bean.QuestionBean;
import com.jit.AgriIn.model.bean.QuestionListBean;
import com.jit.AgriIn.model.cache.UserCache;
import com.jit.AgriIn.model.request.ConfigActionRequest;
import com.jit.AgriIn.model.request.DiseaseAddRequest;
import com.jit.AgriIn.model.request.InsertFeedRequest;
import com.jit.AgriIn.model.request.InsertWaterRequest;
import com.jit.AgriIn.model.request.PondAddRequest;
import com.jit.AgriIn.model.request.RegisterRequest;
import com.jit.AgriIn.model.request.RobotActionRequest;
import com.jit.AgriIn.model.response.AutoLogResponse;
import com.jit.AgriIn.model.response.AutoStatusResponse;
import com.jit.AgriIn.model.response.AutoTimeStatusResponse;
import com.jit.AgriIn.model.response.BaikeDiseaseBean;
import com.jit.AgriIn.model.response.BaikeDrugBean;
import com.jit.AgriIn.model.response.BaikeFeedBean;
import com.jit.AgriIn.model.response.BaikeProductBean;
import com.jit.AgriIn.model.response.BaikeSeedBean;
import com.jit.AgriIn.model.response.BaseFeedingFoodPageResponse;
import com.jit.AgriIn.model.response.BaseFeedingTemplatePageResponse;
import com.jit.AgriIn.model.response.BaseFishInputPageResponse;
import com.jit.AgriIn.model.response.BaseFishPondPageResponse;
import com.jit.AgriIn.model.response.BaseInputPageResponse;
import com.jit.AgriIn.model.response.BaseReagentInputPageResponse;
import com.jit.AgriIn.model.response.CellProTypeResponse;
import com.jit.AgriIn.model.response.CellResponse;
import com.jit.AgriIn.model.response.ConfigMainResponse;
import com.jit.AgriIn.model.response.CtrlResponse;
import com.jit.AgriIn.model.response.CultureLogResponse;
import com.jit.AgriIn.model.response.CustomerInfo;
import com.jit.AgriIn.model.response.DailyThrowResponse;
import com.jit.AgriIn.model.response.DownLogResponse;
import com.jit.AgriIn.model.response.EquipResponse;
import com.jit.AgriIn.model.response.EquipStatusResponse;
import com.jit.AgriIn.model.response.EquipType;
import com.jit.AgriIn.model.response.FeedingFoodResponse;
import com.jit.AgriIn.model.response.FeedingTemplateResponse;
import com.jit.AgriIn.model.response.FishInputResponse;
import com.jit.AgriIn.model.response.FishPondResponse;
import com.jit.AgriIn.model.response.HeadPostResponse;
import com.jit.AgriIn.model.response.HisResponse;
import com.jit.AgriIn.model.response.IncomeResponse;
import com.jit.AgriIn.model.response.InputResponse;
import com.jit.AgriIn.model.response.LoginResponse;
import com.jit.AgriIn.model.response.ManuInfo;
import com.jit.AgriIn.model.response.OrgResponse;
import com.jit.AgriIn.model.response.PageResponse;
import com.jit.AgriIn.model.response.PondMainResponse;
import com.jit.AgriIn.model.response.ReagentInputResponse;
import com.jit.AgriIn.model.response.RealResponse;
import com.jit.AgriIn.model.response.RepairStateResponse;
import com.jit.AgriIn.model.response.RizhiResponse;
import com.jit.AgriIn.model.response.RobotMainResponse;
import com.jit.AgriIn.model.response.RobotPageResponse;
import com.jit.AgriIn.model.response.RoleUserInfo;
import com.jit.AgriIn.model.response.SensorResponse;
import com.jit.AgriIn.model.response.TemplateResponse;
import com.jit.AgriIn.model.response.TermResponse;
import com.jit.AgriIn.model.response.TypeIncomeResponse;
import com.jit.AgriIn.model.response.TypeRizhiResponse;
import com.jit.AgriIn.model.response.TypeTemplateResponse;
import com.jit.AgriIn.model.response.TypeTermResponse;
import com.jit.AgriIn.model.response.TypeThrowResponse;
import com.jit.AgriIn.model.response.WarnLogResponse;
import com.jit.AgriIn.uinew.fourth.SelfInfoBean;
import com.jit.AgriIn.uinew.third.ZhishiBean;
import com.zxl.baselib.api.BaseApiRetrofit;
import com.zxl.baselib.bean.response.BaseListResponse;
import com.zxl.baselib.bean.response.BaseResponse;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * @author zxl on 2018/8/31.
 *         discription: ????????????
 */

public class ApiRetrofit extends BaseApiRetrofit {
    public MyApi mApi;
    private ApiRetrofit(){
        //???????????????????????????Retrofit??????????????????
        mApi = new Retrofit.Builder()
                .baseUrl(MyApi.BASE_URL)
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MyApi.class);
    }



    private static class  ApiRetrofitHolder{
        private static final ApiRetrofit M_INSTANCE =  new ApiRetrofit();
    }

    public static ApiRetrofit getInstance(){
        return ApiRetrofitHolder.M_INSTANCE;
    }


    // -----------------------------------------??????????????????

    public Observable<BaseResponse> requestRegister(RegisterRequest registerRequest){
        return mApi.requestRegister(getRequestBody(registerRequest));
    }

    public Observable<BaseResponse<LoginResponse>> requestLogin(String usr,String pwd){
        return mApi.requestLogin(usr,pwd);
    }


    public Observable<BaseResponse> changePwd(String oldPwd,String newPwd){
        return mApi.changePwd(UserCache.getUserToken(),oldPwd,newPwd);
    }

    public Observable<BaseResponse<HeadPostResponse>> postHeadImage(File file){
        return mApi.postHeadImage(UserCache.getUserToken(),getMultipartBody(file,"file"));
    }

    public Observable<BaseResponse<SelfInfoBean>> queryUserfInfo(String userName){
        return mApi.queryUserInfo(userName);
    }

    public Observable<BaseResponse> userCheck(String name){
        return mApi.userCheck(name);
    }

    public  Observable<BaseResponse> updateUserInfo(String customer, SelfInfoBean selfInfoBean){
        return mApi.updateUserInfo(UserCache.getUserToken(),customer,getRequestBody(selfInfoBean));
    }

    public Observable<BaseResponse<SelfInfoBean>> queryExpertfInfo(String userName){
        return mApi.queryExpertInfo(userName);
    }

    public  Observable<BaseResponse> updateExpertInfo(String customer, SelfInfoBean selfInfoBean){
        return mApi.updateExpertInfo(UserCache.getUserToken(),customer,getRequestBody(selfInfoBean));
    }

    public  Observable<BaseResponse> forgetPass(String tel, String pass,String pass1){
        return mApi.forgetPass(tel,pass,pass1);
    }


    // -----------------------------------------????????????????????????

    public Observable<BaseListResponse<PondMainResponse>> queryPondMainInfo(){
        return mApi.queryPondMainInfo(UserCache.getUserToken());
    }

    public  Observable<BaseResponse<PondMainResponse>> requestAddPond(PondAddRequest pondAddRequest){
        return mApi.requestAddPond(UserCache.getUserToken(),getRequestBody(pondAddRequest));
    }

    public Observable<BaseResponse> requestDelPond(int pondID){
        return mApi.requestDelPond(UserCache.getUserToken(),pondID);
    }

    public Observable<BaseResponse<PondMainResponse>> requestUpdatePond(int pondID,PondAddRequest pondAddRequest){
        return mApi.requestUpdatePond(UserCache.getUserToken(),pondID,getRequestBody(pondAddRequest));
    }

    // -----------------------------------------?????????????????????
    public Observable<BaseResponse<RobotMainResponse>> requestAddRobot(RobotActionRequest robotActionRequest){
        return mApi.requestAddRobot(UserCache.getUserToken(),getRequestBody(robotActionRequest));
    }

    public Observable<BaseListResponse<RobotMainResponse>> queryMyRobot(){
        return mApi.queryMyRobot(UserCache.getUserToken(),UserCache.getUserUser_id());
    }

    public Observable<BaseResponse<RobotPageResponse>> queryMyRobotWithPage(int pageNum){
        return mApi.queryMyRobotWithPage(UserCache.getUserToken(),UserCache.getUserUser_id(),pageNum);
    }

    public Observable<BaseResponse<RobotMainResponse>> updateRobot(int robotID,RobotActionRequest robotActionRequest){
        return mApi.updateRobot(UserCache.getUserToken(),robotID,getRequestBody(robotActionRequest));
    }

    public Observable<BaseResponse> deleteRobot(int robotID){
        return mApi.deleteRobot(UserCache.getUserToken(),robotID);
    }

    // -----------------------------------------???????????????????????????


    public Observable<BaseListResponse<ConfigMainResponse>> queryAllConfig(){
        return mApi.queryAllConfig(UserCache.getUserToken());
    }


    public Observable<BaseResponse<ConfigMainResponse>> requestAddConfig(ConfigActionRequest configActionRequest){
        return mApi.requestAddConfig(UserCache.getUserToken(),getRequestBody(configActionRequest));
    }

    public Observable<BaseResponse<ConfigMainResponse>> requestUpdateConfig(int configID,ConfigActionRequest configActionRequest){
        return mApi.requestUpdateConfig(UserCache.getUserToken(),configID,getRequestBody(configActionRequest));
    }


    public Observable<BaseResponse> requestDelConfig(int configID){
        return mApi.requestDelConfig(UserCache.getUserToken(),configID);
    }

    public Observable<BaseResponse> insertWater(InsertWaterRequest insertWaterRequest){
        return mApi.insertWater(UserCache.getUserToken(),
                insertWaterRequest.getPound_id(),
                insertWaterRequest.getDate(),
                insertWaterRequest.getWeather(),
                insertWaterRequest.getMedicine(),
                insertWaterRequest.getRemark(),
                insertWaterRequest.getTemperature(),
                insertWaterRequest.getO2(),
                insertWaterRequest.getPh());
    }

    public Observable<BaseResponse> insertFeed(InsertFeedRequest insertFeedRequest){
        return mApi.insertFeed(UserCache.getUserToken(),
                insertFeedRequest.getPound_id(),
                insertFeedRequest.getDate(),
                insertFeedRequest.getCount1(),
                insertFeedRequest.getCount2(),
                insertFeedRequest.getCount3(),
                insertFeedRequest.getCount4(),
                insertFeedRequest.getCount5(),
                insertFeedRequest.getCount6());
    }

    public Observable<BaseListResponse<CultureLogResponse>> queryDiaryLogInfo(int pondID,String start_date,String end_date){
        return mApi.queryDiaryLogInfo(UserCache.getUserToken(),pondID,start_date,end_date);
    }

    //--------------------------????????????
    public Observable<BaseResponse> addDownloadLog(String downloadName){
        return mApi.addDownloadLog(UserCache.getUserToken(),downloadName);
    }

    public Observable<BaseResponse> deleleSmLogs(String ids){
        return mApi.deleteSmLogs(UserCache.getUserToken(),ids);
    }

    public Observable<BaseListResponse<DownLogResponse>> getAllLogs(){
        return mApi.getaAllLogs(UserCache.getUserToken());
    }



    public Observable<BaseResponse<PageResponse<BaikeSeedBean>>> queryKindBaike(int pageNum){
        return mApi.queryKindBaike(pageNum);
    }

    public Observable<BaseResponse<BaikeSeedBean>> queryDetailBaikeInfo(int index){
        return mApi.queryDetailBaikeInfo(index);
    }


    public Observable<BaseResponse<PageResponse<BaikeProductBean>>> queryKindPinBaike(int pageNum){
        return mApi.queryPinBaike(pageNum);
    }

    public Observable<BaseResponse<BaikeProductBean>> queryDetailBaikePinInfo(int index){
        return mApi.queryDetailPinBaikeInfo(index);
    }


    public Observable<BaseResponse<PageResponse<BaikeFeedBean>>> queryKindWeiBaike(int pageNum){
        return mApi.queryWeiBaike(pageNum);
    }

    public Observable<BaseResponse<BaikeFeedBean>> queryDetailWeiBaikeInfo(int index){
        return mApi.queryDetailWeiBaikeInfo(index);
    }

    // ??????
    public Observable<BaseResponse<PageResponse<BaikeDrugBean>>> queryKindYaoBaike(int pageNum){
        return mApi.queryYaoBaike(pageNum);
    }

    public Observable<BaseResponse<BaikeDrugBean>> queryDetailYaoBaikeInfo(int index){
        return mApi.queryDetailYaoBaikeInfo(index);
    }

    // ??????
    public Observable<BaseResponse<PageResponse<BaikeDiseaseBean>>> queryKindBingBaike(int pageNum){
        return mApi.queryBingBaike(pageNum);
    }

    public Observable<BaseResponse<BaikeDiseaseBean>> queryDetailBingBaikeInfo(int index){
        return mApi.queryDetailBingBaikeInfo(index);
    }


    public Observable<BaseResponse> deleteDrugBaikeByID(int index){
        return mApi.deleteDrugBaikeByID(UserCache.getUserToken(),index);
    }

    public Observable<BaseResponse> deleteSeedBaikeByID(int index){
        return mApi.deleteSeedBaikeByID(UserCache.getUserToken(),index);
    }

    public Observable<BaseResponse> deleteFeedBaikeByID(int index){
        return mApi.deleteFeedBaikeByID(UserCache.getUserToken(),index);
    }

    public Observable<BaseResponse> deleteDiseaseBaikeByID(int index){
        return mApi.deleteDiseaseBaikeByID(UserCache.getUserToken(),index);
    }

    public Observable<BaseResponse> deleteProductBaikeByID(int index){
        return mApi.deleteProductBaikeByID(UserCache.getUserToken(),index);
    }

    public Observable<BaseResponse> addDiseaseBaike(DiseaseAddRequest diseaseAddRequest){
        return mApi.addDiseaseBaike(UserCache.getUserToken(),
                diseaseAddRequest.getDiseaseName(),
                diseaseAddRequest.getBigCategory(),
                diseaseAddRequest.getSymptom(),
                diseaseAddRequest.getTreatment(),
                getMultipartBody(diseaseAddRequest.getImage())
                );
    }

    public Observable<BaseResponse<BaikeDiseaseBean>>  updateDiseaseBaikeWithPic(int baikeID,BaikeDiseaseBean diseaseAddRequest){
        return mApi.updateDiseaseBaikeWithPic(UserCache.getUserToken(),
                baikeID,
                diseaseAddRequest.getDiseaseName(),
                diseaseAddRequest.getBig_category(),
                diseaseAddRequest.getSymptom(),
                diseaseAddRequest.getTreatment(),
                getMultipartBody(new File(diseaseAddRequest.getImage()))
        );
    }

    public Observable<BaseResponse<BaikeDiseaseBean>>  updateDiseaseBaikeNoPic(int baikeID,BaikeDiseaseBean diseaseAddRequest){
        return mApi.updateDiseaseBaikeNoPic(UserCache.getUserToken(),
                baikeID,
                diseaseAddRequest.getDiseaseName(),
                diseaseAddRequest.getBig_category(),
                diseaseAddRequest.getSymptom(),
                diseaseAddRequest.getTreatment()
        );
    }



    public Observable<BaseResponse> addProductBaike(BaikeProductBean productBean){
        return mApi.addProductBaike(UserCache.getUserToken(),
                productBean);

    }
//
//    public Observable<BaseResponse<BaikeProductBean>> updateProductBaikeWithPic(int baikeID,BaikeProductBean productBean){
//        return mApi.updateProductBaikeWithPic(UserCache.getUserToken(),
//                baikeID,
//                productBean.getName(),
//                productBean.getSubKind(),
//                productBean.getDescription(),
//                getMultipartBody(new File(productBean.getImage())));
//    }
//
    public Observable<BaseResponse<BaikeProductBean>> updateProductBaikeNoPic(int baikeID,BaikeProductBean productBean){
        return mApi.updateProductBaikeNoPic(UserCache.getUserToken(),
                baikeID,
                productBean);
    }



    public Observable<BaseResponse> addSeedBaike(BaikeSeedBean baikeSeedBean){
        return mApi.addSeedBaike(UserCache.getUserToken(),
                baikeSeedBean.getName(),
                baikeSeedBean.getSource(),
                baikeSeedBean.getContent(),
                getMultipartBody(new File(baikeSeedBean.getImage())));
    }

    public Observable<BaseResponse<BaikeSeedBean>> updateSeedBaikeWithPic(int baikeID,BaikeSeedBean baikeSeedBean){
        return mApi.updateSeedBaikeWithPic(UserCache.getUserToken(),
                baikeID,
                baikeSeedBean.getName(),
                baikeSeedBean.getSource(),
                baikeSeedBean.getContent(),
                getMultipartBody(new File(baikeSeedBean.getImage())));
    }
//
    public Observable<BaseResponse<BaikeSeedBean>> updateSeedBaikeNoPic(int baikeID,BaikeSeedBean baikeSeedBean){
        return mApi.updateSeedBaikeNoPic(UserCache.getUserToken(),
                baikeID,
                baikeSeedBean.getName(),
                baikeSeedBean.getSource(),
                baikeSeedBean.getContent());
    }


    public Observable<BaseResponse>  addFeedBaike(BaikeFeedBean baikeFeedBean){
        return mApi.addFeedBaike(UserCache.getUserToken(),
                baikeFeedBean.getName(),
                baikeFeedBean.getCategory(),
                baikeFeedBean.getSource(),
                baikeFeedBean.getContent());

    }
//
//    public Observable<BaseResponse<BaikeFeedBean>>  updateFeedBaikeWithPic(int baikeID,BaikeFeedBean baikeFeedBean){
//        return mApi.updateFeedBaikeWithPic(UserCache.getUserToken(),
//                baikeID,
//                baikeFeedBean.getName(),
//                baikeFeedBean.getSubKind(),
//                baikeFeedBean.getPrice(),
//                baikeFeedBean.getTelPhone(),
//                baikeFeedBean.getContact(),
//                baikeFeedBean.getCompany(),
//                baikeFeedBean.getManualInstruct(),
//                getMultipartBody(new File(baikeFeedBean.getImage())));
//
//    }
//
    public Observable<BaseResponse<BaikeFeedBean>>  updateFeedBaikeNoPic(int baikeID,BaikeFeedBean baikeFeedBean){
        return mApi.updateFeedBaikeNoPic(UserCache.getUserToken(),
                baikeID,
                baikeFeedBean.getName(),
                baikeFeedBean.getCategory(),
                baikeFeedBean.getSource(),
                baikeFeedBean.getContent());

    }

    public Observable<BaseResponse>  addDrugBaike(BaikeDrugBean baikeDrugBean){
        return mApi.addDrugBaike(UserCache.getUserToken(),
                baikeDrugBean);
    }
//
//    public Observable<BaseResponse<BaikeDrugBean>>  updateDrugBaikeWithPic(int baikeID,BaikeDrugBean baikeDrugBean){
//        return mApi.updateDrugBaikeWithPic(
//                UserCache.getUserToken(),
//                baikeID,
//                baikeDrugBean.getName(),
//                baikeDrugBean.getSubKind(),
//                baikeDrugBean.getPrice(),
//                baikeDrugBean.getTelPhone(),
//                baikeDrugBean.getContact(),
//                baikeDrugBean.getCompany(),
//                baikeDrugBean.getManualInstruct(),
//                getMultipartBody(new File(baikeDrugBean.getImage()))
//        );
//    }

    public Observable<BaseResponse<BaikeDrugBean>>  updateDrugBaikeNoPic(int baikeID,BaikeDrugBean baikeDrugBean){
        return mApi.updateDrugBaikeNoPic(
                UserCache.getUserToken(),
                baikeID,
                baikeDrugBean
        );
    }

    public Observable<BaseResponse<PageResponse<SelfInfoBean>>> queryExpertInfo(int pageNum){
        return mApi.queryExpertInfo(UserCache.getUserToken(),pageNum);
    }

    public Observable<BaseResponse<PageResponse<QuestionListBean>>> queryQuestionList(int pageNum){
        return mApi.queryQuestionlist(UserCache.getUserToken(),pageNum);
    }

    public Observable<BaseResponse<QuestionListBean>> queryOneQuestion(int ID){
        return mApi.queryOneQuestion(UserCache.getUserToken(),ID);
    }

    public Observable<BaseResponse<AnswerBean>> answerQuestion(String content,int questionID){
        return mApi.answerQuestion(UserCache.getUserToken(),content,questionID);
    }

    public Observable<BaseResponse<QuestionBean>> askQuestion(String des){
        return mApi.askQuestion(UserCache.getUserToken(),des);
    }

    public Observable<BaseResponse<QuestionBean>>  askQuestionWithImage(String des,String img){
        return mApi.askQuestionWithImage(UserCache.getUserToken(),
                des,
                getMultipartBody(new File(img))
        );
    }

    public Observable<BaseResponse<PageResponse<CustomerInfo>>> queryCustomerInfo(int pageNum){
        return mApi.queryCustomerInfo(UserCache.getUserToken(),pageNum);
    }


    public  Observable<BaseListResponse<RepairStateResponse>> queryRepairStateList(){
        return mApi.queryRepairStateList(UserCache.getUserToken());
    }

    public  Observable<BaseResponse> addRobotNeededRepair(int robertID,String description){
        return mApi.addRobotNeededRepair(UserCache.getUserToken(),robertID,description);
    }

    public Observable<BaseResponse>  opinionFeedback(String description,String contact,String img){
        return mApi.opinionFeedback(UserCache.getUserToken(),
                description,
                contact,
                getMultipartBody(new File(img)));

    }

    public Observable<BaseResponse>  opinionFeedbackNoPic(String description,String contact){
        return mApi.opinionFeedbackNopic(UserCache.getUserToken(),
                description,
                contact);

    }


    // ??????????????????
    public Observable<BaseResponse<IncomeResponse>> addIncome(IncomeResponse incomeResponse){
        return mApi.addIncome(UserCache.getUserToken(),incomeResponse);
    }

    // ??????????????????
    public Observable<BaseResponse<TypeIncomeResponse>> getIncome(int type,int pageNum){
        return mApi.getIncome(UserCache.getUserToken(),type,pageNum);
    }

    // ??????????????????
    public Observable<BaseResponse> deleteIncome(String ids){
        return mApi.deleteIncome(UserCache.getUserToken(),ids);
    }

    // ??????????????????
    public Observable<BaseResponse<IncomeResponse>> updateIncome(IncomeResponse incomeResponse){
        return mApi.updateIncome(UserCache.getUserToken(),incomeResponse);
    }


    // ??????????????????
    public Observable<BaseResponse<RizhiResponse>> addRizhi(String name,String content,String time,int pondID){
        return mApi.addRizhi(UserCache.getUserToken(),name,content,time,pondID);
    }

    // ??????????????????
    public Observable<BaseResponse> deleteRizhi(String ids){
        return mApi.deleteRizhi(UserCache.getUserToken(),ids);
    }

    // ??????????????????
    public Observable<BaseResponse<RizhiResponse>> updateRizhi(String name,String content,int pondID,int Id){
        return mApi.updateRizhi(UserCache.getUserToken(),name,content,pondID,Id);
    }


    // ??????????????????
    public Observable<BaseResponse<TypeRizhiResponse>> getRizhi(int pageNum){
        return mApi.getRizhi(UserCache.getUserToken(),pageNum);
    }

    // ??????????????????
    public Observable<BaseResponse<DailyThrowResponse>> addThrow(DailyThrowResponse dailyThrowResponse){
        return mApi.addThrow(UserCache.getUserToken(),dailyThrowResponse);
    }

    // ??????????????????
    public Observable<BaseResponse> deleteThrow(String ids){
        return mApi.deleteThrow(UserCache.getUserToken(),ids);
    }


    // ??????????????????
    public Observable<BaseResponse<DailyThrowResponse>> updateThrow(DailyThrowResponse dailyThrowResponse,int Id){
        return mApi.updateThrow(UserCache.getUserToken(),dailyThrowResponse,Id);
    }

    // ??????????????????
    public Observable<BaseResponse<TypeThrowResponse>> getThrow(int pageNum){
        return mApi.getThrow(UserCache.getUserToken(),pageNum);
    }

    // ??????????????????
    public Observable<BaseResponse<String[]>> getGudingType(){
        return mApi.getGudingType();
    }

    // ????????????????????????
    public Observable<BaseResponse<TemplateResponse>> addTemplate(TemplateResponse templateResponse){
        return mApi.addTemplate(UserCache.getUserToken(),templateResponse);
    }

    // ????????????????????????
    public Observable<BaseResponse> deleteTemplate(String ids){
        return mApi.deleteTemplate(UserCache.getUserToken(),ids);
    }

    // ????????????????????????
    public Observable<BaseResponse<TemplateResponse>> updateTemplate(TemplateResponse templateResponse){
        return mApi.updateTemplate(UserCache.getUserToken(),templateResponse);
    }

    // ????????????????????????
    public Observable<BaseResponse<TypeTemplateResponse>> getTemplate(int pageNum){
        return mApi.getTemplate(UserCache.getUserToken(),pageNum);
    }

    // ??????????????????
    public Observable<BaseResponse<String[]>> getObserveType(){
        return mApi.getObserveType();
    }

    // ??????????????????
    public Observable<BaseResponse<String[]>> getGoumaiType(){
        return mApi.getGoumaiType();
    }

    // ?????????????????????
    public Observable<BaseResponse<String[]>> getXiaoshouType(){
        return mApi.getXiaoshouType();
    }


    // ??????????????????
    public Observable<BaseResponse<String[]>> getErliaoType(){
        return mApi.getErliaoType();
    }

    // ??????????????????
    public Observable<BaseResponse<String[]>> getMedicineType(){
        return mApi.getMedicineType();
    }

    public Observable<BaseResponse<PageResponse<RoleUserInfo>>> queryRoleUserInfo(int pageNum,int role){
        return mApi.queryRoleUserInfo(UserCache.getUserToken(),pageNum,role);
    }

    public Observable<BaseResponse<PageResponse<RoleUserInfo>>> queryRoleExpertInfo(int pageNum,int role){
        return mApi.queryRoleExpertInfo(UserCache.getUserToken(),pageNum,role);
    }

    // ????????? ??????????????????
    public Observable<BaseResponse<TypeThrowResponse>> getThrowByUser(int pageNum,String username){
        return mApi.getThrowByUser(UserCache.getUserToken(),pageNum,username);
    }


    // ??????????????????
    public Observable<BaseResponse<TypeTermResponse>>getAllTerms(int pageNum){
        return mApi.getAllTerms(UserCache.getUserToken(),pageNum);
    }


    // ?????? ??????
    public Observable<BaseResponse<TermResponse>> addTerm(int type,String deveui,String manu,String prod,String name,String user){
        return mApi.addTerm(UserCache.getUserToken(),type,deveui,manu,prod,name,user);
    }

    // ?????? ??????
    public Observable<BaseResponse> updateTerm(int id,String deveui,String name,String user){
        return mApi.updateTerm(UserCache.getUserToken(),id,deveui,name,user);
    }

    // ?????? ??????
    public Observable<BaseResponse> deleteTerm(String id){
        return mApi.deleteTerm(UserCache.getUserToken(),id);
    }

    // ?????? ????????????
    public Observable<BaseResponse<List<ManuInfo>>> getAllManus(){
        return mApi.getAllManus(UserCache.getUserToken());
    }

    // ?????? ???????????????
    public Observable<BaseResponse<List<SensorResponse>>>getAllSensors(int termid){
        return mApi.getAllSensors(UserCache.getUserToken(),termid);
    }

    // ?????? ?????????
    public Observable<BaseResponse> deleteSensor(String sid){
        return mApi.deleteSensor(UserCache.getUserToken(),sid);
    }

    // ?????? ?????????
    public Observable<BaseResponse<SensorResponse>> addSensor(int cell_id,int term_id,int addr,String sensor_type,String sensor_name){
        return mApi.addSensor(UserCache.getUserToken(),cell_id,term_id,addr,sensor_type,sensor_name);
    }

    // ?????? ??????????????????id
    public Observable<BaseResponse<String[]>>getDefSensorType(int termtype){
        return mApi.getDefSensorType(UserCache.getUserToken(),termtype);
    }

    // ?????? ??????????????????id
    public Observable<BaseResponse<PageResponse<CellResponse>>>getAllUserCells(String username, int pageNum){
        return mApi.getAllUserCells(UserCache.getUserToken(),username,pageNum);
    }

    // ?????? ?????????
    public Observable<BaseResponse> updateSensor(int id,int cell_id,int addr,String sensor_name){
        return mApi.updateSensor(UserCache.getUserToken(),id,cell_id,addr,sensor_name);
    }



    // ?????? ??????
    public Observable<BaseResponse<EquipResponse>> addEquip(int cell_id, int term_id, int road, int addr, String equip_type, String equip_name){
        return mApi.addEquip(UserCache.getUserToken(),cell_id,term_id,road,addr,equip_type,equip_name);
    }

    // ?????? ????????????
    public Observable<BaseResponse<List<EquipType>>> getEquipType(){
        return mApi.getEquipType(UserCache.getUserToken());
    }

    // ?????? ????????????
    public Observable<BaseResponse<List<EquipResponse>>>getAllEquips(int termid){
        return mApi.getAllEquips(UserCache.getUserToken(),termid);
    }

    // ?????? ??????
    public Observable<BaseResponse> deleteEquip(String id){
        return mApi.deleteEquip(UserCache.getUserToken(),id);
    }

    // ?????? ??????
    public Observable<BaseResponse> updateEquip(int id,int cell_id,int term_id,int addr,int road,String equip_name){
        return mApi.updateEquip(UserCache.getUserToken(),id,cell_id,term_id,addr,road,equip_name);
    }

    // ?????? ????????????
    public Observable<BaseResponse> deleteCell(String id){
        return mApi.deleteCell(UserCache.getUserToken(),id);
    }

    // ?????? ????????????
    public Observable<BaseResponse<CellResponse>> addCell(double length,double width,double longitude,double latitude,String product,String prod,String cell_name,String user_name){
        return mApi.addCell(UserCache.getUserToken(),length,width,longitude,latitude,product,prod,cell_name,user_name);
    }

    /*--------------------------?????????????????????????????????--------------------------*/
    /*-------??????-------*/
    // ?????? ??????
    public Observable<BaseResponse> addFishPond(String pondName, double length, double width, double depth, String orientation, double longitude, double latitude, String address,String product){
        return mApi.addFishPond(UserCache.getUserToken(),pondName,length,width,depth,orientation,latitude,longitude,address,product,UserCache.getUserUsername());
    }

    // ?????? ?????? ??????
    public Observable<BaseResponse<BaseFishPondPageResponse<FishPondResponse>>> getUserPonds(String username, int pageNum, int pageSize){
        return mApi.getUserPonds(UserCache.getUserToken(),username,pageNum,pageSize);
    }

    // ?????? ?????? ??????
    public Observable<BaseResponse<List<FishPondResponse>>> getAllUserPonds(String username){
        return mApi.getAllUserPonds(UserCache.getUserToken(),username);
    }

    // ?????? ??????
    public Observable<BaseResponse> deleteFishPond(int id){
        return mApi.deleteFishPond(UserCache.getUserToken(),id);
    }

    // ?????? ??????
    public Observable<BaseResponse> updateFishPond(int id,String pondName, double length, double width, double depth, String orientation, double longitude, double latitude, String address,String product){
        return mApi.updateFishPond(UserCache.getUserToken(),id,pondName,length,width,depth,orientation,latitude,latitude,address,product,UserCache.getUserUsername());
    }

    /*-------?????????-------*/
    // ?????? ?????????
    public Observable<BaseResponse> addInput(String inputType, String inputName, String manufacturer, List<MultipartBody.Part> pictures, String remarks){
        return mApi.addInput(UserCache.getUserToken(),inputType,inputName,manufacturer,pictures,remarks,UserCache.getUserUsername());
    }

    // ?????? ????????? ??????
    public Observable<BaseResponse<BaseInputPageResponse<InputResponse>>> getUserInputs(String username,int pageNum,int pageSize){
        return mApi.getUserInputs(UserCache.getUserToken(),username,pageNum,pageSize);
    }

    // ?????? ????????? ??????
    public Observable<BaseResponse<List<InputResponse>>> getAllUserInputs(String username){
        return mApi.getAllUserInputs(UserCache.getUserToken(),username);
    }

    // ?????? ?????????
    public Observable<BaseResponse> deleteInput(int id){
        return mApi.deleteInput(UserCache.getUserToken(),id);
    }

    // ?????? ?????????
    public Observable<BaseResponse> updateInput(int id,String inputType, String inputName, String manufacturer, List<MultipartBody.Part> pictures, String remarks){
        return mApi.updateInput(UserCache.getUserToken(),id,inputType,inputName,manufacturer,pictures,remarks,UserCache.getUserUsername());
    }

    /*-------????????????-------*/
    // ?????? ????????????
    public Observable<BaseResponse> addFishInput(String type,double amount,String unit,String date,List<String> pond,String batchNumber){
        return mApi.addFishInput(UserCache.getUserToken(),type,amount,unit,date,pond,batchNumber,UserCache.getUserUsername());
    }

    // ?????? ????????????
    public Observable<BaseResponse> deleteFishInput(int id){
        return mApi.deleteFishInput(UserCache.getUserToken(),id);
    }

    // ?????? ????????????
    public Observable<BaseResponse> updateFishInput(int id,String type,double amount,String unit,String date,List<String> pond,String batchNumber){
        return mApi.updateFishInput(UserCache.getUserToken(),id,type,amount,unit,date,pond,batchNumber,UserCache.getUserUsername());
    }

    // ?????? ???????????? ??????
    public Observable<BaseResponse<BaseFishInputPageResponse<FishInputResponse>>> getUserFishInput(String username,int pageNum,int pageSize){
        return mApi.getUserFishInput(UserCache.getUserToken(),username,pageNum,pageSize);
    }

    // ?????? ???????????? ??????
    public Observable<BaseResponse<List<FishInputResponse>>> getAllUserFishInput(String username){
        return mApi.getAllUserFishInput(UserCache.getUserToken(),username);
    }

    /*-------??????-------*/
    // ?????? ??????
    public Observable<BaseResponse> addFeedingTemplate(String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        return mApi.addFeedingTemplate(UserCache.getUserToken(),name,batchNumber,pond,food,amount,unit,time,remarks,UserCache.getUserUsername());
    }

    // ?????? ??????
    public Observable<BaseResponse> updateFeedingTemplate(int id,String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        return mApi.updateFeedingTemplate(UserCache.getUserToken(),id,name,batchNumber,pond,food,amount,unit,time,remarks,UserCache.getUserUsername());
    }

    // ?????? ??????
    public Observable<BaseResponse> deleteFeedingTemplate(int id){
        return mApi.deleteFeedingTemplate(UserCache.getUserToken(),id);
    }

    // ?????? ?????? ??????
    public Observable<BaseResponse<BaseFeedingTemplatePageResponse<FeedingTemplateResponse>>> getUserFeedingTemplate(String username, int pageNum, int pageSize){
        return mApi.getUserTemplate(UserCache.getUserToken(),username,pageNum,pageSize);
    }

    // ?????? ?????? ??????
    public Observable<BaseResponse<List<FeedingTemplateResponse>>> getAllUserFeedingTemplate(String username){
        return mApi.getAllUserTemplate(UserCache.getUserToken(),username);
    }

    /*-------????????????-------*/
    // ?????? ????????????
    public Observable<BaseResponse> addFeedingFood(String templateName, String batchNumber, String pond, String food, double amount, String unit, String time, String remarks){
        return mApi.addFeedingFood(UserCache.getUserToken(),templateName,batchNumber,pond,food,amount,unit,time,remarks,UserCache.getUserUsername());
    }

    // ?????? ????????????
    public Observable<BaseResponse> updateFeedingFood(int id,String name,String batchNumber,String pond,String food,double amount,String unit,String time,String remarks){
        return mApi.updateFeedingFood(UserCache.getUserToken(),id,name,batchNumber,pond,food,amount,unit,time,remarks,UserCache.getUserUsername());
    }

    // ?????? ???????????? ??????
    public Observable<BaseResponse<BaseFeedingFoodPageResponse<FeedingFoodResponse>>> getAllUserFeedingFood(String username, int pageNum, int pageSize){
        return mApi.getUserFeedingFood(UserCache.getUserToken(),username,pageNum,pageSize);
    }

    // ?????? ???????????? ??????
    public Observable<BaseResponse<List<FeedingFoodResponse>>> getAllUserFeedingFood(String username){
        return mApi.getAllUserFeedingFood(UserCache.getUserToken(),username);
    }

    // ?????? ????????????
    public Observable<BaseResponse> deleteFeedingFood(int id){
        return mApi.deleteFeedingFood(UserCache.getUserToken(),id);
    }

    /*-------?????????-------*/
    // ?????? ???????????????
    public Observable<BaseResponse> addReagentInput(String batchNumber,String pond,String reagent,double amount,String unit,String time,String remarks){
        return mApi.addReagentInput(UserCache.getUserToken(),batchNumber,pond,reagent,amount,unit,time,remarks,UserCache.getUserUsername());
    }

    // ?????? ???????????????
    public Observable<BaseResponse> deleteReagentInput(int id){
        return mApi.deleteReagentInput(UserCache.getUserToken(),id);
    }

    // ?????? ???????????????
    public Observable<BaseResponse> updateReagentInput(int id,String batchNumber,String pond,String reagent,double amount,String unit,String time,String remarks){
        return  mApi.updateReagentInput(UserCache.getUserToken(),id,batchNumber,pond,reagent,amount,unit,time,remarks,UserCache.getUserUsername());
    }

    // ?????? ??????????????? ??????
    public Observable<BaseResponse<BaseReagentInputPageResponse<ReagentInputResponse>>> getUserReagentInput(String username, int pageNum,int pageSize){
        return mApi.getUserReagentInput(UserCache.getUserToken(),username,pageNum,pageSize);
    }

    // ?????? ????????? ??????
    public Observable<BaseResponse<List<ReagentInputResponse>>> getAllUserReagentInput(String username){
        return mApi.getAllUserReagentInput(UserCache.getUserToken(),username);
    }

    // ?????? ????????????
    public Observable<BaseResponse<List<CellProTypeResponse>>>getCellProType(){
        return mApi.getCellProType(UserCache.getUserToken());
    }

    // ?????? ??????
    public Observable<BaseResponse<String[]>>getCellPro(String celltype){
        return mApi.getCellPro(UserCache.getUserToken(),celltype);
    }


    // ?????? ????????????
    public Observable<BaseResponse> updateCell(int id,double length,double width,double longitude,double latitude,String product,String prod,String cell_name,String user_name){
        return mApi.updateCell(UserCache.getUserToken(),id,length,width,longitude,latitude,product,prod,cell_name,user_name);
    }

    // ?????? ??????????????????
    public Observable<BaseResponse<List<EquipResponse>>>getCellEquips(int cellid){
        return mApi.getCellEquips(UserCache.getUserToken(),cellid);
    }

    // ?????? ??????????????????  ??????
    public Observable<BaseResponse<List<EquipStatusResponse>>>getCellEquipsStatus(int cellid){
        return mApi.getCellEquipsStatus(UserCache.getUserToken(),cellid);
    }

    // ?????? ???????????? ????????????
    public Observable<BaseResponse<List<OrgResponse>>>getOrgData(boolean all_env,int cellid){
        return mApi.getOrgData(UserCache.getUserToken(),all_env,cellid);
    }

    // ?????? ???????????? ????????????
    public Observable<BaseResponse<List<RealResponse>>>getRealData(boolean all_env,int cellid){
        return mApi.getRealData(UserCache.getUserToken(),all_env,cellid);
    }

    // ?????? ???????????? ????????????
    public Observable<BaseResponse<List<HisResponse>>>getHisData(boolean all_env,int cellid,String start_time,String end_time){
        return mApi.getHisData(UserCache.getUserToken(),all_env,cellid,start_time,end_time);
    }

    // ?????? ??????
    public Observable<BaseResponse<CtrlResponse>>ctrlEquip(int appusrid, int equip_id,int ison_fg){
        return mApi.ctrlEquip(UserCache.getUserToken(),appusrid,equip_id,ison_fg);
    }


    // ??????
    public Observable<BaseResponse<PageResponse<ZhishiBean>>> queryZhishi(int pageNum){
        return mApi.queryZhishi(pageNum);
    }

    // ?????? detail
    public Observable<BaseResponse<ZhishiBean>> queryDetailZhishiInfo(int index){
        return mApi.queryDetailZhishiInfo(index);
    }


    // ?????? ????????????  ??????
    public Observable<BaseResponse<List<AutoStatusResponse>>>getAutoEquipsStatus(int cellid){
        return mApi.getAutoEquipsStatus(UserCache.getUserToken(),cellid);
    }


    // ?????? ?????? ??????
    public Observable<BaseResponse<Boolean>>ctrlAuto(int id,String env_type,int param_id,float wnup,float wndw,float actup,float actdw,int ison_fg){
        return mApi.ctrlAuto(UserCache.getUserToken(),id,env_type,param_id,wnup,wndw,actup,actdw,ison_fg);
    }

    // ?????? ????????????  ??????
    public Observable<BaseResponse<List<AutoTimeStatusResponse>>>getAutoTimeStatus(int cellid){
        return mApi.getAutoTimeStatus(UserCache.getUserToken(),cellid);
    }


    // ?????? ?????? ??????
    public Observable<BaseResponse<Boolean>>ctrlAutoTime(int id,String env_type,String time,int opt,int ison_fg){
        return mApi.ctrlAutoTime(UserCache.getUserToken(),id,env_type,time,opt,ison_fg);
    }

    // ?????? ???????????? ??????
    public Observable<BaseResponse<List<AutoLogResponse>>>getAutoLog(int cellid, String start_time, String end_time){
        return mApi.getAutoLog(UserCache.getUserToken(),cellid,start_time,end_time);
    }

    // ?????? ?????? ??????
    public Observable<BaseResponse<List<WarnLogResponse>>>getWarnLog(int cellid, String start_time, String end_time){
        return mApi.getWarnLog(UserCache.getUserToken(),cellid,start_time,end_time);
    }





    /**
     * bean ???body
     * @param obj
     * @return
     */
    private RequestBody getRequestBody(Object obj) {
        String route = new Gson().toJson(obj);
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),route);
    }

    private MultipartBody.Part getMultipartBody(File file, String type){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData(type, file.getName(), requestFile);
        return multipartBody;
    }

    private MultipartBody.Part getMultipartBody(File file){
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part multipartBody =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        return multipartBody;
    }



}
