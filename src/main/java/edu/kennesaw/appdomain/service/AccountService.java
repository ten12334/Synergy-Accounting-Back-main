package edu.kennesaw.appdomain.service;

import edu.kennesaw.appdomain.entity.Account;
import edu.kennesaw.appdomain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account) throws Exception{
        if(accountRepository.findByAccountNumber(account.getAccountNumber()).isPresent()){
            throw new Exception("Warning: Duplicate account number");
        }
        if(accountRepository.findByAccountName(account.getAccountName()).isPresent()){
            throw new Exception("Warning: Duplicate account name");
        }
        account.setDateAdded(LocaleDateTime.now());
        account.setActive(true);
        return accountRepository.save(account);

    }
    public Account deactivateAccount(Long id) throws Exception{
        Account account = accountRepository.findById(id).orElseThrow() -> new Exception("Account not found"));
        if(account.getBalance() > 0){
            throw new Exception("Account with non-zero balance cannot be deactivated");
        }
        account.setActive(false);
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

}
