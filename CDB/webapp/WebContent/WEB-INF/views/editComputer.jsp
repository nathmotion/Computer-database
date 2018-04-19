<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer
				Database </a>
		</div>
	</header>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:"${ComputerDTO.id}"</div>
					<h1>Edit Computer</h1>
                    <form:form action="editComputer" modelAttribute="ComputerDTO" method="POST">
                    <input type="hidden" value="${ComputerDTO.id}" id="id" name="id" />
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label> 
                                <form:input type="text" class="form-control" id="computerName" name="computerName"
                                    placeholder="Computer name" pattern ="[A-Za-z1-9]{1,30}" path="name"/> 
                                 <form:errors path="name"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label> 
                            <form:input type="date" class="form-control" id="introduced" name="introduced"
                                    placeholder="Introduced date" path="introduced"/>
                                    <form:errors path="introduced"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label> 
                                <form:input type="date" class="form-control" id="discontinued" name="discontinued"
                                    placeholder="Discontinued date" path="discontinued"/>
                                    <form:errors path="discontinued"></form:errors>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label> 
                                <form:select class="form-control" id="companyId" name="companyId" path="company.id">
                                    <option value="0">--</option>
                                    <c:forEach items="${companyList}" var="company">
                                        <option value="${company.id}">${company.name}</option>
                                    </c:forEach>
                            </form:select>
                            <form:errors path="company"></form:errors>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
				</div>
			</div>
		</div>
	</section>
</body>
</html>