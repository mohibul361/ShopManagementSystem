<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<style type="text/css">

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
<h3><spring:message code="label.purchaseSaleReport"/></h3>
<label></label>
<!-- <form action="/Inventory/viewPurchaseSaleReport" method="post" id="submitForm"> -->
<form action="${pageContext.request.contextPath}/viewPurchaseSaleReport" method="post" target="_blank" modelAttribute="reportParameter">
	
	<p>
		<label for="vendor"><spring:message code="label.vendor"/></label> <select name="vendor" id="vendor">		
			<c:forEach items="${vendorList}" var="eachItem">
				<option value="${eachItem.id}" <c:if test="${eachItem.id eq vendorId}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</select>
	</p>
	<p>
		<label for="typeInput"><spring:message code="label.itemType"/></label> <select name="itemType" id="itemType">
			<c:forEach items="${itemTypeList}" var="eachItem">
				<option value="${eachItem.id}" <c:if test="${eachItem.id eq itemTypeId}"> selected="selected"</c:if>>${eachItem.name}</option>
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
		<label></label><input type="submit" id="show" value="<spring:message code="label.viewPurchaseSaleReport"/>">
	</p>
</form>

