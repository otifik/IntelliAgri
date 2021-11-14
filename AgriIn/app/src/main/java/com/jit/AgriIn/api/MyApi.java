package com.jit.AgriIn.api;


import com.jit.AgriIn.model.bean.AnswerBean;
import com.jit.AgriIn.model.bean.QuestionBean;
import com.jit.AgriIn.model.bean.QuestionListBean;
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
import com.zxl.baselib.bean.response.BaseListResponse;
import com.zxl.baselib.bean.response.BaseResponse;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author zxl on 2018/8/31.
 *         discription: retrofit访问各个url
 */

public interface MyApi {
    /**
     * 基础URL
     */
    String BASE_URL = "http://4v034050v5.zicp.vip/";
//    String BASE_URL = "https://www.njzhny.com:8443/";

    // -----------------------------------------注册登陆相关

    @POST("auth/login")
    Observable<BaseResponse<LoginResponse>> requestLogin(@Query("username") String username ,@Query("password") String password);

    /**
     * 用户注册
     * @param body 注册请求体
     * @return
     */
    @POST("auth/signup")
    Observable<BaseResponse> requestRegister(@Body RequestBody body);



    @PUT("user/password")
    Observable<BaseResponse> changePwd(@Header("Authorization") String auto,
                                       @Query("oldPassword") String oldPassword,
                                       @Query("newPassword") String newPassword);

    /**
     * 头像上传
     * @param auto
     * @param file
     * @return
     * 文件上传一定要添加 Multipart
     */
    @Multipart
    @POST("user/image")
    Observable<BaseResponse<HeadPostResponse>> postHeadImage(@Header("Authorization") String auto,
                                                             @Part MultipartBody.Part file);


    // -----------------------------------------生产单元配置相关

    /**
     * 查询该用户下的所有生产单元信息
     * @param auto
     * @return
     */
    @GET("pound/")
    Observable<BaseListResponse<PondMainResponse>> queryPondMainInfo(@Header("Authorization") String auto);

    /**
     * 添加生产单元
     * @param auto
     * @param body
     * @return
     */
    @POST("pound/add")
    Observable<BaseResponse<PondMainResponse>> requestAddPond(@Header("Authorization") String auto,@Body RequestBody body);

    /**
     * 删除对应的生产单元
     * @param auto
     * @param pondID
     * @return
     */
    @DELETE("pound/{id}")
    Observable<BaseResponse> requestDelPond(@Header("Authorization") String auto,@Path("id") int pondID);

    /**
     * 更新对应生产单元的信息
     * @param auto
     * @param pondID
     * @param body
     * @return
     */
    @PUT("pound/{id}")
    Observable<BaseResponse<PondMainResponse>> requestUpdatePond(@Header("Authorization") String auto,@Path("id") int pondID,@Body RequestBody body);

    // -----------------------------------------机器人添加相关

    /**
     * 客户端增加机器人
     * @param auto
     * @param body
     * @return
     */
    @POST("robert/add")
    Observable<BaseResponse<RobotMainResponse>> requestAddRobot(@Header("Authorization") String auto,@Body RequestBody body);

    /**
     * 查询对应客户下的所有机器人
     * @param auto
     * @param userID
     * @return
     */
    @GET("robert/customer")
    Observable<BaseListResponse<RobotMainResponse>> queryMyRobot(@Header("Authorization") String auto,@Query("customer_id") int userID);

    /**
     * 查询对应客户下的所有机器人
     * @param auto
     * @param userID
     * @param pageNum
     * @return
     */
    @GET("robert/customer")
    Observable<BaseResponse<RobotPageResponse>> queryMyRobotWithPage(@Header("Authorization") String auto
            , @Query("customer_id") int userID, @Query("pageNum") int pageNum);

    /**
     * 更新机器人相应
     * @param auto
     * @param robotID
     * @param body
     * @return
     */
    @PUT("robert/{id}")
    Observable<BaseResponse<RobotMainResponse>> updateRobot(@Header("Authorization") String auto,@Path("id") int robotID,@Body RequestBody body);

    /**
     * 删除对应机器人
     * @param auto
     * @param robotID
     * @return
     */
    @DELETE("robert/{id}")
    Observable<BaseResponse> deleteRobot(@Header("Authorization") String auto,@Path("id") int robotID);

    // -----------------------------------------机器人设备配置相关

    /**
     * 获得当前用户下的配置信息
     * @param auto
     * @return
     */
    @GET("settings/user")
    Observable<BaseListResponse<ConfigMainResponse>> queryAllConfig(@Header("Authorization") String auto);

    /**
     * 增加设备配置信息
     * @param auto
     * @param body
     * @return
     */
    @POST("settings/add")
    Observable<BaseResponse<ConfigMainResponse>> requestAddConfig(@Header("Authorization") String auto,@Body RequestBody body);

    /**
     * 更新设备配置信息
     * @param auto
     * @param configID
     * @param body
     * @return
     */
    @PUT("settings/{id}")
    Observable<BaseResponse<ConfigMainResponse>> requestUpdateConfig(@Header("Authorization") String auto,@Path("id") int configID,@Body RequestBody body);

    /**
     * 删除设备信息
     * @param auto
     * @param configID
     * @return
     */
    @DELETE("settings/{id}")
    Observable<BaseResponse> requestDelConfig(@Header("Authorization") String auto,@Path("id") int configID);


    // -----------------------------------------种养日志相关

    /**
     * 插入水质信息
     * @param auto
     * @param pound_id
     * @param date
     * @param weather
     * @param medicine
     * @param remark
     * @param temperature
     * @param o2
     * @param ph
     * @return
     */
    @POST("water/insert")
    Observable<BaseResponse> insertWater(@Header("Authorization") String auto,
                                         @Query("pound_id") int pound_id,
                                         @Query("date") String date,
                                         @Query("weather") String weather,
                                         @Query("medicine") String medicine,
                                         @Query("remark") String remark,
                                         @Query("temperature") String temperature,
                                         @Query("o2") String o2,
                                         @Query("ph") String ph);

