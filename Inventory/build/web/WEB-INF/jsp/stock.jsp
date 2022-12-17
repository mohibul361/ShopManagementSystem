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
	function printData() {
		var divToPrint = document.getElementById("printTable");
		var htmlToPrint = '' + '<style type="text/css">'
				+ 'table {border-collapse: collapse; width: 100%}'
				+ 'table th, table td {' + 'border:1px solid #000;'
				+ 'text-align: center;' + 'padding;0.5em;' + '}' + '</style>';
		htmlToPrint += divToPrint.outerHTML;
		newWin = window.open("");
		newWin.document.write(htmlToPrint);
		newWin.print();
		newWin.close();
	}
	$(function() {
		$('button').on('click', function() {
			printData();
		})
	})
</script>

<div class="panel panel-default">
	<div class="panel-heading">
		<spring:message code="label.itemStatus" />
		<button style="float: right; border: 1px solid lightslategrey">
			<spring:message code="label.print" />
		</button>
	</div>
	<table class="table" id="printTable">
		<thead>
			<tr>
				<th rowspan="2"><spring:message code="label.itemType" /></th>
				<th colspan="2"><spring:message code="label.totalReceived" /></th>
				<th colspan="2"><spring:message code="label.totalDelivered" /></th>
				<th colspan="2" style="background-color: lightgreen"><spring:message
						code="label.available" /></th>
			</tr>
			<tr>
				<th><spring:message code="label.bag" /></th>
				<th><spring:message code="label.kg" /></th>
				<th><spring:message code="label.bag" /></th>
				<th><spring:message code="label.kg" /></th>
				<th><spring:message code="label.bag" /></th>
				<th><spring:message code="label.kg" /></th>
			</tr>
		</thead>
		<c:forEach items="${dashBoardInfo.itemStatusInfoList}"
			var="itemStatusInfo" varStatus="loop">
			<tbody>
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/vendorItemBalance/${itemStatusInfo.itemType.id}">${itemStatusInfo.itemType.name}</a></td>
					<td><fmt:formatNumber type="number" pattern="#,##0"
							value="${itemStatusInfo.receivedTotal}" /></td>
					<td><fmt:formatNumber type="number" pattern="#,##0"
							value="${itemStatusInfo.qcFailed}" /></td>
					<td><fmt:formatNumber type="number" pattern="#,##0"
							value="${itemStatusInfo.qcPassed}" /></td>
					<td><fmt:formatNumber type="number" pattern="#,##0"
							value="${itemStatusInfo.delivered}" /></td>
					<td><c:choose>
							<c:when test="${itemStatusInfo.replacement <= itemStatusInfo.itemType.lowMark}">
						        <span class="badgeLowMark"><fmt:formatNumber type="number"
										pattern="#,##0" value="${itemStatusInfo.replacement}" /></span> 					       
							</c:when>
							<c:when test="${itemStatusInfo.replacement >= itemStatusInfo.itemType.highMark}">
						        <span class="badgeHighMark"><fmt:formatNumber type="number"
										pattern="#,##0" value="${itemStatusInfo.replacement}" /></span> 					       
							</c:when>
							<c:otherwise>
								<span class="badge"><fmt:formatNumber type="number"
										pattern="#,##0" value="${itemStatusInfo.replacement}" /></span> 	
							</c:otherwise>
						</c:choose>							
					</td>
					<td><span class="badge"><fmt:formatNumber type="number"
								pattern="#,##0" value="${itemStatusInfo.available}" /></span></td>

					<%-- <td><span class="badge"><fmt:formatNumber type="number" pattern="#,##0" value="${itemStatusInfo.replacement}" /></span></td> --%>
				</tr>
			</tbody>
		</c:forEach>
	</table>
</div>
<br />
