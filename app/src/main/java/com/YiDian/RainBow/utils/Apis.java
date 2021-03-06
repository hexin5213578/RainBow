package com.YiDian.RainBow.utils;


import com.YiDian.RainBow.dynamic.bean.HotTopicBean;
import com.YiDian.RainBow.dynamic.bean.SelectFriendOrGroupBean;
import com.YiDian.RainBow.friend.bean.FriendBean;
import com.YiDian.RainBow.friend.bean.InitGroupBean;
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
import com.YiDian.RainBow.main.fragment.msg.bean.ReportActivityBean;
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
import com.YiDian.RainBow.topic.TopicBean;
import com.YiDian.RainBow.user.bean.UserMsgBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Apis {
    //????????????
    @GET("content/getContentByTime")
    Observable<NewDynamicBean> getNewDynamic(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int pagesize);

    //????????????
    @GET("content/getNearbyContents")
    Observable<NewDynamicBean> getNearDynamic(@Query("userId")int userid,@Query("lat") double lat,@Query("lng")double lng,@Query("page")int page,@Query("pageSize")int pagesize);

    //????????????
    @GET("content/getAttentionUserContents")
    Observable<NewDynamicBean> getAttDynamic(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //??????????????????
    @GET("content/getHotContent")
    Observable<NewDynamicBean> getHotDynamic(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //???????????????
    @POST("user/sendSms")
    Observable<GetPhoneCodeBean> getPhoneCode(@Query("phoneNum")String phone);

    //??????
    @POST("user/register")
    Observable<RegistBean> doRegist(@Query("phoneNum")String phone,@Query("password")String pwd);

    //????????????
    @POST("user/userLogin")
    Observable<LoginBean> doPwdLogin(@Query("phoneNum")String phone,@Query("password")String pwd,@Query("accountType") int type,@Query("lng")double lng,@Query("lat")double lat);

    //QQ??????
    @POST("user/userLogin")
    Observable<LoginBean> doQqLogin(@Query("accountType") int type,@Query("qqOpenID")String qqId,@Query("lng")double lng,@Query("lat")double lat);

    //????????????
    @POST("user/userLogin")
    Observable<LoginBean> doWechatLogin(@Query("accountType") int type,@Query("weChatOpenId")String wechatId,@Query("lng")double lng,@Query("lat")double lat);

    //???????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteBackImg(@Query("id")int userid,@Query("backImg")String backImg);

    //???????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteHeadImg(@Query("id")int userid,@Query("headImg")String headimg);

    //????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPlteAllMsg(@Query("id")int userid,@Query("nickName")String name,@Query("birthday")String birthday,@Query("userRole")String role);

    //??????QQ??????????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPlteThiredLogin(@Query("id")int userid,@Query("nickName")String name,@Query("headImg")String headimg);

    //???????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteName(@Query("id")int userid,@Query("nickName")String name);

    //????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteQM(@Query("id")int userid,@Query("explains")String explains);

    //????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteAge(@Query("id")int userid,@Query("birthday")String birthday);

    //??????????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteUserRole(@Query("id")int userid,@Query("userRole")String role);

    //??????????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteUserAgeAndRole(@Query("id")int userid,@Query("birthday")String birthday,@Query("userRole")String role);

    //??????????????????
    @POST("user/updateUserInfo")
    Observable<ComPleteMsgBean> doComPleteIsSingle(@Query("id")int userid,@Query("isSingle")int single);

    //???????????????
    @GET("user/selectNickName")
    Observable<CheckNickNameBean> doCheckName(@Query("nickName")String name);

    //?????? ????????????
    @POST("user/updatePassword")
    Observable<RememberPwdBean> doRemeberPwd(@Query("password")String pwd,@Query("phoneNum")String phone);

    //??????UpdateToken
    @GET("img/uploadToken")
    Observable<SaveMsgSuccessBean> getUpdateToken();

    //????????????
    @POST("content/writeContent")
    Observable<WriteDevelopmentBean> doWriteDevelopment(@Query("userId")int userid,@Query("contentInfo")String contentinfo,@Query("contentImg")String contentImg,@Query("lng")Double lng,@Query("lat")Double lat,@Query("IsOpen")int isopen,@Query("ImgType")int ImgType,@Query("status")int status,@Query("area")String area);

    //??????
    @POST("click/insertClick")
    Observable<DianzanBean> doDianzan(@Query("clickBy")int id,@Query("clickType")int type,@Query("typeId")int comentId);

    //????????????
    @POST("click/delClick")
    Observable<DianzanBean> doCancleDianzan(@Query("clickType")int type,@Query("typeId")int comentId,@Query("userId") int id);

    //????????????
    @POST("fans/insertFans")
    Observable<FollowBean> doFollow(@Query("fansId")int followid,@Query("userId")int byfollowid);

    //??????????????????
    @POST("fans/deleteByUserIdAndFansId")
    Observable<FollowBean> doCancleFollow(@Query("fansId")int followid,@Query("userId")int byfollowid);

    //????????????
    @GET("content/getContentInfoById")
    Observable<DynamicDetailsBean> dogetDynamicDetails(@Query("contentId")int contentid,@Query("userId")int userid);

    //?????????
    @POST("comment/writeComment")
    Observable<CollectDynamicBean> doWriteComment(@Query("userId")int userid,@Query("beCommentUserId")int beuserid,@Query("commentInfo")String info,@Query("commentType")int type,@Query("fatherId")int fatherid,@Query("replyType")int replyid);

    //?????????
    @GET("comment/getComment")
    Observable<CommentBean> doGetComment(@Query("fatherId")int fatherid,@Query("commentType")int type,@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //??????????????????
    @GET("comment/getCommentByIdAndUserId")
    Observable<OneCommentBean> doGetCommentbyId(@Query("id")int commentid,@Query("userId")int userid);

    //????????????
    @POST("collect/addCollect")
    Observable<CollectDynamicBean> doCollectDynamic(@Query("userId")int userid,@Query("contentId")int contentid);

    //??????????????????
    @POST("collect/delCollect")
    Observable<CollectDynamicBean> doCancleCollectDynamic(@Query("userId")int userid,@Query("contentId")int contentid);


    //?????????????????????
    @GET("content/selectAllDrafts")
    Observable<SelectAllDraftsBean> doGetAllDraftsBy(@Query("userId")int userid,@Query("page") int page,@Query("pageSize")int pagesize);

    //??????????????????
    @POST("/content/updateDraft")
    Observable<CollectDynamicBean> doUpdateDraft(@Query("id")int draftid,@Query("status")int status);

    //????????????
    @POST("content/deleteDraft")
    Observable<CollectDynamicBean> doDeleteDraft(@Query("draftId")int draftid,@Query("userId")int userid);

    //?????????????????????
    @POST("content/delContent")
    Observable<CollectDynamicBean> doDeleteDynamic(@Query("contentId")int contentid,@Query("userId")int userid);

    //?????? ????????????????????????
    @GET("user/selectUser")
    Observable<AllUserInfoBean> doGetAllUserInfo(@Query("id")int userid,@Query("lng") double lng,@Query("lat")double lat,@Query("page")int page,@Query("pageSize")int size);

    //?????? ???????????????????????????
    @GET("user/selectUserOf")
    Observable<AllUserInfoBean> doGetFilterUser(@Query("id")int userid,@Query("age")String age,@Query("distancing")int distance,@Query("isSingle")int issingle,@Query("userRole")String role,@Query("lng")double lng,@Query("lat")double lat,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @POST("favorite/insertUser")
    Observable<LikeUserBean> doLikeUser(@Query("userId")int userid,@Query("bUserId")int buserid,@Query("likeType")int type);

    //??????id??????????????????????????????
    @GET("content/getContentByUserId")
    Observable<NewDynamicBean> doGetDynamicByUserid(@Query("userId")int byId,@Query("currUserId")int userid,@Query("page")int page,@Query("pageSize")int size);


    //??????????????????????????????????????????
    @GET("content/getContentByUserId")
    Observable<NewDynamicBean> doGetDynamicByName(@Query("nickName")String name,@Query("currUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //???????????????????????????
    @GET("content/getContentByUserCollect")
    Observable<NewDynamicBean> doGetCollectDynamicById(@Query("page")int page,@Query("pageSize")int size,@Query("userId")int userid);

    //????????????
    @GET("favorite/selectUserIdS")
    Observable<UserMySeeBean> doGetMyLike(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @GET("favorite/selectbUserId")
    Observable<UserMySeeBean> dogetLikeMine(@Query("bUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @GET("favorite/selectbUser")
    Observable<AllLikeBean> doGetAllLike(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @GET("user/selectByAddUser")
    Observable<NearPersonBean> doGetNearPerson(@Query("id")int userid,@Query("lng")double lng,@Query("lat")double lat,@Query("page")int page,@Query("pageSize")int size);

    //??????????????????
    @GET("fans/selectFansAndUser")
    Observable<FriendBean> doGetMyFriend(@Query("userId")int userid);

    //??????????????????
    @GET("user/searchFriendAndGroup")
    Observable<SelectFriendOrGroupBean> doGetFriendOrGroup(@Query("searchTerm")String msg,@Query("userId")int userid);

    //??????????????????
    @GET("user/seachAllInsert")
    Observable<SelectFriendOrGroupBean> doGetAllUserAndGroup(@Query("searchTerm")String msg, @Query("userId")int userid);

    //??????????????????
    @GET("topic/findHotTopic")
    Observable<HotTopicBean> dogetHotTopicBean();

    //??????????????????
    @GET("fans/selectFansByUserId")
    Observable<MyFansBean> doGetMyFans(@Query("userId")int userid,@Query("page")int page,@Query("pageSize") int size);

    //????????????
    @GET("fans/selectFansByFansId")
    Observable<MyfollowBean> doGetMyFollow(@Query("fansId")int userid, @Query("page")int page, @Query("pageSize")int pagesize);

    //??????????????????
    @GET("message/messageAllNum")
    Observable<NoticeCountBean> doGetNoticeCount(@Query("msgUserId")int userid);

    //??????????????????
    @GET("message/selectMsg")
    Observable<NoticeMsgBean> doGetSystemNoticeMsg(@Query("msgUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //??????????????????
    @GET("message/selectMsgType")
    Observable<FriendNoticeBean> doGetFriendNoticeMsg(@Query("msgUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //??????????????????
    @GET("message/selectCommentType")
    Observable<CommentNoticeBean> doGetContentNotice(@Query("msgUserId")int userid, @Query("page")int page, @Query("pageSize")int size);

    //??????????????????
    @GET("message/selectClickType")
    Observable<ClickNoticeBean> doGetClickNotice(@Query("msgUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @POST("message/deleteAll")
    Observable<CleanNoticeBean> doDeleteNotice(@Query("msgUserId")int userid,@Query("msgType")int type);

    //??????????????????
    @POST("message/deleteId")
    Observable<CleanNoticeBean> doDeleteOneNotice(@Query("id")int id,@Query("msgType")int type);

    //??????????????????
    @POST("idCardCheck/insertIdCardMsg")
    Observable<InsertRealBean> doInsertReal(@Query("idNum")String idnum,@Query("userId")int userid,@Query("userName")String username,@Query("frontCardImg")String zmurl,@Query("reverseCardImg")String fmurl);

    //??????????????????
    @GET("idCardCheck/selectIdCardMsgByUserId")
    Observable<GetRealDataBean> doGetRealMsg(@Query("userId")int userid);

    //???????????????
    @POST("blackList/insertUser")
    Observable<InsertRealBean> doInsertBlackFriend(@Query("userId")int userid,@Query("beUserId")int beuserid);

    //?????????????????????
    @GET("blackList/selectUser")
    Observable<BlackListBean> doGetBlackFriend(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //????????????????????????
    @POST("blackList/deleteUser")
    Observable<InsertRealBean> doDeleteBlackFriend(@Query("userId")int userid,@Query("beUserId")int beuserid);

    //???????????????????????????
    @GET("user/selectPhone")
    Observable<GetBindPhoneMsgBean> doGetPhoneMsg(@Query("userId")int userid);

    //??????/???????????????
    @POST("user/updatePhone")
    Observable<InsertRealBean> doBindPhone(@Query("userId")int userid,@Query("phoneNum")String phonenum);

    //????????????
    @POST("user/updatePassword")
    Observable<InsertRealBean> doUpdatePwd(@Query("password")String pwd,@Query("userId")int userid);

    //????????????????????????
    @GET("user/countUserInfo")
    Observable<LoginUserInfoBean> doGetUserInfo(@Query("userId")int userid);

    //??????????????????
    @GET("meet/selectUser")
    Observable<FangkeMsgBean> dogetMyFangke(@Query("beUserId")int userid,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @POST("meet/addUser")
    Observable<InsertRealBean> doInsertFangke(@Query("beUser")String beuserid,@Query("userId")int userid);

    //??????id??????????????????
    @GET("user/seleteUserOfId")
    Observable<UserMsgBean> doGetUserMsgById(@Query("userId")int userid,@Query("id")int id);

    //??????????????????????????????
    @GET("user/seleteUserOfName")
    Observable<UserMsgBean> doGetUserMsgByName(@Query("userId")int userid,@Query("nickName")String name);

    //??????????????????
    @GET("gift/selectGifts")
    Observable<GiftMsgBean> doGetAllGiftMsg();

    //????????????????????????
    @GET("gold/selectGoldNumByUserId")
    Observable<GlodNumBean> dogetGldNum(@Query("userId")int userid);

    //????????????
    @POST("giftMarkVO/insertGiftMark")
    Observable<InsertRealBean> doSendGift(@Query("inUserId")int reciveuserid,@Query("outUserId") int senduserid,@Query("giftId") int giftid);

    //????????????
    @POST("groupInfo/addGroupInfo")
    Observable<InsertRealBean> doCreatGroup(@Query("holderId") int userid,@Query("groupName")String name);

    //??????????????????
    @GET("groupInfo/selectGroupInfoByGroupId")
    Observable<MyCreateGroupMsgBean> dogetMyJoinGroup(@Query("holderId")int userid);

    //???????????????
    @GET("groupInfo/selectGroupInfoByGroupId")
    Observable<InitGroupBean> doGetGroup(@Query("userId")int userid);

    //??????????????????
    @GET("group/selectGroupInfo")
    Observable<MyJoinGroupMsgBean> dogetMyCreateGroup(@Query("userId")int userid);

    //????????????
    @GET("groupInfo/selectTuiGroup")
    Observable<RecommendGroupBean> dogetRecommendGroup(@Query("userId")int userid);

    //????????????
    @GET("user/selectTuiUser")
    Observable<RecommendUserBean> dogetRecommendFriend(@Query("userId")int userid);

    //?????????????????????
    @GET("giftMarkVO/selectGiftMarkOut")
    Observable<GiftBean> dogetSendGift(@Query("outUserId")int userid);

    //?????????????????????
    @GET("giftMarkVO/selectGiftMark")
    Observable<GiftBean> dogetReciveGift(@Query("inUserId")int userid);

    //??????????????????
    @POST("signIn/selectSign")
    Observable<SigninMsgBean> doGetSigninMsg(@Query("userId")int userid);

    //?????? ??????
    @POST("signIn/addSign")
    Observable<AddSignInBean> doAddSign(@Query("weeks")int week,@Query("userId")int userid);

    //????????????????????? ??????????????????
    @GET("signIn/getReSignInDays")
    Observable<SignNeedPayBean> doGetReSignDays(@Query("userId")int userid);

    //????????????????????????  ??????15
    @GET("goldRecord/selectUserIdRecord")
    Observable<ConsumeRecordBean>doGetConsumeRecord(@Query("userId")int userid,@Query("createTime")String nowday,@Query("page")int page,@Query("pageSize")int pageSize);

    //????????????????????????  ??????15
    @GET("goldRecord/selectUserIdRecord")
    Observable<ConsumeRecordBean>doGetConsumeRecord(@Query("userId")int userid,@Query("page")int page,@Query("pageSize")int pageSize);

    //????????????
    @GET("lovers/selectLoveState")
    Observable<LoveStateBean>doGetLoveState(@Query("userId")int userId);

    //????????????
    @GET("user/selectUserInfoById")
    Observable<UserInfoById>doGetUserInfobyId(@Query("id")int id);

    //??????????????????
    @POST("lovers/buildLovers")
    Observable<LoveBulidBean>doGetBuildLovers(@Query("userPId")int userPId, @Query("userTId")int userTId);

    //????????????
    @POST("lovers/cancelLovers")
    Observable<LoveBulidBean>doGetReleaseLove(@Query("userPId")int userPId, @Query("userTId")int userTId);

    //????????????
    @POST("lovers/breakLovers")
    Observable<LoveBulidBean>doGetInterruption(@Query("userPId")int userPId, @Query("userTId")int userTId);

    //??????????????????
    @POST("lovers/checkBuildLovers")
    Observable<ChackBuildLovesBean>doGetChackBuildLovers(@Query("userPId")int userPId, @Query("userTId")int userTId, @Query("isAgree") int isAgree);

    //????????????????????????
    @GET("content/getContentByTopicTitle")
    Observable<TopicBean>doGetTopicByKey(@Query("userId")int userid,@Query("topicTitle")String title,@Query("page")int page,@Query("pageSize")int size);

    //????????????
    @POST("report/insertReport")
    Observable<ReportActivityBean>doGetInsertReport(@Query("reportType")Integer reportType, @Query("detailed")Integer detailed, @Query("userId")Integer userId, @Query("beId")Integer beId, @Query("exType")Integer exType);

    //????????????
    @POST("feedback/insert")
    Observable<ChackBuildLovesBean> doInsertFeedBack(@Query("info")String info);
}
