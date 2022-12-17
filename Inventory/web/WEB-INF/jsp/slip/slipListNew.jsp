<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.8/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8" src="//cdn.datatables.net/1.10.8/js/jquery.dataTables.js"></script>
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
	font-size: 12px;
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
<script type="text/javascript">
	$(function() {
		$("#search").click(function(){
			var new_url = "";
			var id = $('#slipType option:selected').val();
		    value = "?slipType=" + id;
		    new_url = "${pageContext.request.contextPath}"+ value;
		    location.href = new_url;
		});
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Slip List <a style="float: right; text-decoration: none;" href="${pageContext.request.contextPath}/export/slipList">Export Slip List</a></h3>
<form action="${pageContext.request.contextPath}/slipListNew" method="post">
<p>
	<label>Slip Type</label> 	
	<select name="slipType" id="slipType">
		<c:forEach var="slipType" items="${slipTypeEnums}">
			<option value="${slipType}">${slipType}</option>
		</c:forEach>
	</select>
	<input type="submit" id="search" value="Submit" />
</p>
</form>
<table class="table">
	<thead>
		<tr>
			<th><spring:message code="label.slipNumber"/></th>
			<th>Slip Type</th>
			<th>Item Count</th>
			<th><spring:message code="label.remarks"/></th>
			<th>Creation Date</th>
			<th>Created By</th>
			<th><spring:message code="label.action"/></th>
		</tr>
	</thead>
	<c:forEach var="slip" items="${slipList}">
		<tbody>
			<tr>
				<td><c:out value="${slip.slipNumber}"></c:out></td>
				<td><c:out value="${slip.slipType}"></c:out></td>				
				<td><c:out value="${slip.slipItemTotal}"></c:out></td>
				<td><c:out value="${slip.remarks}"></c:out></td>
				<td><c:out value="${slip.creationDate}"></c:out></td>
				<td><c:out value="${slip.createdBy.userName}"></c:out></td>
				
				<%-- <td><a href="incomingSlip/${slip.id}">view</a></td> --%>
				<c:choose>
					<c:when test="${slip.slipType eq 'INCOMING'}">
						<td><a href=incomingSlip/update/${slip.id}>view</a></td>
					</c:when>
					<c:when test="${slip.slipType eq 'QC_INSPECTION'}">
						<td><a href="qcSlip/update/${slip.id}">view</a></td>
					</c:when>
					<c:when test="${slip.slipType eq 'DELIVERY'}">
						<td><a href="deliverySlip/update/${slip.id}">view</a></td>
					</c:when>
					<c:when test="${slip.slipType eq 'RETURN'}">
						<td><a href="returnSlip/update/${slip.id}">view</a></td>
					</c:when>
					<c:when test="${slip.slipType eq 'REQUISITION'}">
						<td><a href="requisitionSlip/update/${slip.id}">view</a></td>
					</c:when>
					<c:when test="${slip.slipType eq 'SERVICE'}">
						<td><a href="serviceSlip/update/${slip.id}">view</a></td>
					</c:when>
					<c:otherwise>
						<td><a href="replacementSlip/update/${slip.id}">view</a></td>
					</c:otherwise>
			</c:choose>
			</tr>
		</tbody>
	</c:forEach>
</table>
<table style="font-size: 12px">
	<tr>
		<%--For displaying Previous link except for the 1st page --%>
		<c:if test="${currentPage != 1}">
			<td><a href="/Inventory/slipListNew?page=${currentPage - 1}">Previous</a></td>
		</c:if>
		<%--For displaying Page numbers. 
    	The when condition does not display a link for the current page--%>
		<c:forEach begin="1" end="${noOfPages}" var="i">
			<c:choose>
				<c:when test="${currentPage eq i}">
					<td>${i}</td>
				</c:when>
				<c:otherwise>
					<td><a href="/Inventory/slipListNew?page=${i}">${i}</a></td>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		<%--For displaying Next link --%>
		<c:if test="${currentPage lt noOfPages}">
			<td><a href="/Inventory/slipListNew?page=${currentPage + 1}">Next</a></td>
		</c:if>
	</tr>
</table>
