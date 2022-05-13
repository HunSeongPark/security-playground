package com.hunseong.basic.auth;

import com.hunseong.basic.domain.User;
import com.hunseong.basic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Hunseong on 2022/05/14
 * loginProcessingUrl("/login") 시 해당 class의 loadUserByUsername 메서드가 실행됨
 */
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Security Session에 return 객체 Authentication(PrincipalDetails) 저장
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        // Username이 repository 내에 존재할 시
        if (user != null) {
            return new PrincipalDetails(user);
        }

        return null;
    }
}
