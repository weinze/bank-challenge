package com.weinze.bank.account.service;

import com.weinze.bank.account.domain.BankAccount;
import com.weinze.bank.account.repository.BankAccountRepository;
import com.weinze.bank.account.service.errors.InsufficientBalanceException;
import com.weinze.bank.account.service.impl.BankAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BankAccountServiceTest {

    private BankAccountService bankAccountService;

    @MockBean
    private BankAccountRepository bankAccountRepository;

    @BeforeEach
    void setUp() {
        bankAccountService = new BankAccountServiceImpl(bankAccountRepository, null);
    }

    @Test
    void testDeposit() {
        when(bankAccountRepository.findByNumber(anyLong())).thenReturn(Optional.of(bankAccount(BigDecimal.ZERO)));

        final BankAccount result = bankAccountService.updateBalance(1L, BigDecimal.ONE);

        assertEquals(BigDecimal.ONE, result.getCurrentBalance());
    }

    @Test
    void testWithdraw() {
        when(bankAccountRepository.findByNumber(anyLong())).thenReturn(Optional.of(bankAccount(BigDecimal.TEN)));

        final BankAccount result = bankAccountService.updateBalance(1L, BigDecimal.ONE.negate());

        assertEquals(BigDecimal.valueOf(9L), result.getCurrentBalance());
    }

    @Test
    void testWithdrawLeftZero() {
        when(bankAccountRepository.findByNumber(anyLong())).thenReturn(Optional.of(bankAccount(BigDecimal.ONE)));

        final BankAccount result = bankAccountService.updateBalance(1L, BigDecimal.ONE.negate());

        assertEquals(BigDecimal.ZERO, result.getCurrentBalance());
    }

    @Test
    void testWithdrawInsufficientBalance() {
        when(bankAccountRepository.findByNumber(anyLong())).thenReturn(Optional.of(bankAccount(BigDecimal.ZERO)));

        assertThrows(InsufficientBalanceException.class, () ->
                bankAccountService.updateBalance(1L, BigDecimal.ONE.negate())
        );
    }

    private BankAccount bankAccount(BigDecimal balance) {
        return new BankAccount().id(1L).number(1L).currentBalance(balance);
    }
}
