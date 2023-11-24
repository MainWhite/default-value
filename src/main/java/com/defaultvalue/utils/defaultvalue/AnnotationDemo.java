package com.defaultvalue.utils.defaultvalue;

import com.defaultvalue.utils.defaultvalue.annotation.DefaultValue;
import org.springframework.stereotype.Component;

/**
 * 测试类
 * @author 白
 */
@Component
public class AnnotationDemo {

	private final String demoName="demo";

	@DefaultValue({"白","18"})
	public void demo(String name,Integer age,AnnotationDemo annotationDemo,String name2,Integer age2,String demo) {
		System.out.println(name+"  "+age+"  "+annotationDemo.demoName+"  "+name2+"  "+age2);
		System.out.println(demo);
	}
}
