package com.appdirect.http.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.X509Certificate;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.signature.QueryStringSigningStrategy;


public class HTTPClient {
	 

	public static String CONSUMER_KEY="test-130308";
	public static String CONSUMER_SECRET="zRifgyHX3bKKIUJM";


	public URL createUrl(String url) throws MalformedURLException {
		return new URL(url);
	}
	
	public HttpsURLConnection get(String url) throws IOException {
		
		URL connectionUrl = createUrl(url);
		return (HttpsURLConnection) connectionUrl.openConnection();
		
	}
	
	 public void doTrustToCertificates() throws Exception {
	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        TrustManager[] trustAllCerts = new TrustManager[]{
	                new X509TrustManager() {
	                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
	                        return null;
	                    }

	                    public void checkServerTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	                        return;
	                    }

	                    public void checkClientTrusted(X509Certificate[] certs, String authType) throws CertificateException {
	                        return;
	                    }

						@Override
						public void checkClientTrusted(
								java.security.cert.X509Certificate[] arg0,
								String arg1) throws CertificateException {
							
						}

						@Override
						public void checkServerTrusted(
								java.security.cert.X509Certificate[] arg0,
								String arg1) throws CertificateException {
							
						}
	                }
	        };

	        SSLContext sc = SSLContext.getInstance("SSL");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        HostnameVerifier hv = new HostnameVerifier() {
	            public boolean verify(String urlHostName, SSLSession session) {
	                if (!urlHostName.equalsIgnoreCase(session.getPeerHost())) {
	                		System.out.println("Warning: URL host '" + urlHostName + "' is different to SSLSession host '" + session.getPeerHost() + "'.");	
	                }
	                return true;
	            }
	        };
	        HttpsURLConnection.setDefaultHostnameVerifier(hv);
	    }

	
	 public HttpURLConnection get(String url, Map<String, Object> params) throws Exception {
		 doTrustToCertificates();
		 OAuthConsumer  consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET); 
		 consumer.setSigningStrategy(new QueryStringSigningStrategy());
		 String sign = consumer.sign(url);
		 URL connectionUrl = createUrl(sign);
		 HttpsURLConnection conn = (HttpsURLConnection)connectionUrl.openConnection();
		 conn.setRequestMethod("GET");
		 conn.setRequestProperty("Content-Type", "application/json");
		 conn.setDoOutput(true);
		 return conn;
}
	
}
