package com.my.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.sql.MyConnection;
import com.my.vo.RepBoard;

public class RepBoardDAOOracle implements RepBoardDAO {
	public void delete(int board_no, String board_pwd) throws RemoveException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new RemoveException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String deleteSQL = "DELETE repboard  where board_no = ? AND board_pwd = ?";
		try {
			pstmt = con.prepareStatement(deleteSQL);
			pstmt.setInt(1, board_no);
			pstmt.setString(2, board_pwd);
			int rowcnt = pstmt.executeUpdate();
			if(rowcnt == 0) {
				throw new RemoveException("글번호가 없거나 비밀번호가 다릅니다");
			}
		} catch (SQLException e) {
			throw new RemoveException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
	}
	public void update(RepBoard board, String board_pwd) throws ModifyException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new ModifyException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String updateSQL1 = "UPDATE repboard SET ";
		boolean flag = false; 
		//제목
		if(board.getBoard_title() != null) {
			updateSQL1 += "board_title='"+ board.getBoard_title()+"'";
			flag = true;
		}
		
		//비밀번호
		if(flag) {
			updateSQL1 += ",";
		}
		if(board.getBoard_pwd() != null) {
			updateSQL1 += "board_pwd='"+ board.getBoard_pwd()+"'";
			flag = true;
		}
		System.out.println(updateSQL1);
		if(!flag) {
			throw new ModifyException("수정할 내용이 없습니다");
		}
		//----------
		String updateSQL2 = "\r\nWHERE board_no = "+board.getBoard_no()+
				" AND board_pwd = '"+ board_pwd +"'";
		String updateSQL = updateSQL1 + updateSQL2;
		try {
			pstmt = con.prepareStatement(updateSQL);
			int rowcnt = pstmt.executeUpdate();
			if(rowcnt == 0) {
				throw new ModifyException("글번호가 없거나 비밀번호가 다릅니다");
			}
		} catch (SQLException e) {
			throw new ModifyException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
	}

