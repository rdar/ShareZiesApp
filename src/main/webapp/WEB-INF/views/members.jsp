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
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
  </head>
  <body>
	<header>		
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
							    <li >
							     <a id="goprofile" onclick="document.forms['profilelink'].submit()">Profile </a>  		  
			                    </li>
								<li class="active"><a href="#">Members</a></li>	
								<c:if test="${empty member.fname}">  
								<li><a href="services">Look-Ups</a></li>
								</c:if>								
								<li role="presentation" class="small">
									<form class="navbar-form navbar-right" role="search"  action="LookupMembers" method="post">
										    <div class="input-group add-on">
										      <input type="hidden" name="id" value="${member.id}"/>
										      <input type="text" class="form-control" placeholder="Search Members" name="LookupMembers" id="LookupMembers" SIZE='13'>
										      <div class="input-group-btn">
										        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search"></i></button>
										      </div>
										    </div>
									 </form>									
						        </li>
						        
			                    <li class="pull-right"><a href="login">${member.fname} | Logout</a></li>	
							</ul>
						</div>
					</div>						
				</div>
			</div>				
	</header>
	
	<div class="container">
	
							 
	<form class="navbar-form navbar-right" role="search"  action="Lookupcity" method="post">
										    <div class="input-group add-on">										      
										      <input type="text" class="form-control" placeholder="Find members by City (Enter City or Zip)" name="Lookupcity" id="Lookupcity" SIZE='35'>
										      <input type="hidden" name="id" value="${member.id}">
										      <div class="input-group-btn">
										        <button class="btn btn-default" type="submit"><i class="glyphicon glyphicon-search" title="Enter City or Zip"></i></button>
										      </div>
										    </div>
									 </form>
	
	
	<form id="profilelink" method="post" action="profile">
	<input type="hidden" name="id" value="${member.id}">
	</form>
	
	
		                   <c:choose>
							    <c:when test="${empty users}">
							     <div>
									
									<h2>${message}</h2>
									<h2>${success} ${role}</h2>
									<h2>${error} </h2>				
								 </div>    
							    </c:when>    
							    <c:otherwise>
							     <div>
									
									<p>${msg} </p>
									
									<h2>${message}</h2>
									<h2>${success}</h2>
									<h2>${error} </h2>				
								</div>     
							    </c:otherwise>
							</c:choose>	
		               <div>
		               
		<c:if test="${not empty users}">
		<table class="table table-hover table-bordered">
                    <thead style="background-color: #ff6600;">
                    <tr > 
                        <th>Name</th>
                                                
                        <th>Email</th>
                        <th>Telephone</th>	
                        <th>Address</th> 			
						                  	
                    </tr>
                    </thead>
                    <tbody  >
                     <c:forEach items="${users}" var="item">
                        <tr class="text-success">
                            <th> 
                            <%-- <form id="bio" method="post"  action="bio">
                            <input type="hidden" name="emailAddress" value="${member.emailAddress}"/>
                             <c:forEach items="${users}" var="user">                           		 
								 <input type="hidden" name="id" value="${user.id}"/>
							 </c:forEach>
						    </form> --%>
						    <a title="Go to bio" href="bios?id=<c:out value='${item.id}'/>&emailAddress=<c:out value='${member.emailAddress}'/>">
						    <c:out value="${item.fname}"/> 
						    </a>
                             <%-- <a title="Bio" id="gotoBio" onclick="document.forms['bio'].submit()"><c:out value="${item.fname}"/> <c:out value="${item.lname}"/> </a>
                            --%>
                            </th>
							
							<th><c:out value="${item.emailAddress}"/></th> 
							<th><c:out value="${item.tel}"/></th> 
							<th><c:out value="${item.address}"/>, <c:out value="${item.city}"/>
						    <c:out value="${item.state}"/> <c:out value="${item.zip}"/></th>
							 					                	
                           </tr>
                           </c:forEach>
                    </tbody>
                </table>
		</c:if>
		
		
		
		<h3>all users</h3>					
		<c:forEach items="${filenames}" var="item">
			<div class="col-md-3">				
			<a title="go to bio" href="bios?id=<c:out value='${item.id}'/>&emailAddress=<c:out value='${member.emailAddress}'/>">
			<img src="<c:url value="images//${item.id}//profile//${item.picture}" />" width="180" height="150"></a>	
			<p class="text-info"> 
			${item.fname} ${item.fname}
			<a title="go to bio" href="bios?id=<c:out value='${item.id}'/>&emailAddress=<c:out value='${member.emailAddress}'/>"></a> 														        
			</p> 	
			<br>							    				
			</div>			
			</c:forEach>
		</div>
	</div>
   
	
	
  </body>
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	
</html>
