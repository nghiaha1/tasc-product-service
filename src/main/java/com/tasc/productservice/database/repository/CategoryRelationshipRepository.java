package com.tasc.productservice.database.repository;

import com.tasc.productservice.database.entity.CategoryRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRelationshipRepository extends JpaRepository<CategoryRelationship, CategoryRelationship.Pk> {
    @Query(value = "SELECT * FROM category_relationship cr WHERE cr.parent_id = ?1", nativeQuery = true)
    List<CategoryRelationship> findAllChildrenByParentId(long parentId);

    @Query(value = "SELECT count(*) FROM category_relationship cr WHERE cr.child_id = ?1", nativeQuery = true)
    long countParent(long childId);
}
