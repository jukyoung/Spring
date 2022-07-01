package kh.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.student.dao.StudentDAO;
import kh.student.dto.StudentDTO;

@Service
public class StudentService {
	@Autowired
	private StudentDAO dao;
	
	public void insert(StudentDTO dto) throws Exception{
		dao.insert(dto);
	}
	
	public List<StudentDTO> selectList() throws Exception{
		return dao.selectList();
	}
	public void delete(int no) throws Exception{
		dao.delete(no);
	}
	public StudentDTO selectOne(int no) throws Exception{
		return dao.selectOne(no);
	}
	public void update(StudentDTO dto) throws Exception{
		dao.update(dto);
	}
	public List<StudentDTO> search(String category, String keyword) throws Exception{
		return dao.search(category, keyword);
	}
	public void deleteCheck(int[] no) throws Exception{
		dao.deleteCheck(no);
	}
}
