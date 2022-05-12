package com.my.vo;

import java.util.Date;

public class RepBoard {
	private int level;
	private int board_no;
	private int parent_no;
	private String board_title;
	private String board_writer;
	private Date board_dt;
	private String board_pwd;
	private int board_cnt;
	
	// 생성자
	public RepBoard() {
	}
	
	public RepBoard(String board_title, String board_writer, String board_pwd) {
		super();
		this.board_title = board_title;
		this.board_writer = board_writer;
		this.board_pwd = board_pwd;
	}
	
	public RepBoard(int parent_no, String board_title, String board_writer, String board_pwd) {
		super();
		this.parent_no = parent_no;
		this.board_title = board_title;
		this.board_writer = board_writer;
		this.board_pwd = board_pwd;
	}

	public RepBoard(int board_no, int parent_no, String board_title, String board_writer, Date board_dt,
			String board_pwd, int board_cnt) {
		super();
		this.board_no = board_no;
		this.parent_no = parent_no;
		this.board_title = board_title;
		this.board_writer = board_writer;
		this.board_dt = board_dt;
		this.board_pwd = board_pwd;
		this.board_cnt = board_cnt;
	}

	public RepBoard(int level, int board_no, int parent_no, String board_title, String board_writer, Date board_dt,
			String board_pwd, int board_cnt) {
		super();
		this.level = level;
		this.board_no = board_no;
		this.parent_no = parent_no;
		this.board_title = board_title;
		this.board_writer = board_writer;
		this.board_dt = board_dt;
		this.board_pwd = board_pwd;
		this.board_cnt = board_cnt;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getBoard_no() {
		return board_no;
	}

	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}

	public int getParent_no() {
		return parent_no;
	}

	public void setParent_no(int parent_no) {
		this.parent_no = parent_no;
	}

	public String getBoard_title() {
		return board_title;
	}

	public void setBoard_title(String board_title) {
		this.board_title = board_title;
	}

	public String getBoard_writer() {
		return board_writer;
	}

	public void setBoard_writer(String board_writer) {
		this.board_writer = board_writer;
	}

	public Date getBoard_dt() {
		return board_dt;
	}

	public void setBoard_dt(Date board_dt) {
		this.board_dt = board_dt;
	}

	public String getBoard_pwd() {
		return board_pwd;
	}

	public void setBoard_pwd(String board_pwd) {
		this.board_pwd = board_pwd;
	}

	public int getBoard_cnt() {
		return board_cnt;
	}

	public void setBoard_cnt(int board_cnt) {
		this.board_cnt = board_cnt;
	}
	
	@Override
	public String toString() {
		return "RepBoard [level=" + level + ", board_no=" + board_no + ", parent_no=" + parent_no + ", board_title="
				+ board_title + ", board_writer=" + board_writer + ", board_dt=" + board_dt + ", board_pwd=" + board_pwd
				+ ", board_cnt=" + board_cnt + "]";
	}
}