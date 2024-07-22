package com.weinze.bank.account.service.mapper;

import com.weinze.bank.account.domain.BankAccount;
import com.weinze.bank.account.service.dto.BankAccountDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {}
