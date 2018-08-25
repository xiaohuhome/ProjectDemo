package com.xiaohu.demo.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.xiaohu.demo.entity.user.User;

public interface UserRepository extends JpaRepository<User,String>{
	
}
