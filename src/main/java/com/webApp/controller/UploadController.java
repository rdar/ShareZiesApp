package com.webApp.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.webApp.WebUtils.FileValidator;
import com.webApp.WebUtils.MultiFileValidator;
import com.webApp.model.FileBucket;
import com.webApp.model.MultiFileBucket;
import com.webApp.service.UserService;


@Controller
@RequestMapping("/")
public class UploadController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	FileValidator fileValidator;

	@Autowired
	MultiFileValidator multiFileValidator;

	@Autowired
	   private UserService userService; 
	
	@InitBinder("fileBucket")
	protected void initBinderFileBucket(WebDataBinder binder) {
		binder.setValidator(fileValidator);
	}

	@InitBinder("multiFileBucket")
	protected void initBinderMultiFileBucket(WebDataBinder binder) {
		binder.setValidator(multiFileValidator);
	}

	@RequestMapping(value = { "/welcome" }, method = RequestMethod.GET)
	public String getHomePage(ModelMap model) {
		return "welcome";
	}

	@RequestMapping(value = "/singleUpload", method = RequestMethod.GET)
	public String getSingleUploadPage(ModelMap model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
		return "singleFileUploader";
	}
	
	@Autowired
    ServletContext context;

    @RequestMapping(value="/singleUpload", method = RequestMethod.POST)
    public String singleFileUpload(@Valid FileBucket fileBucket, BindingResult result, ModelMap model) throws IOException {
                       
                        long id=fileBucket.getId();  
                        String temp = "/Users/renedarris/Documents/FPTEST111/images";
                        String destDir = "/Users/renedarris/Documents/FPTEST111/images/"+id;    
                      
                        String profiles = "/Users/renedarris/Documents/FPTEST111/images/"+id+"/profile";
                       // String profiles = context.getRealPath("images\\"+id+"\\");
                        String destFile = fileBucket.getFile().getOriginalFilename();
                    if (result.hasErrors()) {
                                    System.out.println("validation errors");
                                    return "singleFileUploader";
                    } else {                                 
                                    System.out.println("Fetching file");
                                    MultipartFile multipartFile = fileBucket.getFile();
                                   
                                    File dir = new File(destDir);
                                    File profile = new File(profiles);
                                    File tmp = new File(temp);
                                    if(!dir.exists()){                                    	 
                                    	new File(destDir).mkdirs();                                        
                                     }
                                    if(!tmp.exists()){
                                    	new File(temp).mkdirs();  
                                      } 
                                    if(!profile.exists()){                                    	
                                        new File(profiles).mkdirs();
                                       }             


                                    //Now do something with file...
                                    FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(destDir + "/" + destFile));
                                    FileCopyUtils.copy(fileBucket.getFile().getBytes(), new File(profiles + "/" + destFile));
                                    //model.addAttribute("contextPath", destDir);
                                    String fileName = multipartFile.getOriginalFilename();
                                    model.addAttribute("file", fileName);
                            		
                                    
                                    try {                                    	
                                    	updateimages(destFile, id);
                                    	model.addAttribute("attribs", userService.findById(id));  
                                    	model.addAttribute("filenames", getJoinTables(id));
                                    	
                                    	
                                    	
                                    }
                            			catch(RuntimeException e) {
                            				model.addAttribute("error", "Unexpected error occured");
                            				model.addAttribute("attribs", userService.findById(id));				
                            			    e.printStackTrace();
                            		  }  
                                    
                                    return "profile";
                    }
    }
   
    @RequestMapping(value="/multiUpload", method = RequestMethod.POST)
    public String multiFileUpload(@Valid MultiFileBucket multiFileBucket, BindingResult result, ModelMap model) throws IOException {
                       long id=multiFileBucket.getId();
                       String destDir = "/Users/renedarris/Documents/FPTEST111/images/"+id;
                      // String destDir = context.getRealPath("images\\"+id+"\\");
                       File dir = new File(destDir);
                                    if(!dir.exists()){
                                                    new File(destDir).mkdirs();
                                    }             
                   
                    if (result.hasErrors()) {
                                    System.out.println("validation errors in multi upload");
                                    return "multiFileUploader";
                    } else {                                 
                                    System.out.println("Fetching files");
                                    List<String> fileNames= new ArrayList<String>();
                                   
                                    //Now do something with file...
                                    for(FileBucket bucket : multiFileBucket.getFiles()){                                         
                                                   
                                                    String destFile = bucket.getFile().getOriginalFilename();
                                                    FileCopyUtils.copy(bucket.getFile().getBytes(), new File(destDir + "/" + destFile));
                                                    fileNames.add(bucket.getFile().getOriginalFilename());
                                                    
                                                    
                                                    try {
                                                    	insertMultimage(destFile, id);
                                                    	model.addAttribute("fileNames", fileNames);
                                                    	
                                                    	model.addAttribute("attribs", userService.findById(id)); 
                                                    	model.addAttribute("images", getImages(id));
                                        				
                                            		    }
                                            			catch(RuntimeException e) {
                                            				model.addAttribute("error", "Unexpected error occured");
                                            				model.addAttribute("attribs", userService.findById(id));
                                            				model.addAttribute("images", getImages(id));
                                            				
                                            			    e.printStackTrace();
                                            		  }
                                       }
                                   
                                    
                                    
                                    return "welcome";
                    }
    }
    
	

	@RequestMapping(value = "/multiUpload", method = RequestMethod.GET)
	public String getMultiUploadPage(ModelMap model) {
		MultiFileBucket filesModel = new MultiFileBucket();
		model.addAttribute("multiFileBucket", filesModel);
		return "multiFileUploader";
	}
	
	@RequestMapping("uploadFiles")
    public String upload(@RequestParam long id, Model model) {
    	try { 
    		model.addAttribute("attribs", userService.findById(id));    		   
		    }
			catch(RuntimeException e) {
				model.addAttribute("error", "Unexpected error occured");
				model.addAttribute("attribs", userService.findById(id));
				model.addAttribute("images", getImages(id));
				
			    e.printStackTrace();
			    return "profile";
			   
		  }               
        return "welcome";
    }
	
	@RequestMapping("uploadsingleFile")
    public String uploadsingleFile(@RequestParam long id, Model model) {
		FileBucket fileModel = new FileBucket();
		model.addAttribute("fileBucket", fileModel);
    	try { 
    		model.addAttribute("attribs", userService.findById(id));    		   
		    }
			catch(RuntimeException e) {
				model.addAttribute("error", "Unexpected error occured");
				model.addAttribute("attribs", userService.findById(id));				
			    e.printStackTrace();
			    return "welcome";
			   
		  }               
        return "singleFileUploader";
    }
	
	@RequestMapping("multiFiles")
    public String multiFiles(@RequestParam long id, Model model) {
		
		MultiFileBucket filesModel = new MultiFileBucket();
		model.addAttribute("multiFileBucket", filesModel);
		
    	try { 
    		model.addAttribute("attribs", userService.findById(id));    		   
		    }
			catch(RuntimeException e) {
				model.addAttribute("error", "Unexpected error occured");
				model.addAttribute("attribs", userService.findById(id));				
			    e.printStackTrace();
			    return "welcome";
			   
		  }               
        return "multiFileUploader";
    }
	
	@RequestMapping("deletePicture")
    public String deletePicture(@RequestParam long id, @RequestParam String fileName, ModelMap model){    	
    			
    	try {
    		deleteImages(fileName, id);
    		model.addAttribute("attribs", userService.findById(id));
    		model.addAttribute("message", "Picture deleted file "+fileName); 
    		model.addAttribute("filenames", getJoinTables(id));
    		
    		}
			catch(RuntimeException e) {
								 
				model.addAttribute("error", "Unexpected error occured");
			    e.printStackTrace();
			   
		  }	
    	 return "profile";
    	
    }
	
	@RequestMapping("makeprofilePicture")
    public String makeprofilePicture(@RequestParam long id, @RequestParam String fileName, ModelMap model){    	
		// copy picture to profile directory
		Path sourcePath      = Paths.get("/Users/renedarris/Documents/FPTEST111/images"+id+"\\"+fileName+"");
		Path destinationPath = Paths.get("/Users/renedarris/Documents/FPTEST111/images"+fileName+"");

		try {
		    Files.copy(sourcePath, destinationPath);
		} catch(FileAlreadyExistsException e) {
		    //e.printStackTrace();
		} catch (IOException e) {		    
		    //e.printStackTrace();
		}
		
    	try {
    		updateimages(fileName, id);
    		 
    		model.addAttribute("attribs", userService.findById(id));
    		model.addAttribute("message", "Profile Picture changed "+fileName); 
    		model.addAttribute("filenames", getJoinTables(id));
    		
    		}
			catch(RuntimeException e) {
								 
				model.addAttribute("error", "Unexpected error occured");
			    e.printStackTrace();
			   
		  }	
    	 return "profile";
    	
    }
	
	public void deleteImages(String fileName, long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("delete from docs where filename = ? AND id=?", fileName, id);
	}
	
	public void updateimages(String fileName, long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("UPDATE users SET picture=? where id=?", fileName, id);
	}
	
	public void insertMultimage(String fileName, long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("INSERT INTO docs SET filename=?, id=? ", fileName, id);
	}
	
	public List<Map<String, Object>> getImages(long id) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String getimage="SELECT * FROM docs where id='%"+ id +"%'";
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
