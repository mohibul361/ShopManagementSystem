<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<style type="text/css">
.page-header {
	border-bottom: 2px solid #DDD;
	margin: 10px 0px 20px 0px;
	padding: 5px 0px;
}

.table {
	width: 100%;
	border-spacing: 0px;
	border-collapse: collapse;
}

.text-left {
	text-align: left !important;
}

.text-right {
	text-align: right !important;
}

table>caption+thead>tr:first-child>td,.table>caption+thead>tr:first-child>th,.table>colgroup+thead>tr:first-child>td,.table>colgroup+thead>tr:first-child>th,.table>thead:first-child>tr:first-child>td,.table>thead:first-child>tr:first-child>th
	{
	border-top: 0;
}

.table>thead>tr>th {
	border-bottom: 2px solid #f4f4f4;
}

.table>thead>tr>th,.table>tbody>tr>th,.table>tfoot>tr>th,.table>thead>tr>td,.table>tbody>tr>td,.table>tfoot>tr>td
	{
	border-top: 1px solid #f4f4f4;
}

.table>thead>tr>th {
	vertical-align: bottom;
	border-bottom: 2px solid #ddd;
}

.table>tbody>tr>td,.table>tbody>tr>th,.table>tfoot>tr>td,.table>tfoot>tr>th,.table>thead>tr>td,.table>thead>tr>th
	{
	padding: 8px;
	line-height: 1.42857143;
	vertical-align: top;
	border-bottom: 1px solid #ddd;
	font-size: 14px;
}

th {
	text-align: left;
}

.table-striped>tbody>tr:nth-of-type(2n+1) {
	background-color: #f9f9f9;
}

/* 

th {
	text-align: left;
	color: #013D6C;
} */
button:hover {
	background-color: lightcyan;
}

.panel-footer>table>tbody>tr>td {
	text-align: right;
}

address {
	margin-bottom: 20px;
	font-style: normal;
	line-height: 1.42857143;
}
</style>
<script type="text/javascript">
	
</script>
<div>
	<h3 class="page-header">ডেলিভারি চালান</h3>
	<div style="width: 50%; float: left">
		<address>
			<b style="font-size: 18px">মেসার্স লক্ষ্মী ভাণ্ডার</b> <br> <b>প্রোঃ
				সুবাস কুমার শিকদার এন্ড ব্রাদার্স</b> <br> ধান ও চালের আড়ৎদার <br>
			মোকামঃ পুরাতন বাজার, মাগুরা। <br> মোবাইল নাম্বার : ০১৭১২-৭৩০৯৭৯,
			০১৭১২-৭৬২২৩০, ০১৭১৮-৬৫৬৮৬১
		</address>
	</div>
	<!-- <div style="width: 300px; float: right"> -->
	<div style="width: 25%; float: right">
		চালান নাম্বার : <b>${slip.slipNumber}</b> <br> চালান তারিখ:
		${slip.slipDate}
	</div>
	<div style="width: 25%; float: right">
		<b>কাস্টমার: ${slip.deliveredTo.name}</b> <br> ঠিকানা:
		${slip.deliveredTo.address}
	</div>

	<div>
		<table class="table table-striped">
			<thead>
				<tr>
					<th><spring:message code="label.no" /></th>
					<th><spring:message code="label.item" /></th>
					<th><spring:message code="label.quantityInBag" /></th>
					<th><spring:message code="label.quantityInKg" /></th>
					<th><spring:message code="label.unitPrice" /></th>
					<th>উপ মূল্য(টাকা)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${slip.slipItems}" var="slipItem" varStatus="loop">
					<tr>
						<td><c:out value="${loop.count}"></c:out></td>
						<td><c:out value="${slipItem.itemType.name}"></c:out></td>
						<td><fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${slipItem.quantityInBag}" /></td>
						<td><fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${slipItem.quantityInKg}" /></td>
						<td><fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${slipItem.unitPrice}" /></td>
						<td><fmt:formatNumber type="number" minFractionDigits="2"
								maxFractionDigits="2" value="${slipItem.subTotalPrice}" /></td>
					</tr>
				</c:forEach>
				<%-- <tr>
					<td></td>
					<td>মোট</td>
					<td class="text-right"><c:out value="${slip.totalQtyInBag}"></c:out></td>
					<td class="text-right"><c:out value="${slip.totalQtyInKg}"></c:out></td>
					<td></td>
					<td class="text-right"><c:out value="${slip.totalPrice}"></c:out></td>
				</tr> --%>

			</tbody>
		</table>
	</div>

	<div style="width: 50%; margin: 0 auto; padding: 20px">
		<table class="table">
			<tbody>
				<tr>
					<td>মোট উপ মূল্য</td>
					<td class="text-right"><fmt:formatNumber type="number"
							minFractionDigits="2" maxFractionDigits="2"
							value="${slip.totalPrice}" /></td>
				</tr>
				<tr>
					<td>(+)আগের পাওনা</td>
					<td class="text-right"><fmt:formatNumber type="number"
							minFractionDigits="2" maxFractionDigits="2"
							value="${slip.prevBalance}" /></td>
				</tr>
				<tr>
					<td><b>মোট পাওনা</b></td>
					<td class="text-right"><fmt:formatNumber type="number"
							minFractionDigits="2" maxFractionDigits="2"
							value="${slip.totalPrice + slip.prevBalance}" /></td>
				</tr>
				<tr>
					<td>(-)জমা</td>
					<td class="text-right"><fmt:formatNumber type="number"
							minFractionDigits="2" maxFractionDigits="2"
							value="${slip.deposit}" /></td>
				</tr>
				<tr>
					<td><b>সর্বমোট</b></td>
					<td class="text-right"><fmt:formatNumber type="number"
							minFractionDigits="2" maxFractionDigits="2"
							value="${slip.challanPrice}" /></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
