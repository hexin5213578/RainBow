package com.YiDian.RainBow.utils;


import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.YiDian.RainBow.main.fragment.find.bean.LikeUserBean;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
import com.YiDian.RainBow.main.fragment.home.bean.FirstCommentBean;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.dynamic.bean.WriteDevelopmentBean;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.login.bean.LoginBean;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.main.fragment.home.bean.MyFollowBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.OneCommentBean;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;
import com.YiDian.RainBow.regist.bean.RegistBean;
import com.YiDian.RainBow.remember.bean.RememberPwdBean;
import com.YiDian.RainBow.setpwd.bean.GetPhoneCodeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apis {
    //最新动态
    @GET("content/getContentByTime")
    Observable<NewDynamicBean> getNewDynamic(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int pagesize);

    //附近动态
    @GET("content/getNearbyContents")
    Observable<NewDynamicBean> getNearDynamic(@Query("userId")int userid,@Query("lat") double lat,@Query("lng")double lng,@Query("page")int page,@Query("pageSize")int pagesize);

    //关注动态
    @GET("content/getAttentionUserContents")
    Observable<NewDynamicBean> getAttDynamic(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //获取热门动态
    @GET("content/getHotContent")
    Observable<NewDynamicBean> getHotDynamic(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //发送验证码
    @GET("user/sendSms")
    Observable<GetPhoneCodeBean> getPhoneCode(@Query("phoneNum")String phone);

    //注册
    @GET("user/register")
    Observable<RegistBean> doRegist(@Query("phoneNum")String phone,@Query("password")String pwd);

    //密码登录
    @GET("user/userLogin")
    Observable<LoginBean> doPwdLogin(@Query("phoneNum")String phone,@Query("password")String pwd,@Query("accountType") int type,@Query("lng")double lng,@Query("lat")double lat);

    //QQ登录
    @GET("user/userLogin")
    Observable<LoginBean> doQqLogin(@Query("accountType") int type,@Query("qqOpenID")String qqId,@Query("lng")double lng,@Query("lat")double lat);

    //微信登录
    @GET("user/userLogin")
    Observable<LoginBean> doWechatLogin(@Query("accountType") int type,@Query("weChatOpenId")String wechatId,@Query("lng")double lng,@Query("lat")double lat);

    //完善资料
    @GET("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteMsg(@Query("nickName")String username,@Query("headImg")String headImg,@Query("birthday")String birthday,@Query("userRole")String gender,@Query("isSingle")int isSingle,@Query("id")int userid);

    //修改 忘记密码
    @GET("user/updatePassword")
    Observable<RememberPwdBean> doRemeberPwd(@Query("password")String pwd,@Query("phoneNum")String phone);

    //获取UpdateToken
    @GET("img/uploadToken")
    Observable<SaveMsgSuccessBean> getUpdateToken();

    //发布动态
    @POST("content/writeContent")
    Observable<WriteDevelopmentBean> doWriteDevelopment(@Query("userId")int userid,@Query("contentInfo")String contentinfo,@Query("contentImg")String contentImg,@Query("lng")Double lng,@Query("lat")Double lat,@Query("IsOpen")int isopen,@Query("ImgType")int ImgType,@Query("status")int status,@Query("area")String area);

    //点赞
    @POST("click/insertClick")
    Observable<DianzanBean> doDianzan(@Query("clickBy")int id,@Query("clickType")int type,@Query("typeId")int comentId);

    //取消点赞
    @POST("click/delClick")
    Observable<DianzanBean> doCancleDianzan(@Query("clickType")int type,@Query("typeId")int comentId,@Query("userId") int id);

    //关注用户
    @POST("fans/insertFans")
    Observable<FollowBean> doFollow(@Query("fansId")int followid,@Query("userId")int byfollowid);

    //取消关注用户
    @POST("fans/deleteByUserIdAndFansId")
    Observable<FollowBean> doCancleFollow(@Query("fansId")int followid,@Query("userId")int byfollowid);

    //我的关注
    @GET("fans/selectFansByFansId")
    Observable<MyFollowBean> doGetMyFollow(@Query("fansId")int userid,@Query("page")int page,@Query("pageSize")int pagesize);

    //动态详情
    @GET("content/getContentInfoById")
    Observable<DynamicDetailsBean> dogetDynamicDetails(@Query("contentId")int contentid,@Query("userId")int userid);

    //写评论
    @POST("comment/writeComment")
    Observable<CollectDynamicBean> doWriteComment(@Query("userId")int userid,@Query("beCommentUserId")int beuserid,@Query("commentInfo")String info,@Query("commentType")int type,@Query("fatherId")int fatherid,@Query("replyType")int replyid);

    //查评论
    @GET("comment/getComment")
    Observable<CommentBean> doGetComment(@Query("fatherId")int fatherid,@Query("commentType")int type,@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //查询一条评论
    @GET("comment/getCommentByIdAndUserId")
    Observable<OneCommentBean> doGetCommentbyId(@Query("id")int commentid,@Query("userId")int userid);


    //收藏动态
    @POST("collect/addCollect")
    Observable<CollectDynamicBean> doCollectDynamic(@Query("userId")int userid,@Query("contentId")int contentid);

    //取消收藏动态
    @POST("collect/delCollect")
    Observable<CollectDynamicBean> doCancleCollectDynamic(@Query("userId")int userid,@Query("contentId")int contentid);


    //查询我的草稿箱
    @GET("content/selectAllDrafts")
    Observable<SelectAllDraftsBean> doGetAllDraftsBy(@Query("userId")int userid,@Query("page") int page,@Query("pageSize")int pagesize);

    //直接发表草稿
    @POST("/content/updateDraft")
    Observable<CollectDynamicBean> doUpdateDraft(@Query("id")int draftid,@Query("status")int status);

    //删除草稿
    @POST("content/deleteDraft")
    Observable<CollectDynamicBean> doDeleteDraft(@Query("draftId")int draftid,@Query("userId")int userid);

    //删除用户下动态
    @POST("content/delContent")
    Observable<CollectDynamicBean> doDeleteDynamic(@Query("contentId")int contentid,@Query("userId")int userid);


    //匹配 查询所有用户信息
    @GET("user/selectUser")
    Observable<AllUserInfoBean> doGetAllUserInfo(@Query("id")int userid,@Query("lng") double lng,@Query("lat")double lat,@Query("page")int page,@Query("pageSize")int size);

    //匹配 查询筛选的用户信息
    @GET("user/selectUserOf")
    Observable<AllUserInfoBean> doGetFilterUser(@Query("id")int userid,@Query("age")String age,@Query("distancing")int distance,@Query("isSingle")int issingle,@Query("userRole")String role,@Query("lng")double lng,@Query("lat")double lat,@Query("page")int page,@Query("pageSize")int size);

    //左滑右滑
    @POST("favorite/insertUser")
    Observable<LikeUserBean> doLikeUser(@Query("userId")int userid,@Query("bUserId")int buserid,@Query("likeType")int type);
}
