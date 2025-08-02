package com.sales.api.repositories;

import com.sales.api.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalesRepository extends JpaRepository<Sale, Long> {


    @Query("SELECT s FROM Sale s WHERE LOWER(s.client.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Sale> findAllByClientIgnoreCase(@Param("name") String name);
}
