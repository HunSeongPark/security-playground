package com.hunseong.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hunseong.jwt.config.auth.PrincipalDetails;
import com.hunseong.jwt.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.hunseong.jwt.config.jwt.JwtProperties.*;

/**
 * Created by Hunseong on 2022/05/14
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter.attemptAuthentication");
        // username, password 받아옴
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            User user = objectMapper.readValue(request.getReader(), User.class);

            // 받아온 username, password로 token 받아옴
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // token 기반으로 AuthenticationManager를 통해 로그인 시도
            // Manager 내부에서 PrincipalDetailsService - loadUserByUsername() 수행
            // ID, PW 일치할 시 Authentication 객체 반환
            Authentication authenticate = authenticationManager.authenticate(token);
            System.out.println("authenticate = " + authenticate);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // attemptAuthentication 정상 수행 시 해당 메서드 수행
    // 여기서 JWT 토큰 만들어서 Header에 담아 응답
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("JwtAuthenticationFilter.successfulAuthentication");

        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();

        // JWT 토큰 생성
        String jwtToken = JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .withClaim("id", principal.getUser().getId())
                .withClaim("username", principal.getUsername())
                .sign(Algorithm.HMAC512(SECRET));

        // 헤더에 JWT 토큰 담아 응답 (Authorization : Bearer jwtToken)
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwtToken);
    }
}
