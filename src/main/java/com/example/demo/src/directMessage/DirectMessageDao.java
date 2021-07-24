package com.example.demo.src.directMessage;

import com.example.demo.src.directMessage.model.GetDirectMessageListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DirectMessageDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DirectMessageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<GetDirectMessageListRes> retrieveGetDirectMessageList(int accountIdx){
        String query = "SELECT DirectMessageRoom.idx AS roomIdx, min(directMessage.contents) as lastsentMessage, max(accountId) as accountId, max(profileImgUrl) as profileImgUrl FROM DirectMessageRoom " +
                "INNER JOIN " +
                "    (SELECT senderIdx, receiverIdx, roomIdx, idx, contents FROM DirectMessage) directMessage on directMessage.roomIdx=DirectMessageRoom.idx " +
                "INNER JOIN " +
                "(SELECT idx, accountId, profileImgUrl FROM Account) account on directMessage.senderIdx=account.idx or directMessage.receiverIdx=account.idx " +
                "WHERE accountIdx1=? OR accountIdx2=? " +
                "GROUP BY roomIdx";
        Object[] params = new Object[]{
            accountIdx, accountIdx
        };

        List<GetDirectMessageListRes> result = this.jdbcTemplate.query(query,
                (rs, rsNum) -> new GetDirectMessageListRes(
                        rs.getInt("roomIdx"),
                        rs.getString("accountId"),
                        rs.getString("profileImgUrl"),
                        rs.getString("lastSentMessage")

                ), params);

        System.out.println("[DAO] DirectMessageList complere");

        return result;
    }

}
