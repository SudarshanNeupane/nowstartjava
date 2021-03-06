package com.nowstartjava.tutorials.cms;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nowstartjava.tutorials.exceptions.UserAlreadyExistsException;
import com.nowstartjava.tutorials.model.Category;
import com.nowstartjava.tutorials.model.Role;
import com.nowstartjava.tutorials.model.User;
import com.nowstartjava.tutorials.service.CategoryService;
import com.nowstartjava.tutorials.service.UserService;

@Controller
@RequestMapping(value = "/cms/writers")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String addWriter(Model model){
		model.addAttribute("categories", categoryService.findAll());
		return "cms/content-writer";
	}
	
	@RequestMapping(value="/get_all",method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAllWriters(){
		return new ResponseEntity<List<User>>(userService.findAll(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<User> addWriter(@RequestBody String str) throws JsonProcessingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(str);
		
		ArrayList categoryIds = mapper.convertValue(node.get("categories"), ArrayList.class);
		List<Category> categories = new ArrayList<Category>();
		
		for(Object id: categoryIds){
			Category category = categoryService.findOne(Integer.valueOf((String)id));			
			categories.add(category);			
		}
		
		Integer userId = mapper.convertValue(node.get("id"), Integer.class);
		
		String method = mapper.convertValue(node.get("method"), String.class).trim();
		
		User user = mapper.convertValue(node.get("user"), User.class);
		
		if(userId != null && userId.intValue() >= 0 ){
			user.setId(userId);
		}
		
		user.setRole(Role.ROLE_WRITER);
		user.setCategories(categories);
		
		if( method.equalsIgnoreCase("Update")){
			userService.update(user);
			return new ResponseEntity<User>(user,HttpStatus.OK);
		}
		
		try{
			userService.save(user);
		}catch(UserAlreadyExistsException ex){
			System.out.println("Exception Thrown");
			return new ResponseEntity<User>(user,HttpStatus.CONFLICT);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/delete/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<User> removeWriter(@PathVariable("id")Integer id){
		User deletedUser = userService.delete(id);
		if(deletedUser == null){
			return new ResponseEntity<User>(deletedUser,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(deletedUser,HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/get_one/{id}",method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable("id")Integer id){
		User updatedUser = userService.findOne(id);
		if(updatedUser == null){
			return new ResponseEntity<User>(updatedUser,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(updatedUser,HttpStatus.OK);
	}


}
