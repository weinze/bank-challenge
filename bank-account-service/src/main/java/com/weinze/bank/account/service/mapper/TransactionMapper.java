package com.weinze.bank.account.service.mapper;

import com.weinze.bank.account.domain.Transaction;
import com.weinze.bank.account.service.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(target = "accountNumber", source = "account.number")
    TransactionDTO toDto(Transaction s);

    @Mapping(target = "account.number", source = "accountNumber")
    Transaction toEntity(TransactionDTO dto);

}
