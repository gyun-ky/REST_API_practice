package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.Comment;
import com.example.demo.src.post.model.GetCommentsRes;
import com.example.demo.src.post.model.PostInfo;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostProvider {

    private final PostDao postDao;

    private final JwtService jwtService;

    @Autowired
    public PostProvider(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
    }

    public GetCommentsRes retrieveComments(int postIdx) throws BaseException {
        try{
            List<Comment> comments = postDao.retrieveComments(postIdx);
            PostInfo postInfo = postDao.retrievePostInfo(postIdx);
            return new GetCommentsRes(postInfo, comments);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }


}
