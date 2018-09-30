package com.example.administrator.myhaicar.main.order.weipay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class AppRegister extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//final IWXAPI api = WXAPIFactory.createWXAPI(context, null);
		final IWXAPI api = WXAPIFactory.createWXAPI(context,"wxf57184e9107971b8", false);

		// ����appע�ᵽ΢��
		api.registerApp(Constants.APP_ID);
	}
}
