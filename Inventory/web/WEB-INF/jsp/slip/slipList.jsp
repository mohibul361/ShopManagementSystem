<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<c:url value="/resources/scripts/jquery.dataTables.js" />"></script>
<link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/mydataTable.css" />" rel="stylesheet">	
	
<style type="text/css">
.table {
	width: 100%;
	border-spacing: 0px;
	border-collapse: collapse;
}

.table>tbody>tr>td,.table>tbody>tr>th,.table>tfoot>tr>td,.table>tfoot>tr>th,.table>thead>tr>td,.table>thead>tr>th
	{
	padding: 8px;
	line-height: 1.42857;
	vertical-align: top;
	border-bottom: 1px solid #DDD;
	font-size: 13px;
}

.table>caption+thead>tr:first-child>td,.table>caption+thead>tr:first-child>th,.table>colgroup+thead>tr:first-child>td,.table>colgroup+thead>tr:first-child>th,.table>thead:first-child>tr:first-child>td,.table>thead:first-child>tr:first-child>th
	{
	border-top: 0px none;
}

th {
	text-align: left;
	color: #013D6C;
}

</style>
<script>
var path = '${pageContext.request.contextPath}';
var locale = '${pageContext.response.locale}';
var languageUrl="";
if(locale=='bn')
{
	languageUrl = path+"/dataTable/localization/bangla";	
}
$(function() {	
	$("#buttonSearch").click(function() 
	{
		$('#datatable').DataTable().destroy();
		$('#datatable').DataTable({
			"processing" : true,
			"pageLength" : 15,
			"lengthMenu" : [ 15, 30, 45 ],
			"serverSide" : true,
			"pagingType" : "full_numbers",
			"dom": '<"top"i>rt<"bottom"lp><"clear">',
			"language": {
            	"url": languageUrl
        	},
			"ajax" : {
				"url" : path + "/slipList",
				"type" : "POST",
				 "data" : {
				 	"slipType" : $('#slipType option:selected').val(),
					"slipNumber" : $("#slipNumber").val()
					 /* "fromDate": $("#fromDate").val(),
					 "toDate": $("#toDate").val() */
				}
			}
		});
	});
 
    $('#fromDate').datepicker({
		dateFormat : 'yy/mm/dd'
	}).datepicker("setDate", new Date());
    $('#toDate').datepicker({
		dateFormat : 'yy/mm/dd'
	}).datepicker("setDate", new Date());
});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3><spring:message code="label.slipList"/>
<a style="float: right; text-decoration: none;" href="${contextPath}/export/slipList"><spring:message code="label.exportSlipList"/></a></h3>
<p>
	<span><spring:message code="label.slipType"/></span> 
	<select name="slipType" id="slipType">
		<option value="ALL"><spring:message code="label.all"/></option>		
		<c:forEach var="slipType" items="${slipTypeEnums}">			
			<option value="${slipType}"><spring:message code="${slipType.messageCode }"/></option>
		</c:forEach>
	</select>
	&nbsp;<span><spring:message code="label.slipNumber"/></span>
	<input type="text" id="slipNumber" placeholder="<spring:message code="label.slipNumber"/>">
	<%--<span>Date: <input type="text" name="fromDate" id="fromDate" />To <input type="text" name="toDate" id="toDate" /></span> --%>
	<input type="submit" id="buttonSearch" value="<spring:message code="label.search"/>" />
</p>

<table id="datatable" class="table display compact" style="padding-top: 10px;">
	<thead>
		<tr>
			<th><spring:message code="label.slipNumber"/></th>
			<th><spring:message code="label.slipType"/></th>
			<th><spring:message code="label.slipDate"/></th>
			<th><spring:message code="label.vendorOrCustomer"/></th>
			<th><spring:message code="label.totalQuantity"/></th>
			<th><spring:message code="label.remarks"/></th>
			<th><spring:message code="label.creationDate"/></th>
			<th><spring:message code="label.createdBy"/></th>
			<th><spring:message code="label.action"/></th>
		</tr>
	</thead>
	<%-- <c:forEach var="slip" items="${slipList}">
		<tbody>
			<tr>
				<td><c:out value="${slip.slipNumber}"></c:out></td>
				<td><c:out value="${slip.creationDate}"></c:out></td>
				<td><c:out value="${slip.createdBy.userName}"></c:out></td>
				<td><c:out value="${slip.remarks}"></c:out></td>
				<td><a href="incomingSlip/${slip.id}">Edit</a></td>
			</tr>
		</tbody>
	</c:forEach> --%>
</table>
