package com.gongyou.worker.network;

/**
 * Author: zlc
 * Date: 2017/5/11.
 * 用于存放网络路径
 */

public interface Api {

    //    线上地址
//    String BASE_URL = "https://www.aaaaa100.cn/vvserver.php/ent/";
//    String IMG_BASE_URL = "https://www.aaaaa100.cn/";


    //   debug地址
//    String BASE_URL = "https://www.aaaaa100.cn/development.php/ent/";

    String IMG_BASE_URL = "https://www.weiweidagong.com/";


    //   debug地址
    String BASE_URL = "https://www.weiweidagong.com/development.php/ent/";
//    String BASE_URL = "http://www.aaaaa100.cn/vvserver.php/ent/";
////    String BASE_URL = "https://api.aaaaa100.cn/";
//    String IMG_BASE_URL = "http://www.aaaaa100.cn/";

    String test = "api.php";

    //    测试地址
//    String BASE_URL = "http://192.168.0.244:82/vvserver.php/ent/";
//    String IMG_BASE_URL = "http://192.168.0.244:82/";
//    String IMG_BASE_URL = "http://www.aaaaa100.cn/";

    //    测试地址
//    String BASE_URL = "http://192.168.0.244:82/vvserver.php/ent/";
//    String IMG_BASE_URL = "http://192.168.0.244:82/";

    String BASE_URL_ZRF = "http://api.aaaaa100.cn/ent/";
    String refreshMsgView = "Message/get_no_read_num";
    String getQQUnionId = "https://graph.qq.com/oauth2.0/me";


    //http://api.aaaaa100.cn/ent/
    interface Test {
        String GET_DATA = "Usercenter/index.html";
    }

    interface find_fragment {

        String mapData = "Mapindex/index";
        String getListFilterData = "Joblist/index";
        String searchProfession = "Joblist/profession_list";
        String searchMayInterest = "Joblist/intest_profession";
        String getJobListDetail = "Joblist/job_detail";
        String addCollection = "Usercenter/add_collect";
        String report = "Usercenter/add_accuse";
        String deleteCollection = "Usercenter/delete_collect_job";
        String getJobList = "Vvrob/get_joblist";
        String addRobJob = "Vvrob/add_rob_job";
        String getUserInfo = "Joblist/user_info";
        String getUserDetailJobList = "Joblist/seek_job";
        String getUserCommentList = "Joblist/comment_list";
        String getCityData = "City/index";
        String cancelAttention = "Usercenter/delete_follow_user";
        String toAttention = "Usercenter/add_subscribe";
        String popSearchWorkType = "Joblist/profession_list";
    }

    interface on_and_off_fragment {
        String quickInPay = "Fastrecruitment/index";
        String detail_page = "Employee/recruit_detail";
        String detail_page4 = "Fastrecruitment/ongoing_job_detail";
        String detail = "Employee/job_detail";
        String employee = "Employee/index";
        String stop = "Employee/pause_job";
        String refresh = "Employee/refresh_job";
        String delete = "Employee/delete_job";
        String recommendAndTopCost = "Employee/get_service_price";
        String buildOrder = "Employee/save_buy_service";
        String getTimeCardData = "Attendcard/index";
        String getSignManageData = "Attendcard/sign_manage";
        String getTimeSheetData = "attendinfo/index";
        String getSalaryNoPay = "wagetable/unpaidindex";
        String getSalaryHasPay = "wagetable/hadpaidindex";
        String salaryNopayListData = "wagetable/unpaiddetail ";
        String getSalaryDetailData = "wagetable/attendancerecord";
        String getHasArriveData = "Attendcard/has_arrive_user";
        String getFireList = "Dismiss/index";
        String getFireDetailTab0Data = "Dismiss/job_detail";      //同
        String getFireDetailTab1Data = "Dismiss/job_detail";      //同
        String checkFire = "Dismiss/dismiss_user";
        String onLinePay = "Dismiss/online_order";
        String underLinePay = "Dismiss/offline_payment";
        String getInnerQRData = "Innerattendcard/index";
        String getInnerListData = "Innerattendcard/sign_manage";
        String getInnerArrive = "Innerattendcard/has_arrive_user";
        String getInnerNoArrive = "Innerattendcard/no_arrive_user";
        String getUnArrive = "Attendcard/no_arrive_user";
        String changeMoney = "Wage/modify_wage";                    //修改金额
        String getScrollImage = "Mapindex/job_slider";              //获取轮播图
        String getSalaryTableList = "Wage/index";
        String getSalaryWeekProjectList = "Wage/wage_detail";
        String NormalRecruitInfo = "Generalrecruitment/index";

