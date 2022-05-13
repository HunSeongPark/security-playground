package com.hunseong.jwt.controller;

import com.hunseong.jwt.domain.Role;
import com.hunseong.jwt.domain.User;
import com.hunseong.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hunseong on 2022/05/14
 */
@RequiredArgsConstructor
@RestController
public class BaseApiController {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @GetMapping("/home")
    public String home() {
        return "<h1>home</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        return "회원가입 완료";
    }

    // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN 접근 가능
    @GetMapping("/api/v1/user")
    public String user() {
        return "user";
    }

    // ROLE_MANAGER, ROLE_ADMIN 접근 가능
    @GetMapping("/api/v1/manager")
    public String manager() {
        return "manager";
    }

    // ROLE_ADMIN 접근 가능
    @GetMapping("/api/v1/admin")
    public String admin() {
        return "admin";
    }
}
