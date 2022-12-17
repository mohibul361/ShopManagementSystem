<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link href="<c:url value="/resources/css/list_group.css" />"
	rel="stylesheet">
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

#customerInfo{
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
	function deleteOB(obId)
	{
		if(confirm('আপনি কি এই ওপেনিং ব্যালেন্স ডিলিট করতে চান ?')){
			var url = '${pageContext.request.contextPath}/transaction/delete/'+obId+"/2";
			window.location.href = url;
		}
	}
	$(function() {
		$("#createPayment").click(function() {
			$("#showPayment").css("display", "block");
		});

		$('#dateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());

		$("#recipient")
				.change(
						function() {
							var id = $('option:selected', this).val();
							if (!id) {
								$("#customerInfo").html("");
								return;
							}

							reqUrl = "${pageContext.request.contextPath}/customerInfo/"
									+ id;

							$.ajax({
								url : reqUrl,
								type : 'GET',
								dataType : 'html',
								success : function(data) {
									$("#customerInfo").html(data);									
								},
								error : function(data, status, er) {
									console.log(data);
									alert("error: " + data + " status: "
											+ status + " er:" + er);
								}
							});
						});
	})
</script>
<h3>
	<spring:message code="label.openingBalance" />
</h3>
<table class="table">
	<thead>
		<tr>
			<th><spring:message code="label.vendor" /></th>
			<th><spring:message code="label.recipient" /></th>	
			<th><spring:message code="label.transactionNo" /></th>
			<th><spring:message code="label.date" /></th>
			<th><spring:message code="label.amount" /></th>
			<th><spring:message code="label.remarks" /></th>			
			<th>Action</th>
		</tr>
	</thead>
	<c:forEach items="${openingBalanceList}" var="openingBalance" varStatus="loop">
		<tbody>
			<tr>
				<td><c:if test="${openingBalance.vendor != null}">${openingBalance.vendor.name}</c:if></td>
				<td><c:if test="${openingBalance.recipient != null}">${openingBalance.recipient.name}</c:if></td>
				<td>${openingBalance.transactionNo}</td>
				<td><fmt:formatDate type="date" value="${openingBalance.date}" /></td>
				<td><fmt:formatNumber type="number" pattern="#,##0" value="${openingBalance.amount}"/></td>
				<td>${openingBalance.remarks}</td>
				<td><a href="${pageContext.request.contextPath}/openingBalance/update/${openingBalance.id}">View</a>&nbsp;|&nbsp;<a href="#" onclick="deleteOB(${openingBalance.id})">Delete</a></td>
			</tr>
		</tbody>
	</c:forEach>
</table>
<table style="font-size: 12px">
	<tbody>
		<tr>
			<%--For displaying Previous link except for the 1st page --%>
			<c:if test="${currentPage != 1}">
				<td><a href="${pageContext.request.contextPath}/openingBalances?page=${currentPage - 1}">Previous</a></td>
			</c:if>
			<%--For displaying Page numbers. 
    	The when condition does not display a link for the current page--%>
			<c:forEach begin="1" end="${noOfPages}" var="i">
				<c:choose>
					<c:when test="${currentPage eq i}">
						<td><span class="badge">${i}</span></td>
					</c:when>
					<c:otherwise>
						<td><a href="${pageContext.request.contextPath}/openingBalances?page=${i}">${i}</a></td>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<%--For displaying Next link --%>
			<c:if test="${currentPage lt noOfPages}">
				<td><a href="${pageContext.request.contextPath}/openingBalances?page=${currentPage + 1}">Next</a></td>
			</c:if>
		</tr>
	</tbody>
</table>
