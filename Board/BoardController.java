package kh.board.board;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kh.board.file.Board_FileDTO;
import kh.board.member.MemberDTO;

@RequestMapping("/board")
@Controller
public class BoardController {
	@Autowired
	private BoardService service;
	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "toBoard") //board 페이지 요청
	public String toBoard(Model model) throws Exception {
		List<BoardDTO> list = service.selectAll();
		model.addAttribute("list", list);
		return "board/board";
	}
	@RequestMapping(value = "toWrite") // 글쓰기 페이지 요청
	public String toWrite() {
		return "board/write";
	}
	@RequestMapping(value = "/toDetailView") // 상세보기 페이지 요청
	public String toDetailView(int seq_board, Model model)throws Exception{
		System.out.println("게시글 번호 : " +seq_board);
		service.updateView_count(seq_board);
		Map<String, Object> map = service.selectByNo(seq_board);
		System.out.println(map.get("boardDTO")); // boardDTO 라고 설정한 키값을 활용해 게시글 데이터 출력
		System.out.println(map.get("fileList"));
		model.addAttribute("map", map);
		return "board/detailView";
	}
	@RequestMapping(value = "/write") // 글 등록 요청
	public String write(BoardDTO dto, MultipartFile[] file)throws Exception{
		System.out.println(dto.toString());
		String id = ((MemberDTO)session.getAttribute("loginSession")).getId();
		String nickname = ((MemberDTO)session.getAttribute("loginSession")).getNickname();
		for(int i = 0; i <file.length; i++) {
			System.out.println("file : " + file[i]);
		}
		
		int seq_board = service.getSeq();
		System.out.println("seq_board : " + seq_board);
		dto.setSeq_board(seq_board);
		
		Board_FileDTO bfdto = new Board_FileDTO();
		bfdto.setSeq_board(seq_board);
		
		dto.setWriter_id(id);
		dto.setWriter_nickname(nickname);
		int rs = service.write(dto);
		
		String realPath = session.getServletContext().getRealPath("boardFile");
		System.out.println("경로값 realPath : " + realPath);
		
		service.fileInput(bfdto, file, realPath);
		
		return "redirect:/board/toBoard";
	}
	@RequestMapping(value="/delete") // 게시글 삭제요청
	public String delete(int seq_board) throws Exception{
		System.out.println("삭제 게시글 번호 : " + seq_board);
		service.delete(seq_board);
		return "redirect:/board/toBoard";
	}
	@RequestMapping(value="/modify") // 게시글 수정 요청
	public String modify(BoardDTO dto, MultipartFile[] files, @RequestParam(value = "deleteFileList[]") String[] deleteFileList) throws Exception{
		System.out.println("dto : " + dto.toString());
		System.out.println("files : " + files[0]);
		// deleteFileList -> sys_name 이 담긴 즉 String 배열 -> 가끔 값이 제대로 안넘어올때 있음
		// -> 받아오는 매개변수 앞에 @RequestParam (value ="받아오는 name 값 -> 즉 배열의 값을 셋팅한 input 의 name") 
		// -> value 값을 매개변수에 넣는다
		
		String path = session.getServletContext().getRealPath("boardFile");
		service.modify(dto, path, files, deleteFileList);
		
		
		return "redirect:/board/toDetailView?seq_board="+dto.getSeq_board();
	}

	
	@ExceptionHandler
	public String toError(Exception e){
		System.out.println("예외 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}
}
