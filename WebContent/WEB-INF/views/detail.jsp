<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<c:set var="board" value="${requestScope.board}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list.jsp</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(function(){
	//--답글쓰기 클릭 시작--
	$("section>div.detail>a.reply").click(function(){
		$("section>div.reply").show();
		$("section>div.remove").hide();
		$("section>div.modify").hide();
		return false;
	});
	//--답글쓰기 클릭 끝--
	
	//--수정링크 클릭 시작--
	$("section>div.detail>a.modify").click(function(){
		$("section>div.reply").hide();
		$("section>div.remove").hide();
		$("section>div.modify").show(); //수정하기 창띄우기
		return false;
	});
	//--수정링크 클릭 끝--
	
	//--삭제링크 클릭 시작--
	$("section>div.detail>a.remove").click(function(){
		$("section>div.reply").hide();
		$("section>div.modify").hide(); 
		$("section>div.remove").show(); //삭제하기 창띄우기
		return false;
	});
	//--삭제링크 클릭 끝--
	
});
</script>
<style>
* {
	box-sizing: border-box;
}
</style>
</head>
<body>
	<%@include file="/WEB-INF/views/header.jsp"%>
	<section>

		<div class="detail">
			글번호 :${board.board_no}<br> 제목 : ${board.board_title}<br>
			작성자 :${board.board_writer}<br> 작성일자 :${board.board_dt }<br>
			조회수:${board.board_cnt }<br>
			<hr>
			<a href="#" class="reply">답글쓰기</a> <a href="#" class="modify">수정</a>&nbsp;&nbsp;
			<a href="#" class="remove">삭제</a>
		</div>
		<div class="reply" style="display: none;">
			<form method="post" action="${contextPath }/reply">
				<input type="hidden" name="parent_no" value="${board.board_no}">
				답글제목: <input type="text" name="board_title"><br> 작성자 :
				<input type="text" name="board_writer"><br> 비밀번호:<input
					type="password" name="board_pwd" required><br> <input
					type="submit" value="답글쓰기">
			</form>

		</div>
		<div class="modify" style="display: none;">
			<form method="post" action="${contextPath }/modify">
				글번호 <input type="text" name="board_no" value="${board.board_no}"
					readonly><br> 제목 : <input type="text"
					name="board_title" value="${board.board_title}"><br>
				작성자 :${board.board_writer}<br> 작성일자 :${board.board_dt }<br>
				조회수: ${board.board_cnt }<br> 기존 비밀번호 : <input type="password"
					name="certify_board_pwd" required><br> 변경할 비밀번호:<input
					type="password" name="board_pwd" required><br> <input
					type="submit" value="수정">
			</form>
		</div>
		<div class="remove" style="display: none;">
			<form method="post" action="${contextPath }/remove">
				<input type="hidden" name="board_no" value="${board.board_no}">
				기존 비밀번호 : <input type="password" name="certify_board_pwd" required><br>
				<input type="submit" value="삭제">
			</form>
		</div>
	</section>
	<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>