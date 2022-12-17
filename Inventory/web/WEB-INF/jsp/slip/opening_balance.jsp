<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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

#customerInfo{
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
	$(function() {
		$("#export").click(
				function() {
					var itemTypeId = $('#itemType option:selected').val();
					var recipientId = $('#recipient option:selected').val();

					window.location.href = path
							+ "/export/deliveryReport?itemTypeId=" + itemTypeId
							+ "&recipientId=" + recipientId;
				});
		$("#createPayment").click(function() {
			$("#showPayment").css("display", "block");
		});

		$('#dateInput').datepicker({
			dateFormat : 'yy/mm/dd'
		}).datepicker("setDate", new Date());
	
		$("#recipient").change(function() {
					
					var id = $('option:selected', this).val();					
					if (!id) {
						$("#vendor").html("");
						return;
					}
		});
		$('#itemType').trigger('change');
	});
</script>
<h3>
	<spring:message code="label.openingBalance" />
</h3>
<a href="${pageContext.request.contextPath}/openingBalances"><spring:message code="label.viewAllOpeningBalances" /></a>
<form:form cssStyle="width: 800px; float: left;" method="post" action="${pageContext.request.contextPath}/openingBalance/create"
	modelAttribute="transaction">
	<c:if test="${!empty msg}">
		<p id="msg">${msg}</p>
	</c:if>
	<form:errors path="*" cssClass="error" element="div" />
	<!-- <p>
		<input type="radio" name=myradio value="vendor" checked="checked"  />Vendor 			
			<input type="radio" name=myradio value="customer" />Customer
	</p> -->
	<form:hidden path="id"/>
	<form:hidden path="transactionType"/>
	<form:hidden path="transactionNo"/>
	<form:hidden path="createdBy"/>
	<p>
		<label for="recipient"><spring:message code="label.recipient" /></label>
		<form:select path="recipient" >
			<option value=""><spring:message code="label.selectOne" /></option>
			<c:forEach items="${recipientList}" var="eachItem">
				<option value="${eachItem.id}"
					<c:if test="${eachItem.id eq transaction.recipient.id}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</form:select>
	</p>
	<p><label></label><span><spring:message code="label.or" /></span></p>
	<p>
		<label for="vendor"><spring:message code="label.vendor" /></label>
		<form:select path="vendor" id="vendor">
			<option value=""><spring:message code="label.selectOne" /></option>
			<c:forEach items="${vendorList}" var="eachItem">
				<option value="${eachItem.id}"
					<c:if test="${eachItem.id eq transaction.vendor.id}"> selected="selected"</c:if>>${eachItem.name}</option>
			</c:forEach>
		</form:select>
	</p>

	<p>
		<label for="dateInput"><spring:message
				code="label.date" /></label>
		<form:input path="date" id="dateInput" readonly="true" />
	</p>
	<p>
		<label for="amountInput"><spring:message
				code="label.amount" /></label>
		<form:input path="amount" id="amountInput" />
	</p>
	<p>
		<label for="remarksInput"><spring:message
				code="label.remarks" /></label>
		<form:textarea style="vertical-align: middle" rows="1" cols="40"
			path="remarks" id="remarksInput" />
	</p>
	<p>
		<label></label>
		<input type="submit" value="<spring:message code="label.createOpeningBalance" />">
	</p>
</form:form>
