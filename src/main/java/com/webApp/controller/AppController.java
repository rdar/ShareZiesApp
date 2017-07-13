package com.webApp.controller;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.webApp.WebUtils.States;
import com.webApp.maillService.SendMail;
import com.webApp.model.User;
import com.webApp.service.UserService;

@Controller
@RequestMapping("/")
public class AppController {
	
	   @Autowired
	   private UserService userService; 
	   
	   SendMail  sendMail =new SendMail();
		  
 
	   @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
	    public String index(Model model){ 
	    //model.addAttribute("msg", "Hi welcome to wellness club");
	     
	    	return "index";				 
		} 
	   
	   @RequestMapping(value = "/about", method = RequestMethod.GET)
	    public String about(Model model) {
	               
	        return "about";
	    }
	   
	   @RequestMapping(value = "/contact", method = RequestMethod.GET)
	    public String contact(Model model) {
	    	model.addAttribute("sendEmail", new User());
	    	model.addAttribute("message", "Send ShareZies an email!"); 
	    	
	        return "contact";
	    }
	    
	 
	 @RequestMapping(value = "/services", method = RequestMethod.GET)
	    public String service(Model model) {
	              model.addAttribute("message", "Welcome to ShareZies ");	
	              model.addAttribute("filenames", userService.findAllUsers());
	        return "services";
	    }
	 
	 
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String login(Model model) {
	    	model.addAttribute("loginUser", new User());
	    	model.addAttribute("msg", "Login!");
	        return "login";
	    }
	 
	 @RequestMapping("loginUser")
	    public String login(@ModelAttribute("loginUser") User user, ModelMap model) throws ParseException{	    	
	    		
	    	try {
	    		User details=userService.findByEmail(user.getEmailAddress());
	    		if(userService.findByEmail(user.getEmailAddress())==null){
		    		model.addAttribute("error", "Invalid user "+ user.getEmailAddress());		    		        
	 		        
 		            return "login";	
	    		
		    	}else
	    		if(validateLogin(user.getEmailAddress(), user.getPassword())){
		    		model.addAttribute("success", "Welcome!");		    		        
	 		        model.addAttribute("attribs", details);
	 		       model.addAttribute("filenames", getJoinTables(details.getId()));
 		            return "profile";	
	    		
		    		}else{
		    			model.addAttribute("error", "Invalid credentials try again ");	
	    		 }
			    }
				catch(EmptyResultDataAccessException e){
					model.addAttribute("error", "User "+user.getEmailAddress()+" does not exists <a href='signups'>Sign up </a>");					    
				  }catch (NonUniqueResultException e) {
					   model.addAttribute("error", "error ");
					   model.addAttribute("error", "Dublicates supplied email "+user.getEmailAddress());	
		           	   e.printStackTrace(); 	        
				   }catch(RuntimeException e) {						
						model.addAttribute("error", "Unexpected error occured");
						e.printStackTrace();
					    
				  }
	    	
	    	return "login";
	    }
	 
	 @RequestMapping(value = "/signups", method = RequestMethod.GET)
	    public String signups(Model model) {
	              model.addAttribute("signupMember", new User());
	               
	        return "signups";
	    }
	 
	 @RequestMapping(value = "/signups", method = RequestMethod.POST)
     public String registration(@Valid @ModelAttribute("signupMember") User userLogin, BindingResult result, Model model) {
  	   
	  	    if (result.hasErrors()) {
	 			return "signups";
	 		   }
	  	   
	  	   try {
	  		    User user=userService.findByEmail(userLogin.getEmailAddress());
	  		   	if(user==null){
	    		   model.addAttribute("error", "A user exists with the supplied email "+userLogin.getEmailAddress()+" <a href='forgotpassword'> Forgot Password click here </a>");	
	           	    
	    	     }
	  		   	if(userLogin.getPassword().equals(userLogin.getPasswordConfirm())){
	   	    	userService.saveUser(userLogin);
	   	    	return "redirect:/login";  
 	    		}else{
 	    			model.addAttribute("error", "These Passwords don't match ");
 	    		}
		       
	 		 }catch(EmptyResultDataAccessException e){
	 			model.addAttribute("error", "error ");
	 			e.printStackTrace();
	 			
			   }catch (NonUniqueResultException e) {
				   model.addAttribute("error", "error ");
				   model.addAttribute("error", "A user exists with the supplied email "+userLogin.getEmailAddress()+" <a href='forgotpassword'> Forgot Password click here </a>");	
	           	   e.printStackTrace(); 	        
			   }catch(Exception e) {
				 model.addAttribute("error", "error ");
			     e.printStackTrace(); 
			     
			 }
  	      
           return "signups";
       }
	 
	 
	    @RequestMapping("userEdits")
	    public String userEdits(@RequestParam long id, @ModelAttribute User userLogin, ModelMap model){    	
	    	
	    	try { 
	    		 model.addAttribute("userEdit", userService.findById(id));
	    		 model.addAttribute("attribs", userService.findById(id));
	    		
			    }
				catch(RuntimeException e) {
					model.addAttribute("error", "Unexpected error occured");
				    e.printStackTrace();
				   
			  }
	    		
	    	return "UserUpdateForm";
	    }
	    
