<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath }"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>list.jsp</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js">
</script>
<script>
$(function(){
	//--게시물 클릭 시작--
	$("section>table tr>td").click(function(event){ // 특정행의 게시물을 클릭했을 때
		var board_no = $(event.target).parent().children("td.board_no").html().trim(); // 이렇게 board_no를 추출해서 servlet으로 보내는 것.
		$("section>form>input[name=board_no]").val(board_no);
		var $formObj = $("form");
		$formObj.attr("method", "get");
		$formObj.attr("action", "/boardmvc/detail");
		$formObj.submit();
		return false;
	});
	//--게시물 클릭 끝--
	
	//--검색버튼 클릭 시작--
	$("section>form>input[type=button]").click(function(){
		var $formObj = $("form");
		$formObj.attr("method", "get");
		$formObj.attr("action", "/boardmvc/list");
		$formObj.submit();
		return false;
	});
	//--검색버트 클릭 끝--
	
	
});
</script>
<style>
* {
	box-sizing: border-box;
}

section>table {
	border: 1px solid;
	border-collapse: collapse;
	width: 50%;
}

section>table tr>td {
	border: 1px solid;
}

section>table tr>td.board_no {
	width: 10%;
	text-align: right;
}

section>table tr>td.board_title {
	width: 40%;
}

section>table tr>td.board_writer {
	width: 20%;
}

section>table tr>td.board_dt {
	width: 20%;
}

section>table tr>td.board_cnt {
	width: 10%;
	text-align: right;
}
</style>
</head>
<body>
	<%@include file="/WEB-INF/views/header.jsp"%>
	<section>
		<form>
			<input type="hidden" name="board_no"> 
			<input type="search" name="word">
			<input type="button" value="검색">
		</form>
		<table>
			<c:forEach items="${requestScope.list}" var="board">
				<tr>
					<td class="board_no">${board.board_no}</td>
					<td class="board_title">
						<c:forEach begin="2" end="${board.level}" step="1">&#10149;</c:forEach>
						${board.board_title}
      				</td>
					<td class="board_writer">${board.board_writer}</td>
					<td class="board_dt">${board.board_dt}</td>
					<td class="board_cnt">${board.board_cnt}</td>
				</tr>
			</c:forEach>
		</table>
	</section>
	<%@include file="/WEB-INF/views/footer.jsp"%>
</body>
</html>