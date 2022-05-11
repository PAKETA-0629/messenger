package com.example.messenger.controller.user;

import com.example.messenger.model.User;
import com.example.messenger.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {


    private final UserServiceImpl userService;

    @Autowired
    public LoginController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpServletRequest request, @RequestParam(defaultValue = "") String username) {
        username = username.trim();

        if (username.isEmpty()) {
            return "login";
        } else if (!userService.verify(username)) {
            return "redirect:/register";
        }
        request.getSession().setAttribute("username", username);

        return "redirect:/";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String doRegister(HttpServletRequest request, @RequestBody User user) {
        if(userService.register(user.getPhone(), user.getEmail(), "pass", user.getNickname(), user.getFirst_name(), user.getLast_name(), user.getCreatedAt())) {
            return doLogin(request, user.getNickname());
        } else {
            return "redirect:/register";
        }
    }


    @RequestMapping(path = "/logout")
    public String logout(HttpServletRequest request) {
        request.getSession(true).invalidate();
        return "redirect:/login";
    }
}
