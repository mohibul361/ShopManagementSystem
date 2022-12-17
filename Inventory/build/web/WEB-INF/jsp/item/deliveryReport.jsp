<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

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
	font-size: 14px;
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
		$('#fromDateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
	
		$('#toDateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
		
		$("#export").click(
				function() {
					var itemTypeId = $('#itemType option:selected').val();
					var recipientId = $('#recipient option:selected').val();

					window.location.href = path
							+ "/export/deliveryReport?itemTypeId=" + itemTypeId
							+ "&recipientId=" + recipientId;
				});
	$('#submitForm').submit(function(e) {
			var frm = $('#submitForm');
			e.preventDefault();
 
		    var data = {}
		    var Form = this;
 
		    //Gather Data also remove undefined keys(buttons)
		    $.each(this, function(i, v){
		            var input = $(v);
		        data[input.attr("name")] = input.val();
		        delete data["undefined"];
		    });		
	        $.ajax({
	        	headers : {
	                'Accept' : 'application/json',
	                'Content-Type' : 'application/json'
	            },
	            type: frm.attr('method'),
	            url: frm.attr('action'),
	            dataType : 'json',
	            data : JSON.stringify(data),
	            success : function(callback){
	            	alert("Response: Name: "+callback);
	                $(this).html("Success!");
	            },
	            error : function(){
	                $(this).html("Error!");
	            }
	        });
		});
		
	})
</script>
<h3><spring:message code="label.deliveryReport"/></h3>
<label></label>
<form action="${pageContext.request.contextPath}/viewDeliveryReport" method="post" target="_blank" modelAttribute="reportParameter">
<!-- <form action="/Inventory/viewDeliveryReportHtml" method="get"> -->
	<%-- <p>
		<label for="typeInput"><spring:message code="label.itemType"/></label> <select name="itemType" id="itemType">
			<c:forEach items="${itemTypeList}" var="eachItem">
				<option value="${eachItem.id}" <c:if test="${eachItem.id eq itemTypeId}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</select>
	</p> --%>
	<p>
		<label for="recipient"><spring:message code="label.recipient"/></label> <select name="recipient" id="recipient">
		<option value=""><spring:message code="label.all" /></option>
			<c:forEach items="${recipientList}" var="eachItem">
				<option value="${eachItem.id}" <c:if test="${eachItem.id eq recipientId}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</select>
	</p>
	<p>
		<label for="fromDateInput"><spring:message
				code="label.fromDate" /></label>
		<input name="fromDate" id="fromDateInput" />
	</p>
	<p>
		<label for="toDateInput"><spring:message
				code="label.toDate" /></label>
		<input name="toDate" id="toDateInput" />
	</p>
	<p>
		<label></label><input type="submit" id="show" value="<spring:message code="label.viewDeliveryReport"/>">
	</p>
</form>
