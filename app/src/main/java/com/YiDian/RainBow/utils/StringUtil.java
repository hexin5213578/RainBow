package com.YiDian.RainBow.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;


import com.YiDian.RainBow.base.App;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static android.provider.Settings.System.DATE_FORMAT;

public class StringUtil {

    public static final String EMPTY = "";

    public static boolean isBlank(String str) {
        return (str == null || "".equals(str));
    }

    public static String changeResponseStr(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str.substring(0, str.indexOf("request")));
        stringBuffer.append("response");
        stringBuffer
                .append(" type=\"0\" seq=\"0\" resCode=\"0\"><head><resMsg>success</resMsg></head><message><em>成功</em><ec>0</ec>");
        stringBuffer.append(str.substring(str.indexOf("<message>") + 9, str.indexOf("</message>") + 10));
        stringBuffer.append("<sign></sign><key></key></response>");
        return stringBuffer.toString();
    }

    public static String getUUId() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static String changeStr(String str) {
        if (isBlank(str)) {
            return "--";
        }
        return str;
    }


    //格式化时间
    public static String getStringTime(long date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date d = new Date(date);
        return simpleDateFormat.format(d);
    }

    //应用默认金额数字都保留到小数点后两位，要截取出来
    //长字符串的转化中文表示处理，规则为：超过一百万，如有必要标示为小数点后一位，超过一亿，
    /**
     *  时间戳格式转换
     */
    static String dayNames[] = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    public static String getNewChatTime(long timesamp) {
        String result = "";
        Calendar todayCalendar = Calendar.getInstance();
        Calendar otherCalendar = Calendar.getInstance();
        otherCalendar.setTimeInMillis(timesamp);

        String timeFormat="M月d日 HH:mm";
        String yearTimeFormat="yyyy年M月d日 HH:mm";
        String am_pm="";
        int hour=otherCalendar.get(Calendar.HOUR_OF_DAY);
        if(hour>=0&&hour<6){
            am_pm="凌晨";
        }else if(hour>=6&&hour<12){
            am_pm="早上";
        }else if(hour==12){
            am_pm="中午";
        }else if(hour>12&&hour<18){
            am_pm="下午";
        }else if(hour>=18){
            am_pm="晚上";
        }
        timeFormat="M月d日 "+ am_pm +"HH:mm";
        yearTimeFormat="yyyy年M月d日 "+ am_pm +"HH:mm";

        boolean yearTemp = todayCalendar.get(Calendar.YEAR)==otherCalendar.get(Calendar.YEAR);
        if(yearTemp){
            int todayMonth=todayCalendar.get(Calendar.MONTH);
            int otherMonth=otherCalendar.get(Calendar.MONTH);
            if(todayMonth==otherMonth){//表示是同一个月
                int temp=todayCalendar.get(Calendar.DATE)-otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        result = getHourAndMin(timesamp);
                        break;
                    case 1:
                        result = "昨天 " + getHourAndMin(timesamp);
                        break;
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth=todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if(dayOfMonth==todayOfMonth){//表示是同一周
                            int dayOfWeek=otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if(dayOfWeek!=1){//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                result = dayNames[otherCalendar.get(Calendar.DAY_OF_WEEK)-1] + getHourAndMin(timesamp);
                            }else{
                                result = getTime(timesamp,timeFormat);
                            }
                        }else{
                            result = getTime(timesamp,timeFormat);
                        }
                        break;
                    default:
                        result = getTime(timesamp,timeFormat);
                        break;
                }
            }else{
                result = getTime(timesamp,timeFormat);
            }
        }else{
            result=getYearTime(timesamp,yearTimeFormat);
        }
        return result;
    }

    /**
     * 当天的显示时间格式
     * @param time
     * @return
     */
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    /**
     * 不同一周的显示时间格式
     * @param time
     * @param timeFormat
     * @return
     */
    public static String getTime(long time,String timeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        return format.format(new Date(time));
    }

    /**
     * 不同年的显示时间格式
     * @param time
     * @param yearTimeFormat
     * @return
     */
    public static String getYearTime(long time,String yearTimeFormat) {
        SimpleDateFormat format = new SimpleDateFormat(yearTimeFormat);
        return format.format(new Date(time));
    }
    /**
     * @param str 示例 101000.00 1000000 10000 11111 1000
     * @return 10.1万
     */
    public static String rawIntStr2IntStr(String str) {
        if (str.contains(".")) {
            str = str.substring(0, str.indexOf("."));
        }
        if (str.length() > 4 /*&& str.length() < 9*/) {
            //超过万不超过亿
            char c = str.charAt(str.length() - 4);
            if (Integer.valueOf(c + "") > 0) {
                str = str.substring(0, str.length() - 4) + "." + c + "0w";
            } else {
                str = str.substring(0, str.length() - 4) + ".0w";
            }
        }
        return str;
    }

    //校验手机号码,精确地
    public static boolean isLegalPhoneNumDetail(String phoneNum) {
        if (StringUtil.isBlank(phoneNum)) {
            return false;
        }
//		else return phoneNum.matches("^(13[0-9]|17[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|4|3|5|6|7|8|9])\\d{8}$");
        else {
            return phoneNum.matches("^1\\d{10}$");
        }
    }

    //校验手机号码
    public static boolean isLegalPhoneNum(String phoneNum) {
        if (StringUtil.isBlank(phoneNum)) {
            return false;
        } else {
            return phoneNum.matches("^1\\d{10}$");
        }

    }

    //校验身份证号码
    public static boolean isLegalIdNum(String idNum) {
        if (StringUtil.isBlank(idNum)) {
            return false;
        } else {
            return idNum.matches("^\\d{17}(\\d|X|x)$");
        }
    }

    //校验中国用户名
    public static boolean isFailChineseName(String homeName) {
        if (StringUtil.isBlank(homeName)) {
            return false;
        }

        return !homeName.matches("(?:[\u4E00-\u9FFF]{1,8}·\u4E00-\u9FFF]{1,8})|(?:[\u4E00-\u9FFF]{2,5})");
    }

    //校验邮箱
    public static boolean isLegalMailAddress(String mail) {
        if (StringUtil.isBlank(mail)) {
            return false;
        } else{
            return mail.matches("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        }
    }

    //校验银行卡号
    public static boolean isLegalBankCardNo(String bankNo) {
        if (StringUtil.isBlank(bankNo)) {
            return false;
        } else {
            return bankNo.matches("^\\d{16,19}$");
        }
    }

    //混淆手机号码，中间添加*格式   133****1236
    public static String blurPhoneNo(String phoneNo) {
        if (StringUtil.isBlank(phoneNo)) {
            return phoneNo;
        } else if (StringUtil.isLegalPhoneNum(phoneNo)) {
            return phoneNo.substring(0, 3) + "****" + phoneNo.substring(7, 11);
        }
        return null;
    }

    //混淆身份证号，中间添加*格式   133123*********236
    public static String blurIdCardNo(String idNo) {
        if (StringUtil.isBlank(idNo)) {
            return idNo;
        } else if (StringUtil.isLegalIdNum(idNo)) {
            return idNo.substring(0, 4) + "**********"
                    + idNo.substring(idNo.length() - 4, idNo.length());
        }
        return null;
    }

    //混淆银行卡号，中间添加*格式   6228 **** **** 6217
    public static String blurBankCardNo(String bankNo) {
        if (StringUtil.isBlank(bankNo)) {
            return bankNo;
        } else if (StringUtil.isLegalBankCardNo(bankNo)) {
            return bankNo.substring(0, 4) + " **** **** "
                    + bankNo.substring(bankNo.length() - 4, bankNo.length());
        }
        return null;
    }

    //是否是合法的充值金额，是大于0的正整数
    public static boolean isLegalAmount(String amount) {
        String prexString = "^[1-9]\\d*$";
        return amount.matches(prexString);
    }

    /**
     * 超过十万的显示处理
     *
     * @param money 金额【可能带有元单位】
     */
    public static String exceedTenW(String money) {
        money = money.replace("元", "");
        double fMoney = Double.parseDouble(money);
        if (fMoney > 100000) {
            return fMoney / 10000.00 + "万";
        } else {
            return money + "元";
        }
    }

    /**
     * 超过十万的显示处理
     *
     * @param money 金额【可能带有元单位】
     */
    public static String exceedTenW3(String money) {
        money = money.replace("元", "");
        double fMoney = Double.parseDouble(money);
        if (fMoney > 100000) {
            return fMoney / 10000.00 + "0万";
        } else {
            return money + "元";
        }
    }

    /**
     * 当大于等于100000时，要以X万来显示,如果为10000的非整数倍，取两位小数，并不四舍五入，不足10万的不保留小数
     *
     * @param money
     * @return
     */
    public static String exceedTenW2(String money) {
        if (money == null) {
            money = "0.00";
        }
//		money = money.replace("元", "");
        if (money == null) {
            return "0.00";
        }
        money = money.substring(0, money.lastIndexOf("."));
        if (money.length() > 4) {
            String c = money.substring(money.length() - 4, money.length() - 2);
            if (Integer.valueOf(c) > 0) {
                money = money.substring(0, money.length() - 4) + "." + c + "万";
            } else {
                money = money.substring(0, money.length() - 4) + ".00万";
            }
        } else {
            money = money + ".00";
        }
        return money;
    }

    /**
     * 当大于等于100000时，要以X万来显示,如果为10000的非整数倍，取两位小数，并不四舍五入，不足10万的不保留小数
     *
     * @param money
     * @return
     */
    public static String exceedTenW4(String money) {
        money = money.replace("元", "");
        String moneyStr = money;
        money = money.substring(0, money.lastIndexOf("."));
        if (money.length() > 5) {
            String c = money.substring(money.length() - 4, money.length() - 2);
            if (Integer.valueOf(c) > 0) {
                money = money.substring(0, money.length() - 4) + "." + c + "万";
            } else {
                money = money.substring(0, money.length() - 4) + "万";
            }
        } else {
            money = round(moneyStr) + "元";
        }
        return money;
    }

    /**
     * 保留小数点后两位
     *
     * @param value
     * @return
     */
    public static String round(String value) {
        int scale = 2;
        int roundingMode = BigDecimal.ROUND_HALF_EVEN;
        BigDecimal decimal = new BigDecimal(value);
        decimal = decimal.setScale(scale, roundingMode);
        return decimal.toString();
    }

    /**
     * 正则判断密码特殊字符
     *
     * @param password
     * @return
     */
    public static boolean regPassword(String password) {
//        String regPaw = "[a-zA-Z0-9~!@#$^*.]+";
        String regPaw = "^(?![A-Za-z]+$)(?![A-Z\\d]+$)(?![A-Z\\W]+$)(?![a-z\\d]+$)(?![a-z\\W]+$)(?![\\d\\W]+$)\\S{8}$";
        boolean isPassword = password.matches(regPaw);
        return isPassword;
    }

    /**
     * 正则判断邀请码特殊字符
     *
     * @param inviteCode
     * @return
     */
    public static boolean regInviteCode(String inviteCode) {
        String regPaw = "^[0-9a-zA-Z]{6}$";
        boolean isPassword = inviteCode.matches(regPaw);
        return isPassword;
    }

    /**
     * 返回格式化金钱
     *
     * @param money
     * @return
     */
    public static String getMoneyFormat(String money) {
        try {
            BigDecimal in = new BigDecimal(money);
            DecimalFormat out = new DecimalFormat("##################0.00");
            return out.format(in);
        } catch (NumberFormatException e) {
            return "0";
        }

    }

    /**
     * 返回格式化金钱不带最后两个0
     *
     * @param money
     * @return
     */
    public static String getMoneyFormatWithoutEnd(String money) {
        try {
            BigDecimal in = new BigDecimal(money);
            DecimalFormat out = new DecimalFormat("#,###,###,###,###,###,##0");
            return out.format(in);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    /**
     * 组成两种颜色的字符串
     *
     * @param context
     * @param s
     * @param index   从第几个字开始变色
     * @param color1  第一种颜色id
     * @param color2  第二种颜色id
     * @return
     */
    public static SpannableString changeStringColor(Context context, StringBuffer s, int index, @ColorRes int color1, @ColorRes int color2) {
        if (s.length() < index) {
            return new SpannableString(s);
        }
        SpannableString content = new SpannableString(s);
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color1)), 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color2)), index, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return content;
    }

    /**
     * 改变从index开始的字符大小
     *
     * @param s
     * @param index        从第几个字符开始
     * @param relativeSize 相对大小倍数
     * @return
     */
    public static SpannableString changeStringSize(String s, int index, float relativeSize) {
        if (s.length() < index) {
            return new SpannableString(s);
        }
        SpannableString content = new SpannableString(s);
        content.setSpan(new RelativeSizeSpan(relativeSize), index, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return content;
    }

    /**
     * 改变从index开始的字符大小
     *
     * @param s
     * @param textSize
     * @return
     */
    public static SpannableString changeStringSize(String s, int textSize) {
        if (TextUtils.isEmpty(s)) return new SpannableString("");
        SpannableString spann = new SpannableString(s);
        spann.setSpan(new AbsoluteSizeSpan(textSize, true), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spann;
    }

    /**
     * 改变字体大小、颜色、背景
     *
     * @param s
     * @param textSize
     * @param textColor
     * @param bgColor
     * @return
     */
    public static SpannableString changeStringSizeColorBg(String s, int textSize, String textColor, String bgColor) {
        if (TextUtils.isEmpty(s)) return new SpannableString("");
        try {
            SpannableString content = new SpannableString(s);
            content.setSpan(new ForegroundColorSpan(Color.parseColor(textColor)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new AbsoluteSizeSpan(textSize, true), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            content.setSpan(new BackgroundColorSpan(Color.parseColor(!TextUtils.isEmpty(bgColor) ? bgColor : "#00000000")), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return content;
        } catch (Exception e) {
        }
        return new SpannableString(s);
    }


    /**
     * 获取不同字体大小的文本
     *
     * @param s1
     * @param size1
     * @param s2
     * @param size2
     * @return
     */
    public static SpannableStringBuilder getMultiplySizeText(String s1, int size1, String s2, int size2) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(changeStringSize(s1, size1));
        builder.append(changeStringSize(s2, size2));
        return builder;
    }

    /**
     * 改变从s中“.”开始的字符大小  价格展示用
     *
     * @param s
     * @param relativeSize
     * @return
     */
    public static SpannableString changeSizeByDot(String s, float relativeSize) {
        if (TextUtils.isEmpty(s)) {
            return new SpannableString("");
        }
        if (!s.contains(".")){
            return new SpannableString(s);
        }

//        if(TextUtils.equals(s , "0") || TextUtils.equals(s , "0.0") ||TextUtils.equals(s , "0.00")){
//            return new SpannableString(s);
//        }
//
//        float f = Float.parseFloat(s);
//        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
//        s = decimalFormat.format(f);//for

        return changeStringSize(s, s.indexOf("."), relativeSize);
    }

    /**
     * 验s证正整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 返回后四个字符串
     *
     * @param s
     * @return
     */
    public static String end4(String s) {
        int i = s.length();
        if (i <= 4) {
            return s;
        } else {
            return s.substring(i - 4, i);
        }
    }

    /**
     * 检查姓名是否符合规则,带提示
     *
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(App.getContext(), "请输入你的名字", Toast.LENGTH_SHORT).show();
            return false;
        }
        int length = name.length();
        if (length < 2 || length > 8 || StringUtil.isFailChineseName(name)) {
            Toast.makeText(App.getContext(), "姓名输入有误", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    /**
     * 检查地区是否符合规则,带提示
     *
     * @param address
     * @return
     */
    public static boolean checkAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(App.getContext(), "请选择您的收货地区", Toast.LENGTH_SHORT).show();
            return false;
        }
      /*  int length = address.length();
        if (length < 2 || length > 8) {
            ToastUtil.showCenter("收货地区输入有误");
            return false;
        }*/
        return true;
    }

    /**
     * 检查身份证号是否符合规则,带提示
     *
     * @param id
     * @return
     */
    public static boolean checkIdCard(String id) {
        if (TextUtils.isEmpty(id)) {
            Toast.makeText(App.getContext(), "请输入您的身份证号码", Toast.LENGTH_SHORT).show();
            return false;
        }
        int lengthId = id.length();
        if (lengthId < 18 || !StringUtil.isLegalIdNum(id)) {
            Toast.makeText(App.getContext(), "身份证号码不正确", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    /**
     * 检查银行卡号是否符合规则,带提示
     *
     * @param card
     * @return
     */
    public static boolean checkBankCardNumber(String card) {
        if (TextUtils.isEmpty(card)) {
            Toast.makeText(App.getContext(), "请选择您的银行卡号", Toast.LENGTH_SHORT).show();

            return false;
        }
        int cardL = card.length();
        if (cardL < 15 || cardL > 25) {
            Toast.makeText(App.getContext(), "银行卡号不正确", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    /**
     * 检查手机号是否符合规则,带提示
     *
     * @param phone
     * @return
     */
    public static boolean checkPhoneNumber(String phone) {
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(App.getContext(), "请输入您的手机号", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!StringUtil.isLegalPhoneNum(phone)) {
            Toast.makeText(App.getContext(), "手机号输入有误", Toast.LENGTH_SHORT).show();

            return false;
        }
        return true;
    }

    /**
     * 检查密码是否符合规则,带提示
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(App.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static boolean checkSms(String sms) {
        if (TextUtils.isEmpty(sms)) {
            Toast.makeText(App.getContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //是否包含数字与字母
    public static boolean isLetterDigitOrChinese(String str) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";//其他需要，直接修改正则表达式就好
        return str.matches(regex);
    }

    // 过滤特殊字符
    public static boolean StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 // String regEx = "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

//        String regPaw ="^(?![A-Za-z]+$)(?![A-Z\\d]+$)(?![A-Z\\W]+$)(?![a-z\\d]+$)(?![a-z\\W]+$)(?![\\d\\W]+$)\\S{8}$";
//        boolean isPassword = str.matches(regPaw);
//        return isPassword;
        return m.find();
    }

    public static boolean checkPassword1(String password) {

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(App.getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 8 || password.length() > 16) {
            Toast.makeText(App.getContext(), "密码必须为8~16字母，数字，部分符号", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 半角转为全角,字符全角化。防止TextView换行后不整齐
     * 即将所有的数字、字母及标点全部转为全角字符，使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。
     *
     * @param input
     * @return
     */
    public static String toDBC(String input) {
        if (TextUtils.isEmpty(input)) {
            return EMPTY;
        }
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String getUrl(String url, Map<String, String> params) {
        // 添加url参数
        if (params != null) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }

    /**
     * 提取数字
     *
     * @param content
     * @return
     */
    public static String getNumbers(String content) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(content);
        return m.replaceAll("").trim();
    }

    /**
     * 替换字符串中的“/”
     *
     * @param s
     * @return
     */
    public static String replaceSprit(String s) {
        if (s.contains("/")) {
            s = s.replace("/", "_");
        }
        return s;
    }

    /**
     * 计算剩余时间
     *
     * @param todayDeadTime
     * @param serverRightTime
     * @return
     */
    public static String[] calculateSurplusTime(String todayDeadTime, String serverRightTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long date = 0;
        try {
            date = sdf.parse(todayDeadTime).getTime() - sdf.parse(serverRightTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return StringUtil.formatTime(date);
    }

    /*
     * 毫秒转化
     */
    public static String[] formatTime(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        String strDay = (day < 10 && day >= 0) ? "0" + day : "" + day; //天
        String strHour = (hour < 10 && hour >= 0) ? "0" + hour : "" + hour;//小时
        String strMinute = (minute < 10 && minute >= 0) ? "0" + minute : "" + minute;//分钟
        String strSecond = (second < 10 && second >= 0) ? "0" + second : "" + second;//秒
        String[] time = {strHour, strMinute, strSecond};

        return time;
    }

    /**
     * 毫秒转换，返回int[]
     *
     * @param ms
     * @return
     */
    public static int[] formatTimeToInt(long ms) {

        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;

        int[] time = {(int) hour, (int) minute, (int) second};

        return time;
    }

    /**
     * 转换String为int
     *
     * @param s
     * @return
     */
    public static int tranStr2Int(String s) {
        try {
            return Integer.valueOf(s);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 转换String为long
     *
     * @param s
     * @return
     */
    public static long tranStr2Long(String s) {
        try {
            return Long.valueOf(s);
        } catch (Exception e) {
            return 0L;
        }
    }

    /**
     * 转换String为double
     *
     * @param s
     * @return
     */
    public static double tranStr2Double(String s) {
        try {
            return Double.valueOf(s);
        } catch (Exception e) {
            return 0d;
        }
    }

    /**
     * 转换String为float
     *
     * @param s
     * @return
     */
    public static float tranStr2Float(String s) {
        try {
            return Float.valueOf(s);
        } catch (Exception e) {
            return 0f;
        }
    }


    /**
     * 保留小数，四舍五入
     *
     * @param d
     * @param i
     * @return
     */
    public static double scaleDouble(double d, int i) {
        BigDecimal bg = new BigDecimal(d);
        d = bg.setScale(i, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d;
    }

    /**
     * 保留两位小数
     *
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(d);
    }

    /**
     * 去除小数多余0
     * 1.12 ==》 1.12
     * 0.10 ==》 0.1
     * 1.00 ==》 1
     *
     * @param d
     * @return
     */
    public static String formatDoubleWithZero(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }

    /**
     * 删除开头的0
     *
     * @param s
     * @return
     */
    public static String delStartZero(String s) {
        if (s.length() == 2) {
            if (s.startsWith("0")) {
                s = s.substring(1);
            }
        }
        return s;
    }

    /**
     * 格式化银行卡号，四位隔开
     *
     * @param bankNumber
     * @return
     */
    public static String formatBankNumber(String bankNumber) {
        if (TextUtils.isEmpty(bankNumber)) return bankNumber;
        int index = 0;
        StringBuffer buffer = new StringBuffer(bankNumber);
        while (index < buffer.length()) {
            if (index == 4 || index == 9 || index == 14 || index == 19 || index == 24 || index == 29) {
                buffer.insert(index, ' ');
            }
            index++;
        }
        return buffer.toString();
    }

    /**
     * 格式化银行卡号，前五位隔开，之后每四位隔开
     *
     * @param bankNumber
     * @return
     */
    public static String formatBankNumberFirst5(String bankNumber) {
        if (TextUtils.isEmpty(bankNumber)) return bankNumber;
        int index = 0;
        StringBuffer buffer = new StringBuffer(bankNumber);
        while (index < buffer.length()) {
            if (index == 5 || index == 10 || index == 15 || index == 20 || index == 25 || index == 30) {
                buffer.insert(index, ' ');
            }
            index++;
        }
        return buffer.toString();
    }


    /**
     * 名称取第一个字和最后一个字，中间加****
     *
     * @param name
     * @return
     */
    public static String maskNickname(String name) {
        if (!TextUtils.isEmpty(name)) {
            String mask = "****";
            if (name.length() >= 2) {
                return name.charAt(0) + mask + name.charAt(name.length() - 1);
            } else {
                return name + mask;
            }
        }
        return "";
    }
    /**
     * 密码一秒后明文变密文
     *
     * @param editText
     * @return
     */
    public static void changePwdToCiphertext(EditText editText){

        //明文显示
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        //密文显示
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        //光标在最后
        editText.setSelection(editText.length());
    }
}
