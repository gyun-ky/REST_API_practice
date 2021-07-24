package com.example.demo.src.feed;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.feed.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedProvider {

    private final FeedDao feedDao;

    private final JwtService jwtService;

    @Autowired

    public FeedProvider(FeedDao feedDao, JwtService jwtService) {
        this.feedDao = feedDao;
        this.jwtService = jwtService;
    }

    public GetFeedRes retrieveFeed(int accountIdx) throws BaseException {
        try{
            List<Post> posts = feedDao.retrievePost(accountIdx);
            List<PostImage> postImages = feedDao.retrievePostImage(accountIdx);
            int pidx = 0;
            for(int i=0; i<postImages.size(); i++) {
                PostImage image = postImages.get(i);
                System.out.println(postImages.get(i).getPostImageUrl());
                if (image.getPostIdx() == posts.get(pidx).getIdx()) {
                    posts.get(pidx).setPostImage(image);
                } else {
                    posts.get(++pidx).setPostImage(image);
                }
            }
            UserInfo userInfo = feedDao.retrieveUserInfo(accountIdx);
            List<Story> stories = feedDao.retrieveStory(accountIdx);

            GetFeedRes result = new GetFeedRes(userInfo, stories ,posts);
            System.out.println("[PROVIDER] GetFeedRes complete");
            return result;
        }
        catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
