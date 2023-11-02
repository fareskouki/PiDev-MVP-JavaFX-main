package com.MVP.Entite;

import java.sql.Timestamp;
import javafx.beans.property.*;

public class Comment {

    private final IntegerProperty id;
    private final IntegerProperty blogId;
    private final StringProperty content;
    private final ObjectProperty<Timestamp> createdAt;

    public Comment(int blogId, String content) {
        this.id = new SimpleIntegerProperty();
        this.blogId = new SimpleIntegerProperty(blogId);
        this.content = new SimpleStringProperty(content);
        this.createdAt = new SimpleObjectProperty<>(new Timestamp(System.currentTimeMillis()));
    }

    public Comment(int id, int blogId, String content, Timestamp createdAt) {
        this.id = new SimpleIntegerProperty(id);
        this.blogId = new SimpleIntegerProperty(blogId);
        this.content = new SimpleStringProperty(content);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getBlogId() {
        return blogId.get();
    }

    public IntegerProperty blogIdProperty() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId.set(blogId);
    }

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public Timestamp getCreatedAt() {
        return createdAt.get();
    }

    public ObjectProperty<Timestamp> createdAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt.set(createdAt);
    }
}
