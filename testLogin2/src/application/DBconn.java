package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//DB를 이용하기 위한 작업
public class DBconn {
	public static Connection connect() {
		Connection connection = null;

		String url = "jdbc:mariadb://localhost:3307/javatest";
		String user = "root";
		String pwd = "1234";
		
		try {
			connection = DriverManager.getConnection(url, user, pwd);					
		} catch (SQLException e) {
			System.out.println("DB 연결 실패 : "+e.getMessage());
		}
		return connection;
	}
	
	public int chkIdPw(String chkID, String chkPW) { //로그인할 때 id, pw체크
		
		connect();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = connect().createStatement();
			rs = st.executeQuery("SELECT ID, PW from members");

			while (rs.next()) {
				String getID = rs.getString("ID");
				String getPW = rs.getString("PW");
				
				System.out.println("ID/PW 정보 : ("+getID+","+getPW+")");//잘 가지고 왔나 확인
				int result = 1;
				
				if(getID.equals(chkID)) {
					result += 1; // 멤버 리스트에 ID가 있을경우 결과값에 +1
					if(getPW.equals(chkPW)) 
						result +=1; //멤버리스트에 ID가 있고 해당 멤버객체에 저장된 PW와  입력한PW가 같을경우 결과값에 +1
				return result;
				}
			}
		}catch (Exception e) {
			System.out.println("select 오류1 : "+e.getMessage());
		}finally {
			try {
				rs.close();
			}catch (SQLException e) {
				System.out.println("select 오류2 : "+e.getMessage());
				e.printStackTrace();
			}
		}
	
		return 0;
	}
}
