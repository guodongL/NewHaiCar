package com.example.administrator.myhaicar.main.order.weipay;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/5.
 */

public class WeiXinOrder {
    private Context mContext;
    private ProgressDialog dialog;
    private Map<String,String> resultunifiedorder;
    private PayReq req;
    private final IWXAPI msgApi;
    private String strRand = "";
    private long time;
    private String money;
    private String title;
    private String TradeNo;


    public WeiXinOrder(Context mContext, String money, String title, String TradeNo){
        this.mContext = mContext;
        this.money=money;
        this.title = title;
        this.TradeNo=TradeNo;
        msgApi = WXAPIFactory.createWXAPI(mContext,"wx1876d6c050b3bb22", false);
        req=new PayReq();
        msgApi.registerApp(Constants.APP_ID);
    }
    public void WeiChatPay()
    {
        String urlString="https://api.mch.weixin.qq.com/pay/unifiedorder";
        PrePayIdAsyncTask prePayIdAsyncTask=new PrePayIdAsyncTask();
        prePayIdAsyncTask.execute(urlString);
    }
    private class PrePayIdAsyncTask extends AsyncTask<String,Void, Map<String, String>>
    {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(mContext, "提示", "正在提交订单");

        }
        @Override
        protected Map<String, String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            String url= String.format(params[0]);
            String entity=getProductArgs();
            Log.e("Simon",">>>>"+entity);
            byte[] buf= Util.httpPost(url, entity);
            String content = new String(buf);
            Log.e("orion", "----"+content);
            Map<String,String> xml=decodeXml(content);
            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (dialog != null) {
                dialog.dismiss();
            }
            resultunifiedorder=result;
            genPayReq();//生成签名参数
            sendPayReq();
        }
    }

    //微信支付类
    /*
	 * 调起微信支付
	 */
    private void sendPayReq() {
        msgApi.registerApp(Constants.APP_ID);
        msgApi.sendReq(req);
    }
    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
    private void genPayReq() {

        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        if (resultunifiedorder!=null) {
            req.prepayId = resultunifiedorder.get("prepay_id");
            //req.packageValue = "prepay_id="+resultunifiedorder.get("prepay_id");
            req.packageValue = "Sign=WXPay";
            Log.i(">lis**********>>>>",req.prepayId);

        }
        else {
            Toast.makeText(mContext, "prepayid为空", Toast.LENGTH_SHORT).show();
        }
        req.nonceStr = getNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package",req.packageValue));//"Sign=WXPay"
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        signParams.add(new BasicNameValuePair("sign", req.sign));
        Log.e("Simon***************", "----"+signParams.toString());

    }
    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("Simon","----"+appSign);
        return appSign;
    }

    //方法类
    public Map<String,String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName=parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:
                        if("xml".equals(nodeName)==false){
                            //实例化student对象
                            xml.put(nodeName,parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {
            Log.e("Simon","----"+e.toString());
        }
        return null;

    }
    private String getProductArgs() {
        // TODO Auto-generated method stub
        StringBuffer xml=new StringBuffer();
        try {
            String nonceStr=getNonceStr();
            xml.append("<xml>");
            List<NameValuePair> packageParams=new LinkedList<NameValuePair>();

            packageParams.add(new BasicNameValuePair("appid",Constants.APP_ID));
            packageParams.add(new BasicNameValuePair("attach", title));

            packageParams.add(new BasicNameValuePair("body",title ));//"办理VIP会员" title

            packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));

            packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));

            packageParams.add(new BasicNameValuePair("notify_url", "http://apptest.yingqin8.com/haiche/app/pay/weixinpays.php"));//写你们的回调地址

            packageParams.add(new BasicNameValuePair("out_trade_no",TradeNo));

            packageParams.add(new BasicNameValuePair("total_fee",money ));

            packageParams.add(new BasicNameValuePair("trade_type", "APP"));

            String sign=getPackageSign(packageParams);
            packageParams.add(new BasicNameValuePair("sign", sign));

            String xmlString=toXml(packageParams);
            return new String(xmlString.getBytes(), "ISO8859-1");
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    //生成随机号，防重发
    private String getNonceStr() {
        // TODO Auto-generated method stub
        Random random=new Random();

        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }
    /**
     生成签名
     */

    private String getPackageSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(Constants.API_KEY);


        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        Log.e("Simon",">>>>"+packageSign);
        return packageSign;
    }
    /*
     * 转换成xml
     */
    private String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<"+params.get(i).getName()+">");


            sb.append(params.get(i).getValue());
            sb.append("</"+params.get(i).getName()+">");
        }
        sb.append("</xml>");

        Log.e("Simon",">>>>"+sb.toString());
        return sb.toString();
    }
}
