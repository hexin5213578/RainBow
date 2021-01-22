package com.YiDian.RainBow.utils;


import com.YiDian.RainBow.dynamic.bean.HotTopicBean;
import com.YiDian.RainBow.dynamic.bean.SelectFriendOrGroupBean;
import com.YiDian.RainBow.friend.bean.FriendBean;
import com.YiDian.RainBow.friend.bean.MyFansBean;
import com.YiDian.RainBow.friend.bean.MyfollowBean;
import com.YiDian.RainBow.friend.bean.RecommendGroupBean;
import com.YiDian.RainBow.friend.bean.RecommendUserBean;
import com.YiDian.RainBow.imgroup.bean.MyCreateGroupMsgBean;
import com.YiDian.RainBow.imgroup.bean.MyJoinGroupMsgBean;
import com.YiDian.RainBow.main.bean.NoticeCountBean;
import com.YiDian.RainBow.main.fragment.find.bean.AllLikeBean;
import com.YiDian.RainBow.main.fragment.find.bean.AllUserInfoBean;
import com.YiDian.RainBow.main.fragment.find.bean.LikeUserBean;
import com.YiDian.RainBow.main.fragment.find.bean.NearPersonBean;
import com.YiDian.RainBow.main.fragment.find.bean.UserMySeeBean;
import com.YiDian.RainBow.main.fragment.home.bean.CommentBean;
import com.YiDian.RainBow.dynamic.bean.SaveMsgSuccessBean;
import com.YiDian.RainBow.dynamic.bean.WriteDevelopmentBean;
import com.YiDian.RainBow.login.bean.ComPleteMsgBean;
import com.YiDian.RainBow.login.bean.LoginBean;
import com.YiDian.RainBow.main.fragment.home.bean.CollectDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.DianzanBean;
import com.YiDian.RainBow.main.fragment.home.bean.DynamicDetailsBean;
import com.YiDian.RainBow.main.fragment.home.bean.FollowBean;
import com.YiDian.RainBow.main.fragment.home.bean.NewDynamicBean;
import com.YiDian.RainBow.main.fragment.home.bean.OneCommentBean;
import com.YiDian.RainBow.main.fragment.mine.bean.AddSignInBean;
import com.YiDian.RainBow.main.fragment.mine.bean.ChackBuildLovesBean;
import com.YiDian.RainBow.main.fragment.mine.bean.ConsumeRecordBean;
import com.YiDian.RainBow.main.fragment.mine.bean.FangkeMsgBean;
import com.YiDian.RainBow.main.fragment.mine.bean.GiftBean;
import com.YiDian.RainBow.main.fragment.mine.bean.LoginUserInfoBean;
import com.YiDian.RainBow.main.fragment.mine.bean.LoveBulidBean;
import com.YiDian.RainBow.main.fragment.mine.bean.LoveStateBean;
import com.YiDian.RainBow.main.fragment.mine.bean.SelectAllDraftsBean;
import com.YiDian.RainBow.main.fragment.mine.bean.SignNeedPayBean;
import com.YiDian.RainBow.main.fragment.mine.bean.SigninMsgBean;
import com.YiDian.RainBow.main.fragment.mine.bean.UserInfoById;
import com.YiDian.RainBow.main.fragment.msg.bean.GiftMsgBean;
import com.YiDian.RainBow.main.fragment.msg.bean.GlodNumBean;
import com.YiDian.RainBow.notice.bean.CleanNoticeBean;
import com.YiDian.RainBow.notice.bean.ClickNoticeBean;
import com.YiDian.RainBow.notice.bean.CommentNoticeBean;
import com.YiDian.RainBow.notice.bean.FriendNoticeBean;
import com.YiDian.RainBow.notice.bean.NoticeMsgBean;
import com.YiDian.RainBow.regist.bean.RegistBean;
import com.YiDian.RainBow.remember.bean.RememberPwdBean;
import com.YiDian.RainBow.setpwd.bean.GetPhoneCodeBean;
import com.YiDian.RainBow.setup.bean.BlackListBean;
import com.YiDian.RainBow.setup.bean.CheckNickNameBean;
import com.YiDian.RainBow.setup.bean.GetBindPhoneMsgBean;
import com.YiDian.RainBow.setup.bean.GetRealDataBean;
import com.YiDian.RainBow.setup.bean.InsertRealBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;

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
    @POST("user/sendSms")
    Observable<GetPhoneCodeBean> getPhoneCode(@Query("phoneNum")String phone);

    //注册
    @POST("user/register")
    Observable<RegistBean> doRegist(@Query("phoneNum")String phone,@Query("password")String pwd);

    //密码登录
    @POST("user/userLogin")
    Observable<LoginBean> doPwdLogin(@Query("phoneNum")String phone,@Query("password")String pwd,@Query("accountType") int type,@Query("lng")double lng,@Query("lat")double lat);

    //QQ登录
    @POST("user/userLogin")
    Observable<LoginBean> doQqLogin(@Query("accountType") int type,@Query("qqOpenID")String qqId,@Query("lng")double lng,@Query("lat")double lat);

    //微信登录
    @POST("user/userLogin")
    Observable<LoginBean> doWechatLogin(@Query("accountType") int type,@Query("weChatOpenId")String wechatId,@Query("lng")double lng,@Query("lat")double lat);

    //修改背景图
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteBackImg(@Query("id")int userid,@Query("backImg")String backImg);

    //修改背景图
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteHeadImg(@Query("id")int userid,@Query("headImg")String headimg);

    //完善资料
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPlteAllMsg(@Query("id")int userid,@Query("nickName")String name,@Query("headImg")String headimg,@Query("birthday")String birthday,@Query("userRole")String role);

    //完善QQ微信登录信息
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPlteThiredLogin(@Query("id")int userid,@Query("nickName")String name,@Query("headImg")String headimg);

    //修改用户名
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteName(@Query("id")int userid,@Query("nickName")String name);

    //修改签名
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteQM(@Query("id")int userid,@Query("explains")String explains);

    //修改生日
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteAge(@Query("id")int userid,@Query("birthday")String birthday);

    //修改用户角色
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteUserRole(@Query("id")int userid,@Query("userRole")String role);

    //修改用户角色
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteIsSingle(@Query("id")int userid,@Query("isSingle")int single);

    //用户名判重
    @GET("user/selectNickName")
    Observable<CheckNickNameBean> doCheckName(@Query("nickName")String name);

    //修改 忘记密码
    @POST("user/updatePassword")
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

    //通过id获取某用户发布的动态
    @GET("content/getContentByUserId")
    Observable<NewDynamicBean> doGetDynamicByUserid(@Query("userId")int byId,@Query("currUserId")int userid,@Query("page")int page,@Query("pageSize")int size);


    //通过姓名获取某用户发布的动态
    @GET("content/getContentByUserId")
    Observable<NewDynamicBean> doGetDynamicByName(@Query("nickName")String name,@Query("currUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //查询用户收藏的动态
    @GET("content/getContentByUserCollect")
    Observable<NewDynamicBean> doGetCollectDynamicById(@Query("page")int page,@Query("pageSize")int size,@Query("userId")int userid);

    //我喜欢的
    @GET("favorite/selectUserIdS")
    Observable<UserMySeeBean> doGetMyLike(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //喜欢我的
    @GET("favorite/selectbUserId")
    Observable<UserMySeeBean> dogetLikeMine(@Query("bUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //匹配过的
    @GET("favorite/selectbUser")
    Observable<AllLikeBean> doGetAllLike(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //附近的人
    @GET("user/selectByAddUser")
    Observable<NearPersonBean> doGetNearPerson(@Query("id")int userid,@Query("lng")double lng,@Query("lat")double lat,@Query("page")int page,@Query("pageSize")int size);

    //我的好友列表
    @GET("fans/selectFansAndUser")
    Observable<FriendBean> doGetMyFriend(@Query("userId")int userid);

    //搜索我的好友
    @GET("user/searchFriendAndGroup")
    Observable<SelectFriendOrGroupBean> doGetFriendOrGroup(@Query("searchTerm")String msg,@Query("userId")int userid);

    //搜索所有用户
    @GET("user/seachAllInsert")
    Observable<SelectFriendOrGroupBean> doGetAllUserAndGroup(@Query("searchTerm")String msg, @Query("userId")int userid);

    //获取热门动态
    @GET("topic/findHotTopic")
    Observable<HotTopicBean> dogetHotTopicBean();

    //获取我的粉丝
    @GET("fans/selectFansByUserId")
    Observable<MyFansBean> doGetMyFans(@Query("userId")int userid,@Query("page")int page,@Query("pageSize") int size);

    //我的关注
    @GET("fans/selectFansByFansId")
    Observable<MyfollowBean> doGetMyFollow(@Query("fansId")int userid, @Query("page")int page, @Query("pageSize")int pagesize);

    //获取通知数量
    @GET("message/messageAllNum")
    Observable<NoticeCountBean> doGetNoticeCount(@Query("msgUserId")int userid);

    //获取系统通知
    @GET("message/selectMsg")
    Observable<NoticeMsgBean> doGetSystemNoticeMsg(@Query("msgUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //获取好友通知
    @GET("message/selectMsgType")
    Observable<FriendNoticeBean> doGetFriendNoticeMsg(@Query("msgUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //获取评论通知
    @GET("message/selectCommentType")
    Observable<CommentNoticeBean> doGetContentNotice(@Query("msgUserId")int userid, @Query("page")int page, @Query("pageSize")int size);

    //获取点赞通知
    @GET("message/selectClickType")
    Observable<ClickNoticeBean> doGetClickNotice(@Query("msgUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //清空通知
    @POST("message/deleteAll")
    Observable<CleanNoticeBean> doDeleteNotice(@Query("msgUserId")int userid,@Query("msgType")int type);

    //删除单个通知
    @POST("message/deleteId")
    Observable<CleanNoticeBean> doDeleteOneNotice(@Query("id")int id,@Query("msgType")int type);

    //提交实名认证
    @POST("idCardCheck/insertIdCardMsg")
    Observable<InsertRealBean> doInsertReal(@Query("idNum")String idnum,@Query("userId")int userid,@Query("userName")String username,@Query("frontCardImg")String zmurl,@Query("reverseCardImg")String fmurl);

    //查询是否实名
    @GET("idCardCheck/selectIdCardMsgByUserId")
    Observable<GetRealDataBean> doGetRealMsg(@Query("userId")int userid);

    //加入黑名单
    @POST("blackList/insertUser")
    Observable<InsertRealBean> doInsertBlackFriend(@Query("userId")int userid,@Query("beUserId")int beuserid);

    //查询我的黑名单
    @GET("blackList/selectUser")
    Observable<BlackListBean> doGetBlackFriend(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //将用户移出黑名单
    @POST("blackList/deleteUser")
    Observable<InsertRealBean> doDeleteBlackFriend(@Query("userId")int userid,@Query("beUserId")int beuserid);

    //判断是否绑定手机号
    @GET("user/selectPhone")
    Observable<GetBindPhoneMsgBean> doGetPhoneMsg(@Query("userId")int userid);

    //绑定/修改手机号
    @POST("user/updatePhone")
    Observable<InsertRealBean> doBindPhone(@Query("userId")int userid,@Query("phoneNum")String phonenum);

    //修改密码
    @POST("user/updatePassword")
    Observable<InsertRealBean> doUpdatePwd(@Query("password")String pwd,@Query("userId")int userid);

    //查询我的首页详情
    @GET("user/countUserInfo")
    Observable<LoginUserInfoBean> doGetUserInfo(@Query("userId")int userid);

    //查询我的访客
    @GET("meet/selectUser")
    Observable<FangkeMsgBean> dogetMyFangke(@Query("beUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //增加访客
    @POST("meet/addUser")
    Observable<InsertRealBean> doInsertFangke(@Query("beUser")String beuserid,@Query("userId")int userid);

    //通过id查询用户信息
    @GET("user/seleteUserOfId")
    Observable<UserMsgBean> doGetUserMsgById(@Query("userId")int userid,@Query("id")int id);

    //通过昵称查询用户信息
    @GET("user/seleteUserOfName")
    Observable<UserMsgBean> doGetUserMsgByName(@Query("userId")int userid,@Query("nickName")String name);

    //查询所有礼物
    @GET("gift/selectGifts")
    Observable<GiftMsgBean> doGetAllGiftMsg();

    //获取我的金币数量
    @GET("gold/selectGoldNumByUserId")
    Observable<GlodNumBean> dogetGldNum(@Query("userId")int userid);

    //赠送礼物
    @POST("giftMarkVO/insertGiftMark")
    Observable<InsertRealBean> doSendGift(@Query("inUserId")int reciveuserid,@Query("outUserId") int senduserid,@Query("giftId") int giftid);

    //创建群聊
    @POST("groupInfo/addGroupInfo")
    Observable<InsertRealBean> doCreatGroup(@Query("holderId") int userid,@Query("groupName")String name);

    //我创建的群聊
    @GET("groupInfo/selectGroupInfoByGroupId")
    Observable<MyCreateGroupMsgBean> dogetMyJoinGroup(@Query("holderId")int userid);

    //我加入的群聊
    @GET("group/selectGroupInfo")
    Observable<MyJoinGroupMsgBean> dogetMyCreateGroup(@Query("userId")int userid);

    //推荐群组
    @GET("groupInfo/selectTuiGroup")
    Observable<RecommendGroupBean> dogetRecommendGroup(@Query("userId")int userid);

    //推荐用户
    @GET("user/selectTuiUser")
    Observable<RecommendUserBean> dogetRecommendFriend(@Query("userId")int userid);

    //查询送出的礼物
    @GET("giftMarkVO/selectGiftMarkOut")
    Observable<GiftBean> dogetSendGift(@Query("outUserId")int userid);

    //查询收到的礼物
    @GET("giftMarkVO/selectGiftMark")
    Observable<GiftBean> dogetReciveGift(@Query("inUserId")int userid);

    //查询签到记录
    @POST("signIn/selectSign")
    Observable<SigninMsgBean> doGetSigninMsg(@Query("userId")int userid);

    //签到 补签
    @POST("signIn/addSign")
    Observable<AddSignInBean> doAddSign(@Query("weeks")int week,@Query("userId")int userid);

    //获取补签的天数 及花费的金币
    @GET("signIn/getReSignInDays")
    Observable<SignNeedPayBean> doGetReSignDays(@Query("userId")int userid);

    //查询用户账单详情  每页15
    @GET("goldRecord/selectUserIdRecord")
    Observable<ConsumeRecordBean>doGetConsumeRecord(@Query("userId")int userid,@Query("createTime")String nowday,@Query("page")int page,@Query("pageSize")int pageSize);

    //查询用户账单详情  每页15
    @GET("goldRecord/selectUserIdRecord")
    Observable<ConsumeRecordBean>doGetConsumeRecord(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int pageSize);

    //情侣标识
    @GET("lovers/selectLoveState")
    Observable<LoveStateBean>doGetLoveState(@Query("userId")int userId);

    //查询用户
    @GET("user/selectUserInfoById")
    Observable<UserInfoById>doGetUserInfobyId(@Query("id")int id);

    //请求建立关系
    @POST("lovers/buildLovers")
    Observable<LoveBulidBean>doGetBuildLovers(@Query("userPId")int userPId, @Query("userTId")int userTId);

    //解除关系
    @POST("lovers/cancelLovers")
    Observable<LoveBulidBean>doGetReleaseLove(@Query("userPId")int userPId, @Query("userTId")int userTId);

    @POST("lovers/breakLovers")
    Observable<LoveBulidBean>doGetInterruption(@Query("userPId")int userPId, @Query("userTId")int userTId);

    @POST("lovers/checkBuildLovers")
    Observable<ChackBuildLovesBean>doGetChackBuildLovers(@Query("userPId")int userPId, @Query("userTId")int userTId, @Query("isAgree") int isAgree);

}
