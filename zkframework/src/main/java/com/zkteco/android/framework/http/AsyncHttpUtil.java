package com.zkteco.android.framework.http;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.zkteco.android.framework.base.BaseApplication;

import cz.msebera.android.httpclient.Header;

public class AsyncHttpUtil {
	private static final String TAG = AsyncHttpUtil.class.getSimpleName();
	private static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	private static String getBaseUrl(Context mContext){
		return ""/*"http://"+mContext.getString(R.string.str_server_ip)+":"+mContext.getString(R.string.str_server_port)+"/"+Constants.SERVER_PROJECTNAME*/;
	}
	static{
		asyncHttpClient.setTimeout(1000*60);
		asyncHttpClient.setConnectTimeout(1000*60);
		asyncHttpClient.addHeader("token", "cdcd70d5-01d3-4933-b857-6e60cf260d93");
	}
	
	public static AsyncHttpClient getAsyncHttpClient() {
		return asyncHttpClient;
	}
	
	public static void addHeader(String header, String value){
		asyncHttpClient.addHeader(header, value);
	}
	private static String stoken;
	public static String getToken(){
		return stoken;
	}
	public static void setToken(String token){
		stoken = token;
		addHeader("token", token);
	}
	
	public static void post(String url, RequestParams params,TextHttpResponseHandler responseHandler){
		asyncHttpClient.post(getBaseUrl(BaseApplication.getInstance().getContext())+url, params, responseHandler);
	}
	
	

	public static void post(final String url,final RequestParams params,final Class classz,final HttpResponseHandler responseHandler){
		asyncHttpClient.post(getBaseUrl(BaseApplication.getInstance().getContext())+url, params, new TextHttpResponseHandler() {
			@Override
			public void onFinish() {
				super.onFinish();
				responseHandler.onFinish();
			}

			@Override
			public void onStart() {
				super.onStart();
				if(params!=null){
					Log.d(TAG, "[onStart]"+getBaseUrl(BaseApplication.getInstance().getContext())+url+":"+params.toString());
				}
				responseHandler.onStart();
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int code, Header[] header, String result) {
				Log.d(TAG, "[onSuccess]"+getBaseUrl(BaseApplication.getInstance().getContext())+url+":"+result);
				try {
					responseHandler.onSuccess(code, new Gson().fromJson(result, classz));
				} catch (Exception e) {
					Log.d(TAG, "[Exception]"+e.toString());
				}
				
			}
			
			@Override
			public void onFailure(int code, Header[] header, String result, Throwable throwable) {
				Log.d(TAG, "[onFailure]"+getBaseUrl(BaseApplication.getInstance().getContext())+":"+code+"  "+result+" "+throwable.toString());
				responseHandler.onFailure(code,result);
			}
		});
		
	}
	
	
	public static void get(String url, RequestParams params,TextHttpResponseHandler responseHandler){
		asyncHttpClient.get(getBaseUrl(BaseApplication.getInstance().getContext())+url, params, responseHandler);
	}
	
	public static void get(final String url,final RequestParams params,final Class classz,final HttpResponseHandler responseHandler){
		asyncHttpClient.get(getBaseUrl(BaseApplication.getInstance().getContext())+url, params, new TextHttpResponseHandler() {
			@Override
			public void onFinish() {
				super.onFinish();
				responseHandler.onFinish();
			}

			@Override
			public void onStart() {
				super.onStart();
				if(params!=null){
					Log.d(TAG, "[onStart]"+getBaseUrl(BaseApplication.getInstance().getContext())+url+":"+params.toString());
				}
				responseHandler.onStart();
			}

			@SuppressWarnings("unchecked")
			@Override
			public void onSuccess(int code, Header[] header, String result) {
				Log.d(TAG, "[onSuccess]"+getBaseUrl(BaseApplication.getInstance().getContext())+url+":"+result);
				try {
					responseHandler.onSuccess(code, new Gson().fromJson(result, classz));
				} catch (Exception e) {
					Log.d(TAG, "[Exception]"+e.toString());
				}
			}
			
			@Override
			public void onFailure(int code, Header[] header, String result, Throwable throwable) {
				Log.d(TAG, "[onFailure]"+getBaseUrl(BaseApplication.getInstance().getContext())+result);
				responseHandler.onFailure(code,result);
			}
		});
		
	}
	
	
	
	private final int ONFAILURE = -1;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case ONFAILURE:
				String result = msg.getData().getString("result");
				Toast.makeText(BaseApplication.getInstance().getApplicationContext(), "[onFailure result:"+result+"]", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	

}
