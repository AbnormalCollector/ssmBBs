package com.sy.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JwtTokenUtil
 * @Description TODO
 * @Author Administrator
 * @Date: 2021/12/15 11:28
 * @Version 1.0
 */
@Component
public class JwtTokenUtil {

    private static String secretKey;
    //对时间间隔的抽像
    private static Duration accessTokenExpireTime;
    private static Duration refreshTokenExpireTime;
    private static Duration refreshTokenExpireAppTime;
    private static String issuer;

    public JwtTokenUtil() {
        this.secretKey = "78944878877848fg)";
        this.accessTokenExpireTime = Duration.parse("PT2H");//PT2H
        this.refreshTokenExpireTime = Duration.parse("PT8H");
        this.refreshTokenExpireAppTime = Duration.parse("P30D");
        this.issuer = "com.sy";

    }

    /**
     * 生成 access_token
     * 用于用户登录成功后,向用户签发令牌
     *
     * @param subject
     * @param claims
     * @return
     */
    public String getAccessToken(String subject, Map<String, Object> claims) {

        return generateToken(issuer, subject, claims, accessTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 生产 App端 refresh_token
     */
    public String getRefreshAppToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, refreshTokenExpireAppTime.toMillis(), secretKey);
    }

    /**
     * 生产 PC refresh_token
     */
    public String getRefreshToken(String subject, Map<String, Object> claims) {
        return generateToken(issuer, subject, claims, refreshTokenExpireTime.toMillis(), secretKey);
    }

    /**
     * 签发token
     *
     * @param issuer    签发人
     * @param subject   代表这个JWT的主体，即它的所有人 一般是用户id
     * @param claims    存储在JWT里面的信息 一般放些用户的权限/角色信息
     * @param ttlMillis 有效时间(毫秒)
     * @return java.lang.String
     * @throws
     */
    public String generateToken(String issuer, String subject, Map<String, Object> claims, long ttlMillis, String secret) {

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;//使用的加密算法

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        byte[] signingKey = DatatypeConverter.parseBase64Binary(secret);

        JwtBuilder builder = Jwts.builder();
        //payload
        if (null != claims) {
            builder.setClaims(claims);
        }
        //subject(一般是用户id)
        if (!StringUtils.isEmpty(subject)) {
            builder.setSubject(subject);
        }
        //issuer签发者
        if (!StringUtils.isEmpty(issuer)) {
            builder.setIssuer(issuer);
        }
        //过期开始时间
        builder.setIssuedAt(now);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            //过期结束时间
            builder.setExpiration(exp);
        }
        //使用算法进行签名
        builder.signWith(signatureAlgorithm, signingKey);
        //生成JWT
        return builder.compact();
    }

    /**
     * 获取用户id是subject
     */
    public String getUserId(String token) {
        String userId = null;
        try {
            Claims claims = getClaimsFromToken(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            //log.error("eror={}",e);
            e.printStackTrace();
        }
        return userId;
    }


    /**
     * 获取用户名
     */
    public String getUserName(String token) {

        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = (String) claims.get(JwtConstant.JWT_USER_NAME);
        } catch (Exception e) {
            //log.error("eror={}",e);
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 从令牌中获取数据声明
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey)).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            claims = null;
        }
        return claims;
    }

    /**
     * 校验令牌
     */
    public Boolean validateToken(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        return (null != claimsFromToken && !isTokenExpired(token));
    }

    /**
     * 验证token 是否过期
     */
    public Boolean isTokenExpired(String token) {

        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            //log.error("error={}",e);
            //e.printStackTrace();
            return true;
        }
    }

    /**
     * 刷新token
     * 如用户信息进行改变，则token中用户信息也应该刷新，则需刷新token，把最新的用户信息放入token中，且过期时间重新计算
     */
    public String refreshToken(String refreshToken, Map<String, Object> claims) {
        String refreshedToken;
        try {
            System.out.println(refreshToken);
            Claims parserclaims = getClaimsFromToken(refreshToken);
            System.out.println(parserclaims);
            /**
             * 刷新token的时候如果为空说明原先的 用户信息不变 所以就引用上个token里的内容
             */
            if (null == claims) {
                claims = parserclaims;
            }
            //10:00  1
            //12:00  1;
            refreshedToken = generateToken(parserclaims.getIssuer(), parserclaims.getSubject(), claims, accessTokenExpireTime.toMillis(), secretKey);
        } catch (Exception e) {
            refreshedToken = null;
            //log.error("error={}",e);
            e.printStackTrace();
        }
        return refreshedToken;
    }

    /**
     * 获取token的剩余过期时间
     */
    public long getRemainingTime(String token) {
        long result = 0;
        try {
            long nowMillis = System.currentTimeMillis();
            result = getClaimsFromToken(token).getExpiration().getTime() - nowMillis;
        } catch (Exception e) {
            //log.error("error={}",e);
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {

        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        //payload = subject+claims
        String userid = "1";

        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtConstant.JWT_USER_NAME, "jack");
        claims.put("role", "admin");

        String accessToken = jwtTokenUtil.getAccessToken(userid, claims);
        System.out.println(accessToken);

        String badToken = "eyJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiJqyWNrIiwicm9sZSI6ImFkbWluIiwiaXNzIjoiY29tLnN5IiwiZXhwIjoxNjM5NTQ2ODA4LCJpYXQiOjE2Mzk1Mzk2MDh9" +
                ".nMwF4ujH1IKc7fzJMDGZwkeKmt0Ug3g-y1b0Dz5ESVk";
        Boolean aBoolean = jwtTokenUtil.validateToken(accessToken);
        System.out.println(aBoolean);
        String userName = jwtTokenUtil.getUserName(accessToken);
        System.out.println(userName);
        String userId = jwtTokenUtil.getUserId(accessToken);
        System.out.println(userId);
    }

}
