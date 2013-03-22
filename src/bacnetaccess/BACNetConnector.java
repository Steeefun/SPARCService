package bacnetaccess;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BACNetConnector {

	private static final String OBJECT_ID = "oid";
	private static String baseurl = "http://144.118.54.99:8080/bacnetws/ws/sparc/";

	public BACNetConnector() {

	}

	public String getAllDeviceProperties(Integer bacnetid) {
		String response = getHttpResponse("getAllDeviceProperties?" + OBJECT_ID
				+ "=" + bacnetid);
		return response;
	}

	public Double getPowerUsage(Integer bacnetid) {
		String response = getHttpResponse("getPowerUsage?" + OBJECT_ID + "="
				+ bacnetid);
		Double usage = Double.parseDouble(response);
		return usage;
	}

	public static String getHttpResponse(String urlStr) {
		try {
			urlStr = baseurl + urlStr;
			urlStr = urlStr.replaceAll(" ", "%20");
			System.out.println("HTTP Request: " + urlStr);
			String result;
			// Construct data

			// Send data
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuffer sb = new StringBuffer();
			String line;

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			result = sb.toString();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return e.toString();
		}
	}
}
