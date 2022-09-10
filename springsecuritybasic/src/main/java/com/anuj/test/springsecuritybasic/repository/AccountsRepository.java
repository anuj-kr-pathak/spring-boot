package com.anuj.test.springsecuritybasic.repository;

import com.anuj.test.springsecuritybasic.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends CrudRepository<Accounts,Long> {
    Accounts findByCustomerId(long id);
}