	    @RequestMapping("userEdit")
	    public String userEdit(@ModelAttribute("userEdit") User user, BindingResult bindingResult, ModelMap model){    	
	    	        //model.addAttribute("userEdit", new UserLogin());
	    	        //dataEntryValidator.validate(userLogin, bindingResult);        
	    	            
	    	      /* if (bindingResult.hasErrors()) {
	    	    	   model.addAttribute("userEdit", userService.findById(user.getId()));
	  	    		   model.addAttribute("attribs", userService.findById(user.getId()));	  	    		
	    	    	   model.addAttribute("message", "Update Fail");
	    	    	  
	    	       return "UserUpdateForm";
	    	       }else */      
	    	            
	    	      try {
	    	    	    userService.updateUser(user);
			    		model.addAttribute("message", "Update Success");
			    		model.addAttribute("success", "Welcome "+ user.getFname());		    		        
		 		        model.addAttribute("attribs", userService.findByEmail(user.getEmailAddress()));
		 		        model.addAttribute("user", userService.findByEmail(user.getEmailAddress()));
		 		       model.addAttribute("filenames", getJoinTables(user.getId()));
			    		return "profile";
			    }catch(EmptyResultDataAccessException e){
			    	   model.addAttribute("userEdit", userService.findById(user.getId()));
	  	    		   model.addAttribute("attribs", userService.findById(user.getId()));
	  	    		 model.addAttribute("filenames", getJoinTables(user.getId()));
					   model.addAttribute("error", " unexpected error "); 
		    		
					 }		    	
	    	    catch(Exception e) {
	    	    	model.addAttribute("userEdit", userService.findById(user.getId()));
	  	    		   model.addAttribute("attribs", userService.findById(user.getId()));
					     model.addAttribute("error", " unexpected error "+e);
		                   e.printStackTrace();
		                 
		        }
	    	       
	    	 return "UserUpdateForm";
	    }
	    
	    @RequestMapping(value = "/profile", method = RequestMethod.POST)
	    public String profile(@RequestParam long id, Model model) {
	    	try { 
	    		model.addAttribute("attribs", userService.findById(id));
	    		model.addAttribute("message", "Welcome Back Profile"); 
	    		model.addAttribute("filenames", getJoinTables(id));
			    }
				catch(RuntimeException e) {
					model.addAttribute("error", "Unexpected error occured");
					model.addAttribute("attribs", userService.findById(id));
		    		model.addAttribute("message", "Transaction Cancelled");  
		    		model.addAttribute("filenames", getJoinTables(id));
				   
				    e.printStackTrace();
				   
			  }               
	        return "profile";
	    }
	    
	    //logout
	    @RequestMapping(value = "/logout", method = RequestMethod.GET)
	    public String logout(Model model) {
	    	
	    	model.addAttribute("msg", "You have been logged out!");
	        return "login";
	    }
	    
	    @RequestMapping("admin")
	    public String admin(@RequestParam long id, @ModelAttribute User user, Model model) {
	    	
	    	try { 
	    		
		    	model.addAttribute("users", userService.findAllUsers());
				model.addAttribute("user", userService.findById(id));
				      
		        } catch(EmptyResultDataAccessException e){
					 model.addAttribute("error", " Access denied "); 
		    		 
					 }		    	
	    	    catch(Exception e) {
		    	    	model.addAttribute("users", userService.findAllUsers());
						model.addAttribute("user", userService.findById(id));
					
					     model.addAttribute("error", " Access denied ");
		                 e.printStackTrace();
		                 
		        }
			    	              
	        return "admin";
	    }
	    
