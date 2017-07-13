

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    
  </head>
  <body>
	<div class="col-md-12 col-md-offset-0" >		 
		<form id="sendmessage" method="post" action="sendmessage">
				    <input type="hidden" name="id" value="${member.id}"/>				    
					<input type="hidden"  id="emailAddress" name="emailAddress"  value="${attribs.emailAddress}" >
					<input type="hidden"  id="subject" name="subject"  value="${member.emailAddress}" >	
			<div class="col-lg-10">				
				<textarea class="form-control" id="msg" name="msg"  rows="5" placeholder="Hey  ${member.fname} drop an Email to ${attribs.fname}" required></textarea>
				<br>
				<div class="form-group">
					<button type="submit" name="submit" class="btn btn-primary" >Send Email</button>					
				</div>
			</div>
			</form>	
			</div>	
	
	
  </body>
  
   <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	
</html>
