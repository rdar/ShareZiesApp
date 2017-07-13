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
							    <li><a  id=uploadlink onclick="document.forms['uploadFiles'].submit()">Upload Center </a></li>						       								
								<li class="active"><a href="#">Single Upload </a></li>	
								<!-- <li><a href="multiUpload">multi Upload</a></li>	 -->
								<li class="pull-right"><a href="logout" id="logout">${attribs.lname} | Logout</a></li>
						        							
						        																	  						
							</ul>
						</div>
					</div>
					</div>						
				</div>
			</div>				
	</header>
	
	<div class="container">
	
	<form id="uploadFiles" method="post" action="uploadFiles">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	
	<form id="profile" method="post" action="profile">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	
	<div class="form-container">
		<h1>Upload Single files </h1>
		<form:form method="POST" modelAttribute="fileBucket" action="singleUpload" enctype="multipart/form-data" class="form-horizontal">
			   
			   <form:input type="hidden" path="id" name="id" value="${attribs.id}" class="form-control input-sm" placeholder="Enter Email"/>
						
				<div class="form-group col-md-7">
					<label class="col-md-3 control-lable" for="file">Upload a file</label>
					<div class="col-lg-9">
						<form:input type="file" path="file" id="file" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="file" class="help-inline"/>
						</div>
					</div>
					
					<div class="col-lg-9">
						<form:input type="hidden" path="description" id="description" class="form-control input-sm"/>
						<div class="has-error">
							<form:errors path="description" class="help-inline"/>
						</div>
					</div>
				</div>
				
				
				<div class="col-lg-9 col-lg-offset-2">
					<input type="submit" value="Upload" class="btn btn-primary btn-sm">					
					<a  class="btn btn-default" id=uploadlink onclick="document.forms['uploadFiles'].submit()">Cancel </a>
				
				</div>
		</form:form>
		
	</div>
		  
	</div>
	
  </body>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 	 
 	 
	
</html>
