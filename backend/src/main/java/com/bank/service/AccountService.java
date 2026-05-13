package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    public Account findById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
    public Account save(Account account) {
        return accountRepository.save(account);
    }
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }
    public List<Account> getAllAccounts() {
        // Loads every account from the database
        return accountRepository.findAll();
    }
    public List<Account> getMyAccounts(String username){
        return  accountRepository.findAccountByCustomer_Username(username);
    }
    public Account getAccountById(Long id, String username){
        return  accountRepository.findByIdAndCustomer_Username(id,username).orElseThrow(()->new RuntimeException("Account not found"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }
}