    /**
     * 插入投喂信息
     * @param auto
     * @param pound_id
     * @param date
     * @param count1
     * @param count2
     * @param count3
     * @param count4
     * @param count5
     * @param count6
     * @return
     */
    @POST("feed/insert")
    Observable<BaseResponse> insertFeed(@Header("Authorization") String auto,
                                        @Query("pound_id") int pound_id,
                                        @Query("date") String date,
                                        @Query("count1") long count1,
                                        @Query("count2") long count2,
                                        @Query("count3") long count3,
                                        @Query("count4") long count4,
                                        @Query("count5") long count5,
                                        @Query("count6") long count6
                                        );

    /**
     * 获取特定生产单元对应时间段的历史日志
     * @param auto
     * @param pound_id
     * @param start_date
     * @param end_date
     * @return
     */
    @GET("water/diary")
    Observable<BaseListResponse<CultureLogResponse>> queryDiaryLogInfo(@Header("Authorization") String auto,
                                                                       @Query("pound_id") int pound_id,
                                                                       @Query("start_date") String start_date,
                                                                       @Query("end_date") String end_date);

    /* 日志记录的管理 ======================*/


    @POST("downlog/add")
    Observable<BaseResponse> addDownloadLog(@Header("Authorization") String auto,@Query("downlogname") String downlogname);

    @DELETE("downlog/{ids}")
    Observable<BaseResponse> deleteSmLogs(@Header("Authorization") String auto,@Path("ids") String ids);

    @GET("downlog/all")
    Observable<BaseListResponse<DownLogResponse>> getaAllLogs(@Header("Authorization") String auto);

   /*  百科 ======================*/


    /**
     * 专家信息
     * @param auto
     * @param pageNum
     * @return
     */
    @GET("expert/")
    Observable<BaseResponse<PageResponse<SelfInfoBean>>> queryExpertInfo(@Header("Authorization") String auto,
                                                                               @Query("pageNum") int pageNum);

    @GET("/customer/")
    Observable<BaseResponse<PageResponse<CustomerInfo>>> queryCustomerInfo(@Header("Authorization") String auto,
                                                                           @Query("pageNum") int pageNum);


    /**
     * 分页获得百科信息  品种
     * @param auto
     * @param subKind
     * @param pageNum
     * @return
     */
   @GET("crop/")
    Observable<BaseResponse<PageResponse<BaikeSeedBean>>> queryKindBaike(@Query("pageNum") int pageNum);

    /**
     * 分页获得百科信息 疾病
     * @param auto
     * @param subKind
     * @param pageNum
     * @return
     */
    @GET("disease/")
    Observable<BaseResponse<PageResponse<BaikeDiseaseBean>>> queryBingBaike(@Query("pageNum") int pageNum);

    /**
     * 分页获得百科信息 农药
     * @param auto
     * @param subKind
     * @param pageNum
     * @return
     */
    @GET("pesticide/")
    Observable<BaseResponse<PageResponse<BaikeDrugBean>>> queryYaoBaike(@Query("pageNum") int pageNum);

    /**
     * 分页获得百科信息  综合
     * @param auto
     * @param subKind
     * @param pageNum
     * @return
     */
    @GET("technology/")
    Observable<BaseResponse<PageResponse<BaikeFeedBean>>> queryWeiBaike(@Query("pageNum") int pageNum);

    /**
     * 分页获得百科信息 肥料
     * @param auto
     * @param subKind
     * @param pageNum
     * @return
     */
    @GET("fertilizer/")
    Observable<BaseResponse<PageResponse<BaikeProductBean>>> queryPinBaike(@Query("pageNum") int pageNum);


    /**
     * 获得详细的百科信息
     * @param auto
     * @param id
     * @return
     */
    @GET("crop/{id}")
   Observable<BaseResponse<BaikeSeedBean>> queryDetailBaikeInfo( @Path("id") int id);


    /**
     * 获得详细的百科信息
     * @param auto
     * @param id
     * @return
     */
    @GET("disease/{id}")
    Observable<BaseResponse<BaikeDiseaseBean>> queryDetailBingBaikeInfo(@Path("id") int id);



    /**
     * 获得详细的百科信息 药
     * @param auto
     * @param id
     * @return
     */
    @GET("pesticide/{id}")
    Observable<BaseResponse<BaikeDrugBean>> queryDetailYaoBaikeInfo( @Path("id") int id);


    /**
     * 获得详细的百科信息
     * @param auto
     * @param id
     * @return
     */
    @GET("technology/{id}")
    Observable<BaseResponse<BaikeFeedBean>> queryDetailWeiBaikeInfo( @Path("id") int id);


    /**
     * 获得详细的百科信息
     * @param auto
     * @param id
     * @return
     */
    @GET("fertilizer/{id}")
    Observable<BaseResponse<BaikeProductBean>> queryDetailPinBaikeInfo( @Path("id") int id);

    /**
     * 种苗删除
     * @param auto
     * @param id
     * @return
     */
    @DELETE("crop/{ids}")
    Observable<BaseResponse> deleteSeedBaikeByID(@Header("Authorization") String auto,@Path("ids") int id);

    /**
     * 物品删除
     * @param auto
     * @param id
     * @return
     */
    @DELETE("fertilizer/{ids}")
    Observable<BaseResponse> deleteProductBaikeByID(@Header("Authorization") String auto,@Path("ids") int id);

    /**
     * 投喂删除
     * @param auto
     * @param id
     * @return
     */
    @DELETE("technology/{ids}")
    Observable<BaseResponse> deleteFeedBaikeByID(@Header("Authorization") String auto,@Path("ids") int id);

    /**
     * 疾病删除
     * @param auto
     * @param id
     * @return
     */
    @DELETE("disease/{ids}")
    Observable<BaseResponse> deleteDiseaseBaikeByID(@Header("Authorization") String auto,@Path("ids") int id);

    /**
     * 压迫你删除
     * @param auto
     * @param id
     * @return
     */
    @DELETE("pesticide/{ids}")
    Observable<BaseResponse> deleteDrugBaikeByID(@Header("Authorization") String auto,@Path("ids") int id);


