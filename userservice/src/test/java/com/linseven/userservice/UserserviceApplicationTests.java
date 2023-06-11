package com.linseven.userservice;


import com.linseven.userservice.dao.UserMapper;
import com.linseven.IMServerInfo;
import com.linseven.userservice.model.UserPO;
import com.linseven.userservice.service.IMServerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserserviceApplication.class})
public class UserserviceApplicationTests {

	@Autowired
	UserMapper userMapper;

    @Autowired
	private IMServerInfoService imServerInfoService;

	@Test
	public void findByUsername() throws Exception {

		UserPO userPO = userMapper.findByUsername("test");
		log.info("{}",userPO);
		IMServerInfo imServerInfo = imServerInfoService.getServerInfo("127.0.0.1","123");
        log.info("{}",imServerInfo);
        new ThreadLocal().get();
	}

	@Test
	public void addUser(){

		for(int i=1000;i<10000;i++){

			UserPO userPO = new UserPO();
			String password = new BCryptPasswordEncoder().encode("123456");
			userPO.setPassword(password);
			userPO.setUsername(i+1000+"");
			userMapper.addUser(userPO);
		}
	}






}

