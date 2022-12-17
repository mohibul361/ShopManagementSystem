<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<script src="http://code.jquery.com/ui/1.11.0/jquery-ui.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.11.0/themes/smoothness/jquery-ui.css">
<link href="<c:url value="/resources/css/list_group.css" />" rel="stylesheet">
<style type="text/css">
	.attributeDiv {
		margin-bottom: 10px;
	}
</style>
<script type="text/javascript">
	$(function() {

		$("#itemDetails").hide();
		$("#itemType")
				.change(
						function() {
							var id = $('option:selected', this).val();
							if (!id) {
								$("#itemAttributes").html("");
								return;
							}
							
							var reqUrl = "${pageContext.request.contextPath}/attributeDataForSlipForm/"
										+ id;
							$.ajax({
								url : reqUrl,
								type : 'GET',
								dataType : 'html',
								success : function(data) {
									$("#itemAttributes").html(data);
								},
								error : function(data, status, er) {
									console.log(data);
									alert("error: " + data + " status: "
											+ status + " er:" + er);
								}
							});

						});
		$('#itemType').trigger('change');
		$('#searchItem').click(function(event) {
			loadSearchResult();
		});

		function loadSearchResult() {
			jsonObj = [];
			var attrCount = $(".attribute").length;
			for (var i = 0; i < attrCount; i++) { 
				item = {};
				item["attributeId"] = $("#" + i + "itemTypeAttribute").val();
				item["attributeValue"] = $("#" + i + "attributeValue").val();
				jsonObj.push(item);
			}
			console.log(jsonObj);
			
			jsonData = JSON.stringify(jsonObj);
			console.log(jsonData);
			var itemTypeId = $('#itemType option:selected').val();
			/* $("#itemStatus").load("${pageContext.request.contextPath}/itemCountByAttribute1",  {"myData": jsonObj}); */
			$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type : 'POST',
					url : "${pageContext.request.contextPath}/itemCountByAttribute/"+itemTypeId,
					data : jsonData,
					dataType : 'html',
					success : function(data) {							
						$("#itemStatus").html(data);

					},
					error : function(data, status, er) {
						console.log(data);
						alert("error: " + data + " status: " + status
								+ " er:" + er);
					}
				});
				
			return true;
		}	
	});
</script>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<h3>Item Search By Attribute</h3>
<div style="width: 30%; float: left;">
	<div>
		<label for="typeInput">ItemType: </label>
		<select name="itemType" id="itemType">
			<c:forEach items="${itemTypeList}" var="eachItem">
				<option value="${eachItem.id}">${eachItem.name}</option>
			</c:forEach>
		</select>
	</div>
	<div id="itemAttributes"></div>
	<br/>
	<div>
		<input type="button" id="searchItem" value="Search Item">
	</div>
</div>
<div id="itemStatus" style="width: 60%; float: left; margin-left: 20px;"></div>