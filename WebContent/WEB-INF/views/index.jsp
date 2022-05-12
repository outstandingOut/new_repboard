<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인(index.jsp)</title>
</head>
<body>
	<!-- <header></header> -->
	<%@include file="/WEB-INF/views/header.jsp"%> <!-- 사실 상 http://localhost:8888/boardmvc/WEB-INF/views/header.jsp 와 같은 경로를 지정해준 것. (서버사이드이므로??)  -->
	<section>
		<h1>답변형 게시판 MVC실습</h1>
	</section>
	<!-- <footer></footer> -->
	<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>