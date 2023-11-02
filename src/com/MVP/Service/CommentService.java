package com.MVP.Service;

import com.MVP.Entite.Comment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.MVP.Utils.DataBase;

public class CommentService {

    private Connection conn;

    public CommentService() {
        conn = DataBase.getInstance().getConnection();
    }

 
    public void addComment(Comment comment) {
        String query = "INSERT INTO comment (bbid_id, content, created_at) VALUES (?, ?, ?)";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, comment.getBlogId());
            pst.setString(2, comment.getContent());
            pst.setTimestamp(3, comment.getCreatedAt());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void deleteComment(int id) {
        String query = "DELETE FROM comment WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateComment(Comment comment) {
        String query = "UPDATE comment SET bbid_id = ?, content = ?, created_at = ? WHERE id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, comment.getBlogId());
            pst.setString(2, comment.getContent());
            pst.setTimestamp(3, comment.getCreatedAt());
            pst.setInt(4, comment.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<Comment> readAllComments() {
        List<Comment> list = new ArrayList<>();
        String query = "SELECT * FROM comment";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(rs.getInt("id"), rs.getInt("bbid_id"), rs.getString("content"), rs.getTimestamp("created_at"));
                list.add(comment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

  
    public List<Comment> readCommentsByBlogId(int id) {
        List<Comment> list = new ArrayList<>();
        String query = "SELECT * FROM comment WHERE bbid_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment(rs.getInt("id"), rs.getInt("bbid_id"), rs.getString("content"), rs.getTimestamp("created_at"));
                list.add(comment);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommentService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

}
