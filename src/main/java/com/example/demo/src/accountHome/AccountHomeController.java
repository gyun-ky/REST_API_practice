package com.example.demo.src.accountHome;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.accountHome.model.GetAccountHomeRes;
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

    public AccountHomeController(AccountHomeProvider accountHomeProvider, AccountHomeService accountHomeService, JwtService jwtService) {
        this.accountHomeProvider = accountHomeProvider;
        this.accountHomeService = accountHomeService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<GetAccountHomeRes> retrieveAccountHome(@PathVariable(required = true) int accountIdx){
        System.out.println("[GET] retrieveAccountHome Route");
        try{
            GetAccountHomeRes result = accountHomeProvider.retrieveAccountHome(accountIdx);
            return new BaseResponse<>(result);
        }
        catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
