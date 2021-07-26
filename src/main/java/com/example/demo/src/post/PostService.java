package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostDao postDao;

    private final JwtService jwtService;

    @Autowired

    public PostService(PostDao postDao, JwtService jwtService) {
        this.postDao = postDao;
        this.jwtService = jwtService;
    }

    public PostCommentRes createPostComment(boolean recomment, int postIdx, PostCommentReq postCommentReq, int accountIdx) throws BaseException {
        try{
            int commentIdx = postDao.createPostComment(recomment, postIdx, postCommentReq, accountIdx);

            //recomment가 있다면 recommentTo 값이 존재하는 값인지 valid 필요

            return new PostCommentRes(commentIdx);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public PatchCommentsRes updatePostComment(int commentIdx, int accountIdx, PatchCommentReq patchCommentReq) throws BaseException{

        //댓글 작성자인지 확인
        if(postDao.checkCommentWriter(commentIdx, accountIdx) != true){
            throw new BaseException(BaseResponseStatus.INVALID_USER_COMMENT);
        }

        try{
            int idx = postDao.updatePostComment(commentIdx, patchCommentReq);
            return new PatchCommentsRes(idx);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public PatchCommentsRes deletePostComment(int commentIdx, int accountIdx) throws BaseException{

        //댓글 작성자인지 확인
        if(postDao.checkCommentWriter(commentIdx, accountIdx) != true){
            throw new BaseException(BaseResponseStatus.INVALID_USER_COMMENT);
        }

        try{
            int idx = postDao.deletePostComment(commentIdx);
            return new PatchCommentsRes(idx);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public PostPostRes createPost(int accountIdx, PostPostReq postPostReq) throws BaseException{
        System.out.println("[SERVICE] createPost service");
        try {
            //locationTag 미리 존재하는지 점검
            //1. 존재하면 idx 가져오고
            //2. 존재하지 않으면 새로 만든 후 idx 가져온다
            int locationTagIdx = postDao.checkLocationTag(postPostReq.getLocationTag());

            int postIdx = postDao.createPost(accountIdx, locationTagIdx, postPostReq);

            postDao.createHashTag(postIdx, postPostReq);

            return new PostPostRes(postIdx);
        }
        catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
