package com.psgv.helpdesk.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenericRepository<T, ID> extends MongoRepository<T, ID> {

}
