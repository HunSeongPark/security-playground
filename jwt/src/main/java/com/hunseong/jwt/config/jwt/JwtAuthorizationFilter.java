package com.hunseong.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.hunseong.jwt.config.auth.PrincipalDetails;
import com.hunseong.jwt.domain.User;
import com.hunseong.jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.hunseong.jwt.config.jwt.JwtProperties.*;

/**
 * Created by Hunseong on 2022/05/14
 * 권한, 인증이 필요한 주소 요청 시 해당 필터 동작
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Authorization Header 값 받아옴 (JWT 토큰이 포함된 헤더값)
        String header = request.getHeader(HEADER_STRING);

        // Header 존재 여부, JWT 토큰 존재 여부 확인
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증
        String jwtToken = header.replace(TOKEN_PREFIX, "");

        String username = JWT.require(Algorithm.HMAC512(SECRET))
                .build()
                .verify(jwtToken)
                .getClaim("username")
                .asString();

        // 값이 존재할 시 검증 완료
        if (username != null) {
            User user = userRepository.findByUsername(username).orElse(null);

            // 권한 확인을 위해 Authentication 객체를 세션에 저장
            PrincipalDetails principalDetails = new PrincipalDetails(user);

            // null 부분은 비밀번호가 들어가야 하나, 이미 JWT 토큰을 통해 검증이 완료되었으므로 null로 처리
            Authentication authentication
                    = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 시큐리티 세션에 접근하여 Authentication 객체를 세션에 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
    }
}
