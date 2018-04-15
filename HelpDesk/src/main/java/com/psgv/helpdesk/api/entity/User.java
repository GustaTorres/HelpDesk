package com.psgv.helpdesk.api.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.psgv.helpdesk.api.annotation.isRequired;
import com.psgv.helpdesk.api.enums.ProfileEnum;
import com.psgv.helpdesk.api.enums.TypePersistEnum;

@Document
public class User {
	
	@Id
	private String id;
	
	@Indexed(unique = true)
	@NotBlank(message = "Email Required")
	@Email(message = "Email Invalid")
	private String email;
	
	@NotBlank(message = "Password Required")
	@Size(min = 6)
	private String password;
	
	private ProfileEnum profile;

	@isRequired(message = "Id not informated", typePersist = TypePersistEnum.UPDATE)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@isRequired(message = "Email not informated", typePersist = {TypePersistEnum.SAVE, TypePersistEnum.UPDATE})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@isRequired(message = "Password not informated", typePersist = {TypePersistEnum.SAVE, TypePersistEnum.UPDATE})
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@isRequired(message = "Profile not informated", typePersist = {TypePersistEnum.SAVE, TypePersistEnum.UPDATE})
	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}
}
