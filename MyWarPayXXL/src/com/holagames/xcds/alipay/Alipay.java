package com.holagames.xcds.alipay;

import java.util.Map;

import com.alipay.sdk.app.PayTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

public class Alipay {
	
	public static final int ERROR_RESULT = 1; //错误返回
	public static final int ERROR_PAY    = 2; //支付失败
	public static final int ERROR_NETWORK= 3; //网络异常
	
	private String mPay_info;
	private PayTask mPayTask;
	private AlipayResultCallBack mCallBack;
	
	public interface AlipayResultCallBack{
		void onSuccess(); //支付成功
		void onDealing(); //支付处理中
		void onCancel();  //取消支付
		void onError(int error_code); //支付失败，返回支付失败代码
	}
	
	public Alipay(Context context, String pay_info, AlipayResultCallBack callBack){
		this.mPay_info = pay_info;
		this.mCallBack = callBack;
		this.mPayTask = new PayTask((Activity) context);
	}
	
	public void doPay(){
		final Handler handler = new Handler();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				final Map<String, String> pay_result = mPayTask.payV2(mPay_info, true);
				handler.post(new Runnable() {
					
					@Override
					public void run() {
						if(mCallBack == null){
							return;
						}
						
						if(pay_result == null){
							mCallBack.onError(ERROR_RESULT);
							return;
						}
						
						String resultStatus = pay_result.get("resultStatus");
						if(TextUtils.equals(resultStatus, "9000")){
							mCallBack.onSuccess();
						}else if(TextUtils.equals(resultStatus, "8000")){
							mCallBack.onDealing();
						}else if(TextUtils.equals(resultStatus, "6001")){
							mCallBack.onCancel();
						}else if(TextUtils.equals(resultStatus, "6002")){
							mCallBack.onError(ERROR_NETWORK);
						}else if(TextUtils.equals(resultStatus, "4000")){
							mCallBack.onError(ERROR_PAY);
						}
					}
				});
			}
		}).start();
	}

}
