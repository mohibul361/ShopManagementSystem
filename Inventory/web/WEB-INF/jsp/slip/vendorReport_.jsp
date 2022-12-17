<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
		
		$('#toDateInputForVB').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
		
		$('#submitVLForm').submit(function(e) {
			var frm = $('#submitVLForm');
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
		$('#submitVBForm').submit(function(e) {
			var frm = $('#submitVBForm');
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
	});
</script>
<h3>
	<spring:message code="label.vendorReport" />
</h3>
<fieldset style="width: 45%; float: left;">
	<legend>
		<spring:message code="label.vendorLedger" />
	</legend>
<form action="/Inventory/viewVLedger" method="POST"  id="submitVLForm">
	<p>
		<label for="vendor"><spring:message code="label.vendor" /></label>
		<select name="vendor" id="vendor">
			<option value=""><spring:message code="label.selectOne" /></option>
			<c:forEach items="${vendorList}" var="eachItem">
				<option value="${eachItem.id}"
					<c:if test="${eachItem.id eq recipientId}"> selected="selected"</c:if>>${eachItem.name}</option>
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
	<label></label>
	<input type="submit" value="<spring:message code="label.viewLedger"/>">
</form>
</fieldset>
<fieldset style="width: 45%; float: right;">
	<legend>
		<spring:message code="label.vendorBalance" />
	</legend>
<form action="/Inventory/viewVBalance" method="POST"  id="submitVBForm">	
	<p>
		<label for="toDateInputForVB"><spring:message
				code="label.toDate" /></label>
		<input name="toDate" id="toDateInputForVB" />
	</p>
	<label></label>
	<input type="submit" value="<spring:message code="label.viewBalance"/>">
</form>
</fieldset>