    @Multipart
    @POST("disease/add")
    Observable<BaseResponse>  addDiseaseBaike(@Header("Authorization") String auto,
                                                @Query("diseaseName") String diseaseName,
                                                @Query("big_category") String bigCategory,
                                                @Query("symptom") String symptom,
                                                @Query("treatment") String treatment,
                                               @Part MultipartBody.Part image);

    @Multipart
    @PUT("disease/{id}")
    Observable<BaseResponse<BaikeDiseaseBean>>  updateDiseaseBaikeWithPic(@Header("Authorization") String auto,
                                              @Path("id") int ID,
                                              @Query("diseaseName") String diseaseName,
                                              @Query("big_category") String subKind,
                                              @Query("symptom") String symptom,
                                              @Query("treatment") String treatment,
                                              @Part MultipartBody.Part image);

    @PUT("disease/{id}")
    Observable<BaseResponse<BaikeDiseaseBean>>  updateDiseaseBaikeNoPic(@Header("Authorization") String auto,
                                                                          @Path("id") int ID,
                                                                          @Query("diseaseName") String diseaseName,
                                                                          @Query("big_category") String subKind,
                                                                          @Query("symptom") String symptom,
                                                                          @Query("treatment") String treatment);


    @POST("fertilizer/add")
    Observable<BaseResponse>  addProductBaike(@Header("Authorization") String auto,
                                              @Body BaikeProductBean baikeProductBean);


//    @Multipart
//    @PUT("fertilizer/{id}")
//    Observable<BaseResponse<BaikeProductBean>>  updateProductBaikeWithPic(@Header("Authorization") String auto,
//                                              @Path("id") int ID,
//                                              @Query("name") String name,
//                                              @Query("subKind") String subKind,
//                                              @Query("description") String description,
//                                              @Part MultipartBody.Part image);

    @PUT("fertilizer/{id}")
    Observable<BaseResponse<BaikeProductBean>>  updateProductBaikeNoPic(@Header("Authorization") String auto,
                                                        @Path("id") int ID,
                                                        @Body BaikeProductBean baikeProductBean);


    @Multipart
    @POST("crop/add")
    Observable<BaseResponse>  addSeedBaike(@Header("Authorization") String auto,
                                           @Query("name") String name,
                                           @Query("source") String source,
                                           @Query("content") String content,
                                           @Part MultipartBody.Part image);

    /**
     * 带有图片的更新种苗信息
     * @param auto
     * @param ID
     * @param title
     * @param subKind
     * @param price
     * @param productPlace
     * @param telPhone
     * @param contact
     * @param company
     * @param description
     * @param image
     * @return
     */
    @Multipart
    @PUT("crop/{id}")
    Observable<BaseResponse<BaikeSeedBean>>  updateSeedBaikeWithPic(@Header("Authorization") String auto,
                                                                    @Path("id") int ID,
                                                                    @Query("name") String name,
                                                                    @Query("source") String source,
                                                                    @Query("content") String content,
                                                                    @Part MultipartBody.Part image);

    /**
     * 不带图片的更新种苗信息
     * @param auto
     * @param ID
     * @param title
     * @param subKind
     * @param price
     * @param productPlace
     * @param telPhone
     * @param contact
     * @param company
     * @param description
     * @return
     */
    @PUT("crop/{id}")
    Observable<BaseResponse<BaikeSeedBean>>  updateSeedBaikeNoPic(@Header("Authorization") String auto,
                                                                    @Path("id") int ID,
                                                                    @Query("name") String name,
                                                                    @Query("source") String source,
                                                                    @Query("content") String content);



    @POST("technology/add")
    Observable<BaseResponse>  addFeedBaike(@Header("Authorization") String auto,
                                           @Query("name") String name,
                                           @Query("category") String category,
                                           @Query("source") String source,
                                           @Query("content") String content);

//
//    @Multipart
//    @PUT("technology/{id}")
//    Observable<BaseResponse<BaikeFeedBean>>  updateFeedBaikeWithPic(@Header("Authorization") String auto,
//                                           @Path("id") int ID,
//                                           @Query("name") String name,
//                                           @Query("subKind") String subKind,
//                                           @Query("price") String price,
//                                           @Query("telPhone") String telPhone,
//                                           @Query("contact") String contact,
//                                           @Query("company") String company,
//                                           @Query("manualInstruct") String manualInstruct,
//                                           @Part MultipartBody.Part image);

    @PUT("technology/{id}")
    Observable<BaseResponse<BaikeFeedBean>>  updateFeedBaikeNoPic(@Header("Authorization") String auto,
                                                     @Path("id") int ID,
                                                     @Query("name") String name,
                                                     @Query("category") String category,
                                                     @Query("source") String source,
                                                     @Query("content") String content);



    @POST("pesticide/add")
    Observable<BaseResponse>  addDrugBaike(@Header("Authorization") String auto,
                                           @Body BaikeDrugBean baikeDrugBean);

//    @Multipart
//    @PUT("pesticide/{id}")
//    Observable<BaseResponse<BaikeDrugBean>>  updateDrugBaikeWithPic(@Header("Authorization") String auto,
//                                                                    @Path("id") int ID,
//                                           @Query("name") String name,
//                                           @Query("subKind") String subKind,
//                                           @Query("price") String price,
//                                           @Query("telPhone") String telPhone,
//                                           @Query("contact") String contact,
//                                           @Query("company") String company,
//                                           @Query("manualInstruct") String manualInstruct,
//                                           @Part MultipartBody.Part image);

    @PUT("pesticide/{id}")
    Observable<BaseResponse<BaikeDrugBean>>  updateDrugBaikeNoPic(@Header("Authorization") String auto,
                                                                  @Path("id") int ID,
                                                     @Body BaikeDrugBean baikeDrugBean);



    //---------------------- 维修相关-------------------

    /**
     * 用户维修列表
     * @param auto 用户token
     * @return  listBean
     */
    @GET("customer/repair/list")
    Observable<BaseListResponse<RepairStateResponse>> queryRepairStateList(@Header("Authorization") String auto);


    @POST("customer/repair/{robert_id}")
    Observable<BaseResponse> addRobotNeededRepair(@Header("Authorization") String auto,
                                                  @Path("robert_id") int robertID,
                                                  @Query("description") String description);

