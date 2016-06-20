package models;

//necessary components are imported
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConvertResponseDemo{

    // essential URL structure is built using constants
	public static final String ACCESS_KEY = "2aee6fcea0508df353fd88580c1aad8c";
	public static final String BASE_URL = "http://apilayer.net/api/";
	public static final String ENDPOINT = "live";
	public static final String currencies = "JPY,EUR,GBP,AUD,ZAR,CAD,CHF,CNY,HKD,RUB";

	// this object is used for executing requests to the (REST) API
	public static CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 *
	 * Notes:<br><br>
	 *
	 * A JSON response of the form {"key":"value"} is considered a simple Java JSONObject.<br>
	 * To get a simple value from the JSONObject, use: <JSONObject identifier>.get<Type>("key");<br>
	 *
	 * A JSON response of the form {"key":{"key":"value"}} is considered a complex Java JSONObject.<br>
	 * To get a complex value like another JSONObject, use: <JSONObject identifier>.getJSONObject("key")
	 *
	 * Values can also be JSONArray Objects. JSONArray objects are simple, consisting of multiple JSONObject Objects.
	 *
	 *
	 */

	// sendConvertRequest() function is created to request and retrieve the data
	public static Map<String,Double> sendConvertRequest(){

		// the "from", "to" and "amount" can be set as variables
		HttpGet get = new HttpGet(BASE_URL + ENDPOINT + "?access_key=" + ACCESS_KEY + "&currencies=" + currencies + "&format=1");
		try {
			CloseableHttpResponse response =  httpClient.execute(get);
			HttpEntity entity = response.getEntity();

//			response.close();

			JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
			JSONObject quotes = jsonObject.getJSONObject("quotes");
			Map<String,Double> result = new LinkedHashMap<String,Double>();
			for(String name : quotes.getNames(quotes)) {
				result.put(name, quotes.getDouble(name));
			}
			return result;
			/*
			System.out.println("Single-Currency Conversion");

			// parsed JSON Objects are accessed according to the JSON resonse's hierarchy, output strings are built
			System.out.println("From : " + jsonObject.getJSONObject("query").getString("from"));
			System.out.println("To : " + jsonObject.getJSONObject("query").getString("to"));
			System.out.println("Amount : " + jsonObject.getJSONObject("query").getLong("amount"));
			System.out.println("Conversion Result : " + jsonObject.getDouble("result"));
			System.out.println("\n");
			*/
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
//
//    // sendConvertRequest() function is executed
//	public static void main(String[] args) throws IOException{
//		sendConvertRequest();
//		httpClient.close();
//	}
}