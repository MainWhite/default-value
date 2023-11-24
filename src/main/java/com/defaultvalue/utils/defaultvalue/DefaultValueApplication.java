package com.defaultvalue.utils.defaultvalue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 白
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class DefaultValueApplication implements CommandLineRunner {

	@Autowired
	public AnnotationDemo annotationDemo;

	public static void main(String[] args) {
		SpringApplication.run(DefaultValueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		annotationDemo.demo("bai",10,null,null,null,null);
	}
}
