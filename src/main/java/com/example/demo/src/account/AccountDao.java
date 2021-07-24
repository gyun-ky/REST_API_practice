package com.example.demo.src.account;

import com.example.demo.src.account.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class AccountDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetAccountRes getAccount(int accountIdx){
        String query = "select profileImgUrl, accountName, accountId, website, description from Account where idx =?";
        int getAccountParams = accountIdx;
        System.out.println("여기까지는 온다");
        return this.jdbcTemplate.queryForObject(query,
                (rs, rowNum) -> new GetAccountRes(
                        rs.getString("profileImgUrl"),
                        rs.getString("accountName"),
                        rs.getString("accountId"),
                        rs.getString("website"),
                        rs.getString("description")),
                getAccountParams);
    }

    public List<GetAccountRes> getAllAccount(){
        String query = "select * from Account";
        return this.jdbcTemplate.query(query,
                (rs,rowNum)-> new GetAccountRes(
                        rs.getString("profileImageUrl"),
                        rs.getString("accountName"),
                        rs.getString("accountId"),
                        rs.getString("website"),
                        rs.getString("description"))
                );
    }

    public int createAccount(int userIdx, PostAccountReq postAccountReq){

        String query = "INSERT INTO Account (userIdx, accountId, accountName, website, description, profileImgUrl, status) VALUES (?,?,?,?,?,?,?)";

        Object[] accountParams = new Object[]{
            userIdx, postAccountReq.getAccountId(), postAccountReq.getAccountName(), postAccountReq.getWebsite(), postAccountReq.getDescription(), postAccountReq.getProfileImgUrl(), postAccountReq.getStatus()
        };


        System.out.println("[DAO] accountParams : "+ accountParams.toString());

        this.jdbcTemplate.update(query, accountParams);
        System.out.println("[DAO] insert 완료");
        query = "SELECT last_insert_id()";
        return this.jdbcTemplate.queryForObject(query, int.class);
    }

    public int updateAccount(int accountIdx, PatchAccountReq patchAccountReq){

        String query = "UPDATE Account SET accountId = ?, accountName = ?, website = ?, description = ?, profileImgUrl = ?, status = ? WHERE idx = ?";

        Object[] accountParams = new Object[]{
                patchAccountReq.getAccountId(), patchAccountReq.getAccountName(), patchAccountReq.getWebsite(), patchAccountReq.getDescription(), patchAccountReq.getProfileImgUrl(), patchAccountReq.getStatus(), accountIdx
        };

        int status = this.jdbcTemplate.update(query, accountParams);
        System.out.println("[DAO] account update complete");
        return status;

    }

    public List<FollowerAccount> retrieveFollowerAccount(int accountIdx){

        String query = "SELECT followerIdx, accountId, account.accountName, profileImgUrl FROM Following " +
                "INNER JOIN " +
                "    (SELECT idx, accountId, accountName, profileImgUrl FROM Account) account on Following.followerIdx=account.idx " +
                "WHERE followedIdx=? and status='ACTIVE' ";
        int param = accountIdx;

        return this.jdbcTemplate.query(query,
                (rs, rsNum)->new FollowerAccount(
                        rs.getInt("followerIdx"),
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getString("profileImgUrl")
                ), param);
    }

    public List<FollowingAccount> retrieveFollowingAccount(int accountIdx){

        String query = "SELECT followedIdx, accountId, account.accountName, profileImgUrl FROM Following " +
                "INNER JOIN " +
                "    (SELECT idx, accountId, accountName, profileImgUrl FROM Account) account on Following.followedIdx=account.idx " +
                "WHERE followerIdx=? and status='ACTIVE' ";
        int param = accountIdx;

        return this.jdbcTemplate.query(query,
                (rs, rsNum)->new FollowingAccount(
                        rs.getInt("followedIdx"),
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getString("profileImgUrl")
                ), param);
    }

    public int checkAccountId(String newAccountId){
        String query = "SELECT EXISTS(SELECT accountId FROM Account WHERE accountId = ?)";
        String param = newAccountId;
        int result = this.jdbcTemplate.queryForObject(query, int.class, param);
        System.out.println("[DAO] checkAccountId result = "+ result);
        return result;
    }
}
