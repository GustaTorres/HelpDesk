package com.psgv.helpdesk.api.entity;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.psgv.helpdesk.api.annotation.IsRequired;
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

	@IsRequired(message = "Id not informated", typePersist = TypePersistEnum.UPDATE)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@IsRequired(message = "Email not informated", typePersist = { TypePersistEnum.SAVE, TypePersistEnum.UPDATE })
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@IsRequired(message = "Password not informated", typePersist = { TypePersistEnum.SAVE, TypePersistEnum.UPDATE })
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encodePassword(password);
	}

	@IsRequired(message = "Profile not informated", typePersist = { TypePersistEnum.SAVE, TypePersistEnum.UPDATE })
	public ProfileEnum getProfile() {
		return profile;
	}

	public void setProfile(ProfileEnum profile) {
		this.profile = profile;
	}

	private String encodePassword(String password) {
		if (StringUtils.isBlank(password)) {
			return null;
		}
		return new BCryptPasswordEncoder().encode(password);
	}
}
