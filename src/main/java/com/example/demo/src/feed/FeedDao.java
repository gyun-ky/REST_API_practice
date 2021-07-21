package com.example.demo.src.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FeedDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetAccoutInfoRes retrieveAccountInfo(int accountIdx){
        String query = "SELECT profileImgUrl, accountName, accountId, description from Account where idx =?";
        int param = accountIdx;

        return this.jdbcTemplate.queryForObject(query,
                (rs, rsNum)->new GetAccountInfoRes(
                        rs.getString("accountId"),
                        rs.getString("accountName"),
                        rs.getString("profileImgUrl"),
                        rs.getString("description")
                ), param);
    }
}
