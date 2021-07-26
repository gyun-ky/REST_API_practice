package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.feed.model.GetFeedRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/feeds")
public class FeedController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FeedProvider feedProvider;

    private final JwtService jwtService;

    private final UserProvider userProvider;

    @Autowired
    public FeedController(FeedProvider feedProvider, JwtService jwtService, UserProvider userProvider) {
        this.feedProvider = feedProvider;
        this.jwtService = jwtService;
        this.userProvider = userProvider;
    }

    @ResponseBody
    @GetMapping("/{accountIdx}")
    public BaseResponse<GetFeedRes> retrieveFeed(@PathVariable int accountIdx) throws BaseException {
        System.out.println("[GET] retrieveFeed route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            GetFeedRes result = feedProvider.retrieveFeed(accountIdx);
            return new BaseResponse<>(result);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }
}
