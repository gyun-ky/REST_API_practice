package com.example.demo.src.directMessage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.directMessage.model.GetDirectMessageListRes;
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

    @Autowired
    public DirectMessageController(DirectMessageProvider directMessageProvider, JwtService jwtService) {
        this.directMessageProvider = directMessageProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<List<GetDirectMessageListRes>> retrieveDirectMessageList(@PathVariable int accountIdx) throws BaseException{
        System.out.println("[GET] directMessageList route");
        try{
            List<GetDirectMessageListRes> result = directMessageProvider.retrieveDirectMessageList(accountIdx);

            return new BaseResponse<>(result);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }
}
