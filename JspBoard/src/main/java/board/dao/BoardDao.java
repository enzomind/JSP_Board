package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import board.model.Board;

public class BoardDao {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	String sql;

	public BoardDao() {

		try {
			String driverName = "com.mysql.cj.jdbc.Driver";
			String dbURL = "jdbc:mysql://localhost:3306/board?characterEncoding=utf8";
			String dbID = "jspexam";
			String dbPW = "jsppw";

			Class.forName(driverName);
			conn = DriverManager.getConnection(dbURL, dbID, dbPW);

		} catch (ClassNotFoundException e) {
			System.out.println("로그 : DB 드라이브 로딩 실패 : " + e.toString());
		} catch (SQLException sqle) {
			System.out.println("로그 : DB 접속실패 : " + sqle.toString());
		} catch (Exception e) {
			System.out.println("로그 : 원인모를 에러 발생");
			e.printStackTrace();
		}

	}

	public ArrayList<Board> getList() throws SQLException {
		ArrayList<Board> list = new ArrayList<Board>();

		sql = "SELECT * FROM simple_bbs";

		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			Board bd = new Board();
			bd.setId(rs.getInt(1));
			bd.setWriter(rs.getString(2));
			bd.setTitle(rs.getString(3));
			bd.setContent(rs.getString(4));

			list.add(bd);
		}

		return list;
	}

	public ArrayList<Board> getDetail(int id) throws SQLException {
		ArrayList<Board> list = new ArrayList<Board>();

		sql = "SELECT * FROM simple_bbs WHERE id = ?";

		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		rs = pstmt.executeQuery();

		while (rs.next()) {
			Board bd = new Board();
			bd.setId(rs.getInt(1));
			bd.setWriter(rs.getString(2));
			bd.setTitle(rs.getString(3));
			bd.setContent(rs.getString(4));

			list.add(bd);
		}

		return list;
		
	}

	
	public int write(String writer, String title, String content) throws SQLException {

		sql = "INSERT INTO simple_bbs (writer, title, content) values (?,?,?)";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);

			return pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
	}
	
	
	public int modify(int id, String writer, String title, String content) throws SQLException {
		sql = "UPDATE simple_bbs SET writer=?, title=?, content=? WHERE id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setInt(4, id);
		
			 
			return pstmt.executeUpdate();
		}finally {
			pstmt.close();
		}
	}
	
	public int delete(int id) throws SQLException {
		sql = "DELETE FROM simple_bbs WHERE id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, id);
			return pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}
		
	}
	
	
//	Backup 및 정리예정
//
//	private Board makeModelFromResultSet(ResultSet rs) throws SQLException {
//		Board board = new Board();
//
//		board.setId(rs.getInt("id"));
//		board.setWriter(rs.getString("writer"));
//		board.setTitle(rs.getString("title"));
//		board.setContent(rs.getString("content"));
//
//		return board;
//
//	}
//
//	public Board selectWhereNumber(int id) throws SQLException {
//		
//		sql = "SELECT * FROM simple_bbs WHERE id=?";
//
//		pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, id);
//		rs = pstmt.executeQuery();
//
//		Board board = new Board();
//		board.setId(rs.getInt(1));
//		board.setWriter(rs.getString(2));
//		board.setTitle(rs.getString(3));
//		board.setContent(rs.getString(4));
//			
//		return board;
//		
//	}
	

//		sql = "SELECT * FROM simple_bbs WHERE id=?";
//		pstmt = conn.prepareStatement(sql);
//		pstmt.setInt(1, id);
//		
//		rs = pstmt.executeQuery();
//		
//		return makeModelFromResultSet(rs);


}