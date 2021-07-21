package com.example.demo.src.accountHome;

import com.example.demo.config.BaseException;
import com.example.demo.src.accountHome.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AccountHomeProvider {

    @Autowired
    private AccountHomeDao accountHomeDao;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    public AccountHomeProvider(AccountHomeDao accountHomeDao, JwtService jwtService) {
        this.accountHomeDao = accountHomeDao;
        this.jwtService = jwtService;
    }

    public GetAccountHomeRes retrieveAccountHome(int accountIdx) throws BaseException {
        try{
            AccountInfo accountInfo = accountHomeDao.retrieveAccountInfo(accountIdx);
            System.out.println("[GET] accountInfo DAO 완료");
            AccountCntInfo accountCntInfo = accountHomeDao.retrieveAccountCntInfo(accountIdx);
            System.out.println("[GET] accountCntInfo DAO 완료");
            List<StoryGroup> storyGroup = accountHomeDao.retrieveStoryGroup(accountIdx);
            System.out.println("[GET] storyGroup DAO 완료");
            List<AccountHomePost> accountHomePosts = accountHomeDao.retrieveAccountHomePost(accountIdx);
            System.out.println("[GET] storyGroup DAO 완료");

            return new GetAccountHomeRes(accountInfo, accountCntInfo, storyGroup, accountHomePosts);
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
