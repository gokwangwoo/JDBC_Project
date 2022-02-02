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
	public List<Notice> getList() throws ClassNotFoundException, SQLException{ //List�� ������ �Ӽ�����(�÷��� id, title, writerId ���)
		//�̰͵��� ���������� List���� ū ������ ���𰡰� �ʿ��ϴ�.
		//�װ� �ٷ� app.entity�� Notice.java�̴�.
		
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "SELECT * FROM NOTICE";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "NEWLEC","1234");
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		
		//���޹��� Notice�� ��ȯ�ϴ� �غ�
		List<Notice> list = new ArrayList<Notice>();
		
		
		while(rs.next()) {
			int id = rs.getInt("ID");
			String title = rs.getString("TITLE");
			String writerId = rs.getString("WRITER_ID");
			Date regDate = rs.getDate("REGDATE");
			String content = rs.getString("CONTENT");
			int hit = rs.getInt("hit");
			String files = rs.getString("FILES");
			
			//app.entity���� ���� ������ Notice�� �̿��ؼ� ���� ������ �ǵ� 
			//������ �ٲ��� �ʵ��� �����ؾ� �Ѵ�.
			//entitiy�Ʒ��� Notice.java ������ ���� ��� �־�� �Ѵ�.
			Notice notice = new Notice(
					id,
					title,
					writerId,
					regDate,
					content,
					hit,
					files
					);
			//ArrayList�� ���� Notice���� add�� ���ؼ� list�� �����.
			list.add(notice);
			
		}
		
		
		rs.close();
		st.close();
		con.close();
		
		return list;
	}

	public int insert(Notice notice) throws SQLException, ClassNotFoundException {
		
		String title = notice.getTitle(); //notice��ü�� ���ؼ� ������ ���� �ְ� �ؾ� �Ѵ�.
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
		st.setNString(1, title); //values�� index�� 1������ �����Ѵ�.
		st.setNString(2, writerId);
		st.setNString(3, content);
		st.setNString(4, files);
	
		int result = st.executeUpdate(); //preparestatment�� �̹� �ȿ� sql���� �־ �ٽ� sql���� ������ error�߻�
		//�׸��� �� ������ �����ͺ��̽������̹Ƿ� Update
		
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
			
			//update notice �ڿ� ������ �ϳ� �ٿ� ��� SET�� �ٷ� notice �ڿ� ���� ���Ѵ�.
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
			st.setNString(1, title); //values�� index�� 1������ �����Ѵ�.
			st.setNString(2, content);
			st.setNString(3, files);
			st.setInt(4, id);
		
			int result = st.executeUpdate(); //preparestatment�� �̹� �ȿ� sql���� �־ �ٽ� sql���� ������ error�߻�
			//�׸��� �� ������ �����ͺ��̽������̹Ƿ� Update
			
			System.out.println(result);
			
			st.close();
			con.close();
		return 0;
	}
	
	public int delete(int id) {
		return 0;
	}

}
