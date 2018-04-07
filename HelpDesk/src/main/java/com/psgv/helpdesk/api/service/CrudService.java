package com.psgv.helpdesk.api.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.psgv.helpdesk.api.model.FilterCriteria;
import com.psgv.helpdesk.api.repository.GenericRepository;

public abstract class CrudService<T, ID> {

	private GenericRepository<T, ID> repo;

	public CrudService(GenericRepository<T, ID> repo) {
		this.repo = repo;
	}
	
	public T save(T entity) {
		return repo.save(entity);
	}
	
	public T update(T entity) {
		return repo.save(entity);
	}
	
	public void saveAll(Iterable<T> entities){
		repo.saveAll(entities);
	}
	
	public List<T> findAll(){
		return repo.findAll();
	}
	
	public void delete(T entity){
		repo.delete(entity);
	}

	public void deleteAll(Iterable<T> entities){
		repo.deleteAll(entities);
	}
	
	public List<T> findAllByExample(T entity){
		return repo.findAll((Example.of(entity)));
	}
	
	@SuppressWarnings("deprecation")
	public Page<T> findAllByExamplePaginated(FilterCriteria<T> filter){
		
		Pageable pageable = new PageRequest(filter.getPageNumber(), filter.getPageSize());
		if(filter.getSort() != null) {			
			Direction direction = Direction.valueOf(filter.getSort());
			Sort sort = new Sort(direction, filter.getSortElement());
			pageable = new PageRequest(filter.getPageNumber(), filter.getPageSize(), sort);
		}
		
		T example = filter.getExample();
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.STARTING);
		return repo.findAll(Example.of(example, matcher), pageable);
		
	}
}
