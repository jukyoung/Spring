package com.message.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.message.dto.MessageDTO;

// root-context.xml -> BasicDataSource형 인스턴스가 생성
// MessageDAO 형 인스턴스 생성 (생성될 당시에는 bds라는 멤버필드는 null)
// @Autowired 달려있는 멤버필드는 스프링 컨테이너 안에서 해당 자료형의 인스턴스를 찾아서
//    그 멤퍼필드에 값을 셋팅해주는 작업을 알아서 해주는 어노테이션

// @Repository : 직접적으로 DB에 데이터를 전달/수정/삭제/조회 하는 클래스에 붙여주는 어노테이션
@Repository
public class MessageDAO {
	@Autowired
	private BasicDataSource bds;
	
	public MessageDAO() {
		System.out.println("Message DAO 인스턴스 생성");
	}
	public int modify(MessageDTO dto) throws Exception{//데이터 수정
		String sql = "update msg set nickname=?, message = ? where no = ?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getNickname());
			pstmt.setString(2, dto.getMessage());
			pstmt.setInt(3, dto.getNo());
			
			return pstmt.executeUpdate();
		}
	}
	// no을 이용해 한 행의 데이터를 가져오는 select
	public MessageDTO selectByNo(int no) throws Exception{
		String sql = "select * from msg where no =?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
				
				pstmt.setInt(1, no);
				ResultSet rs = pstmt.executeQuery();
				if(rs.next()) {
					return new MessageDTO(no, rs.getString("nickname"), rs.getString("message"));
				}
				return null;
			}
		
	}
	public int delete(int no) throws Exception{ // 메세지 삭제
		String sql = "delete from msg where no = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}
	public int insert(MessageDTO dto) throws Exception{ // 메세지 삽입
		String sql = "insert into msg values(seq_msg.nextval,?,?)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, dto.getNickname());
			pstmt.setString(2, dto.getMessage());
			
			return pstmt.executeUpdate();
		}
	}
	
	public ArrayList<MessageDTO> selectAll() throws Exception{ // 전체 메세지 조회
		String sql = "select * from msg";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<MessageDTO> list = new ArrayList<>();
			while(rs.next()) {
				int no = rs.getInt("no");
				String nickname = rs.getString("nickname");
				String message = rs.getString("message");
				list.add(new MessageDTO(no, nickname, message));
			}
			return list;			
		}
	}
	
}
