package com.example.demo.src.account;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.account.model.GetAccountRes;
import com.example.demo.src.account.model.PatchAccountReq;
import com.example.demo.src.account.model.PostAccountReq;
import com.example.demo.src.account.model.PostAccountRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.src.user.model.GetUserRes;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/accounts")
public class AccountController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final AccountProvider accountProvider;
    @Autowired
    private final AccountService accountService;
    @Autowired
    private final JwtService jwtService;

    public AccountController(AccountProvider accountProvider, AccountService accountService, JwtService jwtService) {
        this.accountProvider = accountProvider;
        this.accountService = accountService;
        this.jwtService = jwtService;
    }


    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetAccountRes>> getAllAccountInfo() {
        System.out.println("온다");
        try{
            List<GetAccountRes> getAccountResList = accountProvider.getAllAccount();
            return new BaseResponse<>(getAccountResList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<GetAccountRes> getAccountInfo(@PathVariable("accountIdx") int accountIdx) {
        // Get Users
        System.out.println("왔다");
        try{
            GetAccountRes getAccountRes = accountProvider.retrieveAccountInfo(accountIdx);
            return new BaseResponse<>(getAccountRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/{accountIdx}")
    public BaseResponse<PostAccountRes> createAccount(@PathVariable(required = true) int accountIdx, @RequestBody PostAccountReq postAccountReq){
        System.out.println("[POST] createAccount route");
        //형식적 validation 추가 - 이메일 정규표현
        try{
            PostAccountRes postAccountRes = accountService.createAccount(accountIdx, postAccountReq);
            return new BaseResponse<>(postAccountRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/{accountIdx}")
    public BaseResponse<String> patchAccount(@PathVariable int accountIdx, @RequestBody PatchAccountReq patchAccountReq){
        System.out.println("[PATCH] pathAccount route");

        try{
            accountService.patchAccount(accountIdx, patchAccountReq);

            String result = "";
            return new BaseResponse<>(result);
        }
        catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

}
