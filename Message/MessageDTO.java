package com.message.dto;

public class MessageDTO {
	private int no;
	private String nickname;
	private String message;

	public MessageDTO() {
	}

	public MessageDTO(int no, String nickname, String message) {
		super();
		this.no = no;
		this.nickname = nickname;
		this.message = message;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return no + " : " + nickname + " : " + message;
	}

}
