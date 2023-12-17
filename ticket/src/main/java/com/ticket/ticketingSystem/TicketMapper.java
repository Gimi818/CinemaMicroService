package com.ticket.ticketingSystem;

import com.ticket.common.dto.TicketBookedDto;
import com.ticket.common.dto.TicketDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
interface TicketMapper {

    TicketMapper ticketMapper = Mappers.getMapper(TicketMapper.class);

    TicketBookedDto bookedTicketToDto(Ticket ticket);

    TicketDto ticketToTicketDto(Ticket ticket);


}
