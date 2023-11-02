package rsscreator.ui.resourses;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.gn.module.dashboard.Dashboard;

/**
 *
 * @author redayoub
 */
public class filesHandler {

    public static Feed feed = null;
    public static FeedMessage selFeedMsg = null;
    public static int selFeedIndexListView = -1;
    public static boolean msgAdded = false;

    public static void importFrom(File file) throws IOException {
//
//            try {
//                DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder=factory.newDocumentBuilder();
//                Document doc=builder.parse(file);
//                Node root=doc.getFirstChild();
//
//                Element rssElem=doc.getDocumentElement();
//                NodeList chanelElem=rssElem.getFirstChild().getChildNodes();
//                String title="";
//                String descr="";
//                String link="";
//                String lang="";
//                String copyR="";
//                String pubDate="";
//                for (int i=0;i<chanelElem.getLength();i++){
//                    Node child=chanelElem.item(i);
//                    if (child instanceof Element){
//                        Element childElem=(Element) child;
//                        switch(childElem.getTagName()){
//                            case "title":{
//                                Text childText=(Text) childElem.getFirstChild();
//                                title=childText.getData().trim();
//                            }
//                            case "link":{
//                                Text childText=(Text) childElem.getFirstChild();
//                                link=childText.getData().trim();
//                            }
//                            case "description":{
//                                Text childText=(Text) childElem.getFirstChild();
//                                descr=childText.getData().trim();
//                            }
//                            case "language":{
//                                Text childText=(Text) childElem.getFirstChild();
//                                lang=childText.getData().trim();
//                            }
//                            case "copyright":{
//                                Text childText=(Text) childElem.getFirstChild();
//                                copyR=childText.getData().trim();
//                            }
//                            case "pubDate":{
//                                Text childText=(Text) childElem.getFirstChild();
//                                pubDate=childText.getData().trim();
//                            }
//                            case "item":{
//                                if (feed==null){
//                                    feed=new Feed(title, link, descr, lang, copyR, pubDate);
//                                }
//                                 String titleMsg="";
//                                 String authorMsg="";
//                                 String linkMsg="";
//                                 String guidMsg="";
//                                 String descMsg="";
//
//
//                                NodeList childList=childElem.getChildNodes();
//                                for (int j=0;j<childList.getLength();j++){
//                                    Node childL2=childList.item(i);
//                                    if (childL2 instanceof Element){
//                                        Element childL2Elem=(Element) childL2;
//                                        switch(childL2Elem.getTagName()){
//                                            case "title":{
//                                                Text childText=(Text) childL2Elem.getFirstChild();
//                                                titleMsg=childText.getData().trim();
//                                            }
//                                            case "author":{
//                                                Text childText=(Text) childL2Elem.getFirstChild();
//                                                authorMsg=childText.getData().trim();
//                                            }
//                                            case "guid":{
//                                                Text childText=(Text) childL2Elem.getFirstChild();
//                                                guidMsg=childText.getData().trim();
//                                            }
//                                            case "link":{
//                                                Text childText=(Text) childL2Elem.getFirstChild();
//                                                linkMsg=childText.getData().trim();
//                                            }
//                                            case "description":{
//                                                Text childText=(Text) childL2Elem.getFirstChild();
//                                                descMsg=childText.getData().trim();
//                                            }
//                                        }
//                                    }
//                                }
//
//                                FeedMessage feedMessage=new FeedMessage(titleMsg, authorMsg, linkMsg, guidMsg, descMsg);
//                                System.out.println(feedMessage.toString());
//                                feed.getEntries().add(feedMessage);
//                            }
//
//                        }
//                    }
//                }
//                FXMLDocumentController.createList();
//            } catch (ParserConfigurationException | SAXException ex) {
//                Logger.getLogger(filesHandler.class.getName()).log(Level.SEVERE, null, ex);
//            }
//

//        try {
//            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
//                DocumentBuilder builder=factory.newDocumentBuilder();
//                Document doc=builder.parse(file);
//            SyndFeedInput input=new SyndFeedInput();
//            SyndFeed sf=input.build(doc);
//            feed=new Feed(
//                    sf.getTitle(),
//                    sf.getLink(),
//                    sf.getDescription(),
//                    sf.getLanguage(),
//                    sf.getCopyright(),
//                    sf.getPublishedDate().toString()
//            );
//            feed.getEntries().addAll(sf.getEntries());
//            FXMLDocumentController.createList();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(filesHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(filesHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FeedException ex) {
//            Logger.getLogger(filesHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(filesHandler.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SAXException ex) {
//            Logger.getLogger(filesHandler.class.getName()).log(Level.SEVERE, null, ex);
//        }
        URI uri=file.toURI();
        URL url=uri.toURL();
        feed=RSSFeedReader.reedFeed(url);
        Dashboard.createList();
        
    }


    public static boolean checkGUID(String selGuid) {
        for (FeedMessage msg : feed.getEntries()) {
            if (msg.getGuid().equals(selGuid)) {
                return true;
            }
        }
        return false;
    }

    public static void importFrom(URL url) throws MalformedURLException {
        feed=RSSFeedReader.reedFeed(url);
        Dashboard.createList();
    }
}
