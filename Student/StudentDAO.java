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
	
	public int insert(StudentDTO dto) throws Exception{
		String sql = "insert into student values(?,?)";
		
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
				String name = rs.getString("name");
				String memo = rs.getString("memo");
				
				list.add(new StudentDTO(name, memo));
			}
			return list;
		}
	}
}