    @Multipart
    @POST("feedback/")
    Observable<BaseResponse>  opinionFeedback(@Header("Authorization") String auto,
                                           @Query("description") String description,
                                           @Query("contact") String contact,
                                           @Part MultipartBody.Part image);

    @POST("feedback/")
    Observable<BaseResponse>  opinionFeedbackNopic(@Header("Authorization") String auto,
                                              @Query("description") String description,
                                              @Query("contact") String contact);

    /**
     * 查询普通用户个人信息
     * @param auto
     * @return
     */
    @GET("customer/get")
    Observable<BaseResponse<SelfInfoBean>> queryUserInfo(@Query("username") String userName);

    /**
     * 修改普通用户个人信息
     * @param auto
     * @param body
     * @return
     */
    @PUT("customer/update")
    Observable<BaseResponse> updateUserInfo(@Header("Authorization") String auto,@Query("customer") String customer,@Body RequestBody body);


    @GET("user/check")
    Observable<BaseResponse> userCheck(@Query("name") String name);

    @PUT("user/edit")
    Observable<BaseResponse> forgetPass(@Query("tel") String tel,@Query("password") String pass,@Query("password1") String pass1);


    /**
     * 查询普专家户个人信息
     * @param auto
     * @return
     */
    @GET("expert/get")
    Observable<BaseResponse<SelfInfoBean>> queryExpertInfo(@Query("username") String userName);

    /**
     * 修改专家用户个人信息
     * @param auto
     * @param body
     * @return
     */
    @PUT("expert/update")
    Observable<BaseResponse> updateExpertInfo(@Header("Authorization") String auto,@Query("expert") String customer,@Body RequestBody body);


    /**
     * 查询问题列表
     * @param auto
     * @return
     */
    @GET("ac-service/question/")
    Observable<BaseResponse<PageResponse<QuestionListBean>>> queryQuestionlist(@Header("Authorization") String auto,
                                                                 @Query("pageNum") int pageNum);


    /**
     * 查询某条问题
     * @param auto
     * @return
     */
    @GET("ac-service/question/{id}")
    Observable<BaseResponse<QuestionListBean>> queryOneQuestion(@Header("Authorization") String auto,
                                                                               @Path("id") int ID);


    /**
     * 增加回答
     * @param auto
     * @return
     */
    @POST("ac-service/answer/add")
    Observable<BaseResponse<AnswerBean>>  answerQuestion(@Header("Authorization") String auto,
                                                               @Query("content") String content,
                                                               @Query("question_id") int questionID);


    /**
     * 增加回答
     * @param auto
     * @return
     */
    @Multipart
    @POST("ac-service/question/add")
    Observable<BaseResponse<QuestionBean>>  askQuestionWithImage(@Header("Authorization") String auto,
                                                                 @Query("description") String des,
                                                                 @Part MultipartBody.Part image);


    @POST("ac-service/question/add")
    Observable<BaseResponse<QuestionBean>>  askQuestion(@Header("Authorization") String auto,
                                                      @Query("description") String des);

    // 增加经济效益
    @POST("ac-service/income/add")
    Observable<BaseResponse<IncomeResponse>>  addIncome(@Header("Authorization") String auto,
                                        @Body IncomeResponse incomeResponse);

    // 根据类型获取经济效益
    @GET("ac-service/income/{type}")
    Observable<BaseResponse<TypeIncomeResponse>> getIncome(@Header("Authorization") String auto,
                                                           @Path("type") int type,
                                                           @Query("pageNum") int pageNum
                                                           );

    // 删除经济效益
    @DELETE("ac-service/income/{ids}")
    Observable<BaseResponse> deleteIncome(@Header("Authorization") String auto,@Path("ids") String ids);

    // 修改经济效益
    @PUT("ac-service/income/update")
    Observable<BaseResponse<IncomeResponse>> updateIncome(@Header("Authorization") String auto,@Body IncomeResponse incomeResponse);




    // 增加观察记录
    @POST("ac-service/observe/add")
    Observable<BaseResponse<RizhiResponse>>  addRizhi(@Header("Authorization") String auto,
                                                        @Query("name") String name,
                                                        @Query("remark") String content,
                                                        @Query("time") String tme,
                                                        @Query("pound_id") int pondId);

    // 删除观察记录
    @DELETE("ac-service/observe/{ids}")
    Observable<BaseResponse> deleteRizhi(@Header("Authorization") String auto,@Path("ids") String ids);


    // 修改观察记录
    @PUT("ac-service/observe/update")
    Observable<BaseResponse<RizhiResponse>> updateRizhi(@Header("Authorization") String auto,
                                                         @Query("name") String name,
                                                         @Query("content") String content,
                                                         @Query("pound_id") int pondId,
                                                        @Query("id") int Id);

    // 查询观察记录
    @GET("ac-service/observe/")
    Observable<BaseResponse<TypeRizhiResponse>> getRizhi(@Header("Authorization") String auto,
                                                         @Query("pageNum") int pageNum);

    // 获取所有日常投放数据
    @GET("ac-service/throw/")
    Observable<BaseResponse<TypeThrowResponse>> getThrow(@Header("Authorization") String auto,
                                                         @Query("pageNum") int pageNum);


    // 增加日常投放
    @POST("ac-service/throw/add")
    Observable<BaseResponse<DailyThrowResponse>>  addThrow(@Header("Authorization") String auto,
                                                      @Body DailyThrowResponse dailyThrowResponse);

    // 删除日常投放
    @DELETE("ac-service/throw/{ids}")
    Observable<BaseResponse> deleteThrow(@Header("Authorization") String auto,@Path("ids") String ids);


    // 修改日常投放
    @PUT("ac-service/throw/{id}")
    Observable<BaseResponse<DailyThrowResponse>> updateThrow(@Header("Authorization") String auto,
                                                             @Body DailyThrowResponse dailyThrowResponse,
                                                             @Path("id") int Id);


    // 增加日常投放模板
    @POST("ac-service/template/add")
    Observable<BaseResponse<TemplateResponse>>  addTemplate(@Header("Authorization") String auto,
                                                            @Body TemplateResponse templateResponse);

