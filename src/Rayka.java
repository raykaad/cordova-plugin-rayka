package com.raykaad.cordova;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import com.raykaad.AdListener;
import com.raykaad.Banner;
import com.raykaad.Raykaad;
import com.raykaad.VideoAdListener;
import com.raykaad.VideoResult;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class Rayka extends CordovaPlugin {
	private static final String LOG_TAG = "Rayka";
	private static CallbackContext callbackContextKeepCallback = null;
	private static Activity mActivity = null;
	public CordovaInterface cordova = null;
	private FrameLayout bannerLayout;
	private static final int POSITION_TOP = 0;
	private static final int POSITION_BOTTOM = 1;
	private static final int POSITION_XY = 2;
	
	@Override
	public void initialize(CordovaInterface initCordova, CordovaWebView webView) {
		 Log.e (LOG_TAG, "initialize");
		 cordova = initCordova;
		 super.initialize (cordova, webView);
	}
	
	
	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext CallbackContext) throws JSONException {
		if (action.equals("cachePopup")) {
			cachePopup(action, args, CallbackContext);
			return true;
		}
		if (action.equals("showPopup")) {
			showPopup(action, args, CallbackContext);
			return true;
		}
		if (action.equals("addBanner")) {
			addBanner(action, args, CallbackContext);
			return true;
		}
		if (action.equals("removeBanner")) {
			removeBanner(action, args, CallbackContext);
			return true;
		}
		if (action.equals("cacheVideo")) {
			cacheVideo(action, args, CallbackContext);
		    return true;
		}
		if (action.equals("showVideo")) {
			showVideo(action, args, CallbackContext);
		    return true;
		}
	    return false;
	}
	
	private void addBanner(String action, final JSONArray args, CallbackContext callbackContext) throws JSONException {
		callbackContextKeepCallback = callbackContext;
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				try {
					_addBanner(args);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void _addBanner(JSONArray args) throws JSONException {
	    mActivity = cordova.getActivity();
		final int position = args.getInt(0);
		if (bannerLayout != null) {
			_removeBanner();
		}
		bannerLayout = new FrameLayout(mActivity);
	    FrameLayout.LayoutParams fLayoutParams = new FrameLayout.LayoutParams(-2, -2);
	    switch (position) {
		    case POSITION_TOP:
		    	fLayoutParams.gravity = Gravity.TOP;
		    	break;
		    case POSITION_BOTTOM:
		    	fLayoutParams.gravity = Gravity.BOTTOM;
		    case POSITION_XY:
		    	fLayoutParams.leftMargin = args.getInt(1);
		    	fLayoutParams.topMargin = args.getInt(2);
	    }
	    bannerLayout.setLayoutParams(fLayoutParams);
	    ((ViewGroup) getParentGroup().getParent()).addView(bannerLayout, 1);
	    Banner banner = new Banner(mActivity);
	    bannerLayout.addView(banner);
		callbackContextKeepCallback.success();
	}

	private void removeBanner(String action, final JSONArray args, CallbackContext callbackContext) throws JSONException {
		callbackContextKeepCallback = callbackContext;
		cordova.getActivity().runOnUiThread(new Runnable() {
			@Override
			public void run() {
				_removeBanner();
			}
		});
	}
	
	private void _removeBanner() {
		if (bannerLayout == null)
		      return;
	    if (mActivity != null) {
	    	mActivity.runOnUiThread(new Runnable() {
	        public void run() {
	        	ViewGroup viewGroup;
        		if (((viewGroup = getParentGroup()) != null) && ((viewGroup instanceof ViewGroup)) && (((ViewGroup)viewGroup.getParent()).getChildAt(1) != null))
        			((ViewGroup)viewGroup.getParent()).removeViewAt(1);
	        }
	      });
	      if (callbackContextKeepCallback != null)
	    	  callbackContextKeepCallback.success();
	    }
	}
	
	private ViewGroup getParentGroup() {
	    try {
	      return (ViewGroup)this.webView.getClass().getMethod("getView", new Class[0]).invoke(this.webView, new Object[0]);
	    } catch (Exception ex) {
	    	try {
	    		return (ViewGroup)this.webView.getClass().getMethod("getParent", new Class[0]).invoke(this.webView, new Object[0]);
	    	} catch (Exception e) {
	    		e.printStackTrace(); 
	        }
	    }
	    return null;
	}

	private void cachePopup(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		callbackContextKeepCallback = callbackContext;
		final String zoneId = args.getString(0);
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_cachePopup(callbackContextKeepCallback, zoneId);
			}
		});
	}
	
	private void _cachePopup(CallbackContext callbackContext, String zoneId) {
	    mActivity = cordova.getActivity();
	    Raykaad.popupSetListener(mActivity, new PopupListener());
		Raykaad.cachePopup(mActivity, zoneId);
	    callbackContextKeepCallback = callbackContext;
	}
	
	class PopupListener implements AdListener {

		@Override
		public void onClick() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupClick");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onClose() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupClose");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onFail() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupFail");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onReady() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupReady");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onRequest() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupRequest");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onShow() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onPopupShow");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}
		
	}
	
	private void showPopup(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showPopup();
			}
		});
	}
	
	private void _showPopup() {
	    mActivity = cordova.getActivity();
		Raykaad.showPopup(mActivity);
	}
	
	private void cacheVideo(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		callbackContextKeepCallback = callbackContext;
		final String zoneId = args.getString(0);
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_cacheVideo(callbackContextKeepCallback, zoneId);
			}
		});
	}
	
	private void _cacheVideo(CallbackContext callbackContext, String zoneId) {
	    mActivity = cordova.getActivity();
	    Raykaad.setVideoListener(new VideoListener());
	    Raykaad.setVideoResultListener(new VideoResultListener());
		Raykaad.cacheVideo(mActivity, zoneId);
	    callbackContextKeepCallback = callbackContext;
	}
	
	class VideoListener implements VideoAdListener {
		public void onClick() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoClick");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onFail() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoFail");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onReady() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoReady");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onRequest() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoRequest");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}

		@Override
		public void onStart() {
			PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoStart");
			pr.setKeepCallback(true);
			callbackContextKeepCallback.sendPluginResult(pr);
		}
	}
	
	class VideoResultListener implements VideoResult {

		@Override
		public void onResult(boolean complete) {
			if (complete) {
				PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoComplete");
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
			} else {
				PluginResult pr = new PluginResult(PluginResult.Status.OK, "onVideoNotComplete");
				pr.setKeepCallback(true);
				callbackContextKeepCallback.sendPluginResult(pr);
			}
		}
		
	}
	
	private void showVideo(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		cordova.getActivity().runOnUiThread(new Runnable(){
			@Override
			public void run() {
				_showVideo();
			}
		});
	}
	
	private void _showVideo() {
	    mActivity = cordova.getActivity();
		Raykaad.showVideo(mActivity);
	}
}