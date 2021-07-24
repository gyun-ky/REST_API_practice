package com.example.demo.src.feed;

import com.example.demo.src.feed.model.Post;
import com.example.demo.src.feed.model.PostImage;
import com.example.demo.src.feed.model.Story;
import com.example.demo.src.feed.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FeedDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserInfo retrieveUserInfo(int accountIdx){
        String query = "SELECT profileImgUrl AS userProfileImgUrl, accountId FROM Account WHERE idx=?";

        int param = accountIdx;

        UserInfo result = this.jdbcTemplate.queryForObject(query,
                (rs, rsNum)->new UserInfo(
                        rs.getString("userProfileImgUrl"),
                        rs.getString("accountId")
                ), param);
        System.out.println("[DAO] userInfo complete");
        return result;
    }

    public List<Story> retrieveStory(int accountIdx){
        String query = "SELECT story.idx As idx, Account.accountId, profileImgUrl, " +
                "       CASE WHEN TIMESTAMPDIFF(hour, story.createdAt, current_timestamp()) = 0 " +
                "        then CONCAT(TIMESTAMPDIFF(minute, story.createdAt, current_timestamp()), '분 전') " +
                "        else CONCAT(TIMESTAMPDIFF(hour, story.createdAt, current_timestamp()), '시간 전') " +
                "           END AS createdAt " +
                "FROM Account " +
                "INNER JOIN " +
                "(SELECT followedIdx FROM Following " +
                "WHERE followerIdx=? AND status='ACTIVE') follow on follow.followedIdx=Account.idx " +
                "INNER JOIN " +
                "(SELECT idx, accountIdx, createdAt FROM Story WHERE TIMESTAMPDIFF(hour, createdAt, current_timestamp())<24) story on story.accountIdx=Account.idx";
        int param = accountIdx;
        System.out.println("[DAO] story not_complete");
        List<Story> result = this.jdbcTemplate.query(query,
                (rs, rsNum)->new Story(
                        rs.getInt("idx"),
                        rs.getString("accountId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("createdAt")
                ),param);
        System.out.println("[DAO] story complete");
        return result;
    }

    public List<Post> retrievePost(int accountIdx){
        String query = "SELECT Post.idx AS idx, " +
                "       CASE WHEN scrap.status = 'ACTIVE' " +
                "        then true " +
                "        else false END AS scrap, " +
                "       account.accountId, locationTag.location as locationTag, " +
                "       CASE WHEN postLike.likeCnt is NULL " +
                "           then 0 else likeCnt End AS likeCnt, " +
                "        profileImgUrl, contents, " +
                "       CASE WHEN TIMESTAMPDIFF(hour, createdAt, current_timestamp())<24 " +
                "        then CONCAT(TIMESTAMPDIFF(hour, createdAt, current_timestamp()), '시간전') " +
                "        else DATE_FORMAT(createdAt, '%m월 %d일') END AS createdAt " +
                "FROM Post " +
                "INNER JOIN " +
                "(SELECT followedIdx FROM Following " +
                "    WHERE followerIdx=? AND status='ACTIVE') follow on Post.accountIdx=follow.followedIdx " +
                "INNER JOIN " +
                "(SELECT idx, accountId, profileImgUrl FROM Account) account on follow.followedIdx=account.idx " +
                "LEFT JOIN " +
                "(SELECT idx, location FROM LocationTag) locationTag on Post.locationTagIdx=locationTag.idx " +
                "LEFT JOIN " +
                "(SELECT postIdx, COUNT(*) as likeCnt FROM PostLike GROUP BY postIdx) postLike on Post.idx=postLike.postIdx " +
                "LEFT JOIN " +
                "(SELECT status, postIdx FROM Scrap WHERE accountIdx=?) scrap on Post.idx=scrap.postIdx " +
                "ORDER BY createdAt DESC";

        Object[] params = new Object[]{
                accountIdx, accountIdx
            };

        List<Post> result =  this.jdbcTemplate.query(query,
                (rs, rsNum)->new Post(
                    rs.getInt("idx"),
                    rs.getString("profileImgUrl"),
                    rs.getString("accountId"),
                    rs.getString("locationTag"),
                    rs.getBoolean("scrap"),
                    rs.getInt("likeCnt"),
                    rs.getString("createdAt"),
                    rs.getString("contents")
                ), params);

        System.out.println("[DAO] post query complete");
        return result;

    }

    public List<PostImage> retrievePostImage(int accountIdx){
        String query = "SELECT postIdx, imageUrl as postImageUrl FROM PostImage " +
                "INNER JOIN (SELECT idx, accountIdx FROM Post) post on post.idx = PostImage.postIdx " +
                "INNER JOIN (SELECT followedIdx FROM Following WHERE followerIdx = ? AND status = 'ACTIVE') follow on accountIdx = followedIdx";

        int param = accountIdx;

        List<PostImage> result = this.jdbcTemplate.query(query,
                (rs, rsNum)->new PostImage(
                        rs.getInt("postIdx"),
                        rs.getString("postImageUrl")
                ),param);

        System.out.println("[DAO] postImage query complete");

        return result;
    }
}
