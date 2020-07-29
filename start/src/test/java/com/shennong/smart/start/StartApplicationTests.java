package com.shennong.smart.start;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartApplicationTests {

	ApplicationContext applicationContext = new AnnotationConfigApplicationContext(StartApplicationTests.class);

	@Test
	public void contextLoads() {
		Map<Thread, StackTraceElement[]>  map = Thread.getAllStackTraces();
		map.get(Thread.currentThread());

		Thread.currentThread().getStackTrace();
	}

}
