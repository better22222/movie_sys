package com.system.util;

import io.jsonwebtoken.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import java.util.Date;
@Data
@Slf4j
@Component
@PropertySource("classpath:config/token.properties")
@ConfigurationProperties(prefix="jwt")
public class JwtUtils {
    private long expire;   //过期的时间
    private String secret;  //签名
    private String header;  //响应头 key (header) - value（token）   token:shfjksdhfdskjfh.dfhjsdhfjsd.shjdsfkjdshf

    // 生成jwt
    public String generateToken(String username) {
        Date nowDate = new Date();
        //过期时间expireDate
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)  // 7天過期
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    // 解析jwt
    public Claims getClaimByToken(String jwt) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
        return claims;
    }

    // jwt是否过期
    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
