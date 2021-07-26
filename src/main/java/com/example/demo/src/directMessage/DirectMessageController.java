package com.example.demo.src.directMessage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.directMessage.model.GetDirectMessageListRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/directMsgs")
public class DirectMessageController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DirectMessageProvider directMessageProvider;

    private final JwtService jwtService;

    private final UserProvider userProvider;

    @Autowired
    public DirectMessageController(DirectMessageProvider directMessageProvider, JwtService jwtService, UserProvider userProvider) {
        this.directMessageProvider = directMessageProvider;
        this.jwtService = jwtService;
        this.userProvider = userProvider;
    }




    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<List<GetDirectMessageListRes>> retrieveDirectMessageList(@PathVariable int accountIdx) throws BaseException{
        System.out.println("[GET] directMessageList route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            List<GetDirectMessageListRes> result = directMessageProvider.retrieveDirectMessageList(accountIdx);

            return new BaseResponse<>(result);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }
}
