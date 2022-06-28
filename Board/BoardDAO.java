package kh.board.board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kh.board.file.Board_FileDTO;

@Repository
public class BoardDAO {
	@Autowired
	private BasicDataSource bds;
	
	// 게시글 수정
	public void modify(BoardDTO dto) throws Exception{
		String sql = "update board set title=?, content=? where seq_board=?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setInt(3, dto.getSeq_board());
			
			pstmt.executeUpdate();
		}
				
				
	}
	
	// 게시글 삭제
	public int delete(int seq_board) throws Exception{
		String sql = "delete from board where seq_board = ?";
		
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_board);
			return pstmt.executeUpdate();
		}
	}
	
	// seq_board 반환
	public int getSeq() throws Exception{
		String sql = "select seq_board.nextval from dual";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int seq_board = rs.getInt(1); // 첫번째 인덱스를 반환
			return seq_board;
		}
	}
	
	
	// 조회수
	public void updateView_count(int seq_board) throws Exception{
		String sql = "update board set view_count = view_count+1 where seq_board =?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_board);
			pstmt.executeUpdate();
		}
	}
	
	
	// 게시글 작성
	public int write(BoardDTO dto) throws Exception{
		String sql = "insert into board values(?, ?, ?, ?, ?, 0, sysdate)";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, dto.getSeq_board());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getWriter_nickname());
			pstmt.setString(5, dto.getWriter_id());
			
			return pstmt.executeUpdate();
		}
		
	}
	// 게시글 번호 조회
	public BoardDTO selectByNo(int seq_board) throws Exception{
		String sql = "select * from board where seq_board = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setInt(1, seq_board);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				String title = rs.getString("title");
				String content = rs.getString("title");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				Date written_date = rs.getDate("written_date");
				return new BoardDTO(seq_board, title, content, writer_nickname, writer_id, view_count, written_date);
			}
			return null;
			
		}
	}
	// 게시글 조회
	public List<BoardDTO> selectAll()throws Exception{
		String sql = "select * from board order by 1 desc";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			ResultSet rs = pstmt.executeQuery();
			
			// ArrayList가 List를 상속받기 때문에 List 타입 참조변수에 ArrayList인스턴스를 담을 수 있음.
			List<BoardDTO> list = new ArrayList<>();
			while(rs.next()) {
				int seq_board = rs.getInt("seq_board");
				String title = rs.getString("title");
				String content = rs.getString("title");
				String writer_nickname = rs.getString("writer_nickname");
				String writer_id = rs.getString("writer_id");
				int view_count = rs.getInt("view_count");
				Date written_date = rs.getDate("written_date");
				
				list.add(new BoardDTO(seq_board, title, content, writer_nickname, writer_id, view_count, written_date));
			}
			return list;
		}
	}
	
}
