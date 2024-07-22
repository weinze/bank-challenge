package com.weinze.jhipster.test2.service.mapper;

import com.weinze.jhipster.test2.domain.BankAccount;
import com.weinze.jhipster.test2.domain.Transaction;
import com.weinze.jhipster.test2.service.dto.BankAccountDTO;
import com.weinze.jhipster.test2.service.dto.TransactionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {
    @Mapping(target = "account", source = "account", qualifiedByName = "bankAccountId")
    TransactionDTO toDto(Transaction s);

    @Named("bankAccountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BankAccountDTO toDtoBankAccountId(BankAccount bankAccount);
}
