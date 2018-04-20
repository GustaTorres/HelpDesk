package com.psgv.helpdesk.api.service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psgv.helpdesk.api.dto.SummaryDTO;
import com.psgv.helpdesk.api.entity.ChangeStatus;
import com.psgv.helpdesk.api.entity.Ticket;
import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.enums.StatusEnum;
import com.psgv.helpdesk.api.repository.ChangeStatusRepository;
import com.psgv.helpdesk.api.repository.TicketRepository;

@Service
public class TicketService extends CrudService<Ticket, String> {
	
	@Autowired
	private ChangeStatusRepository changeStatusRepository;
	
	private TicketRepository ticketRepository;

	@Autowired
	public TicketService(TicketRepository repo) {
		super(repo);
		this.ticketRepository = repo;
	}
	
	public Iterable<ChangeStatus> findAllChangeStatusByTicketId(String ticketId){
		return changeStatusRepository.findByTicketIdOrderByDateChangeStatusDesc(ticketId);
	}
	
	public ChangeStatus saveOrUpdate(ChangeStatus changeStatus) {
		return changeStatusRepository.save(changeStatus);
	}
	
	@Override
	public Ticket save(Ticket ticket) {
		ticket.setStatus(StatusEnum.NEW);
		ticket.setDate(new GregorianCalendar());
		ticket.setNumber(generateNumber());
		return super.save(ticket);
	}
	
	private Integer generateNumber() {
		Random random = new Random();
		return random.nextInt(9999);
	}
	
	@Override
	public Ticket findById(String id) {
		Ticket ticket = super.findById(id);
		Iterable<ChangeStatus> allChanges = findAllChangeStatusByTicketId(id);
		
		List<ChangeStatus> listChanges = new ArrayList<>();
		
		for (ChangeStatus changeStatus : allChanges) {
			listChanges.add(changeStatus);
		}
		
		ticket.setChanges(listChanges);
		return ticket;
	}

	public void changeStatus(Ticket ticket, User userChange) {
		ChangeStatus newStatus = new ChangeStatus();
		newStatus.setUser(userChange);
		newStatus.setDateChangeStatus(new GregorianCalendar());
		newStatus.setStatus(ticket.getStatus());
		newStatus.setTicket(ticket);
		changeStatusRepository.save(newStatus);
	}

	public SummaryDTO findSummary() {
		Integer amountNew = 0;
		Integer amountResolved = 0;
		Integer amountApproved = 0;
		Integer amountDispproved = 0;
		Integer amountAssigned = 0;
		Integer amountClosed = 0;
		
		SummaryDTO dto = new SummaryDTO();
		
		List<Ticket> all = this.ticketRepository.findAll();
		
		for (Ticket ticket : all) {
			
			switch (ticket.getStatus()) {
			case NEW:
				amountNew++;
				continue;
			case RESOLVED:
				amountResolved++;
				continue;
			case APPROVED:
				amountApproved++;
				continue;
			case DISAPPROVED:
				amountDispproved++;
				continue;
			case ASSIGNED:
				amountAssigned++;
				continue;
			case CLOSED:
				amountClosed++;
				continue;
			}
		}
		
		dto.setAmountNew(amountNew);
		dto.setAmountResolved(amountResolved);
		dto.setAmountApproved(amountApproved);
		dto.setAmountDispproved(amountDispproved);
		dto.setAmountAssigned(amountAssigned);
		dto.setAmountClosed(amountClosed);
		
		return dto;
	}
}
