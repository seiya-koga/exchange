package controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.ConvertResponseDemo;
import models.Currencies;
import models.LiveResponseDemo;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	  public static Result index() throws IOException {
	    	Set<String> countries = countries1();
	    	Map<String,BigDecimal> dollars = dollar();
	    	Map<String,BigDecimal> yens = yen();
	    	Map<String,String> result = new HashMap<String,String>();
	    	Map<String,String> resultDoll = new HashMap<String,String>();
	    	BigDecimal onehundred = new BigDecimal("100");

	    	for(String country : countries) {
	    		BigDecimal dollar = dollars.get(country);
    			System.out.println(dollar);
    			BigDecimal yen = yens.get(country);
        	   	 System.out.println(yen);

	    		BigDecimal val = dollar.divide(yen,6,BigDecimal.ROUND_HALF_UP);

	    		   result.put( country, val.toString());

	    	}
	    	for(String country : countries){
	    		BigDecimal dollar = dollars.get(country);
	    		BigDecimal multi = dollar.multiply(onehundred);
	    		resultDoll.put(country, multi.toString());


	    	}
	    	System.out.println(resultDoll);
	    	return ok(index.render(result,resultDoll));

	  }






    private static Map<String,BigDecimal> dollar(){
    	Map<String,String> data = Currencies.sendConvertRequest();
    	Set<String> countries = countries1();
    	Map<String,BigDecimal> result = new HashMap<String,BigDecimal>();
    	for(String key : data.keySet()) {
    		if(countries.contains(key)) {
    			result.put(key, new BigDecimal(data.get(key)));
    		}
    	}
    	return result;
    }

    private static Map<String,BigDecimal> yen(){
    	Map<String,String> data = Currencies.sendConvertRequest2();
    	Set<String> countries = countries2();
    	Map<String,BigDecimal> result = new HashMap<String,BigDecimal>();
    	for(String key : data.keySet()) {
    		if(countries.contains(key)) {
    			result.put(key, new BigDecimal(data.get(key)));
    		}
    	}
    	return result;
    }

    private static Set<String> countries1() {
    	Set<String> countries = new HashSet<String>();
    	countries.add("EUR");
    	countries.add("CAD");
    	countries.add("CHF");
    	countries.add("AUD");
    	countries.add("GBP");
    	countries.add("HKD");
    	return countries;
    }
    public static Result usd(String msg,String msg2){
    	System.out.println(msg+msg2);
    	return ok(usdChart.render(msg,msg2));
    }

    private static Set<String> countries2() {
    	Set<String> countries = new HashSet<String>();
    	countries.add("EUR");
    	countries.add("CAD");
    	countries.add("CHF");
    	countries.add("AUD");
    	countries.add("GBP");
    	countries.add("HKD");
    	return countries;
    }
    public static Result cad(){
    	   return redirect("http://en.wikipedia.org/wiki/gongoozler");
    }
}
