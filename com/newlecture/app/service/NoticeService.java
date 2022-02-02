package com.newlecture.app.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.newlecture.app.entity.Notice;

public class NoticeService {
	public List<Notice> getList() throws ClassNotFoundException, SQLException{ //List로 가져올 속성값들(컬럼명 id, title, writerId 등등)
		//이것들을 가져오려면 List보다 큰 개념의 무언가가 필요하다.
		//그게 바로 app.entity의 Notice.java이다.
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "NEWLEC","1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		//전달받은 Notice를 반환하는 준비
		List<Notice> list = new ArrayList<Notice>();
		
		
		while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			Date regDate = rs.getDate("REGDATE");
			String content = rs.getString("CONTENT");
			int hit = rs.getInt("hit");
			String files = rs.getString("FILES");
			
			//app.entity에서 만든 생성자 Notice를 이용해서 값을 가져올 건데 
			//순서가 바뀌지 않도록 조심해야 한다.
			//entitiy아래에 Notice.java 순서로 만든 대로 넣어야 한다.
			Notice notice = new Notice(
					id,
					title,
					writerId,
					regDate,
					content,
					hit,
					files
					);
			//ArrayList로 받은 Notice값을 add를 통해서 list로 만든다.
			list.add(notice);
			
		}
		
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	}

	public int insert(Notice notice) throws SQLException, ClassNotFoundException {
		
		String title = notice.getTitle(); //notice객체를 통해서 가져온 값을 넣게 해야 한다.
		String writerId = notice.getWriterId();
		String content = notice.getContent();
		String files = notice.getFiles();
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "INSERT INTO notice ( "+
				 "title, "+
				 "writer_id," +
				 "content," +
				 "files" +
				 ")VALUES (?,?,?,?)";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "NEWLEC","1234");
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setNString(1, title); //values는 index가 1번부터 시작한다.
		st.setNString(2, writerId);
		st.setNString(3, content);
		st.setNString(4, files);
	
		int result = st.executeUpdate(); //preparestatment는 이미 안에 sql문이 있어서 다시 sql문을 보내면 error발생
		//그리고 이 동작은 데이터베이스조작이므로 Update
		
		System.out.println(result);
		
		st.close();
		con.close();
		return result;
	}
	
	public int update(Notice notice) {
		public static void main(String[] args) throws ClassNotFoundException, SQLException {
			
			String title = "TEST4";
			String content = "hahaha33";
			String files = "";
			int id = 73;
			
			//update notice 뒤에 공백을 하나 붙여 줘야 SET이 바로 notice 뒤에 붙지 못한다.
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
			String sql = "update notice " + 
					"SET" + 
					"    title=?," + 
					"    content=?," + 
					"    files=?" + 
					"where id =?";
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "NEWLEC","1234");
			//Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			st.setNString(1, title); //values는 index가 1번부터 시작한다.
			st.setNString(2, content);
			st.setNString(3, files);
			st.setInt(4, id);
		
			int result = st.executeUpdate(); //preparestatment는 이미 안에 sql문이 있어서 다시 sql문을 보내면 error발생
			//그리고 이 동작은 데이터베이스조작이므로 Update
			
			System.out.println(result);
			
			st.close();
			con.close();
		return 0;
	}
	
	public int delete(int id) {
		return 0;
	}

}
