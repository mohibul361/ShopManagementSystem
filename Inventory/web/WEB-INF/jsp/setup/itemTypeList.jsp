<%@ page pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="<c:url value="/resources/scripts/jquery.dataTables.js" />"></script>
<link href="<c:url value="/resources/css/jquery.dataTables.css" />"
	rel="stylesheet" />
<link href="<c:url value="/resources/css/mydataTable.css" />"
	rel="stylesheet" />
<script>
	var path = '${pageContext.request.contextPath}';
	var locale = '${pageContext.response.locale}';
	var languageUrl = "";
	if (locale == 'bn') {
		languageUrl = path + "/dataTable/localization/bangla";
	}
	$(document).ready(function() {
		$('#datatable').DataTable({
			"lengthMenu" : [ 15, 25, 35 ],
			"language" : {
				"url" : languageUrl
			}
		});
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<h3>
	<spring:message code="label.itemTypeList" />
	<a href="${contextPath}/itemType" style="float: right"><img style="vertical-align:middle" src="<c:url value="/resources/images/add.png" />">&nbsp;<spring:message code="label.addNew" /></a>
</h3>
<table id="datatable" class="display compact" style="padding-top: 10px;">
	<thead>
		<tr>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.code" /></th>
			<th><spring:message code="label.lowMark" /></th>
			<th><spring:message code="label.highMark" /></th>
			<th><spring:message code="label.description" /></th>
			<th><spring:message code="label.action" /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="itemType" items="${itemTypeList}">
			<tr>
				<td><c:out value="${itemType.name}"></c:out></td>
				<td><c:out value="${itemType.code}"></c:out></td>
				<td style="text-align:center"><c:out value="${itemType.lowMark}"></c:out></td>
				<td style="text-align:center"><c:out value="${itemType.highMark}"></c:out></td>
				<td><c:out value="${itemType.description}"></c:out></td>
				<td><a href="${contextPath}/itemType/${itemType.id}"><spring:message
							code="label.edit" /></a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
