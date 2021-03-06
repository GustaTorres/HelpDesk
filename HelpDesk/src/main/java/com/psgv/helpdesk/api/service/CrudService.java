package com.psgv.helpdesk.api.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.psgv.helpdesk.api.model.FilterCriteria;

public abstract class CrudService<T, ID extends Serializable> {

	private MongoRepository<T, ID> repo;

	public CrudService(MongoRepository<T, ID> repo) {
		this.repo = repo;
	}

	public T save(T entity) {
		return repo.save(entity);
	}

	public void saveAll(Iterable<T> entities) {
		repo.save(entities);
	}

	public void delete(T entity) {
		repo.delete(entity);
	}

	public void delete(ID id) {
		repo.delete(id);
	}

	public void deleteAll(Iterable<T> entities) {
		repo.delete(entities);
	}

	public List<T> findAll() {
		return repo.findAll();
	}

	public List<T> findAllByExample(T entity) {
		return repo.findAll((Example.of(entity)));
	}

	public Page<T> findAllByExamplePaginated(FilterCriteria<T> filter) {
		Pageable pageable = null;
		if (filter.getSort() != null) {
			Direction direction = Direction.valueOf(filter.getSort());
			Sort sort = new Sort(direction, filter.getSortElement());
			pageable = new PageRequest(filter.getPageNumber(), filter.getPageSize(), sort);
		} else {
			pageable = new PageRequest(filter.getPageNumber(), filter.getPageSize());
		}

		T example = filter.getExample();
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreNullValues()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.STARTING);
		return repo.findAll(Example.of(example, matcher), pageable);
	}

	public T findById(ID id) {
		return repo.findOne(id);
	}

	public T update(T entity) {
		return repo.save(entity);
	}
}
