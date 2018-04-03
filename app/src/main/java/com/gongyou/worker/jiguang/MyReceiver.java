package com.gongyou.worker.jiguang;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;


import com.gongyou.worker.R;
import com.gongyou.worker.SampleApplicationLike;
import com.gongyou.worker.mvp.bean.NotificationInfo;
import com.gongyou.worker.utils.Constant;
import com.gongyou.worker.utils.DateUtils;
import com.gongyou.worker.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";

	/**
	 * 为了观察透传数据变化.
	 */
	private static int cnt;

	/**
	 * 在PendingIntent 使用时，需要计数2，否则通知数据内容会被新来的数据覆盖掉
	 */
	private int count = 0;
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			LogUtil.e(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				SampleApplicationLike.clientid = regId;
				LogUtil.e(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//				processCustomMessage(context, bundle);
				createNotification(bundle.getString(JPushInterface.EXTRA_MESSAGE));

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 接收到推送下来的通知");
				int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
				LogUtil.e(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 用户点击打开了通知");

				//打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

			} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
				LogUtil.e(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
				//在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
			} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
				boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
				LogUtil.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
			} else {
				LogUtil.e(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
			}
		} catch (Exception e){

		}

	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
					LogUtil.e(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it =  json.keys();

					while (it.hasNext()) {
						String myKey = it.next();
						sb.append("\nkey:" + key + ", value: [" +
								myKey + " - " +json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					LogUtil.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	private void createNotification(String finalData) {
//        Notification notification = new Notification();
		LogUtil.i("生成通知--------------------------");
		int icon = R.drawable.app;
		CharSequence tickerText = "Notification01";
		long when = System.currentTimeMillis();

		Gson gson = new Gson();
		NotificationInfo info = new NotificationInfo();
		try {
			info = gson.fromJson(finalData, NotificationInfo.class);
		} catch (JsonSyntaxException e) {
			LogUtil.e("推送Json数据格式不对"+e.getMessage());
			return;
		}


		NotificationCompat.Builder builder = new NotificationCompat.Builder(SampleApplicationLike.getContext())
				.setSmallIcon(R.drawable.app)
				.setContentTitle(info.title).setAutoCancel(true)
				.setContentText(info.content).setTicker(tickerText).setWhen(when);
		LogUtil.e("推送       \n标题-----》"+info.title+"        内容："+info.content+"     type="+info.payload.type+"          relationId="+info.payload.relation_id);
//        Notification notification = builder.build();
//        Notification notification = new Notification(icon, tickerText, when);
//        Notification.Builder builder = recoverBuilder(SampleApplicationLike.getContext(), notification);
//        notification.defaults = Notification.DEFAULT_SOUND;
//        builder.setAutoCancel(true);
//        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_notification);
//        contentView.setImageViewResource(R.id.image, R.drawable.app);
//        LogUtil.i("finalData-----------" + finalData);
//        Gson gson = new Gson();
//        NotificationInfo info = gson.fromJson(finalData, NotificationInfo.class);
//        contentView.setTextViewText(R.id.title, info.title);
//        contentView.setTextViewText(R.id.text, info.content);

//        notification.contentView = contentView;
		notifyMessageDetail(info.payload.type, info.payload.relation_id, builder);
//        Intent notificationIntent = new Intent(SampleApplicationLike.getContext(), MainActivity.class);
//        notificationIntent.putExtra("s_id",0);
//        notificationIntent.putExtra("type",10);
//        PendingIntent contentIntent = PendingIntent.getActivity(SampleApplicationLike.getContext(), 0, notificationIntent, 0);
//        notification.contentIntent = contentIntent;
//
//        String ns = Context.NOTIFICATION_SERVICE;
//        NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
//        mNotificationManager.notify(cnt, notification);
	}

	private void notifyMessageDetail(int type, String relationId, NotificationCompat.Builder builder) {
		Intent intent = null;
		switch (type) {
			case Constant.PushBarId.FaBu_Success:                     //企业发布     ----》外招有通知--跳转到交易详情
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("企业发布--个推relation_id--------" + relationId);
//                intent.putExtra("type", 77);
				break;
			case Constant.PushBarId.YaoQing_Success:                     //邀请成功                    //   企业版抢工人----个人版在选择工作中看不到该消息
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("邀请成功----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.YaoQing_Fail:                     //邀请失败
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);    //-------》拒绝有通知-----》跳转到招工详情（可以重复拒绝）
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("邀请成功----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.ZhaoPingMessage_Read:                //阅读信息
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);      // ---》有通知----跳转到招工详情
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("阅读信息----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.QiuZhi_Off:                         //招满下架
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("招满下架----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.TongYi_Employ:                      //同意邀请
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);          //有通知---跳转到工作详情
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("同意邀请----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.ShangBan_Sign:                       //上班签到
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);      //未收到
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("上班签到----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Fire:                              //辞退工人
//				intent = new Intent(SampleApplicationLike.getContext(), SpecialEmployDetailActivityC.class);      //------》 有通知-----跳转到招工详情
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("辞退工人----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.XiaBan_Sign:                         //下班签退
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);         // 内招外招都没有通知
//				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("下班签退----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Noraml_Salary_Pay:                  //工资发放(非内招)---->区分快结与特约------>改为快结         //1.特约外招无通知
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("fragment", Constant.FragmentId.SalaryHasPayDetailFragment);
				intent.putExtra("p_id", relationId);
				intent.putExtra("type_id", Constant.QUICK_RECRUIT);
//                intent.putExtra("date", mAdapter.fatherList.get(position - 1).date);
				intent.putExtra("pay_type", Constant.HAS_PAY);
				LogUtil.e("工资发放(非内招)----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Out_Salary_Pay:                  //工资发放(非内招)---->区分快结与特约------>改为外招         //1.特约外招无通知
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("fragment", Constant.FragmentId.SalaryHasPayDetailFragment);
				intent.putExtra("p_id", Integer.valueOf(relationId));
				intent.putExtra("date", DateUtils.dateToString(new Date()));
				intent.putExtra("type_id", Constant.SPECIAL_OUT_RECRUIT);
//                intent.putExtra("date", mAdapter.fatherList.get(position - 1).date);
				intent.putExtra("pay_type", Constant.HAS_PAY);
				LogUtil.e("工资发放(非内招)----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Inner_Salary_Pay_YiYuFu:             //工资发放（内招已预付）
//				intent = new Intent(SampleApplicationLike.getContext(), NewInnerHasPaySalaryActivity.class);      //2.特约内招有通知，跳转到工资发放详情页，但是标题没有显示。
				intent.putExtra("order_id", relationId);
				intent.putExtra("p_id", relationId);                //-------->缺少   p_id参数
				LogUtil.e("工资发放----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Inner_Salary_Pay_WuYuFu:             //工资发放（内招无预付）             //无通知
//				intent = new Intent(SampleApplicationLike.getContext(), NewInnerHasPaySalaryActivity.class);
				intent.putExtra("order_id", relationId);
				intent.putExtra("p_id", relationId);                //-------->缺少   p_id参数
				LogUtil.e("工资发放----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.BuQian_Inner:                        //内招补签                        //有跳转，跳转成功
//                intent = new Intent(SampleApplicationLike.getContext(), AttendTableInnerDetailActivity.class);
//				intent = new Intent(SampleApplicationLike.getContext(),EntryActivity.class);
				intent.putExtra("fragment", Constant.FragmentId.WorkNotifyDetailFragmentNew);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("job_type", Constant.SPECIAL_IN_RECRUIT+"");
				LogUtil.e("内招补签----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.QiuZhi_Work:                         //求职工作
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);         //个人求职，有通知，跳转到招工详情
				intent.putExtra("p_id", Integer.valueOf(relationId));
//                intent.putExtra("type", 77);
				LogUtil.e("求职工作----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.TongYi_Recruit:                      //同意雇佣
//				intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);            //个人接受有通知
				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("同意雇佣----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.ShuangYue:                           //工人爽约
//                intent = new Intent(SampleApplicationLike.getContext(), QuickRecruitDetailActivityB.class);
//                intent.putExtra("p_id", Integer.valueOf(relationId));

//				intent = new Intent(SampleApplicationLike.getContext(), SpecialEmployDetailActivityC.class);      //------》 有通知-----跳转到招工详情
				intent.putExtra("p_id", Integer.valueOf(relationId));

				LogUtil.e("工人爽约----个推relation_id--------" + relationId);
				break;
			//系统通知
			case Constant.PushBarId.Today_Recommend:                     //今日推荐
				LogUtil.e("今日推荐----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Salary_Warm:                         //温馨提示（内招项目）
//				intent = new Intent(SampleApplicationLike.getContext(), SpecialEmployDetailActivityG.class);
				intent.putExtra("p_id", Integer.valueOf(relationId));
				LogUtil.e("温馨提示----个推relation_id--------" + relationId);
				break;
			//交易通知break;


			case Constant.PushBarId.OutPayForAnother:                        //交易通知（代付外招）
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("fragment", Constant.FragmentId.TradeNotifyDetailFragment);
				LogUtil.e("交易通知----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.OutPayForAnother_Refused:                        //交易通知（代付外招拒绝）
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("fragment", Constant.FragmentId.TradeNotifyDetailFragment);
				LogUtil.e("交易通知----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.OutPayForAnother_Success:                        //交易通知（代付外招成功代付）
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("fragment", Constant.FragmentId.TradeNotifyDetailFragment);
				LogUtil.e("交易通知----个推relation_id--------" + relationId);
				break;


			case Constant.PushBarId.InnerPayForAnother:                                 //交易通知（代付内招）       发起代付人未收到已通知代付，代付人收到了。
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("fragment", Constant.FragmentId.TradeNotifyDetailFragment);
				LogUtil.e("交易通知----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.InnerPayForAnother_Refused:                        //交易通知（代付内招拒绝）         有通知---条状到交易详情
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("fragment", Constant.FragmentId.TradeNotifyDetailFragment);
				LogUtil.e("交易通知----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.InnerPayForAnother_Success:                        //交易通知（代付内招成功代付）        有通知---条状到交易详情
//				intent = new Intent(SampleApplicationLike.getContext(), EntryActivity.class);
				intent.putExtra("s_id", Integer.valueOf(relationId));
				intent.putExtra("fragment", Constant.FragmentId.TradeNotifyDetailFragment);
				LogUtil.e("交易通知----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Refund_ProjectMoney:                        //交易通知（代付内招拒绝）         有通知---条状到交易详情
//				intent = new Intent(this, RefundActivity.class);
				intent.putExtra("order_no",relationId);
//				startActivity(intent);
				LogUtil.e("退款通知----个推relation_id--------" + relationId);
				break;
			case Constant.PushBarId.Inner_Refund_ProjectMoney:                        //交易通知（代付内招成功代付）        有通知---条状到交易详情
//				intent = new Intent(this, RefundActivity.class);
				intent.putExtra("order_no",relationId);
//				startActivity(intent);
				LogUtil.e("退款通知----个推relation_id--------" + relationId);
				break;

		}
		if (intent != null) {
			count = new Random().nextInt();
			PendingIntent contentIntent = PendingIntent.getActivity(SampleApplicationLike.getContext(), count, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            LogUtil.e("count=" + count + "       type=" + type + "          relationId=" + relationId + "     cnt=" + cnt);
			Notification notification = builder.build();
			notification.defaults = Notification.DEFAULT_SOUND;
			notification.contentIntent = contentIntent;
			String ns = Context.NOTIFICATION_SERVICE;
			NotificationManager mNotificationManager = (NotificationManager) SampleApplicationLike.getContext().getSystemService(ns);
			mNotificationManager.notify(cnt, notification);
		}
	}


	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
//		if (MainActivity.isForeground) {
//			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//			if (!ExampleUtil.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (extraJson.length() > 0) {
//						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
//		}
	}
}