    // 删除日常投放模板
    @DELETE("ac-service/template/{ids}")
    Observable<BaseResponse> deleteTemplate(@Header("Authorization") String auto,@Path("ids") String ids);

    // 修改日常投放模板
    @PUT("ac-service/template/update")
    Observable<BaseResponse<TemplateResponse>> updateTemplate(@Header("Authorization") String auto,
                                                             @Body TemplateResponse templateResponse);

    // 获取所有日常投放模板数据
    @GET("ac-service/template/all/")
    Observable<BaseResponse<TypeTemplateResponse>> getTemplate(@Header("Authorization") String auto,
                                                            @Query("pageNum") int pageNum);

    // 获取固定投入类型
    @GET("ac-service/type/fixed")
    Observable<BaseResponse<String[]>> getGudingType();

    // 获取固定投入类型
    @GET("ac-service/type/observe/name")
    Observable<BaseResponse<String[]>> getObserveType();


    // 获取 药品
    @GET("ac-service/type/medicine")
    Observable<BaseResponse<String[]>> getMedicineType();

    // 获取购买物品
    @GET("ac-service/type/cost")
    Observable<BaseResponse<String[]>> getGoumaiType();

    // 获取销售物品
    @GET("ac-service/type/sale")
    Observable<BaseResponse<String[]>> getXiaoshouType();

    // 获取饵料名称
    @GET("ac-service/type/feed")
    Observable<BaseResponse<String[]>> getErliaoType();

    // 获取 用户列表
//    @GET("ac-service/user/expert")
//    Observable<BaseResponse<PageResponse<RoleUserInfo>>> queryRoleUserInfo(@Header("Authorization") String auto,
//                                                                       @Query("pageNum") int pageNum);

    // 获取 用户列表
    @GET("admin/lists")
    Observable<BaseResponse<PageResponse<RoleUserInfo>>> queryRoleUserInfo(@Header("Authorization") String auto,
                                                                           @Query("pageNum") int pageNum,
                                                                           @Query("role") int role);


    // 获取 用户列表
    @GET("user-center/admin/lists")
    Observable<BaseResponse<PageResponse<RoleUserInfo>>> queryRoleExpertInfo(@Header("Authorization") String auto,
                                                                           @Query("pageNum") int pageNum,
                                                                           @Query("role") int role);

    // 获取所有日常投放数据
    @GET("ac-service/throw/expert")
    Observable<BaseResponse<TypeThrowResponse>> getThrowByUser(@Header("Authorization") String auto,
                                                         @Query("pageNum") int pageNum,
                                                               @Query("username") String username);


    // 获取所有网关
    @GET("micro-iot/termdef/listAllTerms")
    Observable<BaseResponse<TypeTermResponse>> getAllTerms(@Header("Authorization") String auto,
                                                           @Query("pageNum") int pageNum);


    // 增加终端
    @POST("micro-iot/termdef/addTerm")
    Observable<BaseResponse<TermResponse>>  addTerm(@Header("Authorization") String auto,
                                                            @Query("type") int type,
                                                            @Query("deveui") String deveui,
                                                            @Query("manu") String manu,
                                                            @Query("product") String prod,
                                                            @Query("name") String name,
                                                            @Query("user") String user);

    // 修改终端
    @POST("micro-iot/termdef/updateTerm")
    Observable<BaseResponse> updateTerm(@Header("Authorization") String auto,
                                        @Query("id") int id,
                                        @Query("deveui") String deveui,
                                        @Query("name") String name,
                                        @Query("user") String user);
    // 删除终端
    @DELETE("micro-iot/termdef/deleteSensor")
    Observable<BaseResponse> deleteTerm(@Header("Authorization") String auto,@Query("id") String id);

    // 1 查询终端类型 终端厂商 产品
    @GET("micro-iot/termdef/listTermManus")
    Observable<BaseResponse<List<ManuInfo>>> getAllManus(@Header("Authorization") String auto);

    // 2 查询生产单元id
    @GET("micro-iot/celldef/listUserCells")
    Observable<BaseResponse<PageResponse<CellResponse>>> getAllUserCells(@Header("Authorization") String auto,
                                                                         @Query("username") String username,
                                                                         @Query("pageNum") int pageNum);


    // 获取 终端传感器
    @GET("micro-iot/sensordef/termSensors")
    Observable<BaseResponse<List<SensorResponse>>> getAllSensors(@Header("Authorization") String auto,
                                                               @Query("term_id") int termid);

    // 增加传感器
    @POST("micro-iot/sensordef/addSensor")
    Observable<BaseResponse<SensorResponse>>  addSensor(@Header("Authorization") String auto,
                                                    @Query("cell_id") int cell_id,
                                                    @Query("term_id") int term_id,
                                                    @Query("addr") int addr,
                                                    @Query("product") String sensor_type,
                                                    @Query("sensor_name") String sensor_name);

    // 获取 自定义传感器类型
    @GET("micro-iot/sensordef/listProduct")
    Observable<BaseResponse<String[]>> getDefSensorType(@Header("Authorization") String auto,
                                                                 @Query("ttid") int termtype);

    // 删除 传感器
    @DELETE("micro-iot/sensordef/deleteSensor")
    Observable<BaseResponse> deleteSensor(@Header("Authorization") String auto,@Query("sid") String sid);

    // 修改传感器
    @POST("micro-iot/sensordef/updateSensor")
    Observable<BaseResponse> updateSensor(@Header("Authorization") String auto,
                                        @Query("id") int id,
                                                          @Query("cell_id") int cell_id,
                                                          @Query("addr") int addr,
                                                          @Query("sensor_name") String sensor_name);

    // 增加 设备
    @POST("micro-iot/equipdef/addEquip")
    Observable<BaseResponse<EquipResponse>>  addEquip(@Header("Authorization") String auto,
                                                      @Query("cell_id") int cell_id,
                                                      @Query("term_id") int term_id,
                                                      @Query("road") int road,
                                                      @Query("addr") int addr,
                                                      @Query("equip_type") String equip_type,
                                                      @Query("equip_name") String equip_name);

    // 获取 设备类型
    @GET("micro-iot/equipdef/showEquipType")
    Observable<BaseResponse<List<EquipType>>> getEquipType(@Header("Authorization") String auto);


