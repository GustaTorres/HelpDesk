package com.psgv.helpdesk.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psgv.helpdesk.api.dto.ResponseDTO;
import com.psgv.helpdesk.api.entity.Ticket;
import com.psgv.helpdesk.api.entity.User;
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
	
	@Autowired
	public TicketController(TicketService service) {
		super(service);
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

	
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	@Override
	public ResponseEntity<ResponseDTO<Ticket>> update(Ticket entity, BindingResult result) {
		return super.update(entity, result);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER','TECHNICIAN')")
	@Override
	public ResponseEntity<ResponseDTO<Ticket>> findById(String id) {
		return super.findById(id);
	}
	
	@PreAuthorize("hasAnyRole('CUSTOMER')")
	@Override
	public ResponseEntity<ResponseDTO<String>> delete(String id) {
		return super.delete(id);
	}
}
