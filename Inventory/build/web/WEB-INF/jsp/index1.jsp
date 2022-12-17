<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!-- <script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css"> -->
<link href="<c:url value="/resources/css/list_group.css" />" rel="stylesheet">

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
	font-size: 14px;
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
</style>
<script type="text/javascript">
	
</script>
<!-- <h3>Item Status</h3> -->
<div class="panel panel-default">
	<div class="panel-heading"><th><spring:message code="label.itemStatus" /></div>
	<table class="table">
		<thead>
			<tr>
				<th rowspan="2"><spring:message code="label.itemType" /></th>				
				<th colspan="2" style="background-color: lightgreen"><spring:message code="label.available" /></th>
			</tr>
			 <tr>				
				<th><spring:message code="label.bag" /></th>
				<th><spring:message code="label.kg" /></th>
			</tr>
		</thead>
		<c:forEach items="${itemAvailableStatusList}" var="itemAvailableStatus" varStatus="loop">
			<tbody>
				<tr>
					<td>${itemAvailableStatus.itemTypeName}</td>
					<td><span class="badge"><fmt:formatNumber type="number" pattern="#,##0" value="${itemAvailableStatus.totalQtyinBagAvailable}" /></span></td>
					<td><span class="badge"><fmt:formatNumber type="number" pattern="#,##0" value="${itemAvailableStatus.totalQtyinKgAvailable}" /></span></td>
					
				</tr>
			</tbody>
		</c:forEach>
	</table>
</div>

