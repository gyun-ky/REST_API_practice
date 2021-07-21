package com.example.demo.src.accountHome;

import com.example.demo.src.accountHome.model.AccountCntInfo;
import com.example.demo.src.accountHome.model.AccountHomePost;
import com.example.demo.src.accountHome.model.AccountInfo;
import com.example.demo.src.accountHome.model.StoryGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccountHomeDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public AccountInfo retrieveAccountInfo(int accountIdx){
        String query = "SELECT profileImgUrl, accountName, accountId, description from Account where idx =?";
        int param = accountIdx;

        return this.jdbcTemplate.queryForObject(query,
                (rs, rsNum)->new AccountInfo(
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getString("profileImgUrl"),
                        rs.getString("description")
                ), param);
    }

    public AccountCntInfo retrieveAccountCntInfo(int accountIdx) {
        String query = "SELECT Post.postCnt, COUNT(followerIdx) as followingCnt, Follower.followerCnt as followerCnt FROM Following " +
                "JOIN(SELECT COUNT(followedIdx) as followerCnt FROM Following WHERE followedIdx =?) Follower " +
                "JOIN(SELECT COUNT(*) as postCnt FROM Post WHERE Post.accountIdx = ?) Post " +
                "WHERE Following.followerIdx= ?";
        Object[] params = new Object[]{
                accountIdx, accountIdx, accountIdx
        };

        return this.jdbcTemplate.queryForObject(query,
                (rs, rsNum) -> new AccountCntInfo(
                        rs.getInt("postCnt"),
                        rs.getInt("followingCnt"),
                        rs.getInt("followerCnt")
                ), params);
    }

    public List<StoryGroup> retrieveStoryGroup(int accountIdx){
        String query = "SELECT idx, groupName, groupImage FROM  StoryGroup " +
                "WHERE accountIdx=? AND status='active' " +
                "ORDER BY createdAt DESC";
        int param = accountIdx;

        return this.jdbcTemplate.query(query,
                (rs, rsNum)-> new StoryGroup(
                        rs.getInt("idx"),
                        rs.getString("groupName"),
                        rs.getString("groupImage")
                ),param);
    }

    public List<AccountHomePost> retrieveAccountHomePost(int accountIdx){
        String query = "SELECT idx as postIdx, imageUrl, contents FROM Post " +
                "INNER JOIN " +
                "(SELECT postIdx, imageUrl FROM PostImage group by postIdx) postImage on idx=postImage.postIdx " +
                "WHERE Post.accountIdx=? and status='ACTIVE' " +
                "ORDER BY Post.createdAt DESC";
        int param = accountIdx;

        return this.jdbcTemplate.query(query,
                (rs, rsNum)-> new AccountHomePost(
                        rs.getInt("postIdx"),
                        rs.getString("imageUrl"),
                        rs.getString("contents")
                ), param);
    }


}
