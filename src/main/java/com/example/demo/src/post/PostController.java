package com.example.demo.src.post;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.feed.model.GetFeedRes;
import com.example.demo.src.feed.model.Post;
import com.example.demo.src.post.model.*;
import com.example.demo.src.user.UserProvider;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/app/posts")
public class PostController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostProvider postProvider;

    private final PostService postService;

    private final UserProvider userProvider;

    private final JwtService jwtService;

    public PostController(PostProvider postProvider, PostService postService, UserProvider userProvider, JwtService jwtService) {
        this.postProvider = postProvider;
        this.postService = postService;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/comments/{postIdx}")
    public BaseResponse<GetCommentsRes> retrieveComments(@PathVariable int postIdx, @RequestParam int accountIdx) throws BaseException {
        System.out.println("[GET] retrieveComments route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            GetCommentsRes result = postProvider.retrieveComments(postIdx);
            return new BaseResponse<>(result);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/comments/{postIdx}")
    public BaseResponse<PostCommentRes> createPostComment(@RequestParam(required = true) int accountIdx, @RequestParam(required = false) boolean recomment, @PathVariable int postIdx, @RequestBody PostCommentReq postCommentReq) throws BaseException{
        System.out.println("[POST] createPostComment route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            PostCommentRes postCommentRes = postService.createPostComment(recomment, postIdx, postCommentReq, accountIdx);
            return new BaseResponse<>(postCommentRes);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }

    @ResponseBody
    @PatchMapping("/comments/{commentIdx}")
    public BaseResponse<PatchCommentsRes> updatePostComment(@PathVariable int commentIdx, @RequestParam int accountIdx, @RequestBody PatchCommentReq patchCommentReq) throws BaseException{

        System.out.println("[PATCH] updatePostComment route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            PatchCommentsRes result = postService.updatePostComment(commentIdx, accountIdx, patchCommentReq);
            return new BaseResponse<>(result);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }

    @ResponseBody
    @DeleteMapping("/comments/{commentIdx}")
    public BaseResponse<PatchCommentsRes> updateDeleteComment(@PathVariable int commentIdx, @RequestParam int accountIdx) throws BaseException{

        System.out.println("[DELETE] updatePostComment route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

        try{
            PatchCommentsRes result = postService.deletePostComment(commentIdx, accountIdx);
            return new BaseResponse<>(result);
        }
        catch (BaseException e){
            throw new BaseException(e.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostPostRes> createPost(@RequestParam int accountIdx, @RequestBody PostPostReq postPostReq) throws BaseException{

        System.out.println("[POST] createPost route");

        int userIdxByJwt = jwtService.getUserIdx();
        int userIdxByPath = userProvider.getUserIdxByAccountIdx(accountIdx);
        if(userIdxByJwt != userIdxByPath){
            return new BaseResponse<>(BaseResponseStatus.INVALID_USER_JWT);
        }

//        try{
//            return new BaseResponse<>(new PostPostRes(1));
//        }
//        catch(BaseException e){
//            throw new BaseException(e.getStatus());
//        }
        return new BaseResponse<>(new PostPostRes(1));
    }
}
