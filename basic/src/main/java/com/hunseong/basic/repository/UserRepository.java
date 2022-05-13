package com.hunseong.basic.repository;

import com.hunseong.basic.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Hunseong on 2022/05/13
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
