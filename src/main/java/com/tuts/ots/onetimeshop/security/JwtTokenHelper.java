package com.tuts.ots.onetimeshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenHelper   {

    public static final long JWT_TOKEN_VALIDAITY=5*60*60;

    private String secret = "jwtTokenKey";

    //retrieve username jwt token
    public String getUsernameFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }

    //retrieve Expiration date jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token,Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims =getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information form token we will need the secret key
    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check is token has expired
    private Boolean isTokenExpried(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //genrate Token for user
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    //while creating  the token
    //1.define claims of the token, like Issuer,Expiration subject , and the ID
    //2.Sign the JWT  using the HS512 algorithm and secret key
    //3.
    //compaction of the JWT to  a URL-safe string

    private  String doGenerateToken(Map<String,Object> claims,String subject){
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ JWT_TOKEN_VALIDAITY + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }
    //validate token
    public Boolean validateToken(String token,UserDetails userDetails){
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpried(token) );
    }


}