	public void updateBoardCnt(int board_no) throws ModifyException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new ModifyException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String updateBoardCntSQL = "\r\n" + 
				"UPDATE repboard SET board_cnt = board_cnt+1\r\n" + 
				"WHERE board_no = ?";
		try {
			pstmt = con.prepareStatement(updateBoardCntSQL);
			pstmt.setInt(1, board_no);
			int rowcnt = pstmt.executeUpdate();
			if(rowcnt == 0) {
				throw new ModifyException("글번호가 없습니다");
			}
		} catch (SQLException e) {
			throw new ModifyException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt);
		}
	}
	
	public RepBoard selectByBoard_no(int board_no) throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String selectAllSQL = "SELECT *\r\n" + 
				"FROM repboard\r\n" + 
				"WHERE board_no=?";
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			pstmt.setInt(1, board_no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				int parent_no = rs.getInt("parent_no");
				String board_title = rs.getString("board_title");
				String board_writer = rs.getString("board_writer");
				Date board_dt = rs.getDate("board_dt");
				String board_pwd = rs.getString("board_pwd");
				int board_cnt = rs.getInt("board_cnt");
				RepBoard board = new RepBoard(board_no, parent_no, board_title, board_writer, board_dt, board_pwd, board_cnt);
				return board;
			}else{
				throw new FindException("게시글이 없습니다");
			}
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	
	public List<RepBoard> selectByBoard_titleORBoard_writer(String word) throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String selectAllSQL = "SELECT repboard.*\r\n" + 
				"FROM repboard\r\n" + 
				"WHERE board_title LIKE ? OR board_writer LIKE ?\r\n" + 
				"ORDER BY board_no DESC";
		ResultSet rs = null;
		List<RepBoard> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			pstmt.setString(1, "%"+word+"%");
			pstmt.setString(2, "%"+word+"%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int board_no = rs.getInt("board_no");
				int parent_no = rs.getInt("parent_no");
				String board_title = rs.getString("board_title");
				String board_writer = rs.getString("board_writer");
				Date board_dt = rs.getDate("board_dt");
				String board_pwd = rs.getString("board_pwd");
				int board_cnt = rs.getInt("board_cnt");
				RepBoard board = new RepBoard(board_no, parent_no, board_title, board_writer, board_dt, board_pwd, board_cnt);
				list.add(board);
			}
			if(list.size() == 0) {
				throw new FindException("게시글이 없습니다");
			}
			return list;
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public List<RepBoard> selectAll() throws FindException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new FindException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String selectAllSQL = "SELECT level, repboard.*\r\n" + 
				"FROM repboard\r\n" + 
				"START WITH parent_no = 0\r\n" + 
				"CONNECT BY PRIOR board_no = parent_no\r\n" + 
				"ORDER SIBLINGS BY board_no DESC";
		ResultSet rs = null;
		List<RepBoard> list = new ArrayList<>();
		try {
			pstmt = con.prepareStatement(selectAllSQL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				int level = rs.getInt("level");
				int board_no = rs.getInt("board_no");
				int parent_no = rs.getInt("parent_no");
				String board_title = rs.getString("board_title");
				String board_writer = rs.getString("board_writer");
				Date board_dt = rs.getDate("board_dt");
				String board_pwd = rs.getString("board_pwd");
				int board_cnt = rs.getInt("board_cnt");
				RepBoard board = new RepBoard(level, board_no, parent_no, board_title, board_writer, board_dt, board_pwd, board_cnt);
				list.add(board);
			}
			if(list.size() == 0) {
				throw new FindException("게시글이 없습니다");
			}
			return list;
		}catch(SQLException e) {
			throw new FindException(e.getMessage());
		}finally {
			MyConnection.close(con, pstmt, rs);
		}
	}
	public void insert(RepBoard board) throws AddException{
		Connection con = null;
		try {
			con = MyConnection.getConnection();
		}catch(Exception e) {
			throw new AddException(e.getMessage());
		}
		PreparedStatement pstmt = null;
		String insertSQL = "INSERT INTO repboard(\r\n" + 
				"   board_no,               parent_no, board_title, board_writer, board_dt, BOARD_PWD, board_cnt)  VALUES    \r\n" + 
				"   (board_seq.NEXTVAL,             ?,            ?,          ?,   SYSDATE,    ?,         0)";
		
		try {
			pstmt = con.prepareStatement(insertSQL);
			pstmt.setInt(1, board.getParent_no());
			pstmt.setString(2, board.getBoard_title());
			pstmt.setString(3, board.getBoard_writer());
			pstmt.setString(4, board.getBoard_pwd());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			MyConnection.close(con, pstmt);
		}	
	}
	public static void main(String[] args) {
		RepBoardDAOOracle dao = new RepBoardDAOOracle();
//		String board_title = "테스트1";
//		String board_writer = "작성자1";
//		String board_pwd = "p1";
//		RepBoard board = new RepBoard(board_title, board_writer, board_pwd);
//		
//		try {
//			dao.insert(board);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		
//		int parent_no = 6;
//		String board_title = "테스트1-답1";
//		String board_writer = "작성자2";
//		String board_pwd = "p2";
//		RepBoard board = new RepBoard(parent_no, board_title, board_writer, board_pwd);
//		
//		try {
//			dao.insert(board);
//		} catch (AddException e) {
//			e.printStackTrace();
//		}
		
//		try {
//			System.out.println(dao.selectAll());
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
		
//		int board_no = 6;
//		try {
//			System.out.println(dao.selectByBoard_no(board_no));
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
		
//		String word = "2";
//		try {
//			System.out.println(dao.selectByBoard_titleORBoard_writer(word));
//		} catch (FindException e) {
//			e.printStackTrace();
//		}
	
//		int board_no = 6;
//		try {
//			dao.updateBoardCnt(board_no);
//		} catch (ModifyException e) {
//			e.printStackTrace();
//		}
		
//		int board_no = 6;
//		String board_pwd = "upp1";
//		RepBoard board = new RepBoard();
//		board.setBoard_no(board_no);
//		board.setBoard_pwd(board_pwd);
//		try {
//			dao.update(board, "p1");
//		} catch (ModifyException e) {
//			e.printStackTrace();
//		}
		
//		int board_no = 6;
//		String board_title = "up제목1";
//		String board_pwd = "upp2";
//		RepBoard board = new RepBoard();
//		board.setBoard_no(board_no);
//		board.setBoard_title(board_title);
//		board.setBoard_pwd(board_pwd);
//		try {
//			dao.update(board, "upp1");
//		} catch (ModifyException e) {
//			e.printStackTrace();
//		}
		

		
//		int board_no = 7;
//		String board_pwd = "p2";
//		try {
//			dao.delete(board_no, board_pwd);
//		} catch (RemoveException e) {
//			e.printStackTrace();
//		}
	}
}