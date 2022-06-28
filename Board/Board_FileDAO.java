package kh.board.file;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Board_FileDAO {
	@Autowired
	private BasicDataSource bds;
	
	// 파일 삭제
	public void delete(String sys_name) throws Exception{
		String sql = "delete from board_file where sys_name = ?";
		try(Connection con = bds.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql);){
			
			pstmt.setString(1, sys_name);
			pstmt.executeUpdate();
		}
				
	}
	
	// 게시글 파일 저장
		public int fileInput(Board_FileDTO dto)throws Exception{
			String sql = "insert into board_file values(seq_board_file.nextval, ?, ?, ?)";
			try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
				
				pstmt.setInt(1, dto.getSeq_board());
				pstmt.setString(2, dto.getOri_name());
				pstmt.setString(3, dto.getSys_name());
				
				return pstmt.executeUpdate();
				
			}
		}
		// 모든 파일의 sys_name 조회
		public List<String> selectSys_name() throws Exception{
			String sql = "select sys_name from board_file";
			try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
				
				ResultSet rs = pstmt.executeQuery();
				List<String> list = new ArrayList<>();
				while(rs.next()) {
					list.add(rs.getString(1));
				}
				return list;
			}
		}
		
		// 게시글 파일 조회
		public List<Board_FileDTO> selectByFile(int seq_board)throws Exception{
			String sql = "select * from board_file where seq_board = ? order by 1 desc";
			try(Connection con = bds.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);){
				
				pstmt.setInt(1, seq_board);
				ResultSet rs = pstmt.executeQuery();
				
				List<Board_FileDTO> list = new ArrayList<>();
				while(rs.next()) {
					int seq_board_file = rs.getInt("seq_board_file");
					String ori_name = rs.getString("ori_name");
					String sys_name = rs.getString("sys_name");	
					list.add(new Board_FileDTO(seq_board_file, seq_board, ori_name, sys_name));
				}
				return list;
			}
		}
}
