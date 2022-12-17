<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- <script type="text/javascript">
	$(function() {
		$("#logout-link").click(function() {
			$("#logout-form").submit();
		});
	})
</script> -->
<style>
.locale {
	position: absolute;
	top: 10px;	
	float: right;
	font-size: 14px;
	background-color: lightgreen;
	padding: 2px;
	right: 2px;
}
#header {
	/* border: 1px solid #EEE; */
	width: 100%;
	/* background-color: #EEE; */
	text-align: center;
	/* border-top-left-radius: 10px;
	border-top-right-radius: 10px; */
	/* font-size: 20px; */
	height: 160px; 
	/* background-image : url('resources/images/banner_final.jpg'); 
	background-repeat : no-repeat;
	background-size : cover; */
	font-family: monotype carsiva;
	position: relative;
	
}

</style>
<c:url var="logoutAction" value="/logout"></c:url>
<div id="header">
	
	<div>
	<c:choose>
		<c:when test="${pageContext.response.locale eq 'bn'}">
			<span class="locale"> <a href="?lang=en">English</a></span>
		</c:when>
		<c:otherwise>
			<span class="locale"> <a href="?lang=bn">বাংলা</a></span>
		</c:otherwise>
	</c:choose>
		<%-- <h2><spring:message code="label.header" /></h2> --%>		
		<img style="height:160px; width:1200px" src="<c:url value="/resources/images/banner_final.jpg" />">
	</div>
	<c:if test="${sessionScope.userName != null}">
		<div
			style="position: absolute; top: 50px; padding: 2px; background-color: lightcoral; right: 2px; font-size: 16px">
			Hello,
			<c:out value="${sessionScope.userName}"></c:out>
			!
			<form style="margin: 0; padding: 0; display: inline;"
				id="logout-form" action="${logoutAction}" method="post">
				<a id="logout-link" style="color: white" href="#">Logout</a>
			</form>
		</div>
	</c:if>
	<div style="padding: 10px 0px; position: absolute; top: 5px; left:40%">
		<div style="font-size:26px; font-weight: bold;">${sessionScope.company.name}</div>
		<div style="font-size:20px; font-weight: bold;">${sessionScope.company.proprietor}</div>
		<div style="font-size:16px; font-weight: bold;">${sessionScope.company.businessInfo}</div>
		<div style="font-size:12px; font-weight: bold;">${sessionScope.company.address}</div>
	</div>
</div>