<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="<c:url value="/resources/scripts/jquery.dataTables.js" />"></script>
<link href="<c:url value="/resources/css/jquery.dataTables.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/mydataTable.css" />" rel="stylesheet">	
<link href="<c:url value="/resources/css/list_group.css" />"
	rel="stylesheet">
<script src="<c:url value="/resources/scripts/popup.js" />"></script>
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
.text-right{
	text-align: right;
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
	function deleteCollection(collectionId)
	{
		if(confirm('আপনি কি এই জমা ডিলিট করতে চান ?')){
			var url = '${pageContext.request.contextPath}/transaction/delete/'+collectionId+"/0";
			window.location.href = url;
		}
	}
	
	$(function() {				
		$("#reset").click(function() {
			$("#customerName").val("");
			$("#deliveredTo").val("");
			location.reload();
		});
		$("#buttonSearch").click(function ()
		        {
		            var form = $('#collectionSearchForm');
		            form.validate();
		            if (form.valid()) {		                
		                $('#collectionsTable').DataTable().destroy();
		                var path = '${pageContext.request.contextPath}';
		                $('#collectionsTable').DataTable({
		                    "processing": true,
		                    "serverSide": true,
		                    "pageLength" : 15,
							"lengthMenu" : [15, 30, 45 ],
		                    "bSort": false,
		                    "pagingType": "full_numbers",
		                    "dom": '<"top"i>rt<"bottom"lp><"clear">',
		                    "language": {
		                        "url": languageUrl
		                    }, 
		                   /* columnDefs: [
		                        {className: "text-right", targets: [3]}
		                    ], */
		                    "ajax": {
		                        "url": path + "/collections",
		                        "type": "POST",
		                        "data": {
		                            "recipientId": $("#deliveredTo").val()
		                        }
		                    },
		                    "fnDrawCallback": function (oSettings) {
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
	<spring:message code="label.collectionList" />
	<a href="${contextPath}/collection" style="float:right"><img style="vertical-align:middle" src="<c:url value="/resources/images/add.png" />">&nbsp;<spring:message code="label.addNew"/></a>
</h3>

<form id="collectionSearchForm" action="${pageContext.request.contextPath}/collections" method="post">
<label for="recipientInput"><spring:message code="label.recipient" /></label>
<input type="hidden" id="deliveredTo" value="${cid}" name="cid">
<input type="text" id="customerName" readonly="readonly" value="${cname}" name="cname">
<a href="${pageContext.request.contextPath}/recipientList" onClick="return PopupCenter(this, '', 800, 800)">
<img style="height: 25px; vertical-align: middle" src="<c:url value="/resources/images/search.png" />"> </a>
<input type="button" id="buttonSearch" value="<spring:message code="label.search" />">
<a href="#" id="reset"><spring:message code="label.reset" /></a>
</form>
<table class="table display compact" id="collectionsTable">
	<thead>
		<tr>
			<th><spring:message code="label.recipient" /></th>
			<th><spring:message code="label.collectionNo" /></th>
			<th><spring:message code="label.collectionDate" /></th>
			<th><spring:message code="label.collectionAmount" /></th>
			<th><spring:message code="label.collectionRemarks" /></th>
			<th><spring:message code="label.action" /></th>
		</tr>
	</thead>	
</table>

