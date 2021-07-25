package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.post.model.PatchCommentReq;
import com.example.demo.src.post.model.PatchCommentsRes;
import com.example.demo.src.post.model.PostCommentReq;
import com.example.demo.src.post.model.PostCommentRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
