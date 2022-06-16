package com.student.dto;

public class StudentDTO {
	private String name;
	private String memo;
	
	public StudentDTO() {}
	public StudentDTO(String name, String memo) {
		super();
		this.name = name;
		this.memo = memo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	@Override
	public String toString() {
		return name + " : " + memo;
	}
	
	
}
