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
	private String url = "jdbc:oracle:thin:@localhost:1521/xepdb1"; //url�� �������� �ϳ��� ���� ������
	private String uid = "NEWLEC";
	private String pwd = "1234";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	
	public List<Notice> getList(int page) throws ClassNotFoundException, SQLException{ //List�� ������ �Ӽ�����(�÷��� id, title, writerId ���)
		//�̰͵��� ���������� List���� ū ������ ���𰡰� �ʿ��ϴ�.
		//�װ� �ٷ� app.entity�� Notice.java�̴�.
		
		int start = 1 + (page-1)*10; // 1, 11, 21, 21 ....
		int end = 10*page; // 10, 20, 30, 40 ....
		
		
		String sql = "select * from NOTICE_VIEW where NUM between ? and ?"; //���� view�� ���� NOTICE_VIEW�� ���ؼ� �̹� ���ĵ� �����͸� �̰� �ߴ�.
		
		Class.forName(driver);
		//con, st, rs ���� �͵��� �� ������ ���� ���� �Ǿ�� �Ѵ�.
		Connection con = DriverManager.getConnection(url, uid, pwd);
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, start);
		st.setInt(2, end);
		ResultSet rs = st.executeQuery();
		
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
	//�̷��� �� ��� �Խù��� �ִ��� ���� ���� ������ ����
	//Scalar ���� ���´ٰ� �Ѵ�.
	public int getCount() throws ClassNotFoundException, SQLException {
		int count = 0;
		
		String sql = "select COUNT(ID) AS COUNT from NOTICE"; //select ���� ���ؼ� �����͸� �̴´�.
		
		Class.forName(driver);
		//con, st, rs ���� �͵��� �� ������ ���� ���� �Ǿ�� �Ѵ�.
		Connection con = DriverManager.getConnection(url, uid, pwd);
		Statement st = con.createStatement();

		ResultSet rs = st.executeQuery(sql); //result set���� �޴°� ���� �� �ϳ��� �� ���� �޴� ���̴�.
		
		
		if(rs.next())
			count = rs.getInt("COUNT");
			
		
		
		rs.close();
		st.close();
		con.close();
		
		return count;
		
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
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setNString(1, title); //values�� index�� 1������ �����Ѵ�.
		st.setNString(2, writerId);
		st.setNString(3, content);
		st.setNString(4, files);
	
		int result = st.executeUpdate(); //preparestatment�� �̹� �ȿ� sql���� �־ �ٽ� sql���� ������ error�߻�
		//�׸��� �� ������ �����ͺ��̽������̹Ƿ� Update
		
		st.close();
		con.close();
		return result;
	}
	
	public int update(Notice notice) throws ClassNotFoundException, SQLException{
			
			String title = notice.getTitle();
			String content = notice.getContent();
			String files = notice.getFiles();
			int id = notice.getId();
			
			//update notice �ڿ� ������ �ϳ� �ٿ� ��� SET�� �ٷ� notice �ڿ� ���� ���Ѵ�.
			
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
			st.setNString(1, title); //values�� index�� 1������ �����Ѵ�.
			st.setNString(2, content);
			st.setNString(3, files);
			st.setInt(4, id);
		
			int result = st.executeUpdate(); //preparestatment�� �̹� �ȿ� sql���� �־ �ٽ� sql���� ������ error�߻�
			//�׸��� �� ������ �����ͺ��̽������̹Ƿ� Update
			
			
			st.close();
			con.close();
			return result;
	}
	
	public int delete(int id) throws ClassNotFoundException, SQLException {
		
		
		//update notice �ڿ� ������ �ϳ� �ٿ� ��� SET�� �ٷ� notice �ڿ� ���� ���Ѵ�.
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "DELETE NOTICE where id =?";
		
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, uid, pwd);
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
	
		int result = st.executeUpdate(); //preparestatment�� �̹� �ȿ� sql���� �־ �ٽ� sql���� ������ error�߻�
		//�׸��� �� ������ �����ͺ��̽������̹Ƿ� Update
		
		st.close();
		con.close();
		return result;
	}


}
