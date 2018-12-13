package com.annis.dk.base;

import com.annis.dk.bean.*;
import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author Lee
 * @date 2018/12/11 18:49
 * @Description
 */
public interface HttpApi {
/*    状态说明

1.审核中
            isnew=1  已申请贷款
            ispass=0 正在审核中

2.审核未通过（审核未通过）
    isnew=1  申请贷款
    ispass=1 审核失败

3.审核通过（审核通过支付利息）
    isnew=1  申请贷款
    ispass=2 通过审核
    mloan=0  是否发放贷款

4.发放贷款（审核通过已放款）
    isnew=1  申请贷款
    ispass=2 通过审核
    mloan=1  是否发放贷款

5.可再次申请贷款（还款成功）
    isnew=0  申请贷款*/

    /*** 短信接口还没写***/

    /**
     * 接口功能：读取调用接口要用到的key
     *
     * @return
     */
    @GET("/API.asmx/getkey")
    Flowable<KeyEntity> getKey();

    /**
     * 用户登录接口
     * 接口功能：取得登录用户基础信息
     *
     * @param phone phone 必选 string 用户电话号码
     * @param key   必选 string MD5加密字符串
     * @return
     */
    @GET("/API.asmx/GetUser")
    Flowable<UserEntity> getUser(@Query("phone") String phone, @Query("key") String key);

    /**
     * 2.读取身份认证信息
     * 接口功能：读取登录用户身份认证信息
     *
     * @param uid 必选 int 用户编号
     * @param key 必选 string MD5加密字符串
     * @return
     */
    @GET("/API.asmx/GetIDCard")
    Flowable<IDCardEntity> getIDCard(@Query("uid") String uid, @Query("key") String key);

    /**
     * 3.保存身份证信息
     * 接口功能：保存用户身份证信息
     *
     * @param uid      必选 int 用户编号
     * @param positive string 正面照图片地址(url编码utf-8)
     * @param back     string 背面照图片地址(url编码utf-8)
     * @param hold     string 手持照图片地址(url编码utf-8)
     * @param key      必选 string MD5加密字符串
     * @return
     */
    @GET("/API.asmx/SaveIDCard")
    Flowable<IsSave> saveIDCard(@Query("uid") String uid, @Query("positive") String positive
            , @Query("back") String back, @Query("hold") String hold, @Query("key") String key);

    /**
     * 4.读取运营商认证信息
     * 接口功能：读取登录用户运营商紧急联系人信息
     *
     * @param uid 必选 int 用户编号
     * @param key string MD5加密字符串
     * @return
     */
    @GET("/API.asmx/GetOperator")
    Flowable<MyContacts> getOperator(@Query("uid") String uid, @Query("key") String key);

    /**
     * 5.保存运营商信息
     * 接口功能：保存用户运营商信息
     * <p>
     * uid 必选 int 用户编号
     * name1 string 联系人1
     * contacts1 string 关系1
     * phone1 string 电话1
     * name2 string 联系2
     * contacts2 string 关系2
     * phone2 string 联系2
     * name3 string 联系3
     * contacts3 string 关系3
     * phone3 string 联系3
     * key 必选 string MD5加密字符串
     *
     * @return
     */
    @GET("/API.asmx/SaveOperator")
    Flowable<IsSave> saveOperator(@Query("uid") String uid, @Query("key") String key,
                                  @Query("name1") String name1, @Query("contacts1") String contacts1, @Query("phone1") String phone1,
                                  @Query("name2") String name2, @Query("contacts1") String contacts2, @Query("phone1") String phone2,
                                  @Query("name3") String name3, @Query("contacts1") String contacts3, @Query("phone1") String phone3
    );

    /**
     * 读取支付宝认证信息
     * 接口功能：读取登录用户支付宝认证信息
     * <p>
     * uid 必选 int 用户编号
     * key 必选 string MD5加密字符串
     *
     * @return
     */
    @GET("/API.asmx/GetAlipay")
    Flowable<AlipayInfo> getAlipay(@Query("uid") String uid, @Query("key") String key);

    /**
     * 7.保存支付宝信息
     * 接口功能：保存用户支付宝信息
     * <p>
     * uid 必选 int 用户编号
     * key 必选 string MD5加密字符串
     * <p>
     * * alipay string 支付宝账号
     * * alipaycipher string 支付宝密码
     * * zmImg string 芝麻信用截图地址(url编码utf-8)
     *
     * @return
     */
    @GET("/API.asmx/SaveAlipay")
    Flowable<IsSave> saveAlipay(@Query("uid") String uid, @Query("key") String key,
                                @Query("alipay") String alipay, @Query("alipaycipher") String alipaycipher, @Query("zmImg") String zmImg);

    /**
     * 8.读取银行卡认证信息
     * 接口功能：读取登录用户银行卡认证信息
     *
     * @param uid
     * @param key
     * @return
     */
    @GET("/API.asmx/GetBank")
    Flowable<BankInfo> getBank(@Query("uid") String uid, @Query("key") String key);

    /**
     * 9.保存银行信息
     * 接口功能：保存用户银行信息
     * id 必选 int 用户编号
     * key 必选 string MD5加密字符串
     * bankcard string 银行卡用户名称(对应银行卡名称)
     * idnumber bigint 身份证号
     * savingscard string 储蓄卡号
     * phone string 预留手机号
     *
     * @return
     */
    @GET("/API.asmx/SaveBank")
    Flowable<IsSave> saveBank(@Query("uid") String uid, @Query("key") String key,
                              @Query("bankcard") String bankcard, @Query("idnumber") String idnumber,
                              @Query("savingscard") String savingscard, @Query("phone") String phone);

    /**
     * 10.读取贷款信息
     * 接口功能：读取登录用户贷款信息
     *
     * @param uid
     * @param key
     * @return
     */
    @GET("/API.asmx/GetLoan")
    Flowable<LoanInfo> getLoan(@Query("uid") String uid, @Query("key") String key);

    /**
     * 11.申请贷款（初始化贷款信息）
     * 接口功能：保存新的贷款信息
     *
     * @param uid
     * @param key
     * @return
     */
    @GET("/API.asmx/SaveLoan")
    Flowable<IsSave> saveLoan(@Query("uid") String uid, @Query("key") String key);

    /**
     * 12.读取网站信息
     * 接口功能：读取网站信息
     *
     * @param key
     * @return
     */
    @GET("/API.asmx/GetWebsite")
    Flowable<WebSite> GetWebsite(@Query("key") String key);

    /**
     * 图片上传
     * 说明：
     * 提交方式我不知道java怎么写我这边做的测试是用winform做的。
     * 我把代码写上你看有可能参考一下
     * webUrl = "http://43.229.152.127/UpLoadFiles.aspx"
     * localFileName="E:\1.jpg"
     * <p>
     * System.Net.WebClient myWebClient = new System.Net.WebClient();
     * byte[] byteArray = myWebClient.UploadFile(webUrl, "POST", localFileName);
     *
     * @param file
     * @return
     */
    @POST("/UpLoadFiles.aspx")
    Flowable<ImgResponse> uploadFile(@Part MultipartBody.Part file);
}