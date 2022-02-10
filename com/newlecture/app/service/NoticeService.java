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
	private String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; //url은 공통으로 하나만 만들어도 되지만
	private String uid = "NEWLEC";
	private String pwd = "1234";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	
	public List<Notice> getList(int page) throws ClassNotFoundException, SQLException{ //List로 가져올 속성값들(컬럼명 id, title, writerId 등등)
		//이것들을 가져오려면 List보다 큰 개념의 무언가가 필요하다.
		//그게 바로 app.entity의 Notice.java이다.
		
		int start = 1 + (page-1)*10; // 1, 11, 21, 21 ....
		int end = 10*page; // 10, 20, 30, 40 ....
		
		
		String sql = "select * from NOTICE_VIEW where NUM between ? and ?"; //이제 view를 만들어서 NOTICE_VIEW를 통해서 이미 정렬된 데이터를 뽑게 했다.
		
		Class.forName(driver);
		//con, st, rs 같은 것들은 매 쿼리문 마다 생성 되어야 한다.
		Connection con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, start);
		st.setInt(2, end);
		ResultSet rs = st.executeQuery();
		
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
	//이렇게 총 몇개의 게시물이 있는지 단일 값을 얻어오는 것을
	//Scalar 값을 얻어온다고 한다.
	public int getCount() throws ClassNotFoundException, SQLException {
		int count = 0;
		
		String sql = "select COUNT(ID) AS COUNT from NOTICE"; //select 문을 통해서 데이터를 뽑는다.
		
		Class.forName(driver);
		//con, st, rs 같은 것들은 매 쿼리문 마다 생성 되어야 한다.
		Connection con = DriverManager.getConnection(url, uid, pwd);
		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(sql); //result set으로 받는건 단일 값 하나로 된 값을 받는 것이다.
		
		
		if(rs.next())
			count = rs.getInt("COUNT");
			
		
		
		rs.close();
		st.close();
		con.close();
		
		return count;
		
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
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setNString(1, title); //values는 index가 1번부터 시작한다.
		st.setNString(2, writerId);
		st.setNString(3, content);
		st.setNString(4, files);
	
		int result = st.executeUpdate(); //preparestatment는 이미 안에 sql문이 있어서 다시 sql문을 보내면 error발생
		//그리고 이 동작은 데이터베이스조작이므로 Update
		
		st.close();
		con.close();
		return result;
	}
	
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
			
			String title = notice.getTitle();
			String content = notice.getContent();
			String files = notice.getFiles();
			int id = notice.getId();
			
			//update notice 뒤에 공백을 하나 붙여 줘야 SET이 바로 notice 뒤에 붙지 못한다.
			
			String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
			String sql = "update notice " + 
					"SET" + 
					"    title=?," + 
					"    content=?," + 
					"    files=?" + 
					"where id =?";
			
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, uid, pwd);
			//Statement st = con.createStatement();
			PreparedStatement st = con.prepareStatement(sql);
			st.setNString(1, title); //values는 index가 1번부터 시작한다.
			st.setNString(2, content);
			st.setNString(3, files);
			st.setInt(4, id);
		
			int result = st.executeUpdate(); //preparestatment는 이미 안에 sql문이 있어서 다시 sql문을 보내면 error발생
			//그리고 이 동작은 데이터베이스조작이므로 Update
			
			
			st.close();
			con.close();
			return result;
	}
	
	public int delete(int id) throws ClassNotFoundException, SQLException {
		
		
		//update notice 뒤에 공백을 하나 붙여 줘야 SET이 바로 notice 뒤에 붙지 못한다.
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "DELETE NOTICE where id =?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
	
		int result = st.executeUpdate(); //preparestatment는 이미 안에 sql문이 있어서 다시 sql문을 보내면 error발생
		//그리고 이 동작은 데이터베이스조작이므로 Update
		
		st.close();
		con.close();
		return result;
	}


}
