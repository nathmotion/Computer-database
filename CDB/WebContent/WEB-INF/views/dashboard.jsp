<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="mylib" uri="/WEB-INF/taglib.tld"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand"
				href="dashboard.html?limit=${page.getSize()}&search=null&typeOrder=null">
				<spring:message code="App.Title" />
			</a>
			<div class="pull-right">
				<a href="dashboard.html?myLocale=fr">FR</a> <a
					href="dashboard.html?myLocale=en">EN</a>
			</div>
		</div>
		<div>
			<%-- <select class="selectpicker" data-width="fit">
				<option value="lang=en"
					data-content='<span class="flag-icon flag-icon-us" ></span> English'>English</option>
				<option value="lang=fr"
					data-content='<span class="flag-icon flag-icon-fr"></span> Français'>Français</option>
			</select> --%>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">${nbComputer}
				<spring:message code="label.titleComputerFound" />
			</h1>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="dashboard.html" method="GET"
						class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control"
							placeholder="<spring:message code="label.placeholderSearch"/>" />
						<input type="submit" id="searchsubmit"
							value="<spring:message code="label.buttonSearch" />"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer" href="addComputer.html"><spring:message
							code="label.buttonAddComputer" /></a> <a class="btn btn-default"
						id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message
							code="label.buttonEdit" /></a>
				</div>
			</div>
		</div>
		<form id="deleteForm" action="dashboard.html" method="POST">
			<input type="hidden" name="selection" value="">
		</form>

		<div class="container" style="margin-top: 10px;">
			<table class="table table-striped table-bordered">
				<thead>
					<tr>
						<!-- Variable declarations for passing labels as parameters -->
						<!-- Table header for Computer Name -->

						<th class="editMode" style="width: 60px; height: 22px;"><input
							type="checkbox" id="selectall" /> <span
							style="vertical-align: top;"> - <a href="#"
								id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
									class="fa fa-trash-o fa-lg"></i>
							</a>
						</span></th>
						<th><a href="dashboard.html?typeOrder=computer&orderCmp=0"><spring:message
									code="label.listTitleComputerName" /></a></th>
						<th><spring:message code="label.listTitleDateIntroduced" /></th>
						<!-- Table header for Discontinued Date -->
						<th><spring:message code="label.listTitleDateDiscontinued" /></th>
						<!-- Table header for Company -->
						<th><a href="dashboard.html?typeOrder=company&orderCmp=0"><spring:message
									code="label.listTitleCompany" /></a></th>

					</tr>
				</thead>
				<!-- Browse attribute computers -->
				<tbody id="results">
					<c:forEach items="${ ListeComputer }" var="computer">
						<tr>
							<td class="editMode"><input type="checkbox" name="cb"
								class="cb" value="${computer.id}"></td>
							<td><a href="editComputer.html?id=${computer.id}" onclick="">${ computer.name }</a>
							</td>
							<td>${ computer.date_introduced }</td>
							<td>${ computer.date_discontinued }</td>
							<td>${ computer.companyName }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><mylib:link page="${page}" action="previous" /></li>
				<li><mylib:link page="${page}" action="numpage" /></li>
				<li><mylib:link page="${page}" action="next" /></li>
			</ul>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<input type="button" class="btn btn-default"
					onClick="location.href='dashboard.html?limit=10'" value="10" /> <input
					type="button" class="btn btn-default"
					onClick="location.href='dashboard.html?limit=50'" value="50" /> <input
					type="button" class="btn btn-default"
					onClick="location.href='dashboard.html?limit=100'" value="100" />
			</div>
		</div>
	</footer>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
	<script
		src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
</html>