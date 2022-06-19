package com.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.student.dto.StudentDTO;
@Repository
public class StudentDAO {
	@Autowired
	private BasicDataSource bds;
	
	public int modify(StudentDTO dto) throws Exception{//데이터 수정
		String sql = "update student set name=?, memo = ? where no = ?";
		try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getMemo());
			pstmt.setInt(3, dto.getNo());
			
			return pstmt.executeUpdate();
		}
	}
	
	public StudentDTO selectByNo(int no) throws Exception{
		String sql = "select * from student where no = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, no);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return new StudentDTO(no, rs.getString("name"), rs.getString("memo"));
			}
			return null;
		}
	}
	public int delete(int no) throws Exception{ //삭제
		String sql = "delete from student where no = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}
	public int insert(StudentDTO dto) throws Exception{
		String sql = "insert into student values(seq_std.nextval,?,?)";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getMemo());
			
			return pstmt.executeUpdate();
		}
	}
	
	public ArrayList<StudentDTO> selectAll() throws Exception{
		String sql = "select * from student";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			
			ArrayList<StudentDTO> list = new ArrayList<>();
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String memo = rs.getString("memo");
				
				list.add(new StudentDTO(no,name, memo));
			}
			return list;
		}
	}
}