	    @RequestMapping("assignRole")
	    public String assignrole(@ModelAttribute("assignRole") User userLogin, @RequestParam long id, BindingResult bindingResult, ModelMap model) throws ParseException{    	
	    	try {
	    		String role= userService.findById(userLogin.getId()).getRoles();
	    		if(role.equalsIgnoreCase("super")){
	    			model.addAttribute("error", "cannot re-assign new roles to a user with a super role");
	    			model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", userService.findById(id));
				}else{
					userService.giveUser(userLogin);
		    		model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", userService.findById(id));
				    model.addAttribute("success", "User "+userService.findById(userLogin.getId()).getFname()+" assigned role "+userLogin.getRoles());
		    		}
	    		}
				catch(RuntimeException e) {
					model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", userService.findById(id));
				   
					model.addAttribute("error", "Unexpected error occured");
				    e.printStackTrace();
				   
			  }	
	    	 return "admin";
	    }
	    
	    @RequestMapping("deleteUser")
	    public String searchStudents(@RequestParam String email, @RequestParam long lgid, ModelMap model){    	
	    			
	    	try {
	    		User client= userService.findByEmail(email);
	    		User admin= userService.findById(lgid);
	    		if(email.equalsIgnoreCase(userService.findById(lgid).getEmailAddress())){
	    			model.addAttribute("error", "cannot delete self");
	    			model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", admin);
				}else
	    		if(client.getRoles().equalsIgnoreCase("super")){
	    			model.addAttribute("error", "cannot delete a user with a super role");
	    			model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", admin);
				}else{
					userService.deleteUserByEmail(email);
					model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", admin);
					model.addAttribute("success", "User "+client.getFname()+" Deleted ");
		    		}
	    		}
				catch(RuntimeException e) {
					model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", userService.findById(lgid));					 
					model.addAttribute("error", "Unexpected error occured");
				    e.printStackTrace();
				   
			  }	
	    	 return "admin";
	    	
	    }
	    
	    @RequestMapping("changeSubscriptionType")
	    public String changeMembershipType(@ModelAttribute("changeSubscriptionType") User user, BindingResult bindingResult, ModelMap model) throws ParseException{    	
	    	try {
	    		userService.changeSubscrition(user);
	    		model.addAttribute("success", "Your subscription updated to "+user.getSubscription() +", your credit card on file was proccessed ");		    		        
 		        model.addAttribute("attribs", userService.findById(user.getId()));
		        
	    	    }
				catch(RuntimeException e) {
					model.addAttribute("error", "Unexpected error occured");
					model.addAttribute("attribs", userService.findById(user.getId()));
			       
				    e.printStackTrace();
				   
			  }	
	    	 return "profile";
	    }
	    
