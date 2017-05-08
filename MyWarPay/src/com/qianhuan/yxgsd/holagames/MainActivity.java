package com.qianhuan.yxgsd.holagames;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.qianhuan.yxgsd.holagames.alipay.Alipay;
import com.qianhuan.yxgsd.holagames.alipay.Alipay.AlipayResultCallBack;
import com.qianhuan.yxgsd.holagames.net.HttpUtil;
import com.qianhuan.yxgsd.holagames.net.Json;
import com.qianhuan.yxgsd.holagames.net.NetException;
import com.qianhuan.yxgsd.holagames.net.RespModel;
import com.qianhuan.yxgsd.holagames.net.SDKJsonReqHandler;
import com.qianhuan.yxgsd.holagames.payUtils.OrderNumber;
import com.qianhuan.yxgsd.holagames.payUtils.PayResult;
import com.qianhuan.yxgsd.holagames.wechat.WXPay;
import com.qianhuan.yxgsd.holagames.wechat.WXPay.WXPayResultCallBack;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	private Button btnWXPay;
	private Button btnAlipay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWXPay = (Button) findViewById(R.id.btnWXPay);
        btnAlipay = (Button) findViewById(R.id.btnAliPay);
        
        /**
         * 微信支付
         */
        btnWXPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "http://139.129.21.196/hola_sdk_server/pay/create_order.php";
				String amount = "0.01";
				String orderId = Math.random() + "";
				String productName = "钻石";
				String appUid = "gameId";
				String nitifyUrl = "";
				String product_id = "product_id111";
				String app_username = "app_username222";
				
				String token = "111222333";
				
				String channelID = "wechatpay";
				String platform = "android";
				
				Map<String, Object> params = new HashMap<String, Object>(0);
				params.put("amount", amount);
				params.put("app_order_id", orderId);
				params.put("app_uid", appUid);
				params.put("notify_uri", nitifyUrl);
				params.put("product_name", productName);
				params.put("product_id", product_id);
				params.put("app_username", app_username);
				params.put("access_token", token);
				params.put("channel", channelID);
				params.put("platform", platform);
				params.put("game_name", "10001");
				HttpUtil.newHttpsIntance(MainActivity.this).httpsPost(MainActivity.this, url, params, new SDKJsonReqHandler(params) {
					
					@Override
					public void ReqYes(Object reqObject, String content) {
						try {
							Log.e("content", content);
							RespModel respModel = Json.StringToObj(content, RespModel.class);
							if(respModel.getErrno() == 200){
								JSONObject dataObject = JSONObject.parseObject(respModel.getData());
								OrderNumber orderNumber = Json.StringToObj(respModel.getData(), OrderNumber.class);
								orderNumber.getOut_trade_no();
								if(null == orderNumber || null == orderNumber.getOut_trade_no() || orderNumber.getOut_trade_no().isEmpty()){
									Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
								}else{
									String pay_info = dataObject.getString("pay_info");
									Log.e("tag", pay_info);
									doWXPay(pay_info);
									
								}
							}else{
								Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void ReqNo(Object reqObject, NetException slException) {
						Log.e(TAG, slException.toString());
						Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
        
    	
        
        /**
         * ֧支付宝支付
         */
        btnAlipay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "http://139.129.21.196/hola_sdk_server/pay/create_order.php";
				String amount = "0.01";
				String orderId = "123123123123123";
				String productName = "钻石";
				String appUid = "gameId";
				String nitifyUrl = "http://139.129.21.196/hola_sdk_server/pay/trade_notify.php";
				String product_id = "product_id111";
				String app_username = "app_username222";
				
				String token = "111222333";
				
				String channelID = "alipayquick";
				String platform = "android";
				
				Map<String, Object> params = new HashMap<String, Object>(0);
				params.put("amount", amount);
				params.put("app_order_id", orderId);
				params.put("app_uid", appUid);
				params.put("notify_uri", nitifyUrl);
				params.put("product_name", productName);
				params.put("product_id", product_id);
				params.put("app_username", app_username);
				params.put("access_token", token);
				params.put("channel", channelID);
				params.put("platform", platform);
				params.put("game_name", "10001");
				Log.e(TAG, params.toString());
				
				HttpUtil.newHttpsIntance(MainActivity.this).httpsPost(MainActivity.this, url, params, new SDKJsonReqHandler(params) {
					
					@Override
					public void ReqYes(Object reqObject, String content) {
						try {
							Log.e("content", content);
							RespModel respModel = Json.StringToObj(content, RespModel.class);
							if(respModel.getErrno() == 200){
								JSONObject dataObject = JSONObject.parseObject(respModel.getData());
								OrderNumber orderNumber = Json.StringToObj(respModel.getData(), OrderNumber.class);
								orderNumber.getOut_trade_no();
								if(null == orderNumber || null == orderNumber.getOut_trade_no() || orderNumber.getOut_trade_no().isEmpty()){
									Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
								}else{
									final String pay_info = dataObject.getString("pay_info");
									Log.e("pay_info", pay_info);
									doAlipay(pay_info);
								}
							}else{
								Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void ReqNo(Object reqObject, NetException slException) {
						Log.e(TAG, slException.toString());
						Toast.makeText(getApplicationContext(), "获取订单失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
    }
    
    /**
     * 微信致支付
     * @param pay_info
     */
    private void doWXPay(String pay_info){
    	org.json.JSONObject mJsonObject = null;
    	try {
			mJsonObject = new org.json.JSONObject(pay_info);
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	//String wx_appid = "wx8755c966ad175c4c";
    	String wx_appid = mJsonObject.optString("appid");
    	WXPay.init(getApplicationContext(), wx_appid);
    	WXPay.getInstance().doPay(pay_info, new WXPayResultCallBack() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(int error_code) {
				switch (error_code) {
				case WXPay.NO_OR_LOW_WX:
					Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
					break;
					
				case WXPay.ERROR_PAy:
					Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
					break;
					
				case WXPay.ERROR_PAY_PARAM:
					Toast.makeText(getApplicationContext(), "订单参数不对", Toast.LENGTH_SHORT).show();
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onCancel() {
				Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT).show();
			}
		});
    }
    
    /**
     * 支付宝支付
     * @param pay_info
     */
    private void doAlipay(String pay_info){
    	new Alipay(this, pay_info, new AlipayResultCallBack() {
			
			@Override
			public void onSuccess() {
				Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onError(int error_code) {
				switch (error_code) {
				case Alipay.ERROR_RESULT:
					Toast.makeText(getApplicationContext(), "返回错误", Toast.LENGTH_SHORT).show();
					break;
				
				case Alipay.ERROR_NETWORK:
					Toast.makeText(getApplicationContext(), "网络异常", Toast.LENGTH_SHORT).show();
					break;
					
				case Alipay.ERROR_PAY:
					Toast.makeText(getApplicationContext(), "支付失败" + error_code, Toast.LENGTH_SHORT).show();
					break;
					
				default:
					Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
					break;
				}
			}
			
			@Override
			public void onDealing() {
				Toast.makeText(getApplicationContext(), "处理中", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onCancel() {
				Toast.makeText(getApplicationContext(), "支付取消", Toast.LENGTH_SHORT).show();
			}
		}).doPay();
    }
    
    
}
