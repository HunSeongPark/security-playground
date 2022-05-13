package com.hunseong.basic.auth.oauth;

import com.hunseong.basic.auth.PrincipalDetails;
import com.hunseong.basic.auth.oauth.provider.GoogleOAuth2UserInfo;
import com.hunseong.basic.auth.oauth.provider.OAuth2UserInfo;
import com.hunseong.basic.domain.User;
import com.hunseong.basic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Created by Hunseong on 2022/05/14
 */
@Service
@RequiredArgsConstructor
public class PrincipalOAuth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    // 로그인 완료 후 Provider로부터 받은 OAuth2UserRequest 데이터에 대한 후처리
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // super.loadUser(userRequest)를 통해 OAuth2User 객체 받아옴
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // userRequest의 RegistrationId를 통해 Provider에 맞는 OAuth2UserInfo return
        OAuth2UserInfo oAuth2UserInfo = findInfoProvider(userRequest, oAuth2User);

        String password = passwordEncoder.encode("default");

        User user = userRepository.findByUsername(oAuth2UserInfo.getUsername()).orElse(null);

        // 새로운 회원일 시 OAuth2.0 정보를 활용하여 회원가입 진행
        if (user == null) {
            user = User.fromOAuth2UserInfo(oAuth2UserInfo, password);
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }

    private OAuth2UserInfo findInfoProvider(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        String provider = userRequest.getClientRegistration().getRegistrationId();

        if (provider.equals("google")) {
            return new GoogleOAuth2UserInfo(oAuth2User.getAttributes());
        } // TODO 새로운 Provider 추가 시 OAuth2UserInfo interface 구현체 추가

        return null;
    }
}