        String onAndOffSpecialOut = "Outerrecruit/index";
        String getSalaryDateList = "Wage/sign_wage_detail";
        String specialOutCancell = "Outerrecruit/cancell_order";
        String specialOutDelete = "Outerrecruit/delete_order";
        String onAndOffSpecialInner = "Innerrecruit/index";
        String specialInnerCancell = "Innerrecruit/cancell_order";
        String specialInnerDelete = "Innerrecruit/delete_order";
        String specialOutDetailData = "Employee/recruit_detail";

        String getAttendanceData = "Outerrecruit/job_detail";

        String getSalaryOrder = "Wage/online_pay";                      //工资表，获取支付订单号
        String offLinePay = "Wage/offline_pay";                         //线下支付

        String forPayment = "Generalrecruitment/for_payment";
        String getSpecialInnerDetailData1 = "Innerrecruit/nopay_job_detail";
        String getSpecialInnerDetailData2 = "Innerrecruit/recruit_job_detail";
        String getSpecialInnerDetailData3 = "Innerrecruit/job_detail";
        String getGroupDetailData = "Innerrecruit/member_list";
        String specialInnerDetailGroup0 = "Innerrecruit/work_group_detail";
        String loadQRCode = "Innerrecruit/gen_qr_code";
        //        String forPayment = "Generalrecruitment/for_payment";
//        String forPayment = "Generalrecruitment/for_payment";
//        String getSpecialInnerDetailData1 = "Innerrecruit/nopay_job_detail";
        String getAttendTableList = "Attendinfo/index";                 //考勤表数据
        String getAttendTableDetail = "Attendinfo/attend_detail";       //考勤表详情
        String quickRecruitDelete = "Fastrecruitment/delete_order";
        String quickRecruitCancel = "Fastrecruitment/cancell_order";
        String quickRecruitPay = "Recruit/fast_job_order_detail";
        String quickRecruitCleanInvalid = "Fastrecruitment/clear_fast_order"; //清除失效
        String acceptUser = "Vvrob/confirm_job";
        String getWorkerDetailData = "Fastrecruitment/user_detail";
        String getInnerRecruitVpdata = "Innerattendcard/index";         //内招考勤滑动页数据
        String getInnerRecruitSearchData = "Innerattendcard/project_list";    //内招考勤搜索数据
        String getRecruitData = "Attendcard/index";                       //考勤打卡
        String getRecruitSearchData = "Attendcard/search_list";            //内招搜索
        String getAllPeople = "Attendcard/tobe_all_user";
        String getArrivePeople = "Attendcard/has_arrive_user";
        String getNoArrivePeople = "Attendcard/no_arrive_user";
        String getInnerArrivalData = "Innerattendcard/tobe_all_user";       //内招管理总人数
        String getQuickInPayDetailData = "Joblist/enterprise_job_detail";
        String innerHasArrival = "Innerattendcard/has_arrive_user";          //内招已到人员
        String innerAbsentPeople = "Innerattendcard/no_arrive_user";           //内招未到人员
        String quickRecruitJudgeBaseData = "Usercenter/go_comment";                       //快结招工评价基本信息
        String submitQuickJudge = "Usercenter/save_comment";                      //快结招工提交评价信息
        String lookJudgeData = "Usercenter/see_comment";                    //查看评价
        String normalPersonData = "Generalrecruitment/job_detail";               //普通招工人员
        String normalPersonPayMoney = "Generalrecruitment/for_payment";           //普通招工结款
        String normalSalaryDataList = "Generalrecruitment/wage_list";           //普通招工工资表
        String normalJudgeDataList = "Generalrecruitment/job_detail";             //普通招工评价列表
        String normalMoneyListModify = "Wage/modify_wage";                      //普通招工结款，修改金额
        String deleteBankCard = "Mywallet/delete_bank_card";                          //删除银行卡
        String quickPeopleList = "Fastrecruitment/ongoing_job_detail";                //快结招工-----》进行中----》人员
        String refreshComSign = "Attendcard/get_sign_card";                     //考勤打卡刷新
        String innerSign = "Innerattendcard/get_sign_card";                       //内招打卡刷新
        String getFireStatus = "Dismiss/getAvailableMoney";                         //获取辞退状态
        String innerSalaryDetail = "Wage/inner_wage_detail";                    //新内招工资表工资详情

