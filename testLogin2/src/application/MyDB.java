package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyDB {
	public MyDB() {
		
		
	}
	static int result =0;
	public int chkIdPw(String ID, String PW) { //중복 검사
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
		
		Data data = new Data();
		rooms room = new rooms();

		try {
			result = 0;
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT ID, PW, NAME from members");

			while (rs.next()) {
				
				String getID = rs.getString("ID");
				String getPW = rs.getString("PW");
				String getNAME = rs.getString("NAME");
				
				data.setmem(getNAME);
				System.out.println("ID/PW 정보 : ("+getID+","+getPW+")");//잘 가지고 왔나 확인
				
				if("Manager!".equals(ID)) { //관리자 ID, PW 입력시 result값은 3
					if("1111".equals(PW)) {
						result = 3;
						break;
					}
				}
				
				if(getID.equals(ID)) {
					result += 1; // 멤버 리스트에 ID가 있을경우 결과값에 +1
					if(getPW.equals(PW)) 
						result +=1; //멤버리스트에 ID가 있고 해당 멤버객체에 저장된 PW와  입력한PW가 같을경우 결과값에 +1
					break;
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
		System.out.println(result);
		return result;
		// 반복문이 끝나면 결과값을 반환
		// 즉 , 결과값 0: ID가 존재하지않음, 1: ID는 존재하지만 PW 미일치, 2: ID/PW 모두 만족

	} 
	
	public int R_chkId(String ID, String PW) { //회원가입에서의 아이디 중복 검사
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;

		try {
			result = 0;
			
			
			
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT ID from members");

			while (rs.next()) {

				
				String getID = rs.getString("ID");
				
				System.out.println("ID 정보 : ("+getID+")");//잘 가지고 왔나 확인

				if(getID.equals(ID)) {
					result += 1; // 멤버 리스트에 ID가 있을경우 결과값에 +1
					break;
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
		System.out.println(result);
		return result;
		// 반복문이 끝나면 결과값을 반환
		// 즉 , 결과값 0: ID가 중복되지 않은, 1: ID 중복

	} 
	
	public List<String> getStatus() throws SQLException{ //DB에서 데이터 리스트로 불러오기
		DBconn.connect();
		Statement st = null;		
		ResultSet rs = null;
		String tmpStr = "";
		  try{
			  st = DBconn.connect().prepareStatement("select ID, NAME, PW, EMAIL from members");
			  rs = st.executeQuery(tmpStr);
			  if(rs.next()){
				  List<String> statList = new ArrayList<String>();
				  do{
					  String getID = rs.getString("ID");
					  String getNAME = rs.getString("NAME");
					  String getPW = rs.getString("PW");
					  String getEMAIL = rs.getString("EMAIL");

					  System.out.println("정보 : ("+getID+getNAME+getPW+getEMAIL+")");//잘 가지고 왔나 확인
//					  tmpStr = "";
					  tmpStr = getID;
					  statList.add(getID+getNAME+getPW+getEMAIL);
					  }while(rs.next());
				  return statList;
			}else{
				return Collections.emptyList();
				}
			  }catch(SQLException e){
				  System.out.println("메시지 목록 구하기 실패 : " + e.getMessage());
				  }finally{
					  rs.close();
					  st.close();
					  DBconn.connect().close();		  
					  }
		  List<String> statList=null;
		  return statList;
		  }
	
	
	public void registUser(String R_ID, String R_name, String R_PW, String R_PW2, String R_email, String genderText) {
		DBconn.connect();
		Statement st = null;
		//ID는 중복되지 않도록 기본키임.
		String sql = "INSERT INTO members VALUES('" + '0' + "', '" + R_ID + "', '" + R_name+ "', '" + R_PW + "', '"+ R_PW2 +"' ,'"+R_email+"' ,'"+genderText+ "')";

		try {
			st = DBconn.connect().createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			System.out.println("insertDB 오류 : "+e.getMessage());
		}

		System.out.println("완료하였습니다.");
	}
	
	public int insertUser(String R_ID, String R_name, String R_PW, String R_PW2, String R_email, String genderText) {
		DBconn.connect();
		Statement st = null;
		//ID는 중복되지 않도록 기본키임.
		String sql = "INSERT INTO members VALUES('" + '0' + "', '" + R_ID + "', '" + R_name+ "', '" + R_PW + "', '"+ R_PW2 +"' ,'"+R_email+"' ,'"+genderText+ "')";

		try {
			st = DBconn.connect().createStatement();
			st.execute(sql);
		} catch (SQLException e) {
			System.out.println("insertDB 오류 : "+e.getMessage());
			return 0;
		}

		System.out.println("완료하였습니다.");
		return 1;
	}


	public int updateMember(String u_id, String u_name, String u_pw, String U_email) {
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = DBconn.connect().createStatement();
			st.execute("UPDATE members SET " + "ID='" + u_id + "', NAME=" + u_name + ", PW='"
					+ u_pw + "' " + "EMAIL=" + U_email + ";");
			return 1; //수정 성공
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("수정 완료");
		}
		//수정 실패
		return 0;
	}

	public int delUser(String d_id) {
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = DBconn.connect().createStatement();
			st.execute("DELETE FROM members WHERE ID= '" + d_id + "';");
			
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	return 0;
	}
	
	public static String infoPK = "";
	public void goMessage(String MessageGo, String formatedNow) {
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
//		String sql = "INSERT INTO chat VALUES('" + MessageGo + "')";
		String sql = "INSERT INTO chat(Name, message, fk, time)"+" VALUES('"+getUserName+"', '"+MessageGo+"', '"+infoPK+"', '"+formatedNow+"');"; //방장이름도 넣게 하기. + 외래키도 맞춰야함.
		try {
			st = DBconn.connect().createStatement();
			st.execute(sql);
			System.out.println("DB에 msg넣기 성공.");
		} catch (SQLException e) {
			System.out.println("insertDB 오류 : "+e.getMessage());
		}
	}
	
	public static String getUserName ="";	
	public void inputChatRoom(String c_name, String c_num, String c_main, String c_attr, String c_ip, String c_port) {
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
//		String sql = "INSERT INTO chat VALUES('" + MessageGo + "')";
		
		System.out.println(c_main);
		String sql = "INSERT INTO rooms VALUES('" + '0' + "', '" + c_name + "', '" + c_num+ "', '" + getUserName +"', '" + c_attr+"', '"+ c_ip + "', '"+ c_port +"');";
		try {
			st = DBconn.connect().createStatement();
			st.execute(sql);
			System.out.println("DB에 rooms정보 넣기 성공.");
		} catch (SQLException e) {
			System.out.println("insertDB 오류 : "+e.getMessage());
		}

		
	}

	public void inputYOUChatRoom(String c_name, String c_num, String c_main, String c_attr, String c_ip, String c_port) {
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
//		String sql = "INSERT INTO chat VALUES('" + MessageGo + "')";
		
		System.out.println(c_main);
		String sql = "INSERT INTO rooms VALUES('" + '0' + "', '" + c_name + "', '" + c_num+ "', '" + c_main +"', '" + c_attr+"', '"+ c_ip + "', '"+ c_port +"');";
		try {
			st = DBconn.connect().createStatement();
			st.execute(sql);
			System.out.println("DB에 rooms정보 넣기 성공.");
		} catch (SQLException e) {
			System.out.println("insertDB 오류 : "+e.getMessage());
		}

		
	}
	
	static String UserName ="";
	public String getName(String ID) { //중복 검사
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
		UserName ="";

		try {
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT ID, NAME from members");

			while (rs.next()) {				
				String getID = rs.getString("ID");
				String getNAME = rs.getString("NAME");
				
//				data.setmem(getNAME);
				System.out.println("ID/PW 정보 : ("+getID+","+getNAME+")");//잘 가지고 왔나 확인
				
				if(getID.equals(ID)) { //관리자 ID, PW 입력시 result값은 3
					UserName = getNAME;
					break;
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
		System.out.println(result);
		return UserName;
		// 반복문이 끝나면 결과값을 반환
		// 즉 , 결과값 0: ID가 존재하지않음, 1: ID는 존재하지만 PW 미일치, 2: ID/PW 모두 만족

	} 
	
	//IP번호와 Port번호 찾기
	static String RoomPort = "";
	public static String PortNUM=RoomPort;
	public static String RoomTitle;
	public String getIpPort(String roomPK) { //회원가입에서의 아이디 중복 검사
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;

		try {
			
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT PK, PORTnum, TITLE from rooms");

			while (rs.next()) {

				String getPk = rs.getString("PK");
				String getPort = rs.getString("PORTnum");
				String getTitle = rs.getString("TITLE");

				RoomTitle = getTitle;
				
				System.out.println("Port 정보 : ("+getPort+")");//잘 가지고 왔나 확인

				if(getPk.equals(roomPK)) {
					RoomPort = getPort;
					break;
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
//		String[] roomArray = new String[2];
//		roomArray[0] = RoomIP;
//		roomArray[1] = RoomPort;
		
		System.out.println(RoomPort);
		return RoomPort;

	} 
	
	public ArrayList<String> getMsgs() { //chat의 외래키의 대화기록들 불러오기 위해
		DBconn.connect();
		Statement st = null;
		ResultSet rs = null;
		
		List<String> msgs = new ArrayList<String>();
		try {
			
			st = DBconn.connect().createStatement();
			rs = st.executeQuery("SELECT Name, Message, fk, time from chat");

			while (rs.next()) {
				String getName = rs.getString("Name");
				String getMessage = rs.getString("Message");
				String getfk = rs.getString("fk");
				String gettime = rs.getString("time");

				System.out.println("message들 정보 : ("+getName+":"+getMessage+":"+gettime+"-> getfk"+")");//잘 가지고 왔나 확인
											
				if(getfk.equals(MyDB.infoPK)) {
						msgs.add(getName+":"+getMessage+":"+gettime); //멤버리스트에 ID가 있고 해당 멤버객체에 저장된 PW와  입력한PW가 같을경우 결과값에 +1
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
		
//		System.out.println(msgs);
		return (ArrayList<String>) msgs;
		
		// 반복문이 끝나면 결과값을 반환
		// 즉 , 결과값 0: ID가 존재하지않음, 1: ID는 존재하지만 PW 미일치, 2: ID/PW 모두 만족

	} 
		
}
