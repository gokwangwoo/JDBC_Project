package ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class Program4 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		
		int id = 73;
		
		//update notice 뒤에 공백을 하나 붙여 줘야 SET이 바로 notice 뒤에 붙지 못한다.
		String url = "jdbc:oracle:thin:@localhost:1521/xepdb1";
		String sql = "DELETE NOTICE where id =?";
		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection(url, "NEWLEC","1234");
		//Statement st = con.createStatement();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
	
		int result = st.executeUpdate(); //preparestatment는 이미 안에 sql문이 있어서 다시 sql문을 보내면 error발생
		//그리고 이 동작은 데이터베이스조작이므로 Update
		
		System.out.println(result);
		
		st.close();
		con.close();

	}

}
