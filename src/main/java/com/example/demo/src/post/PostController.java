package com.example.demo.src.post;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PostProvider postProvider;

    private final JwtService jwtService;

    @Autowired

    public PostController(PostProvider postProvider, JwtService jwtService) {
        this.postProvider = postProvider;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @GetMapping("/{postIdx}")
    public Base
}
