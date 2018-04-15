package com.psgv.helpdesk.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psgv.helpdesk.api.entity.ChangeStatus;
import com.psgv.helpdesk.api.entity.Ticket;
import com.psgv.helpdesk.api.repository.ChangeStatusRepository;
import com.psgv.helpdesk.api.repository.TicketRepository;

@Service
public class TicketService extends CrudService<Ticket, String> {
	
	private TicketRepository repo;
	
	@Autowired
	private ChangeStatusRepository changeStatusRepository;

	@Autowired
	public TicketService(TicketRepository repo) {
		super(repo);
		this.repo = repo;
	}
	
	public Iterable<ChangeStatus> findAllChangeStatusByTicketId(String ticketId){
		return changeStatusRepository.findByTicketIdOrderByDateChangeStatusDesc(ticketId);
	}
	
	public ChangeStatus saveOrUpdate(ChangeStatus changeStatus) {
		return changeStatusRepository.save(changeStatus);
	}

}