        String getBalanceOfDismissal = "Dismiss/dismiss_user";                 //辞退确认


        String getInnerAttendDetail = "Attendinfo/inner_attend_detail";                  //新内招考勤表详情接口
        String getInnerWorkGroupList = "innerrecruit/work_group_list";                  //上下班内招获取全部的工作组


        // String getInnerAttendDetail="Attendinfo/inner_attend_detail";          //新内招考勤表详情接口


        String aliPay1 = "Alipay/pre_pay";                                         //辞退支付宝支付
        String weiChartPay1 = "Weipay/pre_pay";                                    //辞退微信支付
        String moveOrDeleteWorker = "Innerrecruit/out_in_workgroup";             //移动移除工人
        String getInnerPublishOrderInfo = "recruit/inner_edit";                  //编辑接口

        String getInnerSalaryPayOrder = "Wage/inner_online_pay";                //内招工资发放，获取订单号
        String servicePay = "Wage/inner_wage_sheet_pay";                        //备付金支付
        String innerSalaryOffLinePay = "Wage/inner_xianxia_pay";                //内招线下支付
        String go_to_pay="Recruit/outer_job_order_detail";                        //外招待支付详情页去支付
        String start_at_once="Innerrecruit/nowToWork";                 //内招立即开工
        String get_to_be_finished="Innerrecruit/nowWorkOver";                              //内招立即完工
    }

    interface publish_fragment {
        String publishBasicData = "joblist/publish_job";
        String workTypeData = "Recruit/profession_list";
        String workTypeInterstData = "Recruit/intesting_profession";
        String doPublish = "Recruit/save_publish_job";
        String doEditPublish = "Recruit/fast_update";
        String weiChartPay = "Weipay/pre_pay";
        String aliPay = "Alipay/pre_pay";
        String unionPay = "Unionpay/pre_pay";
        String yuEPay = "Balancepay/pre_pay";
        String getPublishTradeDetailData = "Recruit/outer_job_order_detail";
        String getPublishInnerDetailData = "Recruit/inner_job_order_detail";
        String specialInnerPublish = "Recruit/save_inner_job";
        String openSecretOutPay = "Recruit/outer_choose_payer";
        String outPayConfirm = "Recruit/save_outer_payer";
        String openSecretInnerPay = "Recruit/inner_choose_payer";
        String innerPayConfirm = "Recruit/save_inner_payer";
        String getScrollImage = "Mapindex/publish_slider";
        String openSpecialPublish = "Recruit/open_special_recruit";
        String getSPStatus = "Recruit/get_special_recruit";
        String getSPAgreement = "Message/innerpro";
        String noSecretPayAgreement = "Message/protrcol";           //免密支付协议
        String finishPay = "Recruit/payment";                       //完工结算
        String rePublishInner = "recruit/inner_update";
        String daifuUpdate = "recruit/daifu_update";                //被拒绝支付后重新代付
        String updateOrder = "recruit/update_payment";              //更新支付方式
    }

    interface msg_fragment {

        //        String getWorkNotifyList = "Message/job_notice.html";
//        String getWorkNotifyDetail = "Message/job_notice_detail.html";
        String getSystemNotifyList = "Message/system_news.html";
        String getSystemNotifyDetail = "Message/system_notice_detail.html";
        String geTradeNotifyList = "Message/deal_news";
        String getTradeNotifyDetail = "Message/deal_detail";
        String getWorkNotifyList = "Message/job_notice.html";
        String getWorkNotifyDetail = "Message/job_notice_detail.html";
        String getSecretFriendList = "Recruit/get_close_payer";
        String deleteWorkNotify = "Message/del_message.html";
        String deleteSystemNotify = "Message/del_system_news";
        String deleteTradeNotify = "Message/del_deal_news";
        String refusePay = "Message/refuse_pay";
        String getTradeNotifyAgree = "Message/order_detail";
        String getAgree = "Message/apply_deal";                            //工作通知消同意
        String getSpecialInnerDetailData1New = "Innerrecruit/nopay_job_detail";   //特约内招支付中工作详情
        String getData1 = "Innerrecruit/refund_detail";   //特约内招支付中退款详情
        String get_empty_unread="Message/read_all";     //消息设置为全部已读
    }

    interface mine_fragment {
        String userData = "Usercenter/index";
        String walletData = "Mywallet/index";
        String rechargeRange = "Mywallet/get_recharge_data";
        String recharge = "Mywallet/save_recharge";
        String setPayPassword = "Setting/add_paypassword";
        String getSMSCode = "Setting/sms_send_luosimao";
        String reSetPayPassword = "Setting/modify_paypassword";
        String reSetPassword = "Account/retrieve_password";
        String loadCompanyInfo = "Usercenter/basic_info";
        String getShareData = "Share/index";
        String fileUpload = "Usercenter/image_upload";
        String uploadImageHead = "Usercenter/upload_vv_logo";               //修改企业头像
        String updateCompanyImage = "Usercenter/save_verify_info";          //保存修改信息
        String getCompanyConfirmInfo = "Usercenter/verify_info";              //获取企业认证信息
        String getCompanyDisplayInfo = "Usercenter/user_show_pic";              //企业展示
        String updateDisplayPath = "Usercenter/save_user_showpic";          //更新企业展示图
        String deleteDisplayPath = "Usercenter/del_pic";                    //删除企业展示图
        String modifyCompanyBaseInfo = "Usercenter/save_info";              //修改企业基本信息
        String settingStatus = "Setting/flag_password";                     //设置中的状态
        String getOutChargeBaseInfo = "Mywallet/withdraw_cash";               //获取可提现金额
        String getBankCardList = "Mywallet/my_bank_list";                     //银行卡列表
        String addBankCard = "Mywallet/add_bank_card";                        //添加银行卡
        String doOutCharge = "Mywallet/save_apply_withdraw_cash";             //发起提现
        String getBillListData = "Mywallet/bill_list";
        String getBillDetailData = "Mywallet/bill_detail.html";
        String setCompanyAddress = "Usercenter/enterprise_address";         //设置公司地址
        String getCollectionList = "Usercenter/collect_list";                //收藏列表数据
        String delNoEffectCollect = "Usercenter/empty_expire_collect";      //清除失效收藏
        String getAttentionData = "Usercenter/subscribe_list";              //获取关注列表数据
        String signOut = "Setting/login_out";
        String bandMobileNumber = "Setting/bind_mobile";
        String cancelAttention = "Usercenter/delete_follow_user";
        String opinion = "Setting/feedback.html";                             //意见反馈
        String reSetOutMoneyPsw = "setting/add_cashpassword";//设置提现密码
        String checkOutMoneySmsCode = "setting/check_code";
        String sendOutMoneySmSCode = "setting/sms_send_luosimao";
        String checkOutMoneyPsw = "setting/check_cashpassword";
        String submitReSetOutMoneyPsw = "setting/modify_cashpassword";
        String rateInfo = "setting/get_config_list";
        String selectMobile = "Setting/select_mobile";                  //筛选电话号码

        String get_The_Verifying_Code="setting/sms_send_luosimao";        //修改密码_发送验证码
        String getmodifyUserPsw="Setting/reset_password";         //修改密码
        String getVerificationCodeOK="Account/sms_check_code";     //验证码确定
        String getModifyThePassword="Setting/reset_password";       //忘记密码修改密码
        String get_obtain_Code="account/bind_mobile_send";          //绑定手机，获取验证码
        String get_code_ok="Setting/set_mobile_password";                   //绑定手机，确定手机验证码
    }

    interface login {
        String login = "Account/login";
        String thirdPartyLogin = "account/vendor_login.html";
        String imageCode = "Verifycode/generate_verify";
        String sms = "Account/sms_send";
        String regist = "Account/register";
    }

}