	    @RequestMapping(value = "/getnewpass", method = RequestMethod.POST)
	    public String setpass(@ModelAttribute("getnewpass") User userLogin, @RequestParam(value="id",required=false) long id, BindingResult bindingResult, ModelMap model) throws ParseException{    	
	    	try {
	    		User user= userService.findByEmail(userLogin.getEmailAddress());
	    		if(user != null){
		    		if(userLogin.getPassword().equals(userLogin.getPasswordConfirm())){	
		    			userService.resetpswd(user);
		    			model.addAttribute("success", "password reset success, please keep your password safely.");
			    		model.addAttribute("attribs", user); 
			    		
		    			try{
		    			 //mailService.sendMails(userLogin.getEmailAddress() , "Wellness Password", "Your password was reset contact admin if you did not do it");
		    		  	 
    		    		}catch(Exception e){
    		    			if(id<0){
    		    				model.addAttribute("error", "Error sending email to "+ userLogin.getEmailAddress()+ " Please check your network");	
    		    		    	return "resetpassword";
			    			}
    		    			model.addAttribute("error", "Error sending email to "+ userLogin.getEmailAddress()+ " Please check your network");	
    		    		}
		    		}else{
		    			
		    			if(id<0){
		    				model.addAttribute("error", "Password not matching ");	
		    				return "resetpassword";
		    			}else
		    				
		    				model.addAttribute("attribs", user); 			    		
			    			model.addAttribute("error", "Password not matching ");	
		    			}	
		    		}

	    		}catch(EmptyResultDataAccessException e){					  
					 model.addAttribute("error", "No user found, <a href='signups'>Sign up </a>");
					 return "resetpassword";
					 }	
				 catch(RuntimeException e) {
					model.addAttribute("error", "Unexpected error occured");
					model.addAttribute("attribs",userService.findByEmail(userLogin.getEmailAddress()));
				    e.printStackTrace();
					}	
	    	return "profile";
	    }
	    
	    
	    @RequestMapping("Lookupcity")
	    public String searchCity(ModelMap model, @RequestParam("Lookupcity") String cityorZip, @RequestParam(value="id",required=false) long id){    	
	    	
	    	
	    	try { 
	    		       if(!(id==-1)){	    			   
	    			   if(lookupCity(cityorZip).isEmpty()){          
	   	                model.addAttribute("error",  "There are no matches for this Look-up");
	   	                }
	    			    model.addAttribute("filenames", userService.findAllUsers());
	    			    model.addAttribute("users", lookupCity(cityorZip));
	    			    model.addAttribute("member", userService.findById(id));
	    			    if(cityorZip.matches("^\\d+$")){
		    		    model.addAttribute("success", lookupCity(cityorZip).size()+ " Members found in Postal ZIP  " +cityorZip);
		    		    
		    		    }else{
		    		    	model.addAttribute("success", lookupCity(cityorZip).size()+ " Members found in the city of " +cityorZip);
		    		    	}
		    		    	return "members";
	    		       }else	    				   
	    			   if(lookupCity(cityorZip).isEmpty()){          
	   	                model.addAttribute("error",  "There are no matches for this Look-up");
	   	               }  
	    		    model.addAttribute("users", lookupCity(cityorZip));
	    		    model.addAttribute("success", lookupCity(cityorZip).size()+ " Members found in " +cityorZip);
	    		    model.addAttribute("filenames", userService.findAllUsers());	    		
	    		    
				    } 
					catch(RuntimeException e) {
						model.addAttribute("error", "Unexpected error occured");
					    e.printStackTrace();					    
				  }
		    		
		    	return "services";
		    }
	    
	    
	    
	    // for populating states 
	    @ModelAttribute("states")
	    public List<States> populateStates(){
	        return Arrays.asList(States.values());
	    }
	    
	    public boolean validateLogin(String uname, String pswd) {
            User dbCredentials = userService.findByEmail(uname);
            if (!(uname.equals(dbCredentials.getEmailAddress()) && pswd.equals(dbCredentials.getPassword()))){
            return false;      
             }else
            return true;
        }
	    
	    @RequestMapping("bio")
	    public String bio(@ModelAttribute("bio") User user, ModelMap model){	
	    	try {
	    		
	    		User member=userService.findByEmail(user.getEmailAddress());
	    		model.addAttribute("attribs",  userService.findById(user.getId()));
	    		model.addAttribute("filenames", getJoinTables(user.getId()));
	    		//model.addAttribute("filenames", getImages(user.getId()));
		        model.addAttribute("member",  member);
		        }
				catch(RuntimeException e) {
					model.addAttribute("error",  "Unexpected error occured");
				    e.printStackTrace();					    
			  }
	    		
	    	return "bio";
	    }
	    
	    @RequestMapping("bios")
	    public String getbio(@RequestParam long id, @RequestParam String emailAddress, ModelMap model){	
	    	try {
	    		
	    		User member=userService.findByEmail(emailAddress);
	    		model.addAttribute("attribs",  userService.findById(id));
	    		model.addAttribute("filenames", getJoinTables(id));
	    		//model.addAttribute("filenames", getImages(user.getId()));
		        model.addAttribute("member",  member);
		        }
				catch(RuntimeException e) {
					model.addAttribute("error",  "Unexpected error occured");
				    e.printStackTrace();					    
			  }
	    		
	    	return "bio";
	    }
	    
	    @RequestMapping("email-lookup")
	    public String handlepost(@ModelAttribute("email-lookup") User user, ModelMap model){    	
	    		    	 
		    	 try {
		    		     User userDeteails= userService.findByEmail(user.getEmailAddress());
			    		 if(userDeteails != null){
			    		    model.addAttribute("attribs", userDeteails);				 		       
			    		 }else{
			    			 model.addAttribute("error", "User email does noe exist in the Data base"); 
			    		 }		    		 
				     }
					 catch(EmptyResultDataAccessException e){
						   model.addAttribute("error", "User email does noe exist in the Data base");
						 }catch(RuntimeException e) {						
							model.addAttribute("error", "Unexpected error occured");
							e.printStackTrace();
						    
					  }
	    	 
	    	 
	         return "services";
	    	
	    }
	    
