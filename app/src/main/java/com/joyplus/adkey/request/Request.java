package com.joyplus.adkey.request;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import com.joyplus.adkey.Ad;
import com.joyplus.adkey.AdRequest;
import com.joyplus.adkey.BannerAd;
import com.joyplus.adkey.Const;
import com.joyplus.adkey.RequestBannerAd;
import com.joyplus.adkey.RequestRichMediaAd;
import com.joyplus.adkey.Util;
import com.joyplus.adkey.video.RichMediaAd;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Request {

	private String      mPublisherId;
	private String      mUniqueId1;
	private String      mUniqueId2;
//	private boolean     mIncludeLocation;
	private Context     mContext;
//	private Thread      mRequestThread;
//	private Handler     mHandler = new Handler();
//	private AdRequest   mRequest = null;
//	private boolean     animation;
//	private AdListener  mListener;
//	private boolean     mEnabled = true;
//	private Ad          mResponse;//response for BannerAd or RichMediaAd
	private String      requestURL;
	private String      MAC ;
	private String      mUserAgent;
//	private SerializeManager serializeManager = null;
	private boolean          includeLocation  = false;
	 
	private LocationManager locationManager;
	private int isAccessFineLocation;
	private int isAccessCoarseLocation;
//	private int telephonyPermission;
	
	 public Request(Context context, String publisherId){
		 mContext     = context;
		 mPublisherId = publisherId;
		 requestURL   = Const.REQUESTURL;
		 InitResource();
	 }

	private void InitResource() {
		// TODO Auto-generated method stub
		Util.PublisherId     = mPublisherId;
		Util.GetPackage(mContext);
		//mUserAgent = Util.getDefaultUserAgentString(mContext);
		mUserAgent = Util.buildUserAgent();
		locationManager        = null;
//		int telephonyPermission    = mContext.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE);
		isAccessFineLocation   = mContext.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
		isAccessCoarseLocation = mContext.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
		if (isAccessFineLocation == PackageManager.PERMISSION_GRANTED
				|| isAccessCoarseLocation == PackageManager.PERMISSION_GRANTED)
			locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
		mUniqueId1 = Util.getTelephonyDeviceId(mContext);
		mUniqueId2 = Util.getDeviceId(mContext);
		if ((mPublisherId == null) || (mPublisherId.length() == 0)){
			throw new IllegalArgumentException("User Id cannot be null or empty");
		}
		if ((mUniqueId2 == null) || (mUniqueId2.length() == 0)){
			throw new IllegalArgumentException("System Device Id cannot be null or empty");
		}
		MAC        = Util.GetMacAddress(mContext);
//		mEnabled = (Util.getMemoryClass(mContext) > 16);
	}
	
	//get richmedia ad 
	public RichMediaAd getRichMediaAd(boolean Report){
		RichMediaAd ad = (RichMediaAd) getAd(RequestRichMediaAd.class);
		if(Report && ad != null){
			new Report(mContext).report(ad);
		}
		return ad;
	}
//	public RichMediaAd getRichMediaAd(){
//		RequestRichMediaAd requestAd = new RequestRichMediaAd();
//		try{
//			return requestAd.sendRequest(getRequest(AdRequest.VAD));
//		} catch (final Throwable e){
//			Log.d("Jas","getRichMediaAd fail..."+e.toString());
//		}
//		return null;
//	}
	//get banner ad
	public BannerAd getBannerAd(boolean report){
		BannerAd ad = (BannerAd) getAd(RequestBannerAd.class);
		if(report && ad != null){
			new Report(mContext).report(ad);
		}
		return ad;
	} 
//	public BannerAd getBannerAd(){
//		RequestBannerAd requestAd = new RequestBannerAd();
//		try{
//			return requestAd.sendRequest(getRequest(AdRequest.BANNER));
//		} catch (final Throwable e){
//			Log.d("Jas","getBannerAd fail..."+e.toString());
//		}
//		return null;
//	}
//	private AdRequest getRequest(int type){
//		AdRequest mRequest = new AdRequest();
//		mRequest.setDeviceId(mUniqueId1);
//		mRequest.setDeviceId2(mUniqueId2);
//		mRequest.setPublisherId(mPublisherId);
//		mRequest.setUserAgent(mUserAgent);
//		mRequest.setMACAddress(MAC);
//		mRequest.setUserAgent2(Util.buildUserAgent());
//		Location location = null;
//		if(includeLocation)location = getLocation();
//		if(location != null){
//			mRequest.setLatitude(location.getLatitude());
//			mRequest.setLongitude(location.getLongitude());
//		}else{
//			mRequest.setLatitude(0.0);
//			mRequest.setLongitude(0.0);
//		}
//		mRequest.setConnectionType(Util.getConnectionType(mContext));
//		mRequest.setIpAddress(Util.getLocalIpAddress());
//		mRequest.setTimestamp(System.currentTimeMillis());
//		mRequest.setType(type);
//		mRequest.setRequestURL(requestURL);
//		return mRequest;
//	}
	private Location getLocation(){
		if (locationManager != null){
			if (isAccessFineLocation == PackageManager.PERMISSION_GRANTED){
				boolean isGpsEnabled = locationManager
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
				if (isGpsEnabled)
					return locationManager
							.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			if (isAccessCoarseLocation == PackageManager.PERMISSION_GRANTED){
				boolean isNetworkEnabled = locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				if (isNetworkEnabled)
					return locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			}
		}
		return null;
	}


	/**
	 *该方法通过反射去获取先对应的方法并自动执行之后生
	 *成配置好的Ad来提供使用，新添加的Model需要继承对
	 *应的Model。
	 * @param cla 填入需要使用的AdModel的Class
	 * @return		返回你需要的AdModel
     */

	public Ad getAd(Class cla) {
		try {
			Object ob = null;
			Object object = cla.newInstance();
			/**
			 * 首先对AdRequest进行基本的配置，例如UA、MAC、Location、IP等
			 */
			AdRequest mRequest = new AdRequest();
			mRequest.setDeviceId(mUniqueId1);
			mRequest.setDeviceId2(mUniqueId2);
			mRequest.setPublisherId(mPublisherId);
			mRequest.setUserAgent(mUserAgent);
			mRequest.setMACAddress(MAC);
			mRequest.setUserAgent2(Util.buildUserAgent());
			Location location = null;
			if(includeLocation)location = getLocation();
			if(location != null){
				mRequest.setLatitude(location.getLatitude());
				mRequest.setLongitude(location.getLongitude());
			}else{
				mRequest.setLatitude(0.0);
				mRequest.setLongitude(0.0);
			}
			mRequest.setConnectionType(Util.getConnectionType(mContext));
			mRequest.setIpAddress(Util.getLocalIpAddress());
			mRequest.setTimestamp(System.currentTimeMillis());
			/**
			 * 新建的Model需要给Model指定对
			 * 应的Type（int），唯一的值来区
			 * 分不同的广告类型，在第一次请求
			 * 网络时会有区分的向后台请求
			 */
			Field filed = cla.getDeclaredField("type");
			/**
			 * 通过反射去获取对应Model的Type属性值，并设置给AdRequest
			 */
			filed.setAccessible(true);
			Object dec = filed.get(object);
			mRequest.setType((Integer) dec);
			mRequest.setRequestURL(requestURL);
			Method[] method = cla.getMethods();//获取到sendRequest的方法
			//使用Method并返回相应的对象
			for (int i = 0; i < method.length; i++) {
				if ("sendRequest".equals(method[i].getName())) {//获取 sendRequest 方法
					ob = method[i].invoke(object, mRequest);//执行 sendRequest 方法并给指定的对象配置好值
					System.out.println(method[i].invoke(object, mRequest)+"--Method");//打印配置完成后的对象
				}
			}
			return (Ad) ob;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
}