    // 获取 终端设备
    @GET("micro-iot/equipdef/listTermEquip")
    Observable<BaseResponse<List<EquipResponse>>> getAllEquips(@Header("Authorization") String auto,
                                                                 @Query("term_id") int termid);

    // 删除 设备
    @DELETE("micro-iot/equipdef/delEquip")
    Observable<BaseResponse> deleteEquip(@Header("Authorization") String auto,@Query("id") String id);

    // 修改设备
    @POST("micro-iot/equipdef/updateEquip")
    Observable<BaseResponse> updateEquip(@Header("Authorization") String auto,
                                          @Query("id") int id,
                                          @Query("cell_id") int cell_id,
                                          @Query("term_id") int term_id,
                                          @Query("addr") int addr,
                                          @Query("road") int road,
                                          @Query("equip_name") String equip_name);


    // 删除  生产单元
    @DELETE("micro-iot/celldef/delCell")
    Observable<BaseResponse> deleteCell(@Header("Authorization") String auto,@Query("id") String id);

    // 增加 生产单元
    @POST("micro-iot/celldef/addCell")
    Observable<BaseResponse<CellResponse>>  addCell(@Header("Authorization") String auto,
                                                      @Query("length") double length,
                                                      @Query("width") double width,
                                                      @Query("longitude") double longitude,
                                                      @Query("latitude") double latitude,
                                                      @Query("cell_type") String product,
                                                      @Query("agri_prod") String prod,
                                                      @Query("cell_name") String cell_name,
                                                      @Query("user_name") String user_name);

    // 增加 塘口
    @POST("micro-aquaculture/pond/add")
    Observable<BaseResponse> addFishPond(@Header("Authorization") String auto,
                                                           @Query("name") String pond_name,
                                                           @Query("length") double length,
                                                           @Query("width") double width,
                                                           @Query("depth") double depth,
                                                           @Query("orientation") String orientation,
                                                           @Query("longitude") double longitude,
                                                           @Query("latitude") double latitude,
                                                           @Query("address") String address,
                                                           @Query("product") String product,
                                                           @Query("username") String username);

    // 查询 塘口 分页
    @GET("micro-aquaculture/pond/queri")
    Observable<BaseResponse<BaseFishPondPageResponse<FishPondResponse>>> getUserPonds(@Header("Authorization") String auto,
                                                                                      @Query("username") String username,
                                                                                      @Query("pageNum") int pageNum,
                                                                                      @Query("pageSize") int pageSize);

    // 查询 塘口 全部
    @GET("micro-aquaculture/pond/queriAll")
    Observable<BaseResponse<List<FishPondResponse>>> getAllUserPonds(@Header("Authorization") String auto,
                                                                     @Query("username") String username);

    // 删除 塘口
    @DELETE("micro-aquaculture/pond/delete/{id}")
    Observable<BaseResponse> deleteFishPond(@Header("Authorization") String auto, @Query("id") int id);

    // 更新 塘口
    @PUT("micro-aquaculture/pond/update/{id}")
    Observable<BaseResponse> updateFishPond(@Header("Authorization") String auto,
                                            @Path("id") int id,
                                            @Query("name") String pond_name,
                                            @Query("length") double length,
                                            @Query("width") double width,
                                            @Query("depth") double depth,
                                            @Query("orientation") String orientation,
                                            @Query("longitude") double longitude,
                                            @Query("latitude") double latitude,
                                            @Query("address") String address,
                                            @Query("product") String product,
                                            @Query("username") String username);

    // 增加 投入品
    @Multipart
    @POST("micro-aquaculture/inputs/add")
    Observable<BaseResponse> addInput(@Header("Authorization") String auto,
                                                     @Query("type") String inputType,
                                                     @Query("name") String inputName,
                                                     @Query("manufacture") String manufacturer,
                                                     @Part List<MultipartBody.Part> pictures,
                                                     @Query("remarks") String remarks,
                                                     @Query("username") String username);

    // 查找 投入品 分页
    @GET("micro-aquaculture/inputs/queri")
    Observable<BaseResponse<BaseInputPageResponse<InputResponse>>> getUserInputs(@Header("Authorization") String auto,
                                                                @Query("username") String username,
                                                                @Query("pageNum") int pageNum,
                                                                @Query("pageSize") int pageSize);

    // 查找 投入品 全部
    @GET("micro-aquaculture/inputs/queriAll")
    Observable<BaseResponse<List<InputResponse>>> getAllUserInputs(@Header("Authorization") String auto,
                                                                                    @Query("username") String username);

    // 删除 投入品
    @DELETE("micro-aquaculture/inputs/delete/{id}")
    Observable<BaseResponse> deleteInput(@Header("Authorization") String auto,
                                         @Query("id") int id);

    // 更新 投入品
    @Multipart
    @PUT("micro-aquaculture/inputs/update/{id}")
    Observable<BaseResponse> updateInput(@Header("Authorization") String auto,
                                         @Path("id") int id,
                                         @Query("type") String inputType,
                                         @Query("name") String inputName,
                                         @Query("manufacture") String manufacturer,
                                         @Part List<MultipartBody.Part> pictures,
                                         @Query("remarks") String remarks,
                                         @Query("username") String username);

    // 增加 鱼苗投入
    @POST("micro-aquaculture/fishinput/add")
    Observable<BaseResponse> addFishInput(@Header("Authorization") String auto,
                                                             @Query("type") String fishType,
                                                             @Query("amount") double amount,
                                                             @Query("unit") String unit,
                                                             @Query("date") String date,
                                                             @Query("pond") List<String> pond,
                                                             @Query("batchNumber") String batchNumber,
                                                             @Query("username") String username);

    // 删除 鱼苗投入
    @DELETE("micro-aquaculture/fishinput/delete/{id}")
    Observable<BaseResponse> deleteFishInput(@Header("Authorization") String auto,
                                             @Query("id") int id);

    // 更新 鱼苗投入
    @PUT("micro-aquaculture/fishinput/update/{id}")
    Observable<BaseResponse> updateFishInput(@Header("Authorization") String auto,
                                             @Path("id") int id,
                                             @Query("type") String fishType,
                                             @Query("amount") double amount,
                                             @Query("unit") String unit,
                                             @Query("date") String date,
                                             @Query("pond") List<String> pond,
                                             @Query("batchNumber") String batchNumber,
                                             @Query("username") String username);

    // 查询 鱼苗投入 分页
    @GET("micro-aquaculture/fishinput/queri")
    Observable<BaseResponse<BaseFishInputPageResponse<FishInputResponse>>> getUserFishInput(@Header("Authorization") String auto,
                                                                                               @Query("username") String username,
                                                                                               @Query("pageNum") int pageNum,
                                                                                               @Query("pageSize") int pageSize);

    //查询 鱼苗投入 全部
    @GET("micro-aquaculture/fishinput/queriAll")
    Observable<BaseResponse<List<FishInputResponse>>> getAllUserFishInput(@Header("Authorization") String auto,
                                                                          @Query("username") String username);

    // 增加 模板
    @POST("micro-aquaculture/feedtemplate/add")
    Observable<BaseResponse> addFeedingTemplate(@Header("Authorization") String auto,
                                                                         @Query("name") String name,
                                                                         @Query("batchNumber") String batchNumber,
                                                                         @Query("pond") String pond,
                                                                         @Query("food") String food,
                                                                         @Query("amount") double amount,
                                                                         @Query("unit") String unit,
                                                                         @Query("time") String time,
                                                                         @Query("remarks") String remarks,
                                                                         @Query("username") String username);

    // 修改 模板
    @PUT("micro-aquaculture/feedtemplate/update/{id}")
    Observable<BaseResponse> updateFeedingTemplate(@Header("Authorization") String auto,
                                                   @Path("id") int id,
                                                   @Query("name") String name,
                                                   @Query("batchNumber") String batchNumber,
                                                   @Query("pond") String pond,
                                                   @Query("food") String food,
                                                   @Query("amount") double amount,
                                                   @Query("unit") String unit,
                                                   @Query("time") String time,
                                                   @Query("remarks") String remarks,
                                                   @Query("username") String username);

    // 删除 模板
    @DELETE("micro-aquaculture/feedtemplate/delete/{id}")
    Observable<BaseResponse> deleteFeedingTemplate(@Header("Authorization") String auto,
                                                   @Path("id") int id);

    // 查询 模板 分页
    @GET("micro-aquaculture/feedtemplate/queri")
    Observable<BaseResponse<BaseFeedingTemplatePageResponse<FeedingTemplateResponse>>> getUserTemplate(@Header("Authorization") String auto,
                                                                                                       @Query("username") String username,
                                                                                                       @Query("pageNum") int pageNum,
                                                                                                       @Query("pageSize") int pageSize);

    // 查询 模板 全部
    @GET("micro-aquaculture/feedtemplate/queriAll")
    Observable<BaseResponse<List<FeedingTemplateResponse>>> getAllUserTemplate(@Header("Authorization") String auto,
                                                                               @Query("username") String username);

    // 增加 饲料投入
    @POST("micro-aquaculture/dailyfeeding/add")
    Observable<BaseResponse> addFeedingFood(@Header("Authorization") String auto,
                                                                 @Query("name") String templateName,
                                                                 @Query("batchNumber") String batchNumber,
                                                                 @Query("pond") String pond,
                                                                 @Query("food") String food,
                                                                 @Query("amount") double amount,
                                                                 @Query("unit") String unit,
                                                                 @Query("time") String time,
                                                                 @Query("remarks") String remarks,
                                                                 @Query("username") String username);

    // 修改 饲料投入
    @PUT("micro-aquaculture/dailyfeeding/update/{id}")
    Observable<BaseResponse> updateFeedingFood(@Header("Authorization") String auto,
                                               @Path("id") int id,
                                               @Query("name") String templateName,
                                               @Query("batchNumber") String batchNumber,
                                               @Query("pond") String pond,
                                               @Query("food") String food,
                                               @Query("amount") double amount,
                                               @Query("unit") String unit,
                                               @Query("time") String time,
                                               @Query("remarks") String remarks,
                                               @Query("username") String username);

    // 查询 饲料投入 分页
    @GET("micro-aquaculture/dailyfeeding/queri")
    Observable<BaseResponse<BaseFeedingFoodPageResponse<FeedingFoodResponse>>> getUserFeedingFood(@Header("Authorization") String auto,
                                                                                                  @Query("username") String username,
                                                                                                  @Query("pageNum") int pageNum,
                                                                                                  @Query("pageSize") int pageSize);

    // 查询 饲料投入 全部
    @GET("micro-aquaculture/dailyfeeding/queriall")
    Observable<BaseResponse<List<FeedingFoodResponse>>> getAllUserFeedingFood(@Header("Authorization") String auto,
                                                                              @Query("username") String username);

    // 删除 饲料投入
    @DELETE("micro-aquaculture/dailyfeeding/delete/{id}")
    Observable<BaseResponse> deleteFeedingFood(@Header("Authorization") String auto,
                                               @Path("id") int id);

    // 增加 调水剂投入
    @POST("micro-aquaculture/reagent/add")
    Observable<BaseResponse> addReagentInput(@Header("Authorization") String auto,
                                                                   @Query("batchNumber") String batchNumber,
                                                                   @Query("pond") String pond,
                                                                   @Query("reagent") String reagent,
                                                                   @Query("amount") double amount,
                                                                   @Query("unit") String unit,
                                                                   @Query("time") String time,
                                                                   @Query("remarks") String remarks,
                                                                   @Query("username") String username);

    // 查找 调水剂投入 分页
    @GET("micro-aquaculture/reagent/queri")
    Observable<BaseResponse<BaseReagentInputPageResponse<ReagentInputResponse>>> getUserReagentInput(@Header("Authorization") String auto,
                                                                                                     @Query("username") String username,
                                                                                                     @Query("pageNum") int pageNum,
                                                                                                     @Query("pageSize") int pageSize);

    // 查找 调水剂投入 全部
    @GET("micro-aquaculture/reagent/queriAll")
    Observable<BaseResponse<List<ReagentInputResponse>>> getAllUserReagentInput(@Header("Authorization") String auto,
                                                                                @Query("username") String username);

    // 更新 调水剂投入
    @PUT("micro-aquaculture/reagent/update/{id}")
    Observable<BaseResponse> updateReagentInput(@Header("Authorization") String auto,
                                                @Path("id") int id,
                                                @Query("batchNumber") String batchNumber,
                                                @Query("pond") String pond,
                                                @Query("reagent") String reagent,
                                                @Query("amount") double amount,
                                                @Query("unit") String unit,
                                                @Query("time") String time,
                                                @Query("remarks") String remarks,
                                                @Query("username") String username);

    // 删除 调水剂投入
    @DELETE("micro-aquaculture/reagent/delete/{id}")
    Observable<BaseResponse> deleteReagentInput(@Header("Authorization") String auto,
                                                @Path("id") int id);



    // 获取 生产单元 产品类型
    @GET("micro-iot/celldef/listCellTypes")
    Observable<BaseResponse<List<CellProTypeResponse>>> getCellProType(@Header("Authorization") String auto);

    // 获取 生产单元 产品
    @GET("micro-iot/celldef/listAgriProducts")
    Observable<BaseResponse<String[]>> getCellPro(@Header("Authorization") String auto,@Query("celltype") String celltype);


    // 修改设备
    @POST("micro-iot/celldef/updateCell")
    Observable<BaseResponse> updateCell(@Header("Authorization") String auto,
                                         @Query("id") int id,
                                        @Query("length") double length,
                                        @Query("width") double width,
                                        @Query("longitude") double longitude,
                                        @Query("latitude") double latitude,
                                        @Query("cell_type") String product,
                                        @Query("agri_prod") String prod,
                                        @Query("cell_name") String cell_name,
                                        @Query("user_name") String user_name);

    // 获取  生产单元 设备
    @GET("micro-iot/equipdef/listEquips")
    Observable<BaseResponse<List<EquipResponse>>> getCellEquips(@Header("Authorization") String auto,
                                                                @Query("cell_id") int cellId);

    // 获取  生产单元 设备
    @GET("micro-iot/opt/equQuery")
    Observable<BaseResponse<List<EquipStatusResponse>>> getCellEquipsStatus(@Header("Authorization") String auto,
                                                                            @Query("cell_id") int cellId);

    // 获取  生产单元 原始数据
    @GET("micro-iot/sensordef/cellParams")
    Observable<BaseResponse<List<OrgResponse>>> getOrgData(@Header("Authorization") String auto,
                                                           @Query("all_env") boolean all_env,
                                                           @Query("cell_id") int cellId);


    // 获取  生产单元 实时数据
    @GET("micro-iot/opt/rtQuery")
    Observable<BaseResponse<List<RealResponse>>> getRealData(@Header("Authorization") String auto,
                                                               @Query("all_env") boolean all_env,
                                                               @Query("cell_id") int cellId);

    // 获取  生产单元 历史数据
    @GET("micro-iot/opt/hisQuery")
    Observable<BaseResponse<List<HisResponse>>> getHisData(@Header("Authorization") String auto,
                                                           @Query("all_env") boolean all_env,
                                                           @Query("cell_id") int cellId,
                                                           @Query("start_time") String start_time,
                                                           @Query("end_time") String end_time);

    // 控制 设备
    @POST("micro-iot/opt/control")
    Observable<BaseResponse<CtrlResponse>>  ctrlEquip(@Header("Authorization") String auto,
                                                    @Query("appusrid") int appusrid,
                                                    @Query("equip_id") int equip_id,
                                                    @Query("ison_fg") int ison_fg);

    @GET("ac-service/aquacu/all")
    Observable<BaseResponse<PageResponse<ZhishiBean>>> queryZhishi(@Query("pageNum") int pageNum);

    @GET("ac-service/aquacu/{id}")
    Observable<BaseResponse<ZhishiBean>> queryDetailZhishiInfo(@Path("id") int id);



    // 获取  阈值控制 状态
    @GET("micro-iot/envctldef/lstThCtlParam")
    Observable<BaseResponse<List<AutoStatusResponse>>> getAutoEquipsStatus(@Header("Authorization") String auto,
                                                                           @Query("cell_id") int cellId);

    // 控制 阈值 开关
    @POST("micro-iot/envctldef/updateThCtl")
    Observable<BaseResponse<Boolean>>  ctrlAuto(@Header("Authorization") String auto,
                                                           @Query("id") int id,
                                                           @Query("env_type") String env_type,
                                                           @Query("param_id") int param_id,
                                                           @Query("wnup") float wnup,
                                                           @Query("wndw") float wndw,
                                                           @Query("actup") float actup,
                                                           @Query("actdw") float actdw,
                                                           @Query("autofg") int ison_fg);

    // 获取  阈值控制 状态
    @GET("micro-iot/envctldef/lstTmCtlParam")
    Observable<BaseResponse<List<AutoTimeStatusResponse>>> getAutoTimeStatus(@Header("Authorization") String auto,
                                                                             @Query("cell_id") int cellId);

    // 控制 时间 开关
    @POST("micro-iot/envctldef/updateTmCtl")
    Observable<BaseResponse<Boolean>>  ctrlAutoTime(@Header("Authorization") String auto,
                                                @Query("id") int id,
                                                @Query("env_type") String env_type,
                                                @Query("time") String time,
                                                @Query("opt") int opt,
                                                @Query("autofg") int ison_fg);

    // 获取  自动控制 日志
    @GET("micro-iot/opt/autoActLogQuery")
    Observable<BaseResponse<List<AutoLogResponse>>> getAutoLog(@Header("Authorization") String auto,
                                                               @Query("cell_id") int cellId,
                                                               @Query("start_time") String start_time,
                                                               @Query("end_time") String end_time);

    // 获取  预警 日志
    @GET("micro-iot/opt/warnLogQuery")
    Observable<BaseResponse<List<WarnLogResponse>>> getWarnLog(@Header("Authorization") String auto,
                                                               @Query("cell_id") int cellId,
                                                               @Query("start_time") String start_time,
                                                               @Query("end_time") String end_time);


}
