package com.psgv.helpdesk.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psgv.helpdesk.api.entity.Ticket;
import com.psgv.helpdesk.api.service.TicketService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController extends CrudController<Ticket, String> {

	@Autowired
	public TicketController(TicketService service) {
		super(service);
	}	
}
