package com.springboot.UserManagementSystem.controllers;

import com.springboot.UserManagementSystem.Exceptions.ApiResponse;
import com.springboot.UserManagementSystem.models.User;
import com.springboot.UserManagementSystem.services.UserService;
import com.springboot.UserManagementSystem.utils.EncryptPwd;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Controller
public class ForgotController {

    private static final Logger log = Logger.getLogger(ForgotController.class);

    Base64.Encoder encoder = Base64.getEncoder();
    Base64.Decoder decoder = Base64.getDecoder();

    @Autowired
    EncryptPwd encryptPwd;
    @Autowired
    UserService userService;

    @GetMapping("/forgot")
    public String forgotPage(){
        return "forgot";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(@RequestParam("email") String email, @RequestParam("securityQuestion") String question, @RequestParam("securityAnswer") String answer, HttpSession session, HttpServletRequest request){
        if(userService.userExist(email)) {
            User user = this.userService.getUserByEmail(email);
            if (!question.equals(user.getSecurityQuestion())){
                session.setAttribute("message", new ApiResponse("Selected question is Incorrect!", null, "alert-danger"));
                return "redirect:/forgot";
            } else if (!answer.equals(user.getSecurityAnswer())) {
                session.setAttribute("message", new ApiResponse("Answer is Incorrect!", null, "alert-danger"));
                return "redirect:/forgot";
            }else {
                String encodeEmail = encoder.encodeToString(email.getBytes(Charset.forName("UTF-8")));
                String url = request.getRequestURL().toString();
                String stringToRemove = "forgotPassword";
                String modifiedURL = url.replace(stringToRemove, "")+"reset/"+encodeEmail;
                log.info(modifiedURL);
                session.setAttribute("message", new ApiResponse("Password reset link sent to the logs", null, "alert-success"));
                return "redirect:/login";
            }
        }else {
            session.setAttribute("message", new ApiResponse("User does not exist with this email!", null, "alert-danger"));
            return "redirect:/forgot";
        }

    }

    @GetMapping("/reset/{email}")
    public String resetPage(@PathVariable("email") String email, Model model){
        byte[] bytes = decoder.decode(email);
        String decodedEmail = new String(bytes, StandardCharsets.UTF_8);
        model.addAttribute("email", decodedEmail);
        return "reset";
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session){
        this.userService.updatePassword(email, this.encryptPwd.encryption(password), session);
        return "redirect:/login";
    }

}
