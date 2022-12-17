<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link href="<c:url value="/resources/css/list_group.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/scripts/jquery.dataTables.js" />"></script>
<link href="<c:url value="/resources/css/jquery.dataTables.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/mydataTable.css" />"
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
	var locale = '${pageContext.response.locale}';
	var languageUrl="";
	if(locale=='bn')
	{
		languageUrl = path+"/dataTable/localization/bangla";	
	}
	function deletePayment(paymentId) {
		if (confirm('আপনি কি এই পরিশোধ ডিলিট করতে চান ?')) {
			var url = '${pageContext.request.contextPath}/transaction/delete/'
					+ paymentId + "/1";
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

		$("#buttonSearch").click(function() {
			var form = $('#paymentSearchForm');
			form.validate();
			if (form.valid()) {
				$('#paymentTable').DataTable().destroy();
				var path = '${pageContext.request.contextPath}';
				$('#paymentTable').DataTable({
					"processing" : true,
					"serverSide" : true,
					"pageLength" : 15,
					"lengthMenu" : [15, 30, 45 ],
					"bSort" : false,
					"pagingType" : "full_numbers",
					"dom" : '<"top"i>rt<"bottom"lp><"clear">',
					"language": {
					    "url": languageUrl
					}, 
					"columnDefs" : [ 
					                 {"width" : "20%", "targets" : 0},
					                 {"width" : "15%", "targets" : 1},
					                 {"width" : "15%", "targets" : 2},
					                 {"width" : "20%", "targets" : 3},
					                 {"width" : "20%", "targets" : 4},
					                 {"width" : "10%", "targets" : 5}
					                 
					             ],
					"ajax" : {
						"url" : path + "/payments",
						"type" : "POST",
						"data" : {
							"vendorId" : $("#vendor").val()
						}
					},
					"fnDrawCallback" : function(oSettings) {
						/*  if (selectedLocale === 'bn')
						 {
						     localizeBanglaInDatatable("userListTable");
						 } */
					}
				});
			}
		});
	})
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>
	<spring:message code="label.paymentList" />
	<a href="${contextPath}/payment" style="float:right"><img style="vertical-align:middle" src="<c:url value="/resources/images/add.png" />">&nbsp;<spring:message code="label.addNew"/></a>
</h3>
<form id="paymentSearchForm"
	action="${pageContext.request.contextPath}/payments" method="post">
	<select id="vendor">
		<option value=""><spring:message code="label.selectOne" /></option>
		<c:forEach items="${vendorList}" var="eachItem">
			<option value="${eachItem.id}"
				<c:if test="${eachItem.id eq transaction.vendor.id}"> selected="selected"</c:if>>${eachItem.name}</option>
		</c:forEach>
	</select> <input type="button" id="buttonSearch"
		value="<spring:message code="label.search" />">
</form>
<table class="table display compact" id="paymentTable">
	<thead>
		<tr>
			<th><spring:message code="label.vendor" /></th>
			<th><spring:message code="label.paymentNo" /></th>
			<th><spring:message code="label.paymentDate" /></th>
			<th><spring:message code="label.paymentAmount" /></th>
			<th><spring:message code="label.paymentRemarks" /></th>
			<th>Action</th>
		</tr>
	</thead>
</table>
