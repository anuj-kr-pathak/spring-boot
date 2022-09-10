package com.anuj.test.springsecuritybasic.repository;

import com.anuj.test.springsecuritybasic.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact,Long> {
}
