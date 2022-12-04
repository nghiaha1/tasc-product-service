package com.tasc.productservice.database.repository;

import com.tasc.productservice.database.entity.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryExtentRepository, JpaSpecificationExecutor<Category> {


}
