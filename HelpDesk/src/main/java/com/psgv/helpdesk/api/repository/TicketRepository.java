package com.psgv.helpdesk.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.psgv.helpdesk.api.entity.Ticket;

public interface TicketRepository extends GenericRepository<Ticket, String>{
	
	Page<Ticket>findByIdOrderByDateDesc(Pageable page,String id);
	
}