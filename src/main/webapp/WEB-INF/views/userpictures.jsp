
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    
  </head>
  <body>
	<div class="container">
	<h3 class="text-info">Pictures</h3>
			<c:forEach items="${filenames}" var="item">
			<div class="col-md-4">
			<img src="<c:url value="images//${attribs.id}//${item.filename}" />" width="340" height="240"/>	
			<p>${item.filename} </p>	
			<br>		
			</div>			
			</c:forEach>
	</div>
   
  </body>
  
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	
</html>
