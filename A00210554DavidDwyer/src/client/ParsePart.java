package client;
import java.io.IOException;  
import java.io.StringReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import myApp.Part;

public class ParsePart {

		boolean inParts = false;
		boolean inPart = false;
		boolean inNumber = false;
		boolean inName = false;
		boolean inDescription = false;
		boolean inStock = false;
		
		Part currentPart;
		
		public Part doParsePart(String s) {
			
			try {
		
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				XmlPullParser pullParser = factory.newPullParser();
				pullParser.setInput(new StringReader(s));
				processDocument(pullParser);
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		
			return currentPart;
			
		}

		
		public void processDocument(XmlPullParser pullParser) throws XmlPullParserException, IOException {
			
			int eventType = pullParser.getEventType();
			
			do{
				
				if (eventType == XmlPullParser.START_DOCUMENT) {
					System.out.println("Start document");
				} else if (eventType == XmlPullParser.END_DOCUMENT) {
					System.out.println("End DOcument");
				}else if (eventType == XmlPullParser.START_TAG) {
					processStartElement(pullParser);
				}else if (eventType == XmlPullParser.END_TAG) {
					processEndElement(pullParser);
				}else if (eventType == XmlPullParser.TEXT) {
					processText(pullParser);
				}
			
				eventType = pullParser.next();
		
			} while (eventType != XmlPullParser.END_DOCUMENT);
	
		}

		public void processStartElement(XmlPullParser event) {
		
			String name = event.getName();
		
			if (name.equals("part")) {
				inPart = true;
				currentPart = new Part();			
			} else if (name.equals("number")) {			
				inNumber = true;
			}else if (name.equals("name")) {
				inName = true;
			}else if (name.equals("description")) {
				inDescription = true;
			}else if (name.equals("stock")) {
				inStock = true;
			}
			
		}	
	
		public void processText(XmlPullParser events) throws XmlPullParserException {
		
			if (inNumber) {
				String s = events.getText();
				currentPart.setNumber(s);
			}
		
			if (inName) {
				String s = events.getText();
				currentPart.setName(s);
			}
		
			if (inDescription) {
				String s = events.getText();
				currentPart.setDescription(s);
			}
		
			if (inStock) {
				String s = events.getText();
				currentPart.setStock(Integer.parseInt(s));
			}
	
		}
	
		public void processEndElement(XmlPullParser event){
		
			String name = event.getName();
		
			if (name.equals("part")){
				inPart = false;
			}else if (name.equals("number")) {
				inNumber = false;
			}else if (name.equals("name")) {
				inName = false;
			}else if (name.equals("description")) {
				inDescription = false;
			}else if (name.equals("stock")) {
				inStock = false;
			}
		
		}
	}
