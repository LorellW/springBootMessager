package com.github.lorellw.letscode.controllers;

import com.github.lorellw.letscode.entiites.User;
import com.github.lorellw.letscode.entiites.dto.CaptchaResponseDto;
import com.github.lorellw.letscode.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;


@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL="https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private final UserService userService;
    private final RestTemplate restTemplate;
    @Value("${recaptcha.secret}")
    private String secret;

    public RegistrationController(UserService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(
            @RequestParam("password2") String passwordConfirm,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            @Valid User user,
            BindingResult bindingResult,
            Model model) {
        boolean isConfirmed = !StringUtils.hasLength(passwordConfirm);
        String url = String.format(CAPTCHA_URL,secret,captchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(url, Collections.EMPTY_LIST, CaptchaResponseDto.class);
        if (!captchaResponseDto.isSuccess()){
            model.addAttribute("captchaError", "Fill captcha");
        }
        if (isConfirmed){
            model.addAttribute("password2Error", "Password confirmation can't be empty");
        }

        if (user.getPassword()!=null && !user.getPassword().equals(passwordConfirm)){
            model.addAttribute("passwordError", "Passwords are different");
            return "registration";
        }
        if (!captchaResponseDto.isSuccess() || isConfirmed || bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exists");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "login";
    }
}
