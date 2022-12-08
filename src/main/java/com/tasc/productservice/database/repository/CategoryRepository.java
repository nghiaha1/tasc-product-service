package com.tasc.productservice.database.repository;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.models.dto.CategoryInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>,
        CategoryExtentRepository, JpaSpecificationExecutor<Category> {

    @Query(value = "SELECT c.* " +
            "FROM category c " +
            "JOIN category_relationship cr " +
            "ON c.id = cr.parent_id WHERE 1 = 1 " +
            "AND cr.child_id = ?1 " +
            "NOT IN (SELECT c1.* FROM category c1 " +
            "WHERE json_contains())",
            nativeQuery = true)
    List<Category> findParentByChild(long id);

    @Query(value = "SELECT c.* " +
            "FROM category c " +
            "JOIN category_relationship cr " +
            "ON c.id = cr.child_id WHERE 1 = 1 " +
            "AND cr.parent_id = ?1",
            nativeQuery = true)
    List<Category> findChildByParent(long id);
}
