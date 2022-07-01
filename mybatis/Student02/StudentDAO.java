package kh.student.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.student.dto.StudentDTO;

@Repository
public class StudentDAO {
	@Autowired
	private SqlSession session;
	
	
	public void deleteCheck(int[] no)throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("deleteArr", no);
		session.delete("studentMapper.deleteCheck", map);
	}
	
	public void insert(StudentDTO dto) throws Exception{
		session.insert("studentMapper.insert", dto);
	}
	
	public List<StudentDTO> selectList() throws Exception{
		return session.selectList("studentMapper.selectList");
	}
	
	public void delete(int no) throws Exception{
		session.delete("studentMapper.delete", no);
	}
	
	public StudentDTO selectOne(int no) throws Exception{
		return session.selectOne("studentMapper.selectOne", no);
	}
	
	public void update(StudentDTO dto) throws Exception{
		session.update("studentMapper.update", dto);
	}
	public List<StudentDTO> search(String category, String keyword) throws Exception{
		Map<String, String> map = new HashMap<>();
		map.put("category", category);
		map.put("keyword", keyword);
		return session.selectList("studentMapper.search", map);
	}
	
	
}
