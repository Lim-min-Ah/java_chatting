# java_chatting(개인 과제 프로젝트)
<h3>[DB 연결 구도]</h3>
<img width="475" alt="image" src="https://github.com/Lim-min-Ah/java_chatting/assets/87717513/6d6745d8-feee-41e5-a3c8-d98e4a71a966">
<br>
<pre>
  mariaDB를 통합적으로 관리해주는 HeidiSQL를 사용해 데이터베이스를 관리한다.
</pre>
<br>

<h3>[Table 정보]</h3>
<img width="482" alt="image" src="https://github.com/Lim-min-Ah/java_chatting/assets/87717513/69e5c095-82e3-4367-a8d3-2393dae7a186">
<br>
<pre>
chat : 채팅 기록 테이블
members : 회원 테이블
rooms : 채팅 목록 테이블
</pre>
<br>

<h3>[진행 구성도]</h3>
<img width="470" alt="image" src="https://github.com/Lim-min-Ah/java_chatting/assets/87717513/592c57c7-5f1f-4f57-a4b0-62989ddb7aed">
<pre>
 컨트롤러를 통해 사용자와 직접적으로 상호작용하여 사용자가 Button을 클릭할 시에 서비스를 통해 구현한 기능을 컨트롤러에 할당하여 이에 맞는 이벤트를 컨트롤러에서 수행할 수 있도록 한다.
</pre>
<br><br>
<hr>
자바 채팅 프로젝트 
<br>
<img width="283" alt="image" src="https://github.com/Lim-min-Ah/java_chatting/assets/87717513/9507d584-42b4-4a6e-a04d-78ab8e13d622">
<br>
<img width="349" alt="image" src="https://github.com/Lim-min-Ah/java_chatting/assets/87717513/ee942f01-f105-4a50-84c5-5eb0979015bc">
<br>
DB의 room테이블에서 가져온 테이블 뷰 와 입장, 방 만들기, 새로고침, 친구 목록 버튼 총 5가지로 구성되어 있음
<br>
<img width="408" alt="image" src="https://github.com/Lim-min-Ah/java_chatting/assets/87717513/1605800e-bd96-4a96-9ad6-d624dfba925a">
<br>
회원들이 대화를 나누고 있는 그림으로 말풍선 아래 시간과 과거 채팅 기록을 띄울 수 있음
