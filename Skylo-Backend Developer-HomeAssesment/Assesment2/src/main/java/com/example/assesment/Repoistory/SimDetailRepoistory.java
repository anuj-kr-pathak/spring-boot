package com.example.assesment.Repoistory;

import com.example.assesment.model.Sim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimDetailRepoistory extends JpaRepository<Sim,String> {

}
