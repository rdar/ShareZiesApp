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
					
					<div class="navbar-collapse collapse">							
						<div class="menu">
							<ul class="nav nav-tabs" role="tablist">								
								<li class="active">								       
								<a href="#">${attribs.fname}'s Bio</a></li>
								<li><a class="btn btn-link" id="profilelink" onclick="document.forms['profile'].submit()">${member.fname}'s Profile</a>
					            </li>					            							
								<li role="presentation" class="pull-right">
									<form class="navbar-form navbar-right" role="search"  action="LookupMembers" method="post">
										    <div class="input-group add-on">
										      <input type="hidden" name="id" value="${attribs.id}"/>
										      <input type="text" class="form-control" placeholder="Find Members by Name" name="LookupMembers" id="LookupMembers" SIZE='20'>
										      <div class="input-group-btn">
										        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
										      </div>
										    </div>
									 </form>									
						        </li>
						        
						        <li class="pull-right"><a href="logout" id="logout">${member.fname} | Logout</a></li>
						        
																			  						
							</ul>
						</div>
					</div>						
				</div>
			</div>				
	</header>
	
	<div class="container">
	<form id="profile" method="post" action="profile">
	<input type="hidden" name="id" value="${member.id}">
	</form>
			<div>
				
				<p style="color: #008000">${msg} </p>
				<h2 style="color: #008000">${message}</h2>
				<h2 style="color: #008000">${success} </h2>
				<h2 style="color: #FF0000">${error} </h2>
				<h2 style="color: #008000">${email} </h2>
				<h2 style="color: #008000">${myname} </h2>
				
				
			</div>
			
			<div class="row">
			   	 <h3 class="text-center">Bio for ${attribs.fname} ${attribs.background}
			   	 <button type="button" class="btn btn-link" data-toggle="collapse" data-target="#show">ShareZies with ${attribs.fname}</button>		      
			   	 </h3>
				
			</div>
			
		<div id="show" class="collapse">
		<%@include file="email.jsp" %>
		</div>	
		<div class="row">
		<img src="<c:url value="images//${attribs.id}//${attribs.picture}" />" height=auto; width="200" />	
		 <%-- id = ${attribs.id} and picture ${attribs.picture}" --%>
		<div class="col-md-6">
		<table class="table table-hover table-bordered" > 
						<tr style="background-color: #bce8f1;">
                    	 	<td>Organization</td>
						     <td>
						    
						     <span class="fa fa-calendar" style="font-size:18px;color:green; padding-top: 2px;">
					         Details <%= df.format(new java.util.Date()) %>
					         </span>
						     
						      </td>
                    	 </tr>
                    	 <tr>
                    	    <td>Name </td> <td><span style="color:red" class="glyphicon glyphicon-user"></span> <c:out value="${attribs.fname}"/> <c:out value="${attribs.lname}"/></td>
                    	 </tr>                    	 
                    	 <tr>
                    	    <td>Contact Info</td> <td><span style="color:red" class="fa fa-phone fa"></span> <c:out value="${attribs.tel}"/>  <span style="color:red" class="fa fa-envelope fa"></span> <c:out value="${attribs.emailAddress}"/></td>
                    	 </tr>
                    	 
                    	 <tr>
                    	    <td>Address </td> <td><span style="color:red" class="glyphicon glyphicon-home"></span> <c:out value="${attribs.address}"/> <c:out value="${attribs.city}"/> <c:out value="${attribs.state}"/> <c:out value="${attribs.zip}"/></td>
                    	 </tr>
                    	 
    		  </table>
    		  </div>
			  
		</div><br>
		
		
		
		<c:forEach items="${filenames}" var="item">
			<div class="col-md-4">			
			<img src="<c:url value="images//${attribs.id}//${item.filename}" />" width="300" height="320"/>	
			<p>${item.filename} </p>	
			<br>		
			</div>			
			</c:forEach>
	</div>
	<br>
	
	
	
    </body>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 	 
 	 
	
</html>
