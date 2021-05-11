package com.gdpu.homework.Entity.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {
    //设置过期时间,毫秒为单位,该到期时间是30分钟
    private static final long EXPIRE_DATE=1000*60*30;
    //token秘钥
    private static final String TOKEN_SECRET = "ZCfasfhuaUUHufguGuwu2020BQWE";
    public static String token(String username,String password,int root){
        String token = "";
        try{
            //到期时间为当前时间加30分钟
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            //密钥及加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String,Object> header = new HashMap<>();
            //标记token生成方法
            header.put("typ","JWT");
            //标记加密方式
            header.put("alg","HS256");
            //携带username,password信息，生成签名
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("username",username)
                    .withClaim("password",password)
                    .withClaim("root",root)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return  token;
    }
    //token校验方法
    public static boolean verify(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //如果解密成功，不会报错，则返回true，解密失败，报错返回false
            DecodedJWT jwt = verifier.verify(token);
            //解析出username
//            String username = jwt.getClaim("username").asString();

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    public static boolean root(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            //如果解密成功，不会报错，则返回true，解密失败，报错返回false
            DecodedJWT jwt = verifier.verify(token);
            //解析出root
           int  root = jwt.getClaim("root").asInt();
            if(root ==1)
            return true;
            else
                return  false;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
}

