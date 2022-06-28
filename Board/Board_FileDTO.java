package kh.board.file;

public class Board_FileDTO {
	private int seq_board_file;
	private int seq_board;
	private String ori_name;
	private String sys_name;
	
	public Board_FileDTO() {}

	public Board_FileDTO(int seq_board_file, int seq_board, String ori_name, String sys_name) {
		super();
		this.seq_board_file = seq_board_file;
		this.seq_board = seq_board;
		this.ori_name = ori_name;
		this.sys_name = sys_name;
	}

	public int getSeq_board_file() {
		return seq_board_file;
	}

	public void setSeq_board_file(int seq_board_file) {
		this.seq_board_file = seq_board_file;
	}

	public int getSeq_board() {
		return seq_board;
	}

	public void setSeq_board(int seq_board) {
		this.seq_board = seq_board;
	}

	public String getOri_name() {
		return ori_name;
	}

	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}

	public String getSys_name() {
		return sys_name;
	}

	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}

	@Override
	public String toString() {
		return seq_board_file + " : " + seq_board + " : " + ori_name
				+ " : " + sys_name;
	}
	
	
	
}
