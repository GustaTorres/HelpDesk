package com.psgv.helpdesk.api.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psgv.helpdesk.api.annotation.isRequired;
import com.psgv.helpdesk.api.dto.ResponseDTO;
import com.psgv.helpdesk.api.entity.User;
import com.psgv.helpdesk.api.enums.TypePersistEnum;
import com.psgv.helpdesk.api.model.FilterCriteria;
import com.psgv.helpdesk.api.service.UserService;

@RestController
@RequestMapping("/api/user/")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAnyRole('ADMIN')")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping()
	public ResponseEntity<ResponseDTO<User>> create(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		ResponseDTO<User> response = new ResponseDTO<User>();
		try {
			validateCreateUser(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) userService.save(user);
			response.setData(userPersisted);
		} catch (DuplicateKeyException dE) {
			response.getErrors().add("E-mail already registered !");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateCreateUser(User user, BindingResult result)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<? extends User> clazz = user.getClass();

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(isRequired.class)) {
				isRequired isRequiredAnnotation = method.getAnnotation(isRequired.class);
				TypePersistEnum[] typePersist = isRequiredAnnotation.typePersist();
				for (TypePersistEnum typePersistEnum : typePersist) {
					if (TypePersistEnum.SAVE.equals(typePersistEnum)) {
						Object invoke = method.invoke(user);
						if (invoke == null) {
							result.addError(
									new ObjectError(user.getClass().getSimpleName(), isRequiredAnnotation.message()));
						}
					}
				}
			}
		}

	}

	@PutMapping()
	public ResponseEntity<ResponseDTO<User>> update(HttpServletRequest request, @RequestBody User user,
			BindingResult result) {
		ResponseDTO<User> response = new ResponseDTO<User>();
		try {
			validateUpdate(user, result);
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User userPersisted = (User) userService.save(user);
			response.setData(userPersisted);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validateUpdate(User user, BindingResult result) {
		if (user.getId() == null) {
			result.addError(new ObjectError("User", "Id not information"));
			return;
		}
		if (user.getEmail() == null) {
			result.addError(new ObjectError("User", "Email not information"));
			return;
		}
	}

	@GetMapping(value = "{id}")
	public ResponseEntity<ResponseDTO<User>> findById(@PathVariable("id") String id) {
		ResponseDTO<User> response = new ResponseDTO<User>();
		User user = userService.findById(id);
		if (user == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(user);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "{id}")
	public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") String id) {
		ResponseDTO<String> response = new ResponseDTO<String>();
		User user = userService.findById(id);
		if (user == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		userService.delete(id);
		return ResponseEntity.ok(new ResponseDTO<String>());
	}

	@PostMapping(value = "filter")
	public ResponseEntity<ResponseDTO<Page<User>>> findAll(@RequestBody FilterCriteria<User> filter) {
		ResponseDTO<Page<User>> response = new ResponseDTO<Page<User>>();
		Page<User> users = userService.findAllByExamplePaginated(filter);
		response.setData(users);
		return ResponseEntity.ok(response);
	}
}
