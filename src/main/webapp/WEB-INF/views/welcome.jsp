<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.text.NumberFormat" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.io.*,java.util.*" %>
<%java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy"); %> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
    
  </head>
  <body>
	<header style="background-color: #FFFFFF;">	
			<div class="navigation">
				<div class="container">					
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse.collapse">
							<span class="sr-only">Toggle navigation</span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
							<span class="icon-bar"></span>
						</button>						
					</div>
					<div class="col-lg-8 col-lg-offset-2">
					<div class="navbar-collapse collapse">							
						<div class="menu">
							<ul class="nav nav-tabs" role="tablist">
							    <li>
							      <a class="btn btn-link" id="profilelink" onclick="document.forms['profile'].submit()">Profile</a>
					            </li>									
								<li class="active"><a href="#">File Upload Center</a></li>	
								<li>
								 <a class="btn btn-link" id=uploadlinkmulti onclick="document.forms['multiFiles'].submit()">Upload Multiple </a>
								</li>																
								<li>
								<a class="btn btn-link" id=uploadlinkone onclick="document.forms['singleFiles'].submit()">Upload Single </a>
		 						
								<li class="pull-right"><a href="logout" id="logout">${attribs.lname} | Logout</a></li>
						        				
							</ul>
						</div>
					</div>
					</div>						
				</div>
			</div>				
	</header>
	
	<div class="container">	
	
	<form id="singleFiles" method="post" action="uploadsingleFile">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	
	<form id="multiFiles" method="post" action="multiFiles">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	
	<form id="profile" method="post" action="profile">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	<br>
	<div class="text-center">
			<div class="text-info">	
			<c:if test="${not empty file}">
			File  <strong>${file}</strong> upload success.
			</c:if>
			<c:if test="${not empty fileNames}">
			<c:forEach var="fileNames" items="${fileNames}">
			<div class="text-info">	
			File  <strong>${fileNames}</strong> uploaded successfully		
			</div><br>			
			</c:forEach>
			</c:if>		
			</div><br>			
	</div>
	
	
	<div class="col-xs-3 col-md-4">
                <img src="<c:url value="images//${attribs.id}//${attribs.picture}" />" width="340" height="240"/>
				<a class="btn btn-link" id=uploadlinkone onclick="document.forms['singleFiles'].submit()">Profile Picture - Update <span class="glyphicon glyphicon-user"></span></a>
				
                </div><br><br>
                
	<div class="col-md-8">	
	<div class="text-center">
			<div class="text-info">	
			<h1>Welcome to FileUploader Center</h1>
		 <a class="btn btn-primary" id=uploadlinkone onclick="document.forms['singleFiles'].submit()">Upload Profile Picture</a>
		 <a class="btn btn-primary" id=uploadlinkmulti onclick="document.forms['multiFiles'].submit()">Upload Multiple </a>
		</div><br>			
	    </div>	
		</div>
	</div>
	
  </body>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 	 
 
</html>
