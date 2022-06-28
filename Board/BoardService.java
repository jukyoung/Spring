package kh.board.board;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.board.file.Board_FileDAO;
import kh.board.file.Board_FileDTO;

@Service
public class BoardService {
	@Autowired
	private BoardDAO dao;
	@Autowired
	private Board_FileDAO bfdao;
	
	// 게시글 삭제 요청
	public int delete(int seq_board) throws Exception{
		return dao.delete(seq_board);
	}
	
	// 게시글 전체 조회
	public List<BoardDTO> selectAll()throws Exception{
		return dao.selectAll();
	}
	// seq_board 가져오기
	public int getSeq()throws Exception{
		return dao.getSeq();
	}
	// 게시글 작성
	public int write(BoardDTO dto)throws Exception{
		return dao.write(dto);
	}
	// 서비스의 메서드는 사용자 입장에서의 하나의 기능에 해당하게끔
	/* 사용자가 글 제목을 클릭했을때(상세보기를 요청했을때) 실행되는 메서드
	 * 게시글의 데이터 가져오기 + 파일 데이터 가져오기
	 * */
	// seq_board 로 게시글 조회
	// Map -> HashMap 클래스가 상속받는 부모클래스
	public Map<String, Object> selectByNo(int seq_board)throws Exception{
		// 다른 타입의 데이터를 반환받기 위해 map을 이용(dao -> int bfdao -> list) 
		Map<String, Object> map = new HashMap<>();
		map.put("fileList", bfdao.selectByFile(seq_board));// 파일 데이터 가져오기
		map.put("boardDTO", dao.selectByNo(seq_board)); // 게시글 데이터 가져오기
		 
		return map;
	
	}
	// 조회수 업데이트
	public void updateView_count(int seq_board) throws Exception{
		dao.updateView_count(seq_board);
	}
	// 게시글 수정
	public void modify(BoardDTO dto, String path, MultipartFile[] files, String[] deleteFileList) throws Exception{
		// 새롭게 첨부된 파일 업로드 + DB에 파일 데이터 저장
		File realPath = new File(path);
		if(!realPath.exists()) realPath.mkdir();

		for(MultipartFile mf : files){
			if(!mf.isEmpty()){ // 파일이 들어있다면
				String ori_name = mf.getOriginalFilename();
				String sys_name = UUID.randomUUID() + "_" + ori_name;

				mf.transferTo(new File(path + File.separator + sys_name));
			
			bfdao.fileInput(new Board_FileDTO(0, dto.getSeq_board(), ori_name, sys_name));
			}
	}
		// 수정된 게시글 정보 DB에서 수정하기
		dao.modify(dto);
		// 삭제 요청된 파일들을 서버 경로에서 삭제
		if(deleteFileList.length !=0 ) {
			for(String sys_name : deleteFileList) {
				// full 파일 경로를 만들어서 File 객체 생성 -> delete메서드로 파일 삭제
				File file = new File(path + File.separator + sys_name);
				if(file.exists()) { // 실제 그 경로의 파일이 존재하는지 체크
					file.delete(); // 실제 서버 경로에서 파일 삭제
					bfdao.delete(sys_name);// DB에서 파일 데이터 삭제
				}
				
			}
		}
	}
	
	// 파일 업로드 및 저장
	public void fileInput(Board_FileDTO bfdto, MultipartFile[] file, String realPath)throws Exception{
		File realPathFile = new File(realPath);
		
		if(!realPathFile.exists()) {
			realPathFile.mkdir();
		}
		
		for(MultipartFile f : file) {
			if(!f.isEmpty()) {
				String ori_name = f.getOriginalFilename();
				System.out.println("ori_name : " + ori_name);
				String sys_name = UUID.randomUUID() + "_" + ori_name;
				System.out.println("sys_name : " + sys_name);
				
				f.transferTo(new File(realPath + File.separator + sys_name));
				bfdto.setOri_name(ori_name);
				bfdto.setSys_name(sys_name);
				bfdao.fileInput(bfdto);
				
			}
			
		}
		
		
		
	}
}
