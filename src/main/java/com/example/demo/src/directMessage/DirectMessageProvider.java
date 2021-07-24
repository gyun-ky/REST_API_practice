package com.example.demo.src.directMessage;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.directMessage.model.GetDirectMessageListRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectMessageProvider {

    private final DirectMessageDao directMessageDao;

    private final JwtService jwtService;

    @Autowired
    public DirectMessageProvider(DirectMessageDao directMessageDao, JwtService jwtService) {
        this.directMessageDao = directMessageDao;
        this.jwtService = jwtService;
    }

    public List<GetDirectMessageListRes> retrieveDirectMessageList(int accountIdx) throws BaseException {

        try{
            List<GetDirectMessageListRes> result = directMessageDao.retrieveGetDirectMessageList(accountIdx);
            return result;
        }
        catch(Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
