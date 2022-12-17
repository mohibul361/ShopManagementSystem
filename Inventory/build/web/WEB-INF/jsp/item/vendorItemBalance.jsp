<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

<script type="text/javascript">
	var path = '${pageContext.request.contextPath}';
	$(function() {
		
	})
</script>
<h3>
	<spring:message code="label.vendorBalance" />
	<span style="background-color: lightblue; padding: 2px 5px;">${itemType.name}</span>
</h3>
<table class="table">
	<thead>
		<tr>
			<th><spring:message code="label.vendor" /></th>	
			<th><spring:message code="label.address" /></th>
			<th><spring:message code="label.quantityInBag" /></th>
			<th><spring:message code="label.quantityInKg" /></th>
		</tr>
	</thead>
	<c:forEach items="${vendorItemCounts}" var="vendorItemCount" varStatus="loop">
		<tbody>
			<tr>
				<td>${vendorItemCount.vendor.name}</td>
				<td>${vendorItemCount.vendor.address}</td>
				<td><fmt:formatNumber type="number" pattern="#,##0" value="${vendorItemCount.balanceQuantityInBag}"/></td>
				<td><fmt:formatNumber type="number" pattern="#,##0" value="${vendorItemCount.balanceQuantityInKg}"/></td>
			</tr>
		</tbody>
	</c:forEach>
</table>

