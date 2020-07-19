package com.xb.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
	
	private static final long EXPIRE_TIME = 30 * 60 * 1000;
	private static final String TOKEN_SECRET = "xingbo";
	
	/**
	 * 生成签名，30分钟过期
	 * @param **username**
	* @param **password**
	* @return
	 */
	public static String sign(Long id) {
	    try {
	        // 设置过期时间
	        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
	        // 私钥和加密算法
	        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	        // 设置头部信息
	        Map<String, Object> header = new HashMap<>(2);
	        header.put("Type", "Jwt");
	        header.put("alg", "HS256");
	        // 返回token字符串
	        return JWT.create()
	                .withHeader(header)
	                .withClaim("userId", id)
	                .withExpiresAt(date)
	                .sign(algorithm);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}

	public static void main(String[] args) {
		//System.out.println(JwtUtil.sign("123","abc"));;
		System.out.println(JwtUtil.verify("eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKd3QiLCJ0eXAiOiJKV1QifQ.eyJwYXNzd29yZCI6ImFiYyIsImV4cCI6MTU5NTE2MDY5OSwidXNlcm5hbWUiOiIxMjMifQ.Wj1t0oSWKQHGLRnmJy6jjL5VaEydFmlhs4Xslvei7wg"));
	}
	
	/**
	 * 检验token是否正确
	 * @param **token**
	* @return
	 */
	public static UserTokenModel verify(String token){
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	        JWTVerifier verifier = JWT.require(algorithm).build();
	        DecodedJWT jwt = verifier.verify(token);
			Map<String, Claim> map = jwt.getClaims();
			Long userId = null;
			if (!StringUtils.isEmpty(map.get("userId"))){
				userId = map.get("userId").asLong();
			}
	        return new UserTokenModel().setId(userId);
	    } catch (Exception e){
	        return null;
	    }
	}
	
}
