<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3><spring:message code="label.success" /></h3>
<div>
	<p>
		<spring:message code="label.successInfo" /><b>${slip.slipNumber}</b>
	</p>	
	<c:choose>
		<c:when test="${slip.slipType eq 'INCOMING'}">
			<p>
				<spring:message code="label.viewInfo" /> <a href="incomingSlip/update/${slip.id}"><spring:message code="label.clickHere" /></a>
			</p>
			<p>
				<spring:message code="label.createInfo" /> <a href=incomingSlip/create><spring:message code="label.clickHere" /></a>
			</p>
		</c:when>		
		<c:otherwise>
			<p>
				<spring:message code="label.viewInfo" /> <a href="deliverySlip/update/${slip.id}"><spring:message code="label.clickHere" /></a>.
			</p>
			<p>
				<spring:message code="label.createInfo" /><a href=deliverySlip/create><spring:message code="label.clickHere" /></a>
			</p>
		</c:otherwise>		
	</c:choose>
</div>
