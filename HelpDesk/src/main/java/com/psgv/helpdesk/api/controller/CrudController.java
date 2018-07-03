package com.psgv.helpdesk.api.controller;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.psgv.helpdesk.api.annotation.IsRequired;
import com.psgv.helpdesk.api.dto.ResponseDTO;
import com.psgv.helpdesk.api.enums.TypePersistEnum;
import com.psgv.helpdesk.api.model.FilterCriteria;
import com.psgv.helpdesk.api.service.CrudService;

public abstract class CrudController<T, ID extends Serializable> {

	private CrudService<T, ID> service;

	public CrudController(CrudService<T, ID> service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<ResponseDTO<T>> save(HttpServletRequest request, @RequestBody T entity, BindingResult result) {
		return persist(entity, result, TypePersistEnum.SAVE);
	}
	
	@PutMapping
	public ResponseEntity<ResponseDTO<T>> update(@RequestBody T entity, BindingResult result) {
		return persist(entity, result, TypePersistEnum.UPDATE);
	}

	private ResponseEntity<ResponseDTO<T>> persist(T entity, BindingResult result,TypePersistEnum typePersist) {
		ResponseDTO<T> response = new ResponseDTO<>();
		try {
			
			validatePersist(entity, result, typePersist);
			
			if (result.hasErrors()) {
				result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
				return ResponseEntity.badRequest().body(response);
			}
			
			T entityPersisted = null;
			if(TypePersistEnum.SAVE.equals(typePersist)) {
				entityPersisted = service.save(entity);
			}else {
				entityPersisted = service.update(entity);
			}
			
			response.setData(entityPersisted);
			
		} catch (DuplicateKeyException dE) {
			response.getErrors().add(entity.getClass().getSimpleName() + " already registered !");
			return ResponseEntity.badRequest().body(response);
		} catch (Exception e) {
			response.getErrors().add(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}
		return ResponseEntity.ok(response);
	}

	private void validatePersist(T entity, BindingResult result, TypePersistEnum typePersist)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		Class<?> clazz = entity.getClass();

		for (Method method : clazz.getMethods()) {
			if (method.isAnnotationPresent(IsRequired.class)) {
				IsRequired isRequiredAnnotation = method.getAnnotation(IsRequired.class);
				TypePersistEnum[] types = isRequiredAnnotation.typePersist();
				for (TypePersistEnum typePersistEnum : types) {
					if (typePersist.equals(typePersistEnum)) {
						Object invoke = method.invoke(entity);
						if (invoke == null) {
							result.addError
							(
								new ObjectError(entity.getClass().getSimpleName(), isRequiredAnnotation.message())
							);
						}
						break;
					}
				}
			}
		}
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ResponseDTO<T>> findById(@PathVariable("id") ID id) {
		ResponseDTO<T> response = new ResponseDTO<>();
		T entity = service.findById(id);
		if (entity == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(entity);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ResponseDTO<String>> delete(@PathVariable("id") ID id) {
		ResponseDTO<String> response = new ResponseDTO<String>();
		T entity = service.findById(id);
		if (entity == null) {
			response.getErrors().add("Register not found id:" + id);
			return ResponseEntity.badRequest().body(response);
		}
		service.delete(id);
		return ResponseEntity.ok(new ResponseDTO<String>());
	}
	

	@PostMapping(value = "/filter")
	public ResponseEntity<ResponseDTO<Page<T>>> findAll(@RequestBody FilterCriteria<T> filter) {
		ResponseDTO<Page<T>> response = new ResponseDTO<Page<T>>();
		
		if(filter.getExample() == null) {
			response.getErrors().add("Entity Example not informated: ");
			return ResponseEntity.badRequest().body(response);
		}
		
		Page<T> entities = service.findAllByExamplePaginated(filter);
		response.setData(entities);
		return ResponseEntity.ok(response);
	}
}
