package com.joyplus.adkey;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public abstract class RequestAd<T> {

	InputStream is;

	public T sendRequest(AdRequest request)
			throws RequestException {
//		if (is == null) {
//			String url = request.toString();
//			String device_name = "V8";
//			try {
//				device_name = URLEncoder.encode(Util.GetDeviceName(), "utf-8");
//			} catch (UnsupportedEncodingException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			url = url+"&ds="+device_name;
//			Log.d("Jas",""+url);
//			DefaultHttpClient client = new DefaultHttpClient();
//			HttpConnectionParams.setSoTimeout(client.getParams(),
//					Const.SOCKET_TIMEOUT);
//			HttpConnectionParams.setConnectionTimeout(client.getParams(),
//					Const.CONNECTION_TIMEOUT);
//			HttpProtocolParams.setUserAgent(client.getParams(),
//					request.getUserAgent());
//			HttpGet get = new HttpGet(url);
//			HttpResponse response;
//			try {
			//response = client.execute(get);
			//int responseCode = response.getStatusLine().getStatusCode();
			//if (responseCode == HttpURLConnection.HTTP_OK) {
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><ad type=\"interstitial\" animation=\"none\"><interstitial preload=\"0\" autoclose=\"0\" type=\"markup\" orientation=\"portrait\"><markup><![CDATA[<meta content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\" name=\"viewport\" /><meta name=\"viewport\" content=\"width=device-width\" /><div style=\"position:absolute;top:0;left:0;\"><a href=\"#\"><img src=\"http://advresmd.joyplus.tv/8ad65b40b833ba4873f3dca7f72539ef.jpg\"></a></div>]]></markup><creative_res_url src=\"http://advresmd.joyplus.tv/8ad65b40b833ba4873f3dca7f72539ef.jpg\" sourcetype=\"1\" hash=\"FpEnXapTCALou6jUF6LUt9D3_TtB\"></creative_res_url><click><type>openurl</type><resource></resource></click><clickurl><![CDATA[http://advapi.joyplus.tv/advapi/v1/mdclick?ad=84281469664a5944f9be1188a80e0000&zone=c4b26b14a4ef7fdb9f3f0fbb06dfed92&ds=&dm=null&i=]]></clickurl><impressionurl><![CDATA[http://advapi.joyplus.tv/advapi/v1/mdtrack?ad=84281469664a5944f9be1188a80e0000&zone=c4b26b14a4ef7fdb9f3f0fbb06dfed92&ds=&dm=null&i=]]></impressionurl><trackingurl_miaozhen><![CDATA[]]></trackingurl_miaozhen><trackingurl_iresearch><![CDATA[]]></trackingurl_iresearch><trackingurl_admaster><![CDATA[]]></trackingurl_admaster><trackingurl_nielsen><![CDATA[]]></trackingurl_nielsen><skipbutton show=\"1\" showafter=\"0\"></skipbutton><navigation show=\"0\"><topbar custombackgroundurl=\"\" show=\"0\" title=\"\" titlecontent=\"\"></topbar><bottombar custombackgroundurl=\"\" show=\"0\" backbutton=\"0\" forwardbutton=\"0\" reloadbutton=\"0\" externalbutton=\"0\" timer=\"0\"></bottombar></navigation></interstitial><refresh>30</refresh></ad>";
			is = new ByteArrayInputStream(xml.getBytes());
			return parse(is);
//				} else {
//					throw new RequestException("Server Error. Response code:"
//							+ responseCode);
//				}
//			} catch (RequestException e) {
//				throw e;
//			} catch (ClientProtocolException e) {
//				throw new RequestException("Error in HTTP request", e);
//			} catch (IOException e) {
//				throw new RequestException("Error in HTTP request", e);
//			}
//			catch (Throwable t) {
//				throw new RequestException("Error in HTTP request", t);
//			}
//		} else {
		//}
		//	return parseTestString();
//		}
	}

	abstract T parseTestString() throws RequestException;

	abstract T parse(InputStream inputStream) throws RequestException;
}
