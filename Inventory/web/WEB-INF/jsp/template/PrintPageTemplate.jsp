<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<title><tiles:getAsString name="title" /></title>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<%-- <script src="<c:url value="/resources/scripts/jquery-1.11.1.min.js" />"></script> --%>
<!-- here using jquery ui version 1.11.0 -->
<script src="<c:url value="/resources/scripts/jquery.js" />"></script>
<script src="<c:url value="/resources/scripts/jquery-ui.min.js" />"></script>
<link href="<c:url value="/resources/css/jquery-ui.min.css" />" rel="stylesheet">
</head>
<style>
body
{	
	font-family: SolaimanLipi verdana;
	font-size: 14px;
	width: 100%;
	margin: 10px auto 0px;
	background: #fff;
}
</style>

<body onload="window.print();">
	<div class="container">		
		<!-- Body Page -->
		<div id="main-container"
			style="clear: both; height: auto; min-height: 600px; ">
			<tiles:insertAttribute name="body" />
		</div>
	</div>
</body>
</html>