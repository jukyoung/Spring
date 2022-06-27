package kh.board.member;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kh.board.utils.EncryptionUtils;

/*
   dao를 통해 데이터를 추가, 수정, 삭제, 조회해야하는 경우 메서드 생성
   혹은 요청/응답과는 별개로 추가적으로 가공해야하는 데이터가 있는 경우 
  */
@Service
public class MemberService {
	@Autowired
	private MemberDAO dao;
	
	public MemberDTO isLoginOk(String id, String pw) throws Exception{
		return dao.isLoginOk(id, pw);
	}
	
	public boolean checkId(String id) throws Exception{
		return dao.checkId(id);
	}
	// 세션은 직접적으로 넘겨받지 않음 세션을 통해 얻어낸 값만 넘기기
	public int signup(MemberDTO dto) throws Exception{
		return dao.insert(dto);
	}
	
	public String uploadProfile(MultipartFile file, String realPath)throws Exception{ // 프로필 사진 수정
		      if (!file.isEmpty()) {
		         File realPathFile = new File(realPath);
		         if(!realPathFile.exists()) realPathFile.mkdir();
		         String ori_name = file.getOriginalFilename();
		         String sys_name = UUID.randomUUID() + "_" + ori_name;
		         file.transferTo(new File(realPath + File.separator + sys_name));
		         
		         return sys_name;
		      } return null;
		   }
	
	public int modifyProfile(MemberDTO dto)throws Exception{
		return dao.modifyProfile(dto);
	}
	public int modify(String nickname, String id)throws Exception{
		return dao.modify(nickname, id);
	}

/* 파일 객체를 이용해 폴더가 없으면 폴더 생성
		File realPathFile = new File(realPath);
		if(!realPathFile.exists()) {realPathFile.mkdir();}
		
		// 사용자가 프로필을 등록할수도 있고 아닐수 도 있음
		// 프로필을 등록했다한다면 이름값 꺼내서 파일 이름 등을 저장
		if(!file.isEmpty()) {// 클라이언트로부터 넘어 온 파일이 있다면
			// 우리가 직접 만들어준 sys_name을 이용해 파일을 업로드할 것.
			/* UUID (고유식별값) 
			 *  ori_name 활용 
			 *  ~~~~_ori_name 형태 (~~~~ :UUID)
			String ori_name = file.getOriginalFilename();
			System.out.println("ori_name : " + ori_name);
			//System.out.println(UUID.randomUUID()); //고유한 uuid값
			String sys_name = UUID.randomUUID() + "_" + ori_name;
			System.out.println("sys_name : " + sys_name);
			// transferTo(실제 파일이 저장될 full 경로 -> 파일 객체를 이용); -> 원하는 경로에 파일을 저장해주는 역할
			// -> MultipartFile 객체가 담고 있는 파일 데이터를 인자값으로 넘겨준 경로에 실제로 저장(업로드)해주는 역할을 함.
			// File.separator -> 파일이 가지고 있는 구분자 즉 역슬래시
			file.transferTo(new File(realPath + File.separator + sys_name));
			 
			dto.setProfile_image(sys_name); // dto에 프로필 경로도 셋팅해주기
		}*/
 
}