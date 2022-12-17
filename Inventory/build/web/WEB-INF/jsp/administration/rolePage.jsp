<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<script type="text/javascript">

	var allPageIds = [
	               <c:forEach var="id" items="${allPageIds}">
	               		${id},
	               </c:forEach>
	               ];
	
	$(function() {
		$("#role").change(function() {
			
			var id = $('option:selected', this).val();
			if (!id) {
				return;
			}
		
			//flushing previous selection
			for (var i = 0; i < allPageIds.length; i++) {
				$("#ch" + allPageIds[i]).prop('checked', false);
			}
			getPageByRole(id);
		});
		$('#role').trigger('change');
	});

	function getPageByRole(roleId) {
		var pageIds = [];
		$.ajax({
			url : "${pageContext.request.contextPath}/pageIdsByRole/"
					+ roleId,
			type : 'GET',
			dataType : 'html',
			success : function(data) {
				pageIds = JSON.parse(data);
				console.log(data);
				for (var i = 0; i < pageIds.length; i++) {
					$("#ch".concat(pageIds[i])).prop('checked', true);
				}
			},
			error : function(data, status, er) {
				console.log(data);
				alert("error: " + data + " status: " + status + " er:" + er);
			}
		});
	}

	function savePageRole() 
	{
		var selectedIds = getSelectedIds();
			
		var roleId =  $('#role option:selected').val();
		json = {"roleId": roleId, "pageIds": selectedIds};
		
		$.ajax({
			headers: { 
		        'Accept': 'application/json',
		        'Content-Type': 'application/json' 
		    },
			url : "${pageContext.request.contextPath}/savePagesByRole",
			data : JSON.stringify(json),	
			type : 'POST',
			dataType : 'html',
			success : function(data) {
				console.log(data);
				alert(data);
			},
			error : function(data, status, er) {
				console.log(data);
				alert("error: " + data + " status: " + status + " er:" + er);
			}
		});

	}

	function getSelectedIds()
	{
		var selectedIds = new Array();
		var i, j=0;
		for (i = 0; i < allPageIds.length; i++) 
		{
			if($('#ch'+allPageIds[i]).is(":checked"))
			{
				selectedIds[j++] = allPageIds[i];
			}
		}
		return selectedIds;
	}
</script>
<style>
.all-maindiv {
	width: 99%;
	height: 300px;
	border: 1px solid #ddd;
	border-radius: 3px;
}

.row {
	margin-bottom: 15px;
	vertical-align: top;
	padding: 0;
}

.term-list {
	margin-bottom: 15px;
	display: inline-block;
	margin-top: 5px;
	list-style-type: none;
	margin-left: -45px;
}

.splitter {
	padding: 0px 20px 13px 0;
}

.splitter img {
	background-color: #eee;
	height: 1px;
	width: 100%;
}

.cat-div {
	/* font-size: 14px; */
	width: 25%;
	float: left;
}
</style>

<h3>Add/Edit Role wise Page</h3>
<p>Select Role</p>
<div id="role_container">
	<SELECT id="role">
		<option value="">Select one</option>
		<c:forEach items="${roleList}" var="entry">
			<option value="${entry.id}">${entry.roleName}</option>
		</c:forEach>
	</SELECT>
</div>

<div class="all-maindiv">
	<p style="width: 99%">All Pages</p>
	<%-- <input type="hidden" id="allPageIds" value="${allPageIds}">  --%>
	<div style="padding-left: 10px; padding-right: -5px;">
		<c:forEach items="${pageListMap}" var="entry">
			<div class="cat-div">
				<a style="text-decoration: none; font-weight: bold">${entry.key.name}</a> <br />
				<ul class="term-list" style="">
					<c:forEach var="page" items="${entry.value}">
						<li><a><input id="ch${page.id}" type="checkbox" />${page.name}</a></li>
					</c:forEach>
				</ul>

			</div>
		</c:forEach>
	</div>
</div>
<p>
	<input type="submit" onclick="savePageRole()" value="Save Pages" />
</p>
