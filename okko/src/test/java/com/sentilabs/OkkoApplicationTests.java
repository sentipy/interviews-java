package com.sentilabs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OkkoApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
    private Environment environment;

	@Test
	public void contextLoads() {
	    environment.getActiveProfiles();
	}

}
