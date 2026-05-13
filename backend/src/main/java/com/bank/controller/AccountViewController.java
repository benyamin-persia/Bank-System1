package com.bank.controller;
import com.bank.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AccountViewController {
    private final AccountService accountService;
    @GetMapping("/accounts")
    public String myAccounts(@AuthenticationPrincipal UserDetails user,
                             Model model) {
// Only loads accounts that belong to this user
        model.addAttribute("accounts",
                accountService.getMyAccounts(user.getUsername()));
        return "accounts";
    }
//    @GetMapping("/accounts/{id}")
//    public String accountDetail(@PathVariable Long id,
//                                @AuthenticationPrincipal UserDetails user,
//                                Model model) {
//// Throws 404 if account doesn't belong to this user — no IDOR
//        model.addAttribute("account",
//                accountService.getAccountById(id, user.getUsername()));
//        return "account-detail";
//    }


@GetMapping("/admin/users")
// Allows only admins to access this page
@PreAuthorize("hasRole('ADMIN')")
public String adminUsers(Model model) {
    // Adds all accounts for the admin page
    model.addAttribute("accounts", accountService.getAllAccounts());
    // Reuse accounts.html for now
    return "accounts";
}
}
