package com.example.superbank;

import com.example.superbank.model.TransferBalance;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class BankService {
    private final BalanceRepository repository;
    public BigDecimal getBalance(Long accountId) {
        BigDecimal balance = repository.getBalanceFromId(accountId);
        if (balance == null) {
            throw new IllegalArgumentException();
        }
        return balance;
    }

    public BigDecimal addMoney(Long to, BigDecimal amount) {
        BigDecimal currentAmount = repository.getBalanceFromId(to);
        if (currentAmount == null) {
            repository.save(to, amount);
            return amount;
        } else {
            BigDecimal updatedAmount =  currentAmount.add(amount);
            repository.save(to,updatedAmount);
            return updatedAmount;
        }

    }

    public void makeTransfer(TransferBalance transferBalance) {
        BigDecimal fromBalance = getBalance(transferBalance.getFrom());
        BigDecimal toBalance = getBalance(transferBalance.getTo());
        if (fromBalance == null || toBalance == null) throw new IllegalArgumentException("No id");
        if (fromBalance.compareTo(transferBalance.getAmount()) < 0) throw new IllegalArgumentException("No money");

        BigDecimal updatedFromBalance = fromBalance.subtract(transferBalance.getAmount());
        BigDecimal updatedToBalance = toBalance.add(transferBalance.getAmount());

        repository.save(transferBalance.getFrom(), updatedFromBalance);
        repository.save(transferBalance.getTo(), updatedToBalance);

    }
}
