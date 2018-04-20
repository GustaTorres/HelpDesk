package com.psgv.helpdesk.api.enums;

public enum StatusEnum {

	NEW, 
	ASSIGNED, 
	RESOLVED,
	APPROVED,
	DISAPPROVED, 
	CLOSED;

	public static StatusEnum getStatus(String status) {
		
		StatusEnum[] values = values();
		
		for (StatusEnum statusEnum : values) {
			if (statusEnum.name().equals(status)) {
				return statusEnum;
			}
		}
		return NEW;
	}
}
