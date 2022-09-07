package com.example.superbank;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class BalanceRepository {
    private final Map<Long, BigDecimal> storage = new HashMap<>(Map.of(1L, BigDecimal.TEN));

    public BigDecimal getBalanceFromId(Long accountId) {
        return storage.get(accountId);
    }

    public BigDecimal save(Long id, BigDecimal amount) {
        return storage.put(id, amount);
    }
}
