<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

#customerInfo {
	background: lightgoldenrodyellow;
	border: 1px solid lightgrey;
	width: 300px;
	float: left;
	padding: 10px;
}

#msg {
	border: 1px solid red;
	padding: 5px;
}
</style>

<script type="text/javascript">
	var path = '${pageContext.request.contextPath}';
	$(function() {
		$('#fromDateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());

		$('#toDateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
	});
	function approveSlips() 
	{	
		var selectedIds = getSelectedIds();
		if(selectedIds.length==0)
			{
				alert("চালান নির্বাচন করুন!");
				return;
			}
		json = {"userId": $("#userId").val(), "slipIds": selectedIds};
		console.log(json);
		$.ajax({
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			url : "${pageContext.request.contextPath}/approve",
			data : JSON.stringify(json),	
			type : 'POST',
			dataType : 'html',
			success : function(data) {
				console.log(data);
				alert(data);
			},
			error : function(data, status, er) {
				console.log(data);
				alert("error: " + data + " status: " + status + " er:" + er);
			}
		}); 

	}
	var allSlipIds = [
		               <c:forEach var="slip" items="${pendingSlipList}">
		               		${slip.id},
		               </c:forEach>
		               ];
	
	function getSelectedIds()
	{
		var selectedIds = new Array();
		var i, j=0;
		for (i = 0; i < allSlipIds.length; i++) 
		{
			if($('#ch'+allSlipIds[i]).is(":checked"))
			{
				selectedIds[j++] = allSlipIds[i];
			}
		}
		return selectedIds;
	}
</script>
<h3>
	<spring:message code="label.slipApproval" />
</h3>
<form method="post" action="/Inventory/findPendingSlips">
	<p>
		<label for="fromDateInput"><spring:message
				code="label.fromDate" /></label> <input name="fromDate" id="fromDateInput" />
	</p>
	<p>
		<label for="toDateInput"><spring:message code="label.toDate" /></label>
		<input name="toDate" id="toDateInput" />
	</p>
	<label></label> <input type="submit"
		value="<spring:message code="label.findPendingSlips"/>">
</form>
<c:if test="${fn:length(pendingSlipList) gt 0}">
	<input type="hidden" name="userId" id="userId" value="${sessionScope.userId}">
	<table class="table">
		<thead>
			<tr>
				<th></th>
				<th><spring:message code="label.slipNumber" /></th>
				<th><spring:message code="label.slipType" /></th>
				<th><spring:message code="label.slipDate" /></th>
				<th><spring:message code="label.totalQuantity" /></th>
				<th><spring:message code="label.remarks" /></th>
				<th><spring:message code="label.creationDate" /></th>
				<th><spring:message code="label.createdBy" /></th>
			</tr>
		</thead>
		<c:forEach items="${pendingSlipList}" var="slip" varStatus="loop">
			<tbody>
				<tr>
					<td><input id="ch${slip.id}" type="checkbox"></td>
					<td>${slip.slipNumber}</td>
					<td>${slip.slipType}</td>
					<td>${slip.slipDate}</td>
					<td>${slip.totalQtyInKg}</td>
					<td>${slip.remarks}</td>
					<td>${slip.creationDate}</td>
					<td>${slip.createdBy}</td>
				</tr>
			</tbody>
		</c:forEach>
	</table>
	<br />
	<input type="submit" onclick="approveSlips()"
		value="<spring:message code="label.approveSlips"/>">
</c:if>