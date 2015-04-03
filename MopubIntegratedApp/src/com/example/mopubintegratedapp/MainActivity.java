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
	FrameLayout parent;
	MoPubView mView;
	
	WebView w;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
//		moPubView = (MoPubView) findViewById(R.id.adview);
//		moPubView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da");
//		moPubView.setAdUnitId("fa3986291f6c45b9853e8126acd5224d"); // Enter your Ad Unit ID from www.mopub.com
//		moPubView.setAutorefreshEnabled(true);
//		moPubView.setKeywords("hello world");
		
//		moPubView.loadAd();
//		moPubView.setBannerAdListener(this);
		
//		 WebView wv = (WebView) findViewById(R.id.webview);
//		 wv.getSettings().setJavaScriptEnabled(true);
//		 wv.setBackgroundColor(Color.TRANSPARENT);
//		 String html = "%3Chtml%3E%3Chead%3E%3Cscript%20type%3D%27text%2Fjavascript%27%3Evar%20m_u%20%3D%27http%3A%2F%2Fwww.vizury.com%2Fcampaign%2Fshowad.php%27%3B%20document.write%20(%22%3Cscr%22%2B%22ipt%20type%3D%27text%2Fjavascript%27%20src%3D%27%22%2Bm_u)%3B%20document.write%20(%22%3Fwidth%3D300%22)%3B%20document.write%20(%22%26amp%3Bheight%3D250%22)%3B%20document.write%20(%22%26amp%3Bpublisher_client_id%3D5591%22)%3B%20document.write%20(%22%26amp%3Bloc%3D%22%20%2B%20escape(window.location))%3B%20if%20(document.referrer)%20document.write%20(%22%26amp%3Brfr%3D%22%20%2B%20escape(document.referrer))%3B%20document.write%20(%22%27%3E%3C%5C%2Fscr%22%2B%22ipt%3E%22)%3B%20%3C%2Fscript%3E%20%3C%2Fhead%3E%3C%2Fhtml%3E";
//		 String html = "<html><body>You scored <b>192</b> points.</body></html>";
//		 wv.loadUrl("http://www.vizury.com/banners/images/common/AdDisp.php?aid=1471&filename=MA_Jabong_IN/HTML5_MultiProduct_AppType1_24Sep.php&width=320&height=50&template_id=40&isIframeToUse=1");
//		 wv.loadData(html, "text/html", "utf-8");
		    
			
//	     w = new WebView(this);
//		 w.setLayoutParams(new LayoutParams
//	        		(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
//		 w.getSettings().setJavaScriptEnabled(true);
//	     w.loadUrl("http://www.vizury.com/banners/images/common/AdDisp.php?aid=1471&filename=MA_Jabong_IN/HTML5_MultiProduct_AppType1_24Sep.php&width=320&height=50&template_id=40&isIframeToUse=1");

//		 w.loadData(html, "text/html", "utf-8");

//		 moPubView.addView(w);			
	     
		 
//		 vizAdSlot = (FrameLayout)findViewById(R.id.framelayout1);
		 
		 parent = (FrameLayout) findViewById(R.id.adSlotFrame);

		 w = new WebView(this);
	     w.getSettings().setJavaScriptEnabled(true);
//		 vizAdSlot.addView(w);

	     getVizuryInterest();
		 initializeMoPub();

		 Button b = (Button) findViewById(R.id.button1);
		 b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("AB","onCLick : Is vizury Interested ? " + isVizuryInterested);
				getVizuryInterest();
				initializeMoPub();
			}
		});
		
	}
	
	private void getVizuryInterest() {
		String url="http://localhost:8888/getVizInterest.php?width=300&height=50";
		new GetVizuryAd().execute(url);
	}
	
	private void initializeMoPub() {
//		moPubView.refreshDrawableState();
		if(mView != null)
			mView.removeAllViews();
		
		mView = new MoPubView(getApplicationContext());
		parent.removeAllViews();
		parent.addView(mView, new LayoutParams
	        		(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		 
//		mView.setVisibility(View.VISIBLE);
//		vizAdSlot.setVisibility(View.GONE);
//		mView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da");
//		mView.loadAd();
//		mView.setBannerAdListener(this);
		
		if(isVizuryInterested) {
			Log.d("AB","initializeMoPub : vizInteresetd : destroying mopubView");
			mView.destroy();
			mView.removeAllViews();
			mView.addView(w);
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
		 
		 
//		MoPubView moPubView = (MoPubView) findViewById(R.id.adview);
//		moPubView.setVisibility(View.VISIBLE);
//		vizAdSlot.setVisibility(View.GONE);
//		moPubView.setAdUnitId("b195f8dd8ded45fe847ad89ed1d016da");
//		moPubView.loadAd();
//		moPubView.setBannerAdListener(this);
		
//		if(isVizuryInterested) {
//			Log.d("AB","initializeMoPub : vizInteresetd : destroying mopubView");
//			moPubView.setVisibility(View.GONE);
//			vizAdSlot.setVisibility(View.VISIBLE);
//			moPubView.destroy();
//		}
//		Log.d("AB","framwlayout Height "+moPubView.getLayoutParams().height + " Width " + moPubView.getLayoutParams().height);

	}
	

	@Override
	public void onBannerLoaded(MoPubView paramMoPubView) {
//        final DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, displayMetrics);
//		Log.d("AB","height " + moPubView.getAdHeight() + " width " + moPubView.getAdHeight());
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), 
	            "Banner successfully loaded.", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onBannerFailed(MoPubView paramMoPubView,
			MoPubErrorCode paramMoPubErrorCode) {
		Log.d("AB","banerfailed");

		Toast.makeText(getApplicationContext(), 
	            "onBannerFailed.", Toast.LENGTH_SHORT).show();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBannerClicked(MoPubView paramMoPubView) {
		// TODO Auto-generated method stub
		Log.d("AB","bannerClicked");

		Toast.makeText(getApplicationContext(), 
	            "onBannerClicked", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onBannerExpanded(MoPubView paramMoPubView) {
		// TODO Auto-generated method stub
		Log.d("AB","bannerExpanded");

		Toast.makeText(getApplicationContext(), 
	            "onBannerExpanded.", Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onBannerCollapsed(MoPubView paramMoPubView) {
		// TODO Auto-generated method stub
		Log.d("AB","bannerColapsed");

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
			    w.loadUrl(uri);
			    
//			    if(moPubView.getVisibility() == View.VISIBLE) {
//					vizAdSlot.setVisibility(View.VISIBLE);
//					moPubView.setVisibility(View.GONE);
//					moPubView.destroy();
//			    }
			    
//			    moPubView.destroy();
			    
//				Log.d("AB","childCount "+moPubView.getChildCount());
//				Log.d("AB","processVizuryResponse -> callling destroy and adding new view from vizury");
//			    vizAdSlot.addView(w);
//				moPubView.addView(w); 
				
//				 WebView wv = (WebView) findViewById(R.id.webview);
//				 wv.getSettings().setJavaScriptEnabled(true);
//				 wv.setBackgroundColor(Color.TRANSPARENT);
//				 wv.loadUrl(uri);
			}
		}
	}
}
