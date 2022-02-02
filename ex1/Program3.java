package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program3 {

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

	}

}
