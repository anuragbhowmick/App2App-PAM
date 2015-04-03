package com.example.mopubintegratedapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;



public class VizuryPAM {

	public static boolean isVizuryInterested;
	public static boolean vizBannerURL;

}

//class GetVizuryAd extends AsyncTask<String, String, String> {
//
//	@Override
//	protected String doInBackground(String... params) {
//		try {
//			if(params[0]!=""){
//				URL url = new URL(params[0]);
//				HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//				int status = httpURLConnection.getResponseCode();
//				if (status >= HttpStatus.SC_BAD_REQUEST) {
//					Log.d("AB","BAD request , returning empty string");
//					return "";
//				}
//				String response	=	readStream(httpURLConnection.getInputStream());
//				if (response != null && response != "") {
//					Log.d("AB","returning correct value " +response);
//
//					return getValueFromJSON(response, "URL");
//				}
//			}
//			Log.d("AB","returning empty string");
//
//			return "";
//		}
//		catch (Exception e) {
//			return "";
//		}
//	}
//	public String getValueFromJSON(String JSONString, String key) {
//		Log.d("AB","getting value from JSON");
//
//		String valueString = "";
//		try {
//			JSONObject obj = new JSONObject(JSONString);
//			valueString = obj.getString(key);
//		} catch (JSONException e) {
//		}
//		return valueString;
//	}
//	
//	private String readStream(InputStream in) {
//		BufferedReader reader = null;
//		String pixels = "";
//		try {
//			reader = new BufferedReader(new InputStreamReader(in));
//			String line = "";
//			while ((line = reader.readLine()) != null) {
//				pixels = pixels + line;
//			}
//		} catch (Throwable e) {
//			e.printStackTrace();
//		} finally {
//			if (reader != null) {
//				try {
//					reader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return pixels;
//	}
//
//	@Override
//	protected void onPostExecute(String result) {
////		Log.d("AB","parsed reult length : " +result.length());
//		// TODO Auto-generated method stub
////		result="http://www.vizury.com/banners/images/common/AdDisp.php?aid=1471&filename=MA_Jabong_IN/HTML5_MultiProduct_AppType1_24Sep.php&width=320&height=50&template_id=40&isIframeToUse=1";
////		result = "http://www.vizury.com/banners/images/common/AdDisp.php?aid=2811&filename=MA_Myntra_IN/HTML5_MultiProduct_AppType1_250215.php&width=320&height=100&template_id=38&isIframeToUse=1";
////		result="http://www.vizury.com/banners/images/common/AdDisp.php?aid=2811&filename=MA_Myntra_IN/HTML5_MultiProduct_AppType1_250215.php&width=120&height=600&template_id=38&isIframeToUse=1";
////		result= "http://www.vizury.com/banners/images/common/AdDisp.php?aid=2811&filename=MA_Myntra_IN/HTML5_MultiProduct_AppType1_250215.php&width=950&height=90&template_id=38&isIframeToUse=1";
//		if(result != null && result.length() != 4)
//			processVizuryResponse(result);
//		else {
//			Log.d("AB","process viz response : setting Vizury Not Interested.");
//			isVizuryInterested = false;
//		}
//		super.onPostExecute(result);
//	}
//
//	public void processVizuryResponse(String uri) {
////		Log.d("AB","processVizuryResponse : uri is " + uri );
//		if(uri != null && uri != "") {
//			Log.d("AB","process viz response : setting Vizury is Intereseted !!");
//			isVizuryInterested = true;
//			vizBannerURL = uri;
////		    w.loadUrl(uri);
//		}
//	}
//}