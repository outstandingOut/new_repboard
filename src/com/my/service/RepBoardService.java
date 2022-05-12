package com.my.service;

import java.util.List;

import com.my.dao.RepBoardDAOOracle;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.RepBoard;

public class RepBoardService {
	private RepBoardDAOOracle boardDAO;
	
	public RepBoardService(){
		this.boardDAO = new RepBoardDAOOracle();//?
	}
	
	/**
	 * 게시글을 추가한다.
	 * @param board 부모글번호가 0인 게시물
	 * @throws AddException
	 */
	public void writeBoard(RepBoard board) throws AddException{
		if(board.getParent_no() != 0) {
			board.setParent_no(0);
		}
		boardDAO.insert(board);
	}
	
	/**
	 * 답글을 추가한다.
	 * @param board 부모글번호를 포함한 게시물
	 * @throws AddException 부모글번호가 0이면 예외발생
	 */
	public void writeReply(RepBoard board) throws AddException{
		if(board.getParent_no() == 0) {
			throw new AddException("부모글번호가 없습니다");
		}
		boardDAO.insert(board);
	}
	
	/**
	 * 
	 * @return
	 * @throws FindException
	 */
	public List<RepBoard> findAll() throws FindException{
		return boardDAO.selectAll();
	}
	
	/**
	 * 게시물번호에 해당하는 게시물을 검색하고,
	 * 조회수를 1증가시킨다.
	 * @param board_no
	 * @return
	 * @throws FindException
	 */
	public RepBoard findByBoard_no(int board_no) throws FindException{
		RepBoard board = boardDAO.selectByBoard_no(board_no);
		try {
			boardDAO.updateBoardCnt(board_no);
		} catch (ModifyException e) {
			throw new FindException(e.getMessage());
		}
		return board;
	}
	
	/**
	 * 검색어에 만족하는 게시물들을 검색한다.
	 * @param word 검색어, 글제목 또는 작성자
	 * @return 게시물들
	 * @throws FindException 검색어에 만족하는 게시물이 없으면 예외가 발생한다.
	 */
	public List<RepBoard> findByBoard_titleORBoard_writer(String word) throws FindException{
		return boardDAO.selectByBoard_titleORBoard_writer(word);
	}
	
	public void modify(RepBoard board, String board_pwd) throws ModifyException{
		boardDAO.update(board, board_pwd);
	}
	public void remove(int board_no, String board_pwd) throws RemoveException{
		boardDAO.delete(board_no, board_pwd);
	}
}
