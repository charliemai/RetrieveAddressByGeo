package net.mai33.geo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RetrieveAddressByGeo {

	public static String reverseGeocode(long latitude, long longtitude, String language) {

		// http://maps.googleapis.com/maps/api/geocode/json?address=台北市xx區xx路x段xxx號&sensor=true

		// old V2:
		// http://maps.google.com/maps/geo?q=40.5143224,-73.931452&output=json&oe=utf8&sensor=true_or_false&key=your_api_key
		// new V3:
		// http://maps.googleapis.com/maps/api/geocode/json?latlng=25.0319338,121.5476504&sensor=false&language=zh_TW
		String localityName = "";
		HttpURLConnection connection = null;
		URL serverAddress = null;
		StringBuilder json = new StringBuilder();

		try {
			// build the URL using the latitude & longitude you want
			// to lookup
			// NOTE: I chose XML return format here but you can
			// choose something else
			// serverAddress = new
			// URL("http://maps.google.com/maps/geo?q=" +
			// Double.toString(loc.getLatitude()) + "," +
			// Double.toString(loc.getLongitude()) +
			// "&output=xml&oe=utf8&sensor=true&key=" +
			// R.string.GOOGLE_MAPS_API_KEY);
			if (null == language || "".equals(language)) {
				language = "zh_TW";
			}
			serverAddress = new URL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + Double.toString(latitude) + "," + Double.toString(longtitude)
											+ "&oe=utf8&sensor=true&language=" + language);
			// set up out communications stuff
			connection = null;

			// Set up the initial connection
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);

			connection.connect();

			try {
				// Object content = connection.getContent();

				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					// json = json + inputLine + "\n";
					json.append(inputLine + "\n");
				}

				in.close();

				// InputStreamReader isr = new
				// InputStreamReader(connection.getInputStream());
				// InputSource source = new InputSource(isr);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return json.toString();
	}

	public static String getLatLongByAddress(String address, String language) {

		// http://maps.googleapis.com/maps/api/geocode/json?address=台北市xx區xx路xx段xx號&sensor=true

		// String localityName = "";
		HttpURLConnection connection = null;
		URL serverAddress = null;
		StringBuilder json = new StringBuilder();

		try {
			// if(null == language || "".equals(language)) {
			// language = "zh_TW";
			// }

			serverAddress = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address, "UTF-8") + "&oe=utf8&sensor=true");
			// set up out communications stuff
			connection = null;

			// Set up the initial connection
			connection = (HttpURLConnection) serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.connect();

			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					json.append(inputLine + "\n");
				}

				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// System.out.println(json.toString());

		return json.toString();
	}

}
