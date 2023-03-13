package com.linseven.userservice;


import com.linseven.userservice.dao.UserMapper;
import com.linseven.userservice.model.UserPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserserviceApplication.class})
public class UserserviceApplicationTests {

	@Autowired
	UserMapper userMapper;



	@Test
	public void findByUsername(){

		UserPO userPO = userMapper.findByUsername("test");
		log.info("{}",userPO);
	}

}
