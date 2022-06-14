package com.kh.intro;

public class SamsungLaptop extends Laptop { // Laptop 클래스를 상속받음
	private int price;
	
	public SamsungLaptop() { 
		  System.out.println("삼성 노트북 인스턴스 생성"); 
		  }
	 
	public SamsungLaptop(int price) {
		System.out.println("생성자 주입");
		this.price = price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getPrice() {
		return this.price;
	}
	 
}
