//package com.springboot.UserManagementSystem.utils;
//
//import com.springboot.UserManagementSystem.models.User;
//import com.springboot.UserManagementSystem.services.UserService;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.regex.Pattern;
//
//
//public class CheckValidation {
//	private static final Logger log = Logger.getLogger(CheckValidation.class);
//	private  transient  String regex = "^(?=.*[0-9])"
//            + "(?=.*[a-z])(?=.*[A-Z])"
//            + "(?=.*[@#$%^&+=])"
//            + "(?=\\S+$).{8,20}$";
//	@Autowired
//	private UserService userService;
//
//	public boolean validatepwd(String pwd)
//	{
//		if(Pattern.matches(regex,pwd))
//		{
//			log.debug("Password Validation Pass");
//			return false;
//		}
//		else
//		{
//			log.debug("Password Validation Fails");
//			return true;
//		}
//	}
//	public String validData(User user, String repwd) {
//	String message="";
//			if(userService.userExist(user.getEmail()))
//			{
//				message= "*Email already exist";
//			}
//			else if(validatepwd(user.getPassword()))
//			{
//				message="*Please Choose Strong Password.";
//			}
//			else if(!user.getPassword().equals(repwd))
//			{
//				message= "*Confirm password Should be same as Password.";
//			}
//			else
//			{
//				message="valid";
//			}
//	return message;
//	}
//
//}
