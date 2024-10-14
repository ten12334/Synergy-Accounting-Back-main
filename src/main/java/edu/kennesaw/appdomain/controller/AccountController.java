package edu.kennesaw.appdomain.controller;

import edu.kennesaw.appdomain.entity.Account;
import edu.kennesaw.appdomain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "https://synergyaccounting.app", allowCredentials = "true")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public ResponseEntity<Account> addAccount(@RequestBody Account account) {
        try {
            Account newAccount = accountService.createAccount(account);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Account> deactivateAccount(@PathVariable Long id) {
        try {
            Account updateAccount = accountService.deactivateAccount(id);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/chart-of-accounts")
    public List<Account> getChartOfAccounts() {
        return accountService.getAllAccounts();
    }
}
