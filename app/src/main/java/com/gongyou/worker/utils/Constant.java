package com.gongyou.worker.utils;

public final class Constant {
    public static final String DEVICE_CODE = "be3714e1271600076c24bd56aab87242";
    //  日志开关   >= 7 开 <=1 关
    public static final int DEBUGLEVEL = 7;
    public static final int ERROR_LOAD = -1;

    public static final int OPEN_PIC = 100;
    public static final int OPEN_CAMERA = 101;

    public static final int LOWER_16_BITS = 2131680000;
//    public static final int LOWER_16_BITS = 2131550000;

    public static final long DATE_TIME = 86400000;

    public static final String APP_ID = "wx7df51cd6942a8671";
    public static final int READ_CONTACTS = 13;

    public static final int HAS_MORE = 10;          //单次请求条目数
    /**
     * 支付中
     */
    public static final int InPayment = 1;        //页面指针
    /**
     * 招工中
     */
    public static final int Recruitment = 2;
    /**
     * 进行中
     */
    public static final int UnderWay = 3;
    /**
     * 已完成
     */
    public static final int Completed = 4;

    public static final int NO_PAY = 1;
    public static final int HAS_PAY = 2;

    public static final int QUICK_RECRUIT = 1;
    public static final int NORMAL_RECRUIT = 2;
    public static final int SPECIAL_IN_RECRUIT = 4;         //特内
    public static final int SPECIAL_OUT_RECRUIT = 3;        //特外
    public static final double PAY_RATE = 0.006;        //费率


    public static final long Net_Time = 500;             //网络请求限定时间

    public static final int SpecialEmploy = 10;               //SpecialEmploy页面
    public static final int LOC_CITY = 1000;
    public static final int MOVE_WORKER = 1;            //移动工人
    public static final int DELETE_WORKER = 2;            //移除工人
    public static int HelpCenter = 0;                   //关于我们
    public static int ServiceClause = 1;                 //服务条款
    public static int AboutAs = 2;                      //关于我们
    public static String HelpUrl = "http://www.aaaaa100.cn/helper.html";                              //关于我们
    public static String ServiceUrl = "https://www.aaaaa100.cn/VVdagong/index.html";                 //服务条款
    public static String AboutUrl = "https://www.aaaaa100.cn/qiyeban/index.html";                      //关于我们
    public static int INNER_SALARY_PAY = 20;
    public static int INNER_SALARY_PAY_YUFUANDOTHER = 21;

    //身份证前面
    public static final int ID_CARD_FRONT = 200;
    //身份证反面
    public static final int ID_CARD_BACK = 201;
    //手持身份证
    public static final int HAND_ID_CARD = 203;
    //手持营业执照
    public static final int BUSINESS_LICENSE = 204;

    public static final class FragmentId {
        public static final int GET_DATA = 0;
        public static final int SecretFriendContactFragment = 1;
        public static final int PublishTradeDetailFragment = 2;
        public static final int CollectionFragment = 3;             //首页-->企业中心-->收藏
        public static final int AttentionFragment = 4;              //首页-->企业中心-->关注
        public static final int TradeWayFragment = 5;


        public static final int EmployManageFragment = 0x50;
        public static final int WorkerAttendanceDetailFragment = 0x51;
        public static final int JudgeWorkerFragment = 0x52;
        public static final int NormalWorkingDetailFragment = 0x53;
        public static final int NormalWorkingPayListFragment = 0x54;
        public static final int InnerSignManageDetailFragment = 0x55;
        public static final int SignManageDetailFragment = 0x56;
        public static final int FireFragment = 0x57;
        public static final int SalaryProjectPayListFragment = 0x58;
        public static final int SalaryHasPayDetailFragment = 0x59;
        public static final int WorkNotifyDetailFragment = 0x60;
        public static final int TradeNotifyDetailFragment = 0x61;
        public static final int AttendTableDetailFragment = 0x62;
        public static final int WorkNotifyDetailFragmentNew = 0x63;

    }

    public interface PushBarId {
        int YaoQing_Success = 101;                     //邀请成功                               ------>通知能收到------>详情能获取【不需要】
        int YaoQing_Fail = 115;                        //邀请失败（被个人拒绝）                 ------>通知能收到------>详情能获取
        int ZhaoPingMessage_Read = 102;                //阅读信息                               ------>通知能收到------>详情能获取
        int QiuZhi_Off = 103;                          //招满下架                               ------>通知能收到------>详情能获取
        int TongYi_Employ = 104;                       //同意邀请                               ------>通知能收到------>详情能获取
        int ShangBan_Sign = 105;                       //上班签到                               ------>收不到通知                    【不需要】
        int Fire = 106;                                //辞退工人                               ------>通知能收到------>详情能获取
        int XiaBan_Sign = 107;                         //下班签退                                                                     【不需要】
        int Noraml_Salary_Pay = 108;                   //工资发放(非内招)  ---->改为快结情况
        int Out_Salary_Pay = 408;                      //工资发放(非内招)  ---->改为外招情况
        int Inner_Salary_Pay_YiYuFu = 109;             //工资发放（内招已预付）                 ------>通知能收到------->详情能获取
        int Inner_Salary_Pay_WuYuFu = 110;             //工资发放（内招无预付）                 ------>线上支付能收到消息通知.但无法获取详情/线下支付没有推送
        int BuQian_Inner = 111;                        //内招补签                               ------>消息通知能收到---->详情能获取
        int QiuZhi_Work = 112;                         //求职工作                               ------>通知能收到------->详情能获取
        int TongYi_Recruit = 113;                      //同意雇佣                               ------>无通知                          【不需要】
        int ShuangYue = 114;                           //工人爽约
        //系统通知
        int Today_Recommend = 201;                     //今日推荐
        int Salary_Warm = 202;                         //温馨提示（内招项目）
        //交易通知
        int InnerPayForAnother = 301;                  //交易通知（内招代付）                     ------>通知能收到------>详情能获取
        int InnerPayForAnother_Success = 302;          //交易通知（内招代付支付成功）             ------>通知能收到------>详情能获取
        int InnerPayForAnother_Refused = 303;          //交易通知(内招)代付被拒绝                 ------>通知能收到------>详情能获取

        int OutPayForAnother = 401;                     //交易通知（外招代付）                    ------>通知能收到------>详情能获取
        int OutPayForAnother_Success = 402;             //交易通知（外招成功代付）                ------>通知能收到------>详情能获取
        int OutPayForAnother_Refused = 403;             //交易通知(外招)代付被拒绝                ------>通知能收到------>详情能获取【303】
        int FaBu_Success = 11;                          //企业发布成功                            ------>外招有通知----->-详情能跳转


        //退款通知
        int Refund_ProjectMoney = 120;                  //非内招项目退款
        int Inner_Refund_ProjectMoney = 320;            //内招项目退款
    }
}
