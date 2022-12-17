<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- <script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css"> -->
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
	vertical-align: middle;
	/* border-bottom: 1px solid #DDD; */
	border: 1px solid #DDD;
	font-size: 13px;
	text-align: center;
}

.table>caption+thead>tr:first-child>td,.table>caption+thead>tr:first-child>th,.table>colgroup+thead>tr:first-child>td,.table>colgroup+thead>tr:first-child>th,.table>thead:first-child>tr:first-child>td,.table>thead:first-child>tr:first-child>th
	{
	/* border-top: 0px none; */
	
}

th {
	text-align: left;
	color: #013D6C;
}

button:hover {
	background-color: lightcyan;
}
</style>
<script type="text/javascript">
	
</script>
<h3>
	<spring:message code="label.dailyTransactions" />
</h3>
<div>
	<div class="panel panel-default" style="float: left; width: 50%">
		<div class="panel-heading">
			<spring:message code="label.purchaseInfo" />
		</div>
		<table class="table">
			<thead>
				<tr>
					<th><spring:message code="label.vendor" /></th>
					<th><spring:message code="label.amount" /></th>
					<th><spring:message code="label.bag" /></th>
					<th><spring:message code="label.kg" /></th>
				</tr>
			</thead>
			<c:forEach items="${incomingSlipList}" var="slip" varStatus="loop">
				<tbody>
					<tr>
						<td>${slip.vendor.name}</td>
						<td>${slip.totalPrice}</td>
						<td>${slip.totalQtyInBag}</td>
						<td>${slip.totalQtyInKg}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>

	</div>
	<div class="panel panel-default" style="width: 48%; float: right">
		<div class="panel-heading">
			<spring:message code="label.saleInfo" />
		</div>
		<table class="table">
			<thead>
				<tr>
					<th><spring:message code="label.recipient" /></th>
					<th><spring:message code="label.amount" /></th>
					<th><spring:message code="label.bag" /></th>
					<th><spring:message code="label.kg" /></th>
				</tr>
			</thead>
			<c:forEach items="${deliverySlipList}" var="slip" varStatus="loop">
				<tbody>
					<tr>
						<td>${slip.deliveredTo.name}</td>
						<td><fmt:formatNumber type="number" pattern="#,##0"
								value="${slip.totalPrice}" /></td>
						<td><fmt:formatNumber type="number" pattern="#,##0"
								value="${slip.totalQtyInBag}" /></td>
						<td><fmt:formatNumber type="number" pattern="#,##0"
								value="${slip.totalQtyInKg}" /></td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
</div>
<div style="clear:both"></div>
<div style="margin-top:10px;">
	<div class="panel panel-default" style="width: 50%; float: left">
		<div class="panel-heading">
			<spring:message code="label.paymentInfo" />
		</div>
		<table class="table">
			<thead>
				<tr>
					<th><spring:message code="label.recipient" /></th>
					<th><spring:message code="label.amount" /></th>
					<th><spring:message code="label.remarks" /></th>					
				</tr>
			</thead>
			<c:forEach items="${paymentList}" var="transaction" varStatus="loop">
				<tbody>
					<tr>
						<td>${transaction.vendor.name}</td>						
						<td><fmt:formatNumber type="number" pattern="#,##0"
								value="${transaction.amount}" /></td>
						<td>${transaction.remarks}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
	<div class="panel panel-default" style="width: 48%; float: right">
		<div class="panel-heading">
			<spring:message code="label.collectionInfo" />
		</div>
		<table class="table">
			<thead>
				<tr>
					<th><spring:message code="label.recipient" /></th>
					<th><spring:message code="label.amount" /></th>
					<th><spring:message code="label.remarks" /></th>
				</tr>
			</thead>
			<c:forEach items="${receiveList}" var="transaction" varStatus="loop">
				<tbody>
					<tr>
						<td>${transaction.recipient.name}</td>						
						<td><fmt:formatNumber type="number" pattern="#,##0"
								value="${transaction.amount}" /></td>
						<td>${transaction.remarks}</td>
					</tr>
				</tbody>
			</c:forEach>
		</table>
	</div>
</div>