	    @RequestMapping("sendmessage")
	    public String contactartist(@ModelAttribute("sendmessage") User user, Model model) { 
	    				  String validemails = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		    	          String message="Name: " +user.getName()+ "\nSubject: " +user.getEmailAddress()+"\n\nMessage: " + user.getMsg();
		    	          
		    	          
		   	                try {  User userdetail= userService.findById(user.getId());				            	   
				            	   if(!user.getEmailAddress().matches(validemails)) {
					    	        	  model.addAttribute("attribs", userdetail);
					                	  model.addAttribute("error", "Invalid email "+ user.getEmailAddress()+ " please use format johnsmith@scglobal.com ");		                  
					   	           }else{
				            	   sendMail.sendMails(user.getEmailAddress() , user.getSubject(), message.toString());					          
				                   model.addAttribute("success", "Thanks you "+user.getName()+" an email has been sent to "+ userdetail.getFname());
				                   model.addAttribute("attribs", userdetail);
					   	           }
						        }catch(Exception e) {
						                 model.addAttribute("error", "Error sending email  " );
						                 model.addAttribute("attribs", userService.findById(user.getId()));
						                 e.printStackTrace();
						        }
		   	                
					
					return "profile";
			    }
	    
	    @RequestMapping(value = "/sendEmail", method = RequestMethod.POST)
	    public String sendMail(@ModelAttribute("sendEmail") User user, BindingResult bindingResult, Model model) { 
	    	          
	    	          String validemails = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 
	    	          model.addAttribute("em", user.getEmailAddress());
	       	          model.addAttribute("sb", user.getSubject());              
	                  String message="Subject: " +user.getSubject()+"\nMessage: " + user.getMsg();
	          
	                  if(!user.getEmailAddress().matches(validemails)) {
	                	  model.addAttribute("emailrror", "Invalid email "+ user.getEmailAddress()+ " use format johnsmith@scglobal.com ");		                  
	   	               }
	                  
	                  if(user.getSubject().isEmpty()) {
	                	  model.addAttribute("sbjrror", "Please enter Emain Subject");		                    
	   		           }  
	                  
	                  if(user.getMsg().length() <= 1 || user.getMsg().length() > 3000) {
	                	  model.addAttribute("msgerror", "Must be between 10 and 300 characters");		                   
	   		           }
	                  
	                  else
			       
			             try {     
			            	   sendMail.sendMails(user.getEmailAddress() , user.getSubject(), message.toString());					          
			                   model.addAttribute("success", "Thanks Email has been sent to "+ user.getEmailAddress());
			                   model.addAttribute("emailrror", "");  
			                   model.addAttribute("sbjrror", "");		                   
			       	           model.addAttribute("msgerror", "");
			       	           model.addAttribute("em", "");
			       	           model.addAttribute("sb", "");   
			       	           
					        }catch(Exception e) {
					                 model.addAttribute("sendmailfail", "Error sending email  " );
					                 e.printStackTrace();
					        }
					
					return "contact";
			    }
	    
	    @RequestMapping("LookupMembers")
	    public String searchMembers(ModelMap model, @RequestParam("LookupMembers") String names, @RequestParam(value="id",required=false) long id){    	
	    	
	    	try {
	    		long membersId=id;
	    		//List<User> memberList = lookupByName(names);
	    		List<User> memberList = userService.findUsersByName(names);
	    		if(memberList.isEmpty()){  
	    			model.addAttribute("filenames", userService.findAllUsers());
	                model.addAttribute("error",  "There are no matches for this Look-up");
	               }
	    		else		    				
	    			model.addAttribute("success", memberList.size()+ " members found with name " +names);
	    		model.addAttribute("filenames", userService.findAllUsers());    		    
	    		    model.addAttribute("member", userService.findById(membersId));
	    		    model.addAttribute("users",memberList);
	    		    
			    }
				catch(RuntimeException e) {
					model.addAttribute("error", "Unexpected error occured");
					model.addAttribute("filenames", userService.findAllUsers());
					e.printStackTrace();					    
			  }
	    		
	    	return "members";
	    }
	    
