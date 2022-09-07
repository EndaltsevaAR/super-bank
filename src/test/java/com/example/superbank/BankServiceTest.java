package com.example.superbank;

import com.example.superbank.model.TransferBalance;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankServiceTest {
    private BalanceRepository balanceRepository = new BalanceRepository();
    private BankService bankService = new BankService(balanceRepository);

    @Test
    void getBalance() {
        final BigDecimal balance = bankService.getBalance(1L);
        assertEquals(balance, BigDecimal.TEN);
    }

    @Test
    void addMoney() {
        final BigDecimal balance = bankService.addMoney(1L, BigDecimal.ONE);
        assertEquals(balanceRepository.getBalanceFromId(1L), BigDecimal.valueOf(11));
    }

    @Test
    void makeTransfer() {
        BigDecimal secondBalance = bankService.addMoney(2L, BigDecimal.ONE);
        TransferBalance transferBalance = new TransferBalance();
        transferBalance.setFrom(1L);
        transferBalance.setTo(2L);
        transferBalance.setAmount(BigDecimal.TEN);
        bankService.makeTransfer(transferBalance);
        assertEquals(balanceRepository.getBalanceFromId(1L), BigDecimal.ZERO);
        assertEquals(balanceRepository.getBalanceFromId(2L), BigDecimal.valueOf(11));
    }
}