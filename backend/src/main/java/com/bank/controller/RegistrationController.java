package com.bank.controller;

import com.bank.model.Customer;
import com.bank.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService registrationService;
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute Customer customer) {
        registrationService.register(customer);
        return "redirect:/login?registered";
    }
    @GetMapping("/verify")
    public String verify(@RequestParam String token) {
        registrationService.verify(token);
        return "redirect:/login?verified";

    }
}
