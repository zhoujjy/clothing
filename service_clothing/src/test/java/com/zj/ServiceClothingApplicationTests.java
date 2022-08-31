package com.zj;

import com.zj.entity.User;
import com.zj.service.InboundDetailService;
import com.zj.service.InboundService;
import com.zj.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;


@SpringBootTest
class ServiceClothingApplicationTests {
    @Autowired
    private InboundService inboundDetailService;
    @Autowired
    private RedisUtils redisUtils;
    @Test
    void contextLoads() {
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }

}
