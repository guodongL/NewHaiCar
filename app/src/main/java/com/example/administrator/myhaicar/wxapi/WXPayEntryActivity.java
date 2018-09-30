package com.example.administrator.myhaicar.wxapi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.myhaicar.main.mine.Account.This.Model.BankName;
import com.example.administrator.myhaicar.main.order.weipay.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	private Context mContext=this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		//Toast.makeText(WXPayEntryActivity.this, resp.errCode+""+"-----"+resp.errStr+"", Toast.LENGTH_SHORT).show();
	if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
		if (resp.errCode==0)
		{
            EventBus.getDefault().post(new BankName("88"));
			finish();
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("支付页面");
//			//builder.setMessage(getString(R.string.pay_result_callback_msg, resp.errStr +";code=" + String.valueOf(resp.errCode)));
//			builder.setMessage("支付成功");
//			builder.show();

		}
		else if (resp.errCode==-2){
            finish();
        }
		else {
			finish();
			Toast.makeText(WXPayEntryActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

		}

	}
	}
}