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

public class GetFromSOS {
	private double valueA = -1;
	private double valueB = -1;
	private String apikey = "auth_api_key";
	
	/*public static void main(String[] args){
		
		Timer timer = new Timer();
	    TimerTask task = new TimerTask() {
	        public void run()
	        {
	        	GetFromSOS gfs = new GetFromSOS();
	        	gfs.get();
	        	System.out.println("A: " + gfs.getValueA() + "\tB: "+ gfs.getValueB());
	    		AnalysisOfData res = new AnalysisOfData(gfs.getValueA(), gfs.getValueB());
	    		gfs.postResults(res);
	    		System.out.println("flag: " + res.isFlag());
	    		System.out.println("X:" + res.getX() + "\tY:" + res.getY());
	        }
	    };
	    timer.scheduleAtFixedRate(task, new Date(), 10000l);
	    //timer.schedule( task, 1000 );

	}*/
	
	//getting sensor data for analyzing
	public void get(){
		String str=null;
		HttpURLConnection conn=null;
		try{
			// 1. create url
			URL url = new URL("http://www.domain_name.net:80/api/observations?sensor=mySensor&time=latest");

			// 2. Open connection
			conn = (HttpURLConnection) url.openConnection();

			// 3. Specify POST method
			conn.setRequestMethod("GET");

			// 4. Set the headers
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("x-api-key", apikey);

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
			JSONObject jobj4 = (JSONObject) jarr2.get(1);
			
			String value1 = (String) jobj3.get("value");
			String value2 = (String) jobj4.get("value");
			//System.out.println(value1 + " " + value2);
			setValueA(Double.parseDouble(value1));
			setValueB(Double.parseDouble(value2));
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			conn.disconnect();
		}
	}
	
	//posting analysis results to sos
	public void postResults(AnalysisOfData res){
		String flag = res.isFlag()+"";
		String x = res.getX()+"";
		String y = res.getY()+"";
		
		String payload = "{\"version\": \"1.0.1\",\"observations\": [{\"sensor\": \"ILP_Sensor_grp2_result\",\"record\": [{\"starttime\": \"11-NOV-2015 15:31:45 IST\",\"output\": [{\"name\":\"flag\",\"value\":\"" + flag + "\"},{\"name\": \"xcoordinate\",\"value\": \"" + x + "\"}," + 
				 "{\"name\":\"ycoordinate\",\"value\":\"" + y + "\"}]}]}]}";

				try{
					// 1. URL
					URL url = new URL("http://www.domain_name.net:80/api/observations");

					// 2. Open connection
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					// 3. Specify POST method
					conn.setRequestMethod("POST");

					// 4. Set the headers
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setRequestProperty("x-api-key", apikey);

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
				}
	}

	public double getValueA() {
		return valueA;
	}

	public void setValueA(double valueA) {
		this.valueA = valueA;
	}

	public double getValueB() {
		return valueB;
	}

	public void setValueB(double valueB) {
		this.valueB = valueB;
	}
}
