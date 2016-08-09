package com.appdirect.server.convertor;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import com.appdirect.server.data.SubscriptionData;

/**
 * Take xml response string from APP direct and convert it into a pojo object
 * @author vineetsagar
 *
 */
public class XmlToObject {
	
	private String data;

	public XmlToObject(String data) {
		this.data = data;
	}
	
	public  SubscriptionData get() throws JAXBException{
		JAXBContext jc = JAXBContext.newInstance(SubscriptionData.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		StreamSource streamSource = new StreamSource(new StringReader(data));
		JAXBElement<SubscriptionData> je = unmarshaller.unmarshal(streamSource, SubscriptionData.class);
		return  (SubscriptionData)je.getValue();
	}
}
