
package rsscreator.ui.resourses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndDocument;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author redayoub
 */
public class RSSFeedWriter {
    private final static String FILENAME="output.xml";
    public static void writeRSSFeed(Feed feed,File outputFile){
        
        try {
            
            XMLOutputFactory factory=XMLOutputFactory.newInstance();
            XMLEventWriter eventWriter=factory.createXMLEventWriter(new FileOutputStream(outputFile));
            XMLEventFactory eventFactory=XMLEventFactory.newInstance();
            
            XMLEvent end=eventFactory.createDTD("\n");
            
            StartDocument sd=eventFactory.createStartDocument();
            eventWriter.add(sd);
            eventWriter.add(end);
            
            StartElement rssStart =eventFactory.createStartElement("", "", "rss");
            eventWriter.add(rssStart);
            eventWriter.add(eventFactory.createAttribute("version", "2.0"));
            eventWriter.add(end);

            StartElement channelStart=eventFactory.createStartElement("", "", "channel");
            eventWriter.add(channelStart);
            eventWriter.add(end);
            
            // write diifrent node
            writeNode(eventWriter, "title", feed.getTitle());
            writeNode(eventWriter, "description", feed.getDescription());
            writeNode(eventWriter, "link", feed.getLink());
            writeNode(eventWriter, "language", feed.getLanguage());
            writeNode(eventWriter, "pubDate", feed.getPubDate());
            writeNode(eventWriter, "copyright", feed.getCopyright());
            
            StartElement itemStart=eventFactory.createStartElement("", "", "item");
            XMLEvent tab=eventFactory.createDTD("\t");
            EndElement itemEnd=eventFactory.createEndElement("", "","item");
            
            // write messages
            for (FeedMessage msg:feed.getEntries()){
                eventWriter.add(tab);
                eventWriter.add(itemStart);
                eventWriter.add(end);
                
                eventWriter.add(tab);
                writeNode(eventWriter, "title", msg.getTitle());
                eventWriter.add(tab);
                writeNode(eventWriter, "author", msg.getAuthor());
                eventWriter.add(tab);
                writeNode(eventWriter, "link", msg.getLink());
                eventWriter.add(tab);
                writeNode(eventWriter, "guid", msg.getGuid());
                eventWriter.add(tab);
                writeNode(eventWriter, "description", msg.getDescription());
                
                eventWriter.add(tab);
                eventWriter.add(itemEnd);
                eventWriter.add(end);
            }
            
            EndElement channelEnd=eventFactory.createEndElement("", "", "channel");
            eventWriter.add(channelEnd);
            eventWriter.add(end);
            
            EndElement rssEnd=eventFactory.createEndElement("", "", "rss");
            eventWriter.add(rssEnd);
            eventWriter.add(end);
            
            EndDocument endDoc=eventFactory.createEndDocument();
            eventWriter.add(endDoc);
            eventWriter.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RSSFeedWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XMLStreamException ex) {
            Logger.getLogger(RSSFeedWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    private static void writeNode(XMLEventWriter eventWriter,String nodeName,String nodeText)
            throws XMLStreamException{
            XMLEventFactory eventFactory=XMLEventFactory.newInstance();
            XMLEvent end=eventFactory.createDTD("\n");
            XMLEvent tab=eventFactory.createDTD("\t");
            // crate start element
            StartElement nodeStart=eventFactory.createStartElement("", "", nodeName);
            eventWriter.add(tab);
            eventWriter.add(nodeStart);
            // create content
            Characters characters=eventFactory.createCharacters(nodeText);
            eventWriter.add(characters);
            // create end element
            EndElement endElement=eventFactory.createEndElement("", "", nodeName);
            eventWriter.add(endElement);
            eventWriter.add(end);
        
    }
}
