<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript">
	$(function() {
		$("#logout-link").click(function() {
			$("#logout-form").submit();
		});
	})
</script>
<c:url var="logoutAction" value="/logout"></c:url>
<c:choose>
	<c:when test="${sessionScope.userName != null}">
		<c:set var="pageList" value="${sessionScope.pageList}" />
		<ul id="nav" style="width: 100%; background: #545454;">
			<li><spring:url value="/index" var="homeUrl" htmlEscape="true" />
				<a href="${homeUrl}"><spring:message code="label.home"/></a></li>
			<c:forEach items="${pageList}" var="entry">
				<li>
					<a href="#"><c:out value="${pageContext.response.locale eq 'bn' ? entry.key.nameBn : entry.key.name}" /></a>
					<ul>
						<c:forEach var="page" items="${entry.value}">
							<li><spring:url value="${page.url}" var="pageUrl"
									htmlEscape="true" /> <a href="${pageUrl}">
									<c:out value="${pageContext.response.locale eq 'bn' ? page.nameBn : page.name}" /> 									
							</a></li>
						</c:forEach>
					</ul></li>
			</c:forEach>
		</ul>
	</c:when>
	<c:otherwise>
		<!-- <marquee behavior="scroll" direction="left" scrollamount="5">Welcome !</marquee> -->
	</c:otherwise>
</c:choose>

