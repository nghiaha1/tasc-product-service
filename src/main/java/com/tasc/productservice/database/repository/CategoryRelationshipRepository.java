//package com.tasc.productservice.database.repository;
//
//import com.tasc.productservice.database.entity.CategoryRelationship;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CategoryRelationshipRepository extends JpaRepository<CategoryRelationship, CategoryRelationship.PK> {
//    @Query(value = "select * from category_relationship cr WHERE cr.parent_id = ?", nativeQuery = true)
//    List<CategoryRelationship> findAllChildrenByParentId(long parentId);
//
//    @Query(value = "select count(*) from category_relationship cr WHERE cr.child_id = ?", nativeQuery = true)
//    long countParent(long childId);
//}
