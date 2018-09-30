package com.example.administrator.myhaicar.main.order.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderInfoUtil2_0 {
	public static final String APPID =  "2017051507243969";
	public static final String RSA_PRIVATE = "MIIEowIBAAKCAQEAq7bUPsBtiVvQrJQZufUYG2bfGYjbR8xok7XM+aAko+4+wbb9QOuOKESY7sL7MZiVUg6RIcQqrDhVqGEz9QnQfcihDqdZ3f7zzbS/U0nB9V1TVbFy0CIOOsMEl1gtG7gSHTRBrRx8k8TLsA55m0CyARrjKphvqtp8cy6EunFm2eEO/NIKQ5JJNI7lkYBCTTxu7BMMJ1HguTEMDldj+w35Wzk1OJvnaSj7eQALtmEuvF5VD6WRTy26yIPs0m+4tzw+opMfnQQnc3ftnSGTfGZm8Lk4vQfxs7bxwb9lINH07bMPZ0Rp5ipqyLFmZJTRUp22oOaq+TB82TwfxZ+U2591nQIDAQABAoIBAQCHAaVYfSTiFU05EuwzN8+vY69PPTni9oTWdd7l+RI1zVCML9poW4uWf3CacLTc5rGoJYnYOYqvg09dpqcaJpBFBSQUCGHw185KUq0Gx/v2dVzbCDxUBiKX8tK+R3aXz552Vaq5d6c+60CWeJsGBNSvZ7tptxY9B7FHQjDbAeQysqSGZ5tilFrIFXQncoZ5ccbxTtcjfOC3rTEdKXO9FGZ338gflyYYGl7PUDRcjZ0aNNBMQto0OpCO1fYKh8uHAYsQ2PCRw6A1e7g9qbuX0q6cNDJQCwsqamrYFKuUqdTB72mysLPu24XN0LOQpTXJHV63xzo9aU1HS48q4mLS/vSlAoGBAOTnqhkkfivZ/TJeZjY1mAXo7A88ghE64zsbYpaLNptJk3SVj/YYuEXASxMk+SsC0dE+d8Ea8lm9Omct3AqB7Mte6IyAWCrRLdNmFMiQDx9mabDNz16aV9joSVH476BM1hsiZVDGACmwPFUZ2fy5KlhVdCaDRoi06HmXKL/cJO6jAoGBAMAKJ9at6aJY4jwFcegQB5MuvCr/DWqBbfEr5lXbodTmDLcEODx/bs1ugXi0sOppkK0CNwwIEfWthLrBlmDDi+x1VYVSUXcSSNeCWB7LdaX5BQJlaMzxP/Mfd3AUpRl7d1HzQDHlB8vJvhRPlwMXQuie2tsfuL6YjN8SKYRrsY6/AoGAZkV/QSaF3M/TiYqVvkipuJI9zfzp3ArkdYchwENIzY7M+/zKylh3FGr63/3X+biTyBpR5QxGFYQvMORTeLpd8utfmU7Kla7J+7aTOJzstMnMFPuPWCQ7daUrBR3k6kkKLCts+u7MKdQc0KTxOTH90mOztnVWsUOqzpCT20zfj2MCgYBSLEZNw08kq9nqiv3pqepD3Yyg7VBFUarEtt8xv/BolCO+sGyL3o+AEhgOmWrWc2N31GnPmShuMtiHvUxmjWYzQSXeDZJUXBuVM+4XB0KJSZwOIWyNvXF6y+0kpYDb/YvaIjdG4lTKq8HNGR1qMirsNQU7HEDGNaqZzUC8rARnKwKBgGsac/dBEWMsYB4+nJndJ83Z+v4YQijnbcOW2wkyXxdbxryCfVokwkmLYz6C8a7VwUZ3xqWXfvzrIXgQ0P2haZIk0pXZ9LnVQXzh4YX+clcCInWMFyybVYwfnJF+YEKIvGrYdbi5+0RVVU4rWxXrdKkmOT0qNHCW2s3IIDY1pbGc";


	/**
	 * 构造支付订单参数列表
	 * @return
	 */
	public static Map<String, String> buildOrderParamMap(String message,String money,String TradeNo) {
		Map<String, String> keyValues = new HashMap<String, String>();

		keyValues.put("app_id", APPID);

		keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\""+money+"\",\"subject\":\""+message+"\",\"body\":\"嗨车\",\"out_trade_no\":\"" + TradeNo +  "\"}");
		
		keyValues.put("charset", "utf-8");

		keyValues.put("apiname", "http://apptest.yingqin8.com/haiche/app/pay/alipays.php");
		keyValues.put("notify_url", "http://apptest.yingqin8.com/haiche/app/pay/alipays.php");

		keyValues.put("app_name", "HaiCheAliPayYQ");

		keyValues.put("method", "alipay.trade.app.pay");

		keyValues.put("sign_type",  "RSA");

		keyValues.put("timestamp", "2016-07-29 16:55:53");

		keyValues.put("version", "1.0");
		
		return keyValues;
	}
	
	/**
	 * 构造支付订单参数信息
	 * 
	 * @param map
	 * 支付订单参数
	 * @return
	 */
	public static String buildOrderParam(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			sb.append(buildKeyValue(key, value, true));
			sb.append("&");
		}
		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		sb.append(buildKeyValue(tailKey, tailValue, true));

		return sb.toString();
	}
	
	/**
	 * 拼接键值对
	 * 
	 * @param key
	 * @param value
	 * @param isEncode
	 * @return
	 */
	private static String buildKeyValue(String key, String value, boolean isEncode) {
		StringBuilder sb = new StringBuilder();
		sb.append(key);
		sb.append("=");
		if (isEncode) {
			try {
				sb.append(URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				sb.append(value);
			}
		} else {
			sb.append(value);
		}
		return sb.toString();
	}
	
	/**
	 * 对支付参数信息进行签名
	 * 
	 * @param map
	 *            待签名授权信息
	 * 
	 * @return
	 */
	public static String getSign(Map<String, String> map) {
		List<String> keys = new ArrayList<String>(map.keySet());
		// key排序
		Collections.sort(keys);

		StringBuilder authInfo = new StringBuilder();
		for (int i = 0; i < keys.size() - 1; i++) {
			String key = keys.get(i);
			String value = map.get(key);
			authInfo.append(buildKeyValue(key, value, false));
			authInfo.append("&");
		}

		String tailKey = keys.get(keys.size() - 1);
		String tailValue = map.get(tailKey);
		authInfo.append(buildKeyValue(tailKey, tailValue, false));

		String oriSign = SignUtils.sign(authInfo.toString(), RSA_PRIVATE, false);
		String encodedSign = "";

		try {
			encodedSign = URLEncoder.encode(oriSign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "sign=" + encodedSign;
	}

}
