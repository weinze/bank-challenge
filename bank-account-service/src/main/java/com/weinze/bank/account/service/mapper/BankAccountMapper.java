package com.weinze.jhipster.test2.service.mapper;

import com.weinze.jhipster.test2.domain.BankAccount;
import com.weinze.jhipster.test2.domain.Client;
import com.weinze.jhipster.test2.service.dto.BankAccountDTO;
import com.weinze.jhipster.test2.service.dto.ClientDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BankAccount} and its DTO {@link BankAccountDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankAccountMapper extends EntityMapper<BankAccountDTO, BankAccount> {
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    BankAccountDTO toDto(BankAccount s);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
