package com.kh.intro;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Run {
	public static void main(String[] args) {
		// 우리 회사가 Samsung 계약
		// Laptop 생산
//		SamsungLaptop samsung = new SamsungLaptop();
//		samsung.powerOn();
//		samsung.powerOff();
//		samsung.volumeUp();
//		samsung.volumeDown();
//		
//		// 우리 회사가 Samsung이 아닌 LG랑 계약
//		// LG Laptop 생산
//		LGLaptop lg = new LGLaptop();
//		lg.powerOn();
//		lg.powerOff();
//		lg.volumeUp();
//		lg.volumeDown();
		
		// 다형성 : 부모 클래스 참조변수가 자식 클래스 인스턴스를 담을 수 있는 성질
		//Laptop laptop = new SamsungLaptop();
//		Laptop laptop = new LGLaptop();
//		laptop.powerOn();
//		laptop.powerOff();
//		laptop.volumeUp();
//		laptop.volumeDown();
		
		// 팩토리 패턴(factory) : 공장
		// 인스턴스의 생성은 팩토리 클래스가 담당하고, 개발자인 우리는 어떤 인스턴스를
		// 생성할지에 대한 값만 넘겨서 인스턴스를 반환받는 형식
		
//		BeanFactory factory = new BeanFactory();
//		Laptop laptop = factory.getBean("lg");
//		Laptop laptop = factory.getBean("samsung");
//		laptop.powerOn();
//		laptop.powerOff();
//		laptop.volumeUp();
//		laptop.volumeDown();
		
		AbstractApplicationContext factory = new GenericXmlApplicationContext("applicationContext.xml");
		// 인자값에 넘어온 설정파일을 불러옴으로써 그 안에 있는 bean 태그가 있다면 그 인스턴스를 불러옴 그리고 참조변수에 담아줌
		/* bean(인스턴스) 사용법
		 * DL(Dependency Lookup) : 스프링 컨테이너에서 알아서 생성해준 인스턴스들을 가져다 사용하는 것
		 * */
		 //Laptop laptop1 = (Laptop)factory.getBean("samsung"); //getBean(id값)
		 // samsung 은 SamsungLaptop 형이므로 부모인 Laptop 형으로 받아주고 factory는 object 이므로 형변환해주기
		SamsungLaptop laptop1 = (SamsungLaptop)factory.getBean("samsung"); //getBean(id값)
		System.out.println(laptop1.getPrice());
		// -> 주입한 value 값 잘 나옴
//		 laptop1.powerOn();
//		 laptop1.powerOff();
//		 laptop1.volumeUp();
//		 laptop1.volumeDown();
//		 
//		 // 싱글턴이기 때문에 생성자도 딱 한번만 만들어짐
//		 Laptop laptop2 = (Laptop)factory.getBean("samsung");
	}

}
