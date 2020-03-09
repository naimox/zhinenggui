package com.jeesite.modules.common;

/**
 * Created by Administrator on 2018/9/1 0001.
 */
public class ResultMsg {

    /**
     *  成功  200
     */
    public final static int SUCCESS_FLAG = 200;             //成功


    /**
     *  系统问题、权限问题 200+
     */
    public final static int LOGIN_OVER_TIME = 202;          //连接超时
    public final static int PERMISSION_ERROR = 204;         //权限错误
    public final static int INTERFACE_ERROR = 206;          //接口连接错误
    public final static int DEVICE_NULL_ERROR = 208;        //设备空错误
    public final static int DEVICE_VIEW_NULL_ERROR = 209;			//设备视图数据为空
    /**
     *  操作问题    100-200
     */
    public final static int INPUT_NULL = 101;               //输入空错误
    public final static int MODEL_NULL = 102;               //查询model为空
    public final static int INPUT_ERROR = 121;              //输入错误
    public final static int IMG_FIAL	= 122;				//图片错误
    public final static int MANEY_FAIL =  123;				//余额不足

    /**-----返回消息内容 中文后缀 _ZN------*/
    /**
     * 成功返回消息 MSG开头
     */
    public final static String MSG_SUCCESS_ZN = "操作成功";
    public final static String MSG_VALID_ZN = "验证码发送成功";
    public final static String MSG_REGIST_ZN = "注册成功";
    /**
     * 失败返回消息 ERR开头
     */
    public final static String ERR_OVER_TIME_ZN = "连接超时";
    public final static String ERR_EXPRESS_ZN = "快递员权限错误";
    public final static String ERR_NULL_DEVICE_ZN = "设备信息错误";
    public final static String ERR_NULL_ZN = "必填信息不能为空";
    public final static String ERR_NULL_DATA="无订单数据";
    public final static String ERR_ORDER_ZN = "验证码错误或订单不存在";
    public final static String ERR_OPEN_DOOR_ZN = "设备不在线，开门失败";
    public final static String ERR_IMG_FAIL="上传失败,无此订单或已存在此图片文件";
    public final static String ERR_CODE_NULL = "验证码数量不足";
    public final static String ERR_CODE_ERROR = "验证码发送失败";
    public final static String ERR_MANEY_FAIL="余额不足";
    public final static String ERR_COMPANY_USER_ID_FAIL="无此设备,或此设备未分配所属人";
    public final static String ERR_USER_ADD_FAIL="已有注册信息，注册失败";
    public final static String ERR_COMPANY_USER_ID_NULL="查无此设备配置的管理员";
    public final static String ERR_DEVICE_VIEW_NULL="无此设备的视图数据";
}