	    @RequestMapping("lookUpByLastnameOrFirstname")
	    public String lookUpname(@ModelAttribute("lookUpByLastnameOrFirstname") User user, ModelMap model){    	
	    	  
	    	     try {
	    	    	    model.addAttribute("users", lookupByName(user.getFname()));
	    	    	    model.addAttribute("filenames", userService.findAllUsers());
				     }
					 catch(EmptyResultDataAccessException e){
						 model.addAttribute("error", "User "+user.getFname()+" does noe exist in the Data base"); 
						 model.addAttribute("users", lookupByName(user.getFname()));
						 model.addAttribute("filenames", userService.findAllUsers());
						 }catch(RuntimeException e) {	
							 
							 model.addAttribute("filenames", userService.findAllUsers());
							model.addAttribute("error", "Unexpected error occured"+e);
							model.addAttribute("users", lookupByName(user.getFname()));
							e.printStackTrace();
					  } 
	         return "services";
	    	
	    }
	    
	    @RequestMapping("editUser")
	    public String editUser(@RequestParam String email, @RequestParam long lgid, ModelMap model){    	
	    			
	    	try {
	    		User client= userService.findByEmail(email);
	    		User admin= userService.findById(lgid);
	    		
	    		if(client.getRoles().equalsIgnoreCase("super")){
	    			model.addAttribute("error", "cannot edit a user with a super role");
	    			model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", admin);
				}else{
					userService.deleteUserByEmail(email);					
					model.addAttribute("user", admin);
					model.addAttribute("attribs", client);
					return "dataEdit";
		    		}
	    		}
				catch(RuntimeException e) {
					model.addAttribute("users", userService.findAllUsers());
					model.addAttribute("user", userService.findById(lgid));					 
					model.addAttribute("error", "Unexpected error occured");
				    e.printStackTrace();
				   
			  }	
	    	 
	    	 return "admin";
	    	
	    }
	    @RequestMapping("saveUser")
	    public String adminEdit(@ModelAttribute("saveUser") User user, @RequestParam long loggedin, ModelMap model) {
	    	try {
	    		User client= userService.findByEmail(user.getEmailAddress());
	    		User admin= userService.findById(loggedin);
	    		
	    		model.addAttribute("users", userService.findAllUsers());
				model.addAttribute("user", admin);
				model.addAttribute("success", "User "+client.getFname()+" Updated ");
			    return "admin";
			    }
				catch(RuntimeException e) {
					model.addAttribute("error", "Unexpected error occured");
				    e.printStackTrace();
				   
			  }	
	    	 return "dataEdit";
	    }
	    
	    @Autowired
		 DataSource dataSource;
	    
	   
		@SuppressWarnings("rawtypes")
		public List<User> lookupByName(String name) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);        	
	       	final String sql  = "SELECT m.* FROM users m WHERE m.lname like '%"+ name +"%'"
						       	+ " UNION "
								+ "SELECT m.* FROM users m WHERE m.fname like '%"+ name +"%'";
	       	@SuppressWarnings("unchecked")
	   		List<User> memberList = jdbcTemplate.query(sql, new BeanPropertyRowMapper( User.class ));						
	   		return memberList;
	   		 
		}
	    
	    @SuppressWarnings("rawtypes")
		public List<User> lookupCity(String cityOrZip) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource); 
			
	       	final String sql  = "SELECT m.* FROM users m WHERE m.city like '%"+ cityOrZip +"%'"
	       			+ " UNION "
	       			+ "SELECT m.* FROM users m WHERE m.zip like '%"+ cityOrZip +"%'";
	       	@SuppressWarnings("unchecked")
	   		List<User> memberList = jdbcTemplate.query(sql, new BeanPropertyRowMapper( User.class ));						
	   		return memberList;
	   		 
		}
	    
	    public List<Map<String, Object>> getImages(long id) {
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        String getimage="SELECT distinct* FROM docs where id='%"+ id +"%'";
	     return jdbcTemplate.queryForList(getimage);
	 }
	    
	    public List<Map<String, Object>> getJoinTables(long id) {
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            String join="SELECT DISTINCT users.*, docs.*  FROM users JOIN docs"
                                       +" ON docs.id = users.id"
                                       +" WHERE users.id like '%"+ id +"%'";
         return jdbcTemplate.queryForList(join);
     }
	    
	    

}