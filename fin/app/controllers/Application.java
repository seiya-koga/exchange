package controllers;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import models.Currencies;
import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

	public static Result index() throws IOException {
		Set<String> countries = countries1();
		Map<String, BigDecimal> dollars = dollar();
		Map<String, BigDecimal> yens = yen();
		Map<String, String> result = new HashMap<String, String>();
		Set<String> countryUS = countries3();
		Map<String, String> usd = usd();
		Map<String, String> resultDoll = new HashMap<String, String>();

		for (String country : countries) {
			BigDecimal dollar = dollars.get(country);
			BigDecimal yen = yens.get(country);
			BigDecimal val = dollar.divide(yen, 6, BigDecimal.ROUND_HALF_UP);
			result.put(country, val.toString());
		}
		for (String country : countryUS) {
			String dollar = usd.get(country);
			resultDoll.put(country, dollar.toString());
		}
		return ok(index.render(result, resultDoll));
	}

	public static Result chart(String msg, String msg2) {
		return ok(chart.render(msg, msg2));
	}

	private static Map<String, BigDecimal> dollar() {
		Map<String, String> data = Currencies.sendConvertRequest();
		Set<String> countries = countries1();
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		for (String key : data.keySet()) {
			if (countries.contains(key)) {
				result.put(key, new BigDecimal(data.get(key)));
			}
		}
		return result;
	}

	private static Map<String, String> usd() {
		Map<String, String> data = Currencies.sendConvertRequest();
		Set<String> countries = countries3();
		Map<String, String> result = new HashMap<String, String>();
		for (String key : data.keySet()) {
			if (countries.contains(key)) {
				result.put(key, new String(data.get(key)));
			}
		}
		return result;
	}

	private static Map<String, BigDecimal> yen() {
		Map<String, String> data = Currencies.sendConvertRequest2();
		Set<String> countries = countries2();
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		for (String key : data.keySet()) {
			if (countries.contains(key)) {
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

	private static Set<String> countries3() {
		Set<String> countries = new HashSet<String>();
		countries.add("JPY");
		return countries;
	}

}
