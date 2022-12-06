package com.tasc.productservice.database.repository.impl;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.database.repository.CategoryExtentRepository;
import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.models.response.SearchCategoryResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.PersistenceContext;
import java.util.List;

@Log4j2
public class CategoryExtentRepositoryImpl implements CategoryExtentRepository {
    @PersistenceContext
    Session session;

    @Override
    public List<Category> findAll(SearchBody searchBody, SearchCategoryResponse.Data data) {
        StringBuilder baseQuery1 = new StringBuilder();

        if (StringUtils.isNotBlank(searchBody.getName())) {
            baseQuery1.append(" JOIN category_relationship cr ON c.id = cr.child_id " +
                    "WHERE 1 = 1 AND cr.parent_id = (SELECT c1.id FROM category c1 WHERE c1.name LIKE '%" ).append(searchBody.getName()).append("%')");
        }

        if (searchBody.getIsRoot() == 0 || searchBody.getIsRoot() == 1) {
            if (StringUtils.isBlank(searchBody.getName())) {
                baseQuery1.append(" JOIN category_relationship cr ON c.id = cr.child_id WHERE c.is_root = ").append(searchBody.getIsRoot()).append(" ");
            } else
                baseQuery1.append(" And c.is_root = ").append(searchBody.getIsRoot());
        }

        StringBuilder baseQuery2 = new StringBuilder();

        if (StringUtils.isNotBlank(searchBody.getName())) {
            baseQuery2.append(" JOIN category_relationship cr ON c.id = cr.parent_id " +
                    "WHERE 1 = 1 AND cr.child_id = (SELECT c1.id FROM category c1 WHERE c1.name LIKE '%" ).append(searchBody.getName()).append("%')");
        }

        if (searchBody.getIsRoot() == 0 || searchBody.getIsRoot() == 1) {
            if (StringUtils.isBlank(searchBody.getName())) {
                baseQuery2.append(" JOIN category_relationship cr ON c.id = cr.parent_id WHERE c.is_root = ").append(searchBody.getIsRoot()).append(" ");
            } else
                baseQuery2.append(" And c.is_root = ").append(searchBody.getIsRoot());
        }

        String sql = "SELECT * FROM category c " + baseQuery2 + " GROUP BY c.id "
                + "UNION "
                + "SELECT * FROM category c " + baseQuery1 + " GROUP BY c.id ";

        NativeQuery nativeQuery = session.createNativeQuery(sql, Category.class);

        return nativeQuery.getResultList();
    }


//    @Override
//    public void searchCategory(Integer isRoot, String name, Integer page, Integer pageSize, SearchCategoryResponse.Data data) {
//        StrBuilder baseSql = new StrBuilder();
//
////        baseSql.append("FROM com.tasc.productservice.database.entity.Category c WHERE 1 = 1 ");
//
//        baseSql.append("FROM com.tasc.productservice.database.entity.Category c ");
//
//        if (isRoot != null) {
//            baseSql.append("AND c.isRoot = ").append(isRoot);
//        }
//
//        if (StringUtils.isNotBlank(name)) {
////            baseSql.append("AND EXISTS(SELECT NULL FROM com.tasc.productservice.database.entity.CategoryRelationship.Pk cr " +
////                    "WHERE cr.childId = (SELECT c1.id FROM com.tasc.productservice.database.entity.Category c1 WHERE c1.name LIKE '%").append(name).append("%') " +
////                    "AND c.id = cr.parentId");
//
////            baseSql.append(" AND c.name LIKE  '%").append(name).append("%'");
//
//            baseSql.append(" JOIN com.tasc.productservice.database.entity.CategoryRelationship.Pk cr ON c.id = cr.childId " +
//                    "WHERE 1 = 1 AND cr.parentId = (SELECT c1.id FROM com.tasc.productservice.database.entity.Category c1 WHERE c1.name LIKE '%").append(name).append("%') " +
//                    "GROUP BY c.id");
//        }
//
//        // select total items
//
//        String sqlCount = "SELECT count(c) " + baseSql;
//
//        Query query = session.createQuery(sqlCount);
//
//        Object totalItemValue = query.getSingleResult();
//
//        if (totalItemValue instanceof BigInteger) {
//            BigInteger totalItem = (BigInteger) totalItemValue;
//            data.setTotalItem(totalItem.longValue());
//        } else if (totalItemValue instanceof Long) {
//            data.setTotalItem((Long) totalItemValue);
//        }
//
//        // select item info
//
//        if (data.getTotalItem() > 0) {
//            String querySql =
//                    "SELECT new com.tasc.productservice.models.dto.CategoryInfo(c.id, c.name, c.icon, c.description, c.isRoot) "
//                            + baseSql;
//            Query queryItem = session.createQuery(querySql, CategoryInfo.class);
//
//            log.info("SQL: {}", querySql);
//            page--;
//
//            int firstResult = page * pageSize;
//            query.setMaxResults(pageSize).setFirstResult(firstResult);
//
//            List<CategoryInfo> categoryList = queryItem.getResultList();
//
//            data.setItems(categoryList);
//
//        } else {
//            data.setItems(new ArrayList<>());
//        }
//    }


}
