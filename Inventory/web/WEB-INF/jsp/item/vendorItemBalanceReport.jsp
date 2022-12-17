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
<h3><spring:message code="label.vendorItemBalanceReport"/></h3>
<label></label>
<!-- <form action="/Inventory/viewVendorItemBalanceReport" method="post" id="submitForm"> -->
<form action="${pageContext.request.contextPath}/viewVendorItemBalanceReport" method="post" target="_blank" modelAttribute="reportParameter">
	
	<p>
		<label for="vendor"><spring:message code="label.vendor"/></label> <select name="vendor" id="vendor">		
			<c:forEach items="${vendorList}" var="eachItem">
				<option value="${eachItem.id}" <c:if test="${eachItem.id eq vendorId}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</select>
	</p>	
	
	<p>
		<label></label><input type="submit" id="show" value="<spring:message code="label.viewVendorItemBalanceReport"/>">
	</p>
</form>
