package kh.board.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/file")
@Controller
public class FileController {
	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/download") // 파일 다운로드 요청
	public void fileDown(String ori_name, String sys_name, HttpServletResponse response) throws Exception{
		System.out.println("ori_name : " + ori_name);
		System.out.println("sys_name : " + sys_name);
	
		String uploadFilePath = session.getServletContext().getRealPath("boardFile");
		System.out.println("uploadFilePath : " + uploadFilePath);
		
		
		String filePath = uploadFilePath+ File.separator + sys_name;
		System.out.println("filePath : " + filePath);
		// FileInputStream을 사용할수 있도록 파일의 full 경로를 이용해 File 객체 생성
		File file = new File(filePath);
		System.out.println("file 경로 : " + file.getPath());
		
		ori_name = new String(ori_name.getBytes("utf-8"), "ISO-8859-1");
		
		// 응답되는 값이 일반데이터가 아닌 파일 다운로드에 대한 요청이라는 것을 response header에 셋팅
		response.setHeader("Content-disposition", "attachment; filename=\""+ori_name+"\"");
		try(DataInputStream dis = new DataInputStream(new FileInputStream(filePath));
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());){
			
			byte[] arr = dis.readAllBytes(); // 파일을 한번에 바이트 배열로 불러 들이기
			dos.write(arr);
			dos.flush();
		}
	}
	@ExceptionHandler
	public String toError(Exception e){
		System.out.println("예외 발생");
		e.printStackTrace();
		return "redirect:/toError";
	}
}
