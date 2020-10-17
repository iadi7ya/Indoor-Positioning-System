package com;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// posting the sensor data to the sos
public class PostToSOS {
	private String apiKey = "dUkALmjRclj0XULtCTBXZdvVg= ";
	private String flag = "false";
	
	public void postValues(String dataA, String dataB){
		doPost(apiKey,dataA,dataB);
	}
	
	// posting method 
	private static void doPost(String apiKey, String valueA, String valueB){
		System.out.println("Inside post: " + valueA + " " + valueB);
		
		// json format for posting values to a sensor
		String payload = "{\"version\": \"1.0.1\",\"observations\": [{\"sensor\": \"mySensor\",\"record\": [{\"starttime\": \"11-NOV-2015 15:31:45 IST\",\"output\": [{\"name\": \"distanceA\",\"value\": \"" + valueA + "\"}," + 
		 "{\"name\":\"distanceB\",\"value\":\"" + valueB + "\"}]}]}]}";

		try{
			// 1. URL
			URL url = new URL("http://www.domain_name.net:80/api/observations");

			// 2. Open connection
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 3. Specify POST method
			conn.setRequestMethod("POST");

			// 4. Set the headers
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-api-key", apiKey);

			conn.setDoOutput(true);

			OutputStream os = conn.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			pw.write(payload);
			pw.close();
			
			// 6. Get the response
			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// 7. Print result
			//System.out.println(response.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			GetFromSOS gfs = new GetFromSOS();
			gfs.get();
			//System.out.println("A: " + gfs.valueA + "\tB: "+ gfs.valueB);
			AnalysisOfData res = new AnalysisOfData(gfs.getValueA(), gfs.getValueB());
			gfs.postResults(res);
			System.out.println("flag: " + res.isFlag());
			System.out.println("X:" + res.getX() + "\tY:" + res.getY());
		    
		}
		
	}
	
	// checking the led status	
	public String check(){
		String str=null;
		HttpURLConnection conn=null;
		String msg = "n";
		
		try{
			// 1. create url
			URL url = new URL("http://www.domain_name.net:80/api/observations?sensor=mySensor&time=latest");

			// 2. Open connection
			conn = (HttpURLConnection) url.openConnection();

			// 3. Specify POST method
			conn.setRequestMethod("GET");

			// 4. Set the headers
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-api-key", apiKey);

			int a = conn.getResponseCode();

			conn.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			//print result
			str=(response.toString());

			//String val = "30";

			Object obj = new JSONParser().parse(str);
			JSONObject jobj = (JSONObject) obj;
			
			JSONArray jarr = (JSONArray) jobj.get("observations");
			JSONObject jobj1 = (JSONObject) jarr.get(0);
			
			JSONArray jarr1 = (JSONArray) jobj1.get("record");
			JSONObject jobj2 = (JSONObject) jarr1.get(0);
			
			JSONArray jarr2 = (JSONArray) jobj2.get("output");
			JSONObject jobj3 = (JSONObject) jarr2.get(0);
			
			String temp = (String) jobj3.get("value");
			//System.out.println(temp);
			
			if(temp.equalsIgnoreCase("true")) {
				msg = "y";
			}
			if(temp.equalsIgnoreCase("true") && !flag.equalsIgnoreCase("true")){
				postMessage();
				flag = temp;
			}
			
			
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.disconnect();
		}
		
		return msg;
	}
	
	// sending msg to mobile
	public void postMessage(){
		String msg = "Alert!";
		HttpURLConnection conn = null;
		String payload = "{\"version\": \"1.0.1\",\"observations\": [{\"sensor\": \"message\",\"record\": [{\"starttime\": \"11-NOV-2015 15:31:45 IST\",\"output\": [{\"name\": \"message\",\"value\": \"" + msg + "\"}" + "]}]}]}";

				try{
					// 1. URL
					URL url = new URL("http://www.domain_name.net:80/api/observations");

					// 2. Open connection
					conn = (HttpURLConnection) url.openConnection();

					// 3. Specify POST method
					conn.setRequestMethod("POST");

					// 4. Set the headers
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setRequestProperty("x-api-key", apiKey);

					conn.setDoOutput(true);

					OutputStream os = conn.getOutputStream();
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
					pw.write(payload);
					pw.close();
					
					// 6. Get the response
					int responseCode = conn.getResponseCode();
					System.out.println("\nSending 'POST' request to URL : " + url);
					System.out.println("Response Code : " + responseCode);

					BufferedReader in = new BufferedReader(
							new InputStreamReader(conn.getInputStream()));
					String inputLine;
					StringBuffer response = new StringBuffer();

					while ((inputLine = in.readLine()) != null) {
						response.append(inputLine);
					}
					in.close();

					// 7. Print result
					//System.out.println(response.toString());

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					conn.disconnect();
				    
				}
	}
}
