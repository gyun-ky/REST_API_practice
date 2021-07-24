package com.example.demo.src.account;

import com.example.demo.config.BaseException;
import com.example.demo.src.account.model.FollowerAccount;
import com.example.demo.src.account.model.FollowingAccount;
import com.example.demo.src.account.model.GetAccountRes;
import com.example.demo.src.account.model.PostAccountRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class AccountProvider {

    @Autowired
    private final AccountDao accountDao;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    public AccountProvider(AccountDao accountDao, JwtService jwtService){
        this.accountDao = accountDao;
        this.jwtService = jwtService;
    }



    public GetAccountRes retrieveAccountInfo(int accountIdx) throws BaseException {
        try{
            GetAccountRes getAccountRes = accountDao.getAccount(accountIdx);
            return getAccountRes;
        }
        catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetAccountRes> getAllAccount() throws BaseException{
        System.out.println("call 되는가");
        try{
            List<GetAccountRes> getAccountResList = accountDao.getAllAccount();

            return getAccountResList;
        }
        catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<FollowingAccount> retrieveFollowingAccount(int accountIdx) throws BaseException{
        System.out.println("[PROVIDER] retrieveFollowingAccount");
        try{
            List<FollowingAccount> followingAccounts = accountDao.retrieveFollowingAccount(accountIdx);

            return followingAccounts;
        }
        catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<FollowerAccount> retrieveFollowerAccount(int accountIdx) throws BaseException{
        System.out.println("[PROVIDER] retrieveFollowingAccount");
        try{
            List<FollowerAccount> followerAccounts = accountDao.retrieveFollowerAccount(accountIdx);

            return followerAccounts;
        }
        catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAccountId(String accountId) throws BaseException {
        try{
            return this.accountDao.checkAccountId(accountId);
        }
        catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
