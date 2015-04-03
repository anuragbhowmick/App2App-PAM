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

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubView;
import com.mopub.mobileads.MoPubView.BannerAdListener;

public class MainActivity extends ActionBarActivity implements BannerAdListener {

//	private MoPubView moPubView;
//	private FrameLayout vizAdSlot;
	private boolean isVizuryInterested= false;
	private boolean isMoPubViewDestroyed = false;
	private String vizBannerURL;
	FrameLayout adSlotFramelayout;
	MoPubView mView;
	
	WebView vizuryWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
				 
		adSlotFramelayout = (FrameLayout) findViewById(R.id.adSlotFrame);

		 vizuryWebView = new WebView(this);
		 vizuryWebView.getSettings().setJavaScriptEnabled(true);
		 
//		 vizAdSlot.addView(w);

//	     getVizuryInterest();
//		 initializeMoPub();

		 initMoPub();
		 checkVizuryInterest();
		 
		 Button b = (Button) findViewById(R.id.button1);
		 b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("AB","onCLick : Is vizury Interested ? " + isVizuryInterested);
//				getVizuryInterest();
//				initializeMoPub();
				 checkVizuryInterest();
			}
		});
		
	}
	
	private void getVizuryInterest() {
		String url="http://localhost:8888/getVizInterest.php?width=300&height=50";
		new GetVizuryAd().execute(url);
	}
	
	private void initMoPub() {
		mView = new MoPubView(getApplicationContext());
		mView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da");
		mView.loadAd();
		mView.setBannerAdListener(this);
	}
	
	private void checkVizuryInterest() {
		String url="http://localhost:8888/getVizInterest.php?width=300&height=50";
		new GetVizuryAd().execute(url);
		
		if(isVizuryInterested) {
			adSlotFramelayout.removeAllViews();
			adSlotFramelayout.addView(vizuryWebView);
			vizuryWebView.loadUrl(vizBannerURL);
		}
		else {
			adSlotFramelayout.removeAllViews();
			adSlotFramelayout.addView(mView, new LayoutParams
		        		(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		}
	}
	
	private void initializeMoPub() {
//		moPubView.refreshDrawableState();

		mView = new MoPubView(getApplicationContext());
		adSlotFramelayout.removeAllViews();
		adSlotFramelayout.addView(mView, new LayoutParams
	        		(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		
		if(isVizuryInterested) {
			Log.d("AB","initializeMoPub : vizInteresetd : destroying mopubView");
			mView.destroy();
			
			adSlotFramelayout.removeAllViews();
			adSlotFramelayout.addView(vizuryWebView);
//			mView.addView(vizuryWebView);
			vizuryWebView.loadUrl(vizBannerURL);
//			mView.setVisibility(View.GONE);
//			vizAdSlot.setVisibility(View.VISIBLE);			
		}
		else {
			mView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da");
			mView.loadAd();
			mView.setBannerAdListener(this);
//			mView.setVisibility(View.VISIBLE);
//			vizAdSlot.setVisibility(View.GONE);
		}
	}
	

	@Override
	public void onBannerLoaded(MoPubView paramMoPubView) {
//        final DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, displayMetrics);
		Log.d("AB","onBannerLoaded height " + paramMoPubView.getAdHeight() + " width " + paramMoPubView.getAdHeight());
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), 
	            "Banner successfully loaded.", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onBannerFailed(MoPubView paramMoPubView,
			MoPubErrorCode paramMoPubErrorCode) {
		Log.d("AB","onBannerFailed");

		Toast.makeText(getApplicationContext(), 
	            "onBannerFailed.", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBannerClicked(MoPubView paramMoPubView) {
		// TODO Auto-generated method stub
		Log.d("AB","onBannerClicked");

		Toast.makeText(getApplicationContext(), 
	            "onBannerClicked", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onBannerExpanded(MoPubView paramMoPubView) {
		// TODO Auto-generated method stub
		Log.d("AB","onBannerExpanded");

		Toast.makeText(getApplicationContext(), 
	            "onBannerExpanded.", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onBannerCollapsed(MoPubView paramMoPubView) {
		// TODO Auto-generated method stub
		Log.d("AB","onBannerCollapsed");

		Toast.makeText(getApplicationContext(), 
	            "onBannerCollapsed.", Toast.LENGTH_SHORT).show();
		
	}
	
	
	private class GetVizuryAd extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				if(params[0]!=""){
					URL url = new URL(params[0]);
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
					int status = httpURLConnection.getResponseCode();
					if (status >= HttpStatus.SC_BAD_REQUEST) {
						Log.d("AB","BAD request , returning empty string");
						return "";
					}
					String response	=	readStream(httpURLConnection.getInputStream());
					if (response != null && response != "") {
						Log.d("AB","returning correct value " +response);

						return getValueFromJSON(response, "URL");
					}
				}
				Log.d("AB","returning empty string");

				return "";
			}
			catch (Exception e) {
				return "";
			}
		}
		public String getValueFromJSON(String JSONString, String key) {
			Log.d("AB","getting value from JSON");

			String valueString = "";
			try {
				JSONObject obj = new JSONObject(JSONString);
				valueString = obj.getString(key);
			} catch (JSONException e) {
			}
			return valueString;
		}
		
		private String readStream(InputStream in) {
			BufferedReader reader = null;
			String pixels = "";
			try {
				reader = new BufferedReader(new InputStreamReader(in));
				String line = "";
				while ((line = reader.readLine()) != null) {
					pixels = pixels + line;
				}
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return pixels;
		}

		@Override
		protected void onPostExecute(String result) {
//			Log.d("AB","parsed reult length : " +result.length());
			// TODO Auto-generated method stub
//			result="http://www.vizury.com/banners/images/common/AdDisp.php?aid=1471&filename=MA_Jabong_IN/HTML5_MultiProduct_AppType1_24Sep.php&width=320&height=50&template_id=40&isIframeToUse=1";
//			result = "http://www.vizury.com/banners/images/common/AdDisp.php?aid=2811&filename=MA_Myntra_IN/HTML5_MultiProduct_AppType1_250215.php&width=320&height=100&template_id=38&isIframeToUse=1";
//			result="http://www.vizury.com/banners/images/common/AdDisp.php?aid=2811&filename=MA_Myntra_IN/HTML5_MultiProduct_AppType1_250215.php&width=120&height=600&template_id=38&isIframeToUse=1";
//			result= "http://www.vizury.com/banners/images/common/AdDisp.php?aid=2811&filename=MA_Myntra_IN/HTML5_MultiProduct_AppType1_250215.php&width=950&height=90&template_id=38&isIframeToUse=1";
			if(result != null && result.length() != 4)
				processVizuryResponse(result);
			else {
				Log.d("AB","process viz response : setting Vizury Not Interested.");
				isVizuryInterested = false;
			}
			super.onPostExecute(result);
		}

		public void processVizuryResponse(String uri) {
//			Log.d("AB","processVizuryResponse : uri is " + uri );
			if(uri != null && uri != "") {
				Log.d("AB","process viz response : setting Vizury is Intereseted !!");
				isVizuryInterested = true;
				vizBannerURL = uri;
//			    w.loadUrl(uri);
			}
		}
	}
}
