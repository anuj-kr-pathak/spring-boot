package com.anuj.test.springsecuritybasic.repository;

import com.anuj.test.springsecuritybasic.model.Cards;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface CardsRepository extends CrudRepository<Cards,Integer> {
    List<Cards> findByCustomerId(long customerId);
}
