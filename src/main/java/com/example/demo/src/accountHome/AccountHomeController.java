package com.example.demo.src.accountHome;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.accountHome.model.GetAccountHomeRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.User;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/accountHome")
public class AccountHomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AccountHomeService accountHomeService;

    @Autowired
    private final AccountHomeProvider accountHomeProvider;

    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final UserProvider userProvider;

    public AccountHomeController(AccountHomeService accountHomeService, AccountHomeProvider accountHomeProvider, JwtService jwtService, UserProvider userProvider) {
        this.accountHomeService = accountHomeService;
        this.accountHomeProvider = accountHomeProvider;
        this.jwtService = jwtService;
        this.userProvider = userProvider;
    }

    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<GetAccountHomeRes> retrieveAccountHome(@PathVariable(required = true) int accountIdx) throws BaseException{
        System.out.println("[GET] retrieveAccountHome Route");
        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            GetAccountHomeRes result = accountHomeProvider.retrieveAccountHome(accountIdx);
            return new BaseResponse<>(result);
        }
        catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
