package com.psgv.helpdesk.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psgv.helpdesk.api.dto.ResponseDTO;
import com.psgv.helpdesk.api.dto.SummaryDTO;
import com.psgv.helpdesk.api.entity.Ticket;
import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.model.FilterCriteria;
import com.psgv.helpdesk.api.security.jwt.JwtTokenUtil;
import com.psgv.helpdesk.api.service.TicketService;
import com.psgv.helpdesk.api.service.UserService;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin(origins = "*")
public class TicketController extends CrudController<Ticket, String> {

	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	private TicketService ticketService;
	
	@Autowired
	public TicketController(TicketService service) {
		super(service);
		this.ticketService = service;
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	@Override
	public ResponseEntity<ResponseDTO<Ticket>> save(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result) {
		ticket.setUser(userFromRequest(request));
		return super.save(request,ticket, result);
	}

	private User userFromRequest(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		String username = jwtTokenUtil.getUsernameFromToken(token);
		return userService.findByEmail(username);
	}

	
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	@Override
	public ResponseEntity<ResponseDTO<Ticket>> update(@RequestBody Ticket entity, BindingResult result) {
		return super.update(entity, result);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	@Override
	public ResponseEntity<ResponseDTO<Ticket>> findById(@PathVariable("id")String id) {
		return super.findById(id);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	@Override
	public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id")String id) {
		return super.delete(id);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	@Override
	public ResponseEntity<ResponseDTO<Page<Ticket>>> findAll(@RequestBody FilterCriteria<Ticket> filter) {
		return super.findAll(filter);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	@PutMapping(value = "/changeStatus")
	public ResponseEntity<ResponseDTO<Ticket>> changeStatus(HttpServletRequest request, @RequestBody Ticket ticket, BindingResult result){
		ticketService.changeStatus(ticket, userFromRequest(request));
		return super.update(ticket, result);
	}
	
	@GetMapping(value = "/summary")
	public ResponseEntity<ResponseDTO<SummaryDTO>> findSummary(){
		ResponseDTO<SummaryDTO> response = new ResponseDTO<>();
		SummaryDTO sumaryDTO = ticketService.findSummary();
		response.setData(sumaryDTO);
		return ResponseEntity.ok(response);
	}
}
