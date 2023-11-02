/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsscreator.ui.resourses;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author redayoub
 */
public class RSSFeedReader {
    static final String TITLE = "title";
    static final String DESCRIPTION= "description";
    static final String CHANNEL = "channel";
    static final String LANGUAGE = "language";
    static final String COPYRIGHT = "copyright";
    static final String LINK = "link";
    static final String AUTHOR = "author";
    static final String ITEM = "item";
    static final String PUB_DATE = "pubDate";
    static final String GUID = "guid";

    public static Feed reedFeed(URL mUrl) throws MalformedURLException{

        url=mUrl;
        
        Feed nfeed = null;
        try {
            boolean isFeedHeader = true;
            // Set header values intial to theempty string
            String description = "";
            String title = "";
            String link = "";
            String language = "";
            String copyright = "";
            String author = "";
            String pubdate = "";
            String guid = "";
// First create a new XMLInputFactory
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
// Setup a new eventReader
            InputStream in = read();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
// read the XML document
            while (eventReader.hasNext()) {
                XMLEvent event= eventReader.nextEvent();
                if (event.isStartElement()) {
                    String localPart= event.asStartElement().getName().getLocalPart();
                    switch (localPart) {
                        case ITEM: {
                            if (isFeedHeader) {
                                isFeedHeader = false;
                                nfeed = new Feed(title, link, description, language,copyright,pubdate);
                            }
                            event = eventReader.nextEvent();
                            break;
                        }
                        case TITLE: {
                            title= getCharacterData(event, eventReader);
                            break;
                        }
                        case DESCRIPTION: {
                            description= getCharacterData(event, eventReader);
                            break;
                        }
                        case LINK: {
                            link= getCharacterData(event, eventReader);
                            break;
                        }
                        case GUID: {
                            guid= getCharacterData(event, eventReader);
                            break;
                        }
                        case LANGUAGE: {
                            language= getCharacterData(event, eventReader);
                            break;
                        }
                        case AUTHOR: {
                            author= getCharacterData(event, eventReader);
                            break;
                        }
                        case PUB_DATE: {
                            pubdate= getCharacterData(event, eventReader);
                            break;
                        }
                        case COPYRIGHT: {
                            copyright= getCharacterData(event, eventReader);
                            break;
                        }
                    }
                } else if (event.isEndElement()) {
                    if (event.asEndElement().getName().getLocalPart() == (ITEM)) {
                        FeedMessage message = new FeedMessage();
                        message.setAuthor(author);
                        message.setDescription(description);
                        message.setGuid(guid);
                        message.setLink(link);
                        message.setTitle(title);
                        nfeed.getEntries().add(message);
                        event = eventReader.nextEvent();
                        continue;
                    }
                }
            }
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }

       return nfeed;
        
    }

    private static String getCharacterData(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result= event.asCharacters().getData();
        }
        return result;
    }

    static URL url;

    private static InputStream read() {
        try {
            return url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
