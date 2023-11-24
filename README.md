# 参数默认值
## 介绍
此项目是**springboot**工程 作用在方法上的注解

当传的实参为null时会使用value属性的值

如果默认值数量超过了实参数量并且实参为null时会赋值实参null

当遇到自定义类型时(对象类型)底层会new这个对象

此外在使用基本数据类型作为参数时 请使用他们的包装类型 这点后续版本会优化

**并且会更新此注解写在参数中的写法**

**这可能会比当前版本的用法更方便 并且代码可读性更高**

***

## 用法
```java
@Component
public class AnnotationDemo {

	private final String demoName="demo";

	@DefaultValue({"白","18"})
	public void demo(String name,Integer age,AnnotationDemo annotationDemo,String name2,Integer age2,String demo) {
		System.out.println(name+"  "+age+"  "+annotationDemo.demoName+"  "+name2+"  "+age2);
		System.out.println(demo);
	}
}

// 调用例子
annotationDemo.demo("bai",10,null,null,null,null);
// 输出结果
bai  10  demo  白  18
null
```

**最后该项目仅供学习 所有代码均有注释有详细讲解 请自行查看**

**主要代码在com/defaultvalue/utils/defaultvalue/aop/aspectMyAnnotation.java目录下 基于spring的aop机制**
