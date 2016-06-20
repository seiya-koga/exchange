package models;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;

public class Currencies {

	public static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static Map<String, String> sendConvertRequest() {
		HttpGet get = new HttpGet("http://api.aoikujira.com/kawase/json/usd");
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

	
			Map<String,String> result = new HashMap<String,String>();
			JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
			for(String name : jsonObject.getNames(jsonObject)) {
				result.put(name, jsonObject.getString(name));
			}
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Map<String, String> sendConvertRequest2() {
		HttpGet get = new HttpGet("http://api.aoikujira.com/kawase/json/jpy");
		try {
			CloseableHttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();

			// response.close();
			Map<String,String> result = new HashMap<String,String>();
			JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
			for(String name : jsonObject.getNames(jsonObject)) {
				result.put(name, jsonObject.getString(name));
			}
			return result;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
