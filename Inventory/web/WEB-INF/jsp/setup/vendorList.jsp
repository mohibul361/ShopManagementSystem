<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script src="<c:url value="/resources/scripts/jquery.dataTables.js" />"></script>
<link href="<c:url value="/resources/css/jquery.dataTables.css" />"
	rel="stylesheet"/>
<link href="<c:url value="/resources/css/mydataTable.css" />" rel="stylesheet"/>
<style>
</style>
<script>
	function deleteVendor(vendorId) {
		if (confirm('Do you want to delete this Vendor ?')) {
			var url = '${pageContext.request.contextPath}/vendor/delete/'
					+ vendorId;
			window.location.href = url;
		}
	}
	var path = '${pageContext.request.contextPath}';
	var locale = '${pageContext.response.locale}';
	var languageUrl="";
	if(locale=='bn')
	{
		languageUrl = path+"/dataTable/localization/bangla";	
	}
	$(function() {
		$('#datatable').DataTable({
			"processing" : true,
			"pageLength" : 15,
			"lengthMenu" : [ 15, 25, 35 ],
			"serverSide" : true,
			"pagingType" : "full_numbers",
			/* "dom" : '<"top"i>rt<"bottom"lp><"clear">', */
			"language": {
                "url": languageUrl
            },
			"ajax" : {
				"url" : path + "/vendor/list",
				"type" : "POST"
			}
		});
	/* 	oTable = $('#datatable').DataTable();
		oTable.draw();
 */
		$('#fromDate').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
		$('#toDate').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
	});
</script>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:if test="${not empty message}">
	<div class="message green">${message}</div>
</c:if>
<h3>
	<spring:message code="label.vendorList" />
	<a href="${contextPath}/vendor" style="float:right"><img style="vertical-align:middle" src="<c:url value="/resources/images/add.png" />">&nbsp;<spring:message code="label.addNew"/></a>
</h3>
<table id="datatable" class="display compact" style="padding-top: 10px;">
	<thead>
		<tr>
			<th><spring:message code="label.name" /></th>
			<th><spring:message code="label.mobileNo1" /></th>
			<th><spring:message code="label.mobileNo2" /></th>
			<th><spring:message code="label.address" /></th>
			<th><spring:message code="label.action" /></th>
		</tr>
	</thead>
</table>
