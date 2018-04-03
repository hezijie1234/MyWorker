package com.gongyou.worker.network;


import com.gongyou.worker.mvp.bean.ResponseInfo;
import com.gongyou.worker.mvp.bean.first.ListFilterInfo;
import com.gongyou.worker.mvp.bean.second.FindInfo;
import com.gongyou.worker.mvp.bean.second.LoginInfo;
import com.gongyou.worker.mvp.bean.second.MsgViewInfo;
import com.gongyou.worker.mvp.bean.second.SendSMSInfo;
import com.gongyou.worker.mvp.bean.second.StringInfo;
import com.gongyou.worker.mvp.bean.second.VerifyCode;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/3/13.
 */

public interface ResponseInfoAPI {

    @POST(Api.login.login)
    Observable<ResponseInfo<LoginInfo>> login(@Query("mobile") String mobile, @Query("password") String pwd, @Query("client_id") String clientid, @Query("is_android") int i);

    @POST(Api.find_fragment.getListFilterData)
    Observable<ResponseInfo<ListFilterInfo>> getListFilterData(@Query("token") String token, @Query("lng") double lng,
                                                               @Query("lat") double lat, @Query("page") int page,
                                                               @Query("profession_id") String profession_id,
                                                               @Query("min_wage") int min_wage, @Query("max_wage") int max_wage);

    @POST(Api.login.imageCode)
    Observable<ResponseInfo<VerifyCode>> getImageCode(@Query("client_id") String clientid);

    @POST(Api.login.sms)
    Observable<ResponseInfo<SendSMSInfo>> sendSMS(@Query("mobile") String phone, @Query("verify_code") String ivCode,
                                                  @Query("type") int type, @Query("client_id") String clientid);

    @POST(Api.login.regist)
    Observable<ResponseInfo<LoginInfo>> mobileRegist(@Query("mobile") String phone, @Query("password") String pwd,
                                                     @Query("repassword") String repwd, @Query("code") String smsCode, @Query("invite_code") String o, @Query("is_android") int is_android);

    @POST(Api.mine_fragment.reSetPassword)
    Observable<ResponseInfo<StringInfo>> reSetPassword(@Query("mobile") String phone, @Query("password") String pwd,
                                                       @Query("repassword") String repwd, @Query("code") String smsCode);

    @POST(Api.login.thirdPartyLogin)
    Observable<ResponseInfo<LoginInfo>> thirdPartyLogin(@Query("nick") String nick, @Query("openid") String openid,
                                                        @Query("head_image") String head_image, @Query("province") String province, @Query("city") String city,
                                                        @Query("area") String area, @Query("sex") String sex, @Query("register_type") int register_type,
                                                        @Query("client_id") String clientid, @Query("is_android") int i, @Query("unionid") String unionid);

    //首页
    @POST(Api.find_fragment.mapData)
    //@Query("token") String token,
    Observable<ResponseInfo<List<FindInfo>>> getUserData(@Query("lng") double mLongitude, @Query("lat") double mLatitude);

    @FormUrlEncoded
    @POST(Api.refreshMsgView)
    Observable<ResponseInfo<MsgViewInfo>> refreshMsgView(@Field("token") String token);

}
