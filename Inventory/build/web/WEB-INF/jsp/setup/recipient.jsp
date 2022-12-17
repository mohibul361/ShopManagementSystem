<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript">
	$(function() {
		$("#recipientForm").validate({
	        rules: {
	            "name": {
	                required: true
	            },
	            "mobileNo1": {
	                required: true
	            },
	            "address": {
	                required: true
	            }
	        },
	        errorElement: "span",
	        errorPlacement: function (error, element) {
	            error.insertAfter(element);
	        }
	    });
	});	          
</script>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>
	<spring:message code="label.recipientManagement" />
	<a style="float:right" href="${contextPath}/recipient/list"><img style="vertical-align:middle" src="<c:url value="/resources/images/back.png" />">&nbsp;<spring:message code="label.backToList"/></a>
</h3>
<c:if test="${!empty msg}">
		<p id="msg">${msg}</p>
</c:if>
<c:url var="formAction" value="/recipient"></c:url>
<form:form id="recipientForm" action="${formAction}" modelAttribute="recipient">
	<form:hidden path="id" />

	<p>
		<label for="nameInput"><spring:message code="label.name" /><span class="mandatory">*</span></label>
		<form:input path="name" id="nameInput" autofocus="autofocus"/>
		<form:errors path="name" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="mobileNo1Input"><spring:message
				code="label.mobileNo1" /><span class="mandatory">*</span></label>
		<form:input path="mobileNo1" id="mobileNo1Input" />
		<form:errors path="mobileNo1" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="mobileNo2Input"><spring:message
				code="label.mobileNo2" /></label>
		<form:input path="mobileNo2" id="mobileNo2Input" />
		<form:errors path="mobileNo2" cssClass="error"></form:errors>
	</p>
	<p>
		<label for="addressInput"><spring:message code="label.address" /><span class="mandatory">*</span></label>
		<form:textarea style="vertical-align: middle" rows="1" cols="50"
			path="address" id="addressInput" />
		<form:errors path="address" cssClass="error"></form:errors>
	</p>
	<p>
		<label></label> <input type="submit"
			value="<spring:message code="label.save"/>" /> <input type="reset"
			value="<spring:message code="label.reset"/>" />
	</p>
</form:form>