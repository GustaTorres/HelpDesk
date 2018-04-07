package com.psgv.helpdesk.api.repository;

import com.psgv.helpdesk.api.entity.ChangeStatus;

public interface ChangeStatusRepository extends GenericRepository<ChangeStatus, String> {

	Iterable<ChangeStatus> findByTicketIdOrderByDateChangeStatusDesc(String ticketId);
	
}
