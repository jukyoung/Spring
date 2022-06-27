package kh.board.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAO {
 @Autowired
 private BasicDataSource bds;
 
 // 내정보 수정
 public int modify(String nickname, String id) throws Exception{
	 String sql = "update member set nickname =? where id=?";
	 try(Connection con = bds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, nickname);
		 pstmt.setString(2, id);
		 return pstmt.executeUpdate();
	 }
 }
 // 프로필 변경 요청
 public int modifyProfile(MemberDTO dto)throws Exception{
	 String sql = "update member set profile_message = ?, profile_image =? where id=?";
	 try(Connection con = bds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, dto.getProfile_message());
		 pstmt.setString(2, dto.getProfile_image());
		 pstmt.setString(3, dto.getId());
		 
		 return pstmt.executeUpdate();
		 
	 }
 }
 
 // 로그인 요청
 public MemberDTO isLoginOk(String id, String pw) throws Exception{
	 String sql = "select * from member where id=? and pw=?";
	 try(Connection con = bds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);){
		
		 pstmt.setString(1, id);
		 pstmt.setString(2, pw);
		 
		 ResultSet rs = pstmt.executeQuery();
		
		 if(rs.next()) {
			String id1 = rs.getString("id");
			String pw1 = rs.getString("pw");
			String nickname = rs.getString("nickname");
			String profile_message = rs.getString("profile_message");
			String profile_image = rs.getString("profile_image");
			
			return new MemberDTO(id1, pw1, nickname, profile_message, profile_image);
		 }else {
			 return null;
		 }
		 
	 }
 }
 
 // 회원가입
 public int insert(MemberDTO dto) throws Exception{
	 String sql = "insert into member values(?, ?, ?, null, ?)";
	 try(Connection con = bds.getConnection();
		 PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, dto.getId());
		 pstmt.setString(2, dto.getPw());
		 pstmt.setString(3, dto.getNickname());
		 pstmt.setString(4, dto.getProfile_image());
		 
		 return pstmt.executeUpdate();
	 }
 }
 // 아이디 중복검사
 public boolean checkId(String id) throws Exception{
	 String sql = "select count(*) from member where id=?";
	 try(Connection con = bds.getConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);){
		 
		 pstmt.setString(1, id);
		 ResultSet rs = pstmt.executeQuery();
		 int result = 0;
		 if(rs.next()) {
			 result = rs.getInt(1);
		 }
		 if(result == 0) { //중복 x
			 return true;
		 }else { // 중복 o
			 return false;
		 }
	 }
 }
}
