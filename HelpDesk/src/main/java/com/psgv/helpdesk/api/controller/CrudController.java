package com.psgv.helpdesk.api.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.psgv.helpdesk.api.model.FilterCriteria;
import com.psgv.helpdesk.api.service.CrudService;

public abstract class CrudController<T,ID> {
	
	private CrudService<T, ID> service;
	
	public CrudController(CrudService<T, ID> service) {
		this.service = service;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public T save(@RequestBody T entity){
		return service.save(entity);
	}
	
	@RequestMapping(value = "/saveAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void saveAll(@RequestBody Iterable<T> entities){
		service.saveAll(entities);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(@RequestBody T entity) {
		service.delete(entity);
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteAll(@RequestBody Iterable<T> entities) {
		service.deleteAll(entities);
	}

	@RequestMapping(value = "/filter", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Page<T> filter(@RequestBody FilterCriteria<T> filter) {
		return service.findAllByExamplePaginated(filter);
	}
	
	@RequestMapping(value = "/searchAll", method = RequestMethod.GET)
	@ResponseBody
	public List<T> searchAll(){
		return service.findAll();
	}
}
