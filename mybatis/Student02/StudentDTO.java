package kh.student.dto;

public class StudentDTO {
	private int no;
	private String name;
	private String memo;
	
	public StudentDTO() {}
	
	public StudentDTO(int no, String name, String memo) {
		super();
		this.no = no;
		this.name = name;
		this.memo = memo;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toString() {
		return no + " : " + name + " : " + memo;
	}
	
	
}
