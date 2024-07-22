package com.weinze.bank.client.service.mapper;

import com.weinze.bank.client.domain.Client;
import com.weinze.bank.client.service.dto.ClientDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {}
