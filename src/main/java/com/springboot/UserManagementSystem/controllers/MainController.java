package com.springboot.UserManagementSystem.controllers;

import com.springboot.UserManagementSystem.Exceptions.ApiResponse;
import com.springboot.UserManagementSystem.models.User;
import com.springboot.UserManagementSystem.services.UserService;
import com.springboot.UserManagementSystem.utils.EncryptPwd;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    UserService userService;

    @Autowired
    EncryptPwd encryptPwd;

    @RequestMapping({"/", "/home"})
    public String homePage(HttpSession session) {
        if (session == null) {
            return "home";
        } else {
            String loggedIn = (String) session.getAttribute("loggedIn");
            if (loggedIn == null) {
                return "home";
            } else {
                if (loggedIn.equals("admin")) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/viewPage";
                }
            }

        }

    }


    @GetMapping(value = "/register")
    public String form(Model model, HttpSession session) {
        if (session == null) {
            model.addAttribute("user", new User());
            return "register";
        } else {
            String loggedIn = (String) session.getAttribute("loggedIn");
            if (loggedIn == null) {
                model.addAttribute("user", new User());
                return "register";
            } else {
                if (loggedIn.equals("admin")) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/viewPage";
                }
            }

        }

    }

    @GetMapping(value = "/login")
    public String loginPage(HttpSession session) {
        if (session == null) {
            return "login";
        } else {
            String loggedIn = (String) session.getAttribute("loggedIn");
            if (loggedIn == null) {
                return "login";
            } else {
                if (loggedIn.equals("admin")) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/viewPage";
                }
            }

        }
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, HttpSession session) {
        session.setAttribute("userId", id);
        return "redirect:/editPage";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, HttpSession session, HttpServletResponse res) {
        Object obj = session.getAttribute("loggedIn");
        if (obj == null) {
            return "redirect:/login";
        } else if (((String) obj).equals("admin")) {
            int id = (int) session.getAttribute("adminId");
            List<User> userList = this.userService.getAllUsers();
            List<User> users = new ArrayList<>();
            for (User user : userList) {
                if (user.getId() == id) {
                    continue;
                }
                users.add(user);
            }
            model.addAttribute("users", users);

            return "admin";
        } else {
            return "redirect:/viewPage";
        }
    }

    @GetMapping("/editPage")
    public String editPage(Model model, HttpSession session) {
        if (session==null){
            return "redirect:/login";
        }else {
            String loggedIn = (String) session.getAttribute("loggedIn");
            if (loggedIn == null){
                return "redirect:/login";
            }else {
                int id = (int) session.getAttribute("userId");
                User user = this.userService.getUserById(id);
                String base64Image = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(user.getImage()) + "";
                model.addAttribute("user", user);
                model.addAttribute("imgUrl", base64Image);
                return "edit";
            }
        }

    }

    @GetMapping("/viewPage")
    public String viewPage(Model model, HttpSession session, HttpServletResponse res) {
        Object obj = session.getAttribute("loggedIn");
        if (obj == null) {
            return "redirect:/login";
        } else if (((String) obj).equals("user")) {
            int id = (int) session.getAttribute("userId");
            User user = this.userService.getUserById(id);
            String base64Image = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(user.getImage()) + "";
            model.addAttribute("user", user);
            model.addAttribute("imgUrl", base64Image);
//        session.setAttribute("message", new ApiResponse("User loggedIn Successfully", null, "alert-success"));
            return "view";
        } else {
            return "redirect:/admin";
        }
    }

    @PostMapping("/loginUser")
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        boolean exist = this.userService.userExist(email);
        User user = this.userService.getUserByEmail(email);
        if (!exist) {
            session.setAttribute("message", new ApiResponse("User not exist with this email, please create new one!", null, "alert-danger"));
            return "login";
        } else if (user.getPassword().equals(this.encryptPwd.encryption(password))) {
            if (user.getRole().equals("ADMIN")) {
                session.setAttribute("adminId", user.getId());
                session.setAttribute("loggedIn", "admin");
                session.setAttribute("message", new ApiResponse("Admin loggedIn Successfully", null, "alert-success"));
                return "redirect:/admin";
            } else {
                session.setAttribute("userId", user.getId());
                session.setAttribute("loggedIn", "user");
                session.setAttribute("message", new ApiResponse("User loggedIn Successfully", null, "alert-success"));
                return "redirect:/viewPage";
            }
        } else {
            session.setAttribute("message", new ApiResponse("Incorrect password!", null, "alert-danger"));
            return "login";
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {
        session.setAttribute("userId", id);
        return "redirect:/deleteUser";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("userId");
        session.removeAttribute("adminId");
        session.removeAttribute("loggedIn");
        session.setAttribute("message", new ApiResponse("LoggedOut Successfully", null, "alert-success"));
        return "redirect:/login";
    }


}
