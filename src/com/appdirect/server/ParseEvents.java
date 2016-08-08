package com.appdirect.server;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import com.appdirect.http.client.HTTPClient;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.signature.QueryStringSigningStrategy;

public class ParseEvents {

	String defult_url = "http://ec2-54-254-241-86.ap-southeast-1.compute.amazonaws.com:8080/app/create?eventUrl=";

	public void createEvent(String url) {
		try {
			String baseString = defult_url + url;
			
			System.out.println("base url is " + baseString);
			
			computeSignature(baseString, HTTPClient.CONSUMER_KEY, HTTPClient.CONSUMER_SECRET);
			
			OAuthConsumer  consumer = new DefaultOAuthConsumer(HTTPClient.CONSUMER_KEY, HTTPClient.CONSUMER_SECRET); 
			consumer.setSigningStrategy(new QueryStringSigningStrategy());
			try {
				String sign = consumer.sign(baseString);
				System.out.println("sign value "+ sign);
				
			} catch (OAuthMessageSignerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (GeneralSecurityException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void computeSignature(String baseString, String consumer_key , String key_secret) throws GeneralSecurityException, UnsupportedEncodingException {
	    String keyString = OAuth.percentEncode(consumer_key) + '&' + OAuth.percentEncode(key_secret);
        byte[] keyBytes = keyString.getBytes(OAuth.ENCODING);
	    SecretKeySpec key = new SecretKeySpec(keyBytes, "HmacSHA1");
	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(key);
	    byte[] hashBytes = mac.doFinal(baseString.getBytes());
	    System.out.println("signature --> " + new String(Hex.encodeHex(hashBytes)));
	}
	
	public static void main(String[] args) {
		ParseEvents events = new ParseEvents();
		events.createEvent("abc");
	}
}
