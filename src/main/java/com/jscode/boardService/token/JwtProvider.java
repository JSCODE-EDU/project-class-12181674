package com.jscode.boardService.token;

import com.jscode.boardService.domain.Member;
import com.jscode.boardService.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collections;
import java.util.Date;

import static com.jscode.boardService.domain.ExceptionMessageConst.NOT_EXIST_EMAIL;
import static com.jscode.boardService.domain.ExceptionMessageConst.WRONG_TOKEN;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    // 만료시간 : 30 분
    private final long exp = 1000L * 60 * 30;

    private final MemberRepository memberRepository;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(Member member) {
        Claims claims = Jwts.claims().setSubject(member.getEmail());
        claims.put("memberId", member.getId());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)  // 정보저장
                .setIssuedAt(now)   // 토큰 발행시간 정보
                .setExpiration(new Date(now.getTime() + exp))   // 토큰 유효시각 설정
                .signWith(secretKey, SignatureAlgorithm.HS256)  //암호화 알고리즘, key 값
                .compact();
    }

    // 권한정보 획득
    // Spring Security 인증과정에서 권한확인을 위한 기능
    public Authentication getAuthentication(String token) {
        Member member = memberRepository.findMemberByEmail(this.getEmail(token)).orElseThrow(
            () -> new IllegalArgumentException(NOT_EXIST_EMAIL.getMessage())
        );
        return new UsernamePasswordAuthenticationToken(member, "", Collections.emptyList());
    }

    // 토큰에 담겨있는 유저 정보 획득
    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 토큰에 담겨있는 유저 id(pk) 획득
    public Long getMemberId(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get("memberId", Long.class);
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        // Bearer 검증
        if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
            throw new IllegalAccessError(WRONG_TOKEN.getMessage());
        }

        // 토큰에서 Bearer 접두사 제거 및 앞뒤 공백 제거
        token = token.split(" ")[1].trim();
        return token;
    }

    // 토큰 검증 (유효성, 만료일자 파악)
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
