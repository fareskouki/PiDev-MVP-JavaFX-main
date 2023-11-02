
package rsscreator.ui.resourses;

/**
 *
 * @author redayoub
 */
public class FeedMessage {
    private String title;
    private String author;
    private String link;
    private String guid;
    private String description;

    public FeedMessage(String title, String author, String link, String guid, String description) {
        this.title = title;
        this.author = author;
        this.link = link;
        this.guid = guid;
        this.description = description;
    }

    FeedMessage() {
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return  "Title :" + title + "\n Author :" + author + "\n guid=" + guid ;
    }
    
    
    
}
