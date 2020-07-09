package com.hanseld.wine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hanseld.wine.model.Vinho;

@Repository
public interface VinhoRepository extends JpaRepository<Vinho, Long>{

}
