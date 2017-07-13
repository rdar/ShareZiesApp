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
    <!-- <script>
    window.history.forward();
    </script> 
     -->
  </head>
  <body>
	<header style="background-color: #FFFFFF;" >	
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
								<li class="active"><a href="#">Profile</a></li>	
								<li><a  id=uploadlink onclick="document.forms['uploadFiles'].submit()">Upload Center </a></li>
						       
								<li role="presentation" class="pull-right">
									<form class="navbar-form navbar-right" role="search"  action="LookupMembers" method="post">
										    <div class="input-group add-on">
										      <input type="hidden" name="id" value="${attribs.id}"/>
										      <input type="text" class="form-control" placeholder="Search Organizations by Name" name="LookupMembers" id="LookupMembers" SIZE='20'>
										      <div class="input-group-btn">
										        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
										      </div>
										    </div>
									 </form>									
						        </li>
						        <c:if test="${fn:containsIgnoreCase(attribs.roles, 'super') || fn:containsIgnoreCase(attribs.roles, 'admin')}">
						        <li><a  id="adminlink" onclick="document.forms['admin'].submit()">Administrator </a></li>
						        </c:if>
						        <li class="pull-right"><a href="logout" id="logout">${attribs.fname} | Logout</a></li>
						        
																			  						
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
	
	
	<form id="admin" method="post" action="admin">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	
	<form id="userEdits" method="post" action="userEdits">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
	
	<form id="singleFiles" method="post" action="uploadsingleFile">
	<input type="hidden" name="id" value="${attribs.id}">
	</form>
		
			<div>
				
				<p style="color: #008000">${msg} </p>
				<h2 style="color: #008000">${message}</h2>
				<h3 style="color: #008000">${success} </h3>
				<h2 style="color: #FF0000">${error} </h2>
				<h2 style="color: #008000">${email} 
				<c:if test="${not empty file}">
				File  <strong>${file}</strong> upload success.
				</c:if>
				</h2>
			</div>
			
			<div class="row">
			<h3 class="col-md-6">${attribs.fname}'s Biography 
							      <br>${attribs.background}</h3> 
							 <form id="bio" method="post"  action="bio">                           		 
								 <input type="hidden" name="id" value="${attribs.id}"/>
								 <input type="hidden" name="emailAddress" value="${attribs.emailAddress}"/>
						    </form>
                             <a title="Bio" id="gotoBio" onclick="document.forms['bio'].submit()">How other users see my profile</a>
                           
			</div>
			

		
		<div class="row">
		<div class="col-md-6">
		<table class="table table-hover table-bordered" > 
						<tr style="background-color: #bce8f1;">
                    	 	<td>User</td>
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
                    	 <%-- <tr>
                    	    <td>Web site </td> 
                    	    <td>
                    	    <c:choose>
							    <c:when test="${empty attribs.website}">
							       <p style="color: #E44422">Please update your web site </p>
							    </c:when>							       
							    <c:otherwise>
							    
							    <span style="color:green" class="glyphicon glyphicon-globe"></span> <a href="${attribs.website}"><c:out value="${attribs.website}"/> </a> 
                    	   	    </c:otherwise>
							</c:choose>	
                    	    
                    	    
                    	    </td>
                    	 </tr> --%>
                    	  
    		  </table>
    		  </div>
    		  <div class="col-xs-3 col-md-4">
    		  
    		  				<c:choose>
							    <c:when test="${not empty attribs.picture}">
							     <img src="<c:url value="images//${attribs.id}//${attribs.picture}" />" height= auto; width="200" />	
                				<a class="btn btn-link" id=uploadlinkone onclick="document.forms['singleFiles'].submit()">Update profile Picture <span class="glyphicon glyphicon-user"></span></a>
				 			    </c:when>							       
							    <c:otherwise>
							    <a class="btn btn-link" id=uploadlinkone onclick="document.forms['singleFiles'].submit()">Upload profile Picture <span class="glyphicon glyphicon-user"></span></a>
						    </c:otherwise>
							</c:choose>
							
                </div>
		</div>
		
		       <a class="btn btn-danger" id="edata" onclick="document.forms['userEdits'].submit()">Update Profile </a>
		       <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#show">Reset Password</button>
		       		      <c:choose>
							    <c:when test="${not empty filenames}">
							    <button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#pics">Pictures</button>		       
							   </c:when>							       
							    <c:otherwise>
							    <a  id=uploadlink onclick="document.forms['uploadFiles'].submit()">Upload pictures </a>
							    </c:otherwise>
							</c:choose>
		       
		       <form class="col-md-3" action="Lookupcity" method="GET">
										    <div class="input-group add-on">										      
										      <input type="text" class="form-control" placeholder="Look-up by City or Zip" name="Lookupcity" id="Lookupcity" SIZE='14'>
										      <input type="hidden" name="id" value="${attribs.id}">
										      <div class="input-group-btn">
										        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search" title="Enter City or Zip"></i></button>
										      </div>
										    </div>
									 </form>
			 
	</div>
	
			<div class="container"> 
			    		  
    		  				<c:choose>
							    <c:when test="${not empty attribs.background}">
							       <%-- <h3 class="text-info">${attribs.fname}'s Biography 
							      <br>${attribs.background}</h3>  --%>
							    </c:when>							       
							    <c:otherwise>
							    <div class="col-md-6-">	
							    <form class="col-md-10" action="writeBio" method="post">
							    <input type="hidden" name="id" value="${attribs.id}"/>
		                     	<label for="background" id='background' class="text-danger">Please Write a Biography</label>			
								<textarea class="form-control" id="background" name="background"  rows="5" placeholder="Write a short Bio"></textarea>						
								<br>
								<div class="form-group">
									<button type="submit" name="submit" class="btn btn-primary" >Submit </button>
									<button type="reset" class="btn btn-primary" role="button">Reset</button>
								</div>
								</form>
							    </div> 
							     </c:otherwise>
							</c:choose>
    		    </div>
    		  
	<br>
	<div class="container">
	<div id="pics" class="collapse">
	<h3 class="text-center">Pictures</h3>
			<c:forEach items="${filenames}" var="item">
			<div class="col-md-4" >
			<img src="<c:url value="images//${attribs.id}//${item.filename}" />" height="300" width=320; />	
			<p ><%-- ${item.filename} --%> 
			<a title="Delete Picture" href="deletePicture?id=<c:out value='${item.id}'/>&fileName=<c:out value='${item.filename}'/>">delete picture<span class="glyphicon glyphicon-trash"></span></a>                     	     
			<a title="Make Profile Picture" href="makeprofilePicture?id=<c:out value='${item.id}'/>&fileName=<c:out value='${item.filename}'/>">Make Profile Picture </a>                    	     
			</p>	
			<br>		
			</div>			
			</c:forEach>
	</div>
	</div>
	
	<div id="show" class="collapse">
	<%@include file="getpswd.jsp" %>
	</div>
  </body>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
 	 
 
	
</html>
