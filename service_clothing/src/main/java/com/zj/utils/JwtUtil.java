package com.zj.utils;


import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {

    //定义20分钟
    private static long time = 1000*60*20;
    //签名信息
    private static String signature = "UserLogin";

    public static String createToken(String info){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //头信息 header
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")//算法
                //载荷 payload
                .claim("userId",info)
                // .claim("role","admin")
                //主题
                .setSubject("UserToken")//可以自定义
                //有效时间
                .setExpiration(new Date(System.currentTimeMillis()+time))//从当前开始30分钟后失效
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256,signature)
                //拼接
                .compact();
        return jwtToken;
    }

    /**
     * 获取token中注册信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenClaim(String token) {
        return Jwts.parser().setSigningKey(signature).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否过期失效
     * @param expirationTime
     * @return
     */
    public boolean isTokenExpired (Date expirationTime) {
        return expirationTime.before(new Date());
    }

    /**
     * 获取token失效时间
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return getTokenClaim(token).getExpiration();
    }

    /**
     * 获取用户名从token中
     */
    public static String getUsernameFromToken(String token) {
        return (String) getTokenClaim(token).get("userId");
    }

    /**
     * 获取jwt发布时间
     */
    public Date getIssuedAtDateFromToken(String token) {
        return getTokenClaim(token).getIssuedAt();
    }
}
