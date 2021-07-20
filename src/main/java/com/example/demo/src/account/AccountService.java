package com.example.demo.src.account;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.account.model.PostAccountReq;
import com.example.demo.src.account.model.PostAccountRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.POST_ACCOUNTS_EXISTS_ACCOUNTID;

@Service
public class AccountService {

    private final AccountDao accountDao;

    private final AccountProvider accountProvider;

    private final JwtService jwtService;

    @Autowired
    public AccountService(AccountDao accountDao, AccountProvider accountProvider, JwtService jwtService) {
        this.accountDao = accountDao;
        this.accountProvider = accountProvider;
        this.jwtService = jwtService;
    }


    public PostAccountRes createAccount(int userIdx, PostAccountReq postAccountReq) throws BaseException {
        if(this.accountProvider.checkAccountId(postAccountReq.getAccountId())==1){
            throw new BaseException(POST_ACCOUNTS_EXISTS_ACCOUNTID);
        }
        try{
            int accountIdx = accountDao.createAccount(userIdx, postAccountReq);

            //jwt 발급은 추후에 구현
            return new PostAccountRes(accountIdx);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
