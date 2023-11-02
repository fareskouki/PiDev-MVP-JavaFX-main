package com.MVP.Service;

import com.MVP.Entite.Blog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.MVP.Utils.DataBase;

public class BlogService {

    private Connection conn;

    public BlogService() {
        conn = DataBase.getInstance().getConnection();
    }

 
    public void addBlog(Blog b) {
        String query = "INSERT INTO blog (title, content, image) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, b.getTitle());
            pst.setString(2, b.getContent());
            pst.setString(3, b.getImage());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
    public void deleteBlog(int id) {
        String query = "DELETE FROM blog WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    public void updateBlog(Blog b) {
        String query = "UPDATE blog SET title = ?, content = ?, image = ? WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, b.getTitle());
            pst.setString(2, b.getContent());
            pst.setString(3, b.getImage());
            pst.setInt(4, b.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 
    public List<Blog> readAllBlogs() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT * FROM blog";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                list.add(new Blog(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getString("image")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }


    public Blog readById(int id) {
        String query = "SELECT * FROM blog WHERE id = ?";
        Blog blog = null;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                blog = new Blog(rs.getInt("id"), rs.getString("title"), rs.getString("content"), rs.getString("image"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BlogService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blog;
    }

}
