package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.src.feed.model.Post;
import com.example.demo.src.post.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired

    public PostDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Comment> retrieveComments(int postIdx){
        String query = "SELECT PostComments.idx as idx, account.accountId, account.profileImgUrl, likes.likeCnt, contents, recommentIdx, " +
                "    CASE WHEN TIMESTAMPDIFF(hour, updatedAt, current_timestamp())<24 " +
                "        then CONCAT(TIMESTAMPDIFF(hour, createdAt, current_timestamp()), '시간전') " +
                "        else DATE_FORMAT(updatedAt, '%m월 %d일') END AS updatedAt " +
                "FROM PostComments " +
                "INNER JOIN " +
                "    (SELECT idx, accountId, profileImgUrl FROM Account) account on account.idx=PostComments.accountIdx " +
                "LEFT JOIN " +
                "    (SELECT postCommentIdx, COUNT(*) as likeCnt FROM PostCommentLike GROUP BY postCommentIdx) likes on likes.postCommentIdx=PostComments.idx " +
                "WHERE postIdx=? " +
                "ORDER BY createdAt DESC";
        int param = postIdx;

        List<Comment> result = this.jdbcTemplate.query(query,
                (rs, rsNum) -> new Comment(
                        rs.getInt("idx"),
                        rs.getString("accountId"),
                        rs.getString("profileImgUrl"),
                        rs.getInt("likeCnt"),
                        rs.getString("contents"),
                        rs.getInt("recommentIdx"),
                        rs.getString("updatedAt")
                ), param);
        System.out.println("[DAO] retrieveComments complete");
        return result;

    }

    public PostInfo retrievePostInfo(int postIdx){
        String query = "SELECT profileImgUrl AS userProfileImgUrl, accountId AS userAccountId, Post.contents AS postContents, " +
                "       CASE WHEN TIMESTAMPDIFF(hour, Post.createdAt, current_timestamp()) = 0 " +
                "        then CONCAT(TIMESTAMPDIFF(minute, Post.createdAt, current_timestamp()), '분 전') " +
                "        else CONCAT(TIMESTAMPDIFF(hour, Post.createdAt, current_timestamp()), '시간 전') " +
                "           END AS createdAt " +
                "FROM Account " +
                "INNER JOIN Post on Post.accountIdx=Account.idx " +
                "WHERE Post.idx = ?";
        int param = postIdx;

        PostInfo result = this.jdbcTemplate.queryForObject(query,
                (rs, rsNum)->new PostInfo(
                        rs.getString("userProfileImgUrl"),
                        rs.getString("userAccountId"),
                        rs.getString("postContents"),
                        rs.getString("createdAt")
                ),param);

        System.out.println("[DAO] retrievePostInfo create");
        return result;

    }

    public int createPostComment(boolean recomment, int postIdx, PostCommentReq postCommentReq, int accountIdx){
        String query;
        Object[] params;
        if(recomment == true){
            query = "INSERT INTO rest.PostComments (postIdx, accountIdx, recommentIdx,  contents) VALUES (?,?,?,?)";
            params = new Object[]{
                    postIdx, accountIdx, postCommentReq.getContents(), postCommentReq.getContents()
            };
        }
        else{
            query = "INSERT INTO rest.PostComments (postIdx, accountIdx, recommentIdx,  contents) VALUES (?,?,?,?)";
            params = new Object[]{
                    postIdx, accountIdx, null, postCommentReq.getContents()
            };
        }

        this.jdbcTemplate.update(query,params);
        System.out.println("[DAO] createPostComment complete");
        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

    }

    public int updatePostComment(int commentIdx, PatchCommentReq patchCommentsReq){
        String query = "UPDATE PostComments SET contents=? WHERE idx=?";
        Object[] params = new Object[]{
                patchCommentsReq.getContents(), commentIdx
        };

        this.jdbcTemplate.update(query, params);
        System.out.println("[DAO] updatePostComment");
        return commentIdx;
    }

    public int deletePostComment(int commentIdx){
        String query = "UPDATE PostComments SET status=? WHERE idx=?";
        Object[] params = new Object[]{
                "INACTIVE", commentIdx
        };

        this.jdbcTemplate.update(query, params);
        System.out.println("[DAO] deletePostComment");
        return commentIdx;
    }

    public boolean checkCommentWriter(int commentIdx, int accountIdx){
        String query = "SELECT exists(SELECT * FROM PostComments WHERE idx=? and accountIdx=?)";
        Object[] params = new Object[]{
                commentIdx, accountIdx
        };

        boolean result = this.jdbcTemplate.queryForObject(query, boolean.class, params);
        System.out.println("[DAO] checkCommentWriter : " + result);
        return result;
    }

    public int checkLocationTag(String tagName){
        String query = "SELECT idx FROM LocationTag WHERE location=?";
        try{
            int idx = this.jdbcTemplate.queryForObject(query, int.class, tagName);
            System.out.println("[DAO] checkLocationTag exists! ");
            return idx;
        }
        catch (Exception e){
            String query2 = "INSERT INTO LocationTag (location) value (?)";
            this.jdbcTemplate.update(query2,tagName);
            System.out.println("[DAO] checkLocationTag NotEXIST! CREATE!");
            String lastInsertIdQuery = "select last_insert_id()";
            return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
        }


//        int idx = this.jdbcTemplate.queryForObject(query, int.class, tagName);
//        System.out.println("[DAO] checkLocationTag exists! ");
//
//        if(idx == 0){
//            String query2 = "INSERT INTO LocationTag (location) value (?)";
//            int newIdx = this.jdbcTemplate.update(query,tagName);
//            System.out.println("[DAO] checkLocationTag NotEXIST! CREATE!");
//            return newIdx;
//        }
//        else{
//            return idx;
//        }

    }

    public int createPost(int accountIdx, int locationTagIdx, PostPostReq postPostReq){
        String query = "INSERT INTO Post (accountIdx, locationTagIdx, contents) values (?,?,?)";
        Object[] params = new Object[]{
                accountIdx, locationTagIdx, postPostReq.getContents()
        };

        this.jdbcTemplate.update(query, params);
        String lastInsertIdQuery = "select last_insert_id()";
        int postIdx= this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);

        System.out.println("[DAO] createPost post - complete");

        List<PostImage> postImages = postPostReq.getImages();
        postImages.forEach(item->{
            String imageQuery = "INSERT INTO PostImage (postIdx, imageUrl) values (?,?)";
            Object[] imageParams = new Object[]{
                    postIdx, item.getImageUrl()
            };

            this.jdbcTemplate.update(imageQuery, imageParams);
            int imageIdx =jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
            System.out.println("[DAO] postImage create - "+ imageIdx);
        });
        return postIdx;

    }

    public void createHashTag(int postIdx, PostPostReq postPostReq){

        List<HashTag> hashTags = postPostReq.getHashTags();
        hashTags.forEach(item->{
            String tagQuery = "INSERT INTO HashTag (postIdx, tagName) values (?,?)";
            Object[] imageParams = new Object[]{
                    postIdx, item.getTagName()
            };

            this.jdbcTemplate.update(tagQuery,imageParams);
            String lastInsertIdQuery = "select last_insert_id()";
            int tagIdx = this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
            System.out.println("[DAO] hashTag create - "+ tagIdx);
        });
    }
}
