package com.kh.intro;

public class BeanFactory {
	public Laptop getBean(String name){
		if(name.equals("lg")) {//lg 인스턴스 반환
			return new LGLaptop();
		}else if(name.equals("samsung")) {//samsung 인스턴스 반환
			return new SamsungLaptop();
		}
		return null;
		// return 타입이 다름 -> LGLaptop(); SamsungLaptop();
		// 두개를 다 받는 부모 클래스인 Laptop 클래스를 반환타입으로 쓰기
	}
}
