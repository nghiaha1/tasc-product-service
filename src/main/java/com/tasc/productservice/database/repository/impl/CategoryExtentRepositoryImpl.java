package com.tasc.productservice.database.repository.impl;

import com.tasc.productservice.database.entity.Category;
import com.tasc.productservice.database.repository.CategoryExtentRepository;
import com.tasc.productservice.database.specification.SearchBody;
import com.tasc.productservice.models.dto.CategoryInfo;
import com.tasc.productservice.models.response.SearchCategoryResponse;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Log4j2
public class CategoryExtentRepositoryImpl implements CategoryExtentRepository {
    @PersistenceContext
    Session session;

    @Override
    public List findParentByChild(SearchBody searchBody, SearchCategoryResponse.Data data) {
        StringBuilder baseQuery = new StringBuilder();

        if ((searchBody.getId() != 0 && searchBody.getId() > 0)) {
            baseQuery.append(" JOIN category_relationship cr ON c.id = cr.parent_id " +
                            "WHERE 1 = 1 AND cr.child_id = ")
                    .append(searchBody.getId());
        }

        String sql = "SELECT * FROM category c " + baseQuery + " GROUP BY c.id ";

        NativeQuery<Category> nativeQuery = session.createNativeQuery(sql, Category.class);

        return nativeQuery.getResultList();
    }

    @Override
    public List findChildByParent(SearchBody searchBody, SearchCategoryResponse.Data data) {

        StringBuilder baseQuery = new StringBuilder();

        if ((searchBody.getId() != 0 && searchBody.getId() > 0)) {
            baseQuery.append(" JOIN category_relationship cr ON c.id = cr.child_id " +
                            "WHERE 1 = 1 AND cr.parent_id = ")
                    .append(searchBody.getId());
        }

        String sql = "SELECT * FROM category c " + baseQuery + " GROUP BY c.id ";

        NativeQuery<Category> nativeQuery = session.createNativeQuery(sql, Category.class);

        return nativeQuery.getResultList();
    }

    @Override
    public void findAllUsingView(SearchBody searchBody, SearchCategoryResponse.Data data) {

        StringBuilder baseQuery = new StringBuilder();

        baseQuery.append("where 1 = 1 and id = ").append(searchBody.getId());

//        String sql = "select new com.tasc.productservice.models.dto.CategoryInfo(cp.id, cp.name, cp.icon, cp.isRoot, cp.description) from c_p_1 cp " + baseQuery;
        String sql = "select * from c_p " + baseQuery;

//        String sql = "select c.*,(select JSON_ARRAYAGG(JSON_OBJECT('id',d.id,'name',d.name,'icon',d.icon,'description',d.description,'is_root',d.is_root))" +
//                "            from category d where d.id in(select cr.child_id from category_relationship cr where  cr.parent_id = c.id)) as child, " +
//                "       (select json_arrayagg(json_object('id',p.id,'name',p.name,'icon',p.icon,'description',p.description,'is_root',p.is_root))" +
//                "        from category p where p.id in(select cr2.parent_id from  category_relationship cr2 where cr2.child_id = c.id)) as parent " +
//                "from category c where c.id = "+ searchBody.getId();

        Query nativeQuery = session.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(CategoryInfo.class));

        data.setItems(nativeQuery.getResultList());

    }

    @Override
    public void findAll(SearchBody searchBody, SearchCategoryResponse.Data data) {

        StringBuilder baseQuery = new StringBuilder();

        baseQuery.append("where 1 = 1 and id = ").append(searchBody.getId());

        String sql = "select c.*,(select JSON_ARRAYAGG(JSON_OBJECT('id',d.id,'name',d.name,'icon',d.icon,'description',d.description,'is_root',d.is_root))" +
                "            from category d where d.id in(select cr.child_id from category_relationship cr where  cr.parent_id = c.id)) as child, " +
                "       (select json_arrayagg(json_object('id',p.id,'name',p.name,'icon',p.icon,'description',p.description,'is_root',p.is_root))" +
                "        from category p where p.id in(select cr2.parent_id from  category_relationship cr2 where cr2.child_id = c.id)) as parent " +
                "from category c where c.id = "+ searchBody.getId();

        Query nativeQuery = session.createNativeQuery(sql).unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(CategoryInfo.class));

        data.setItems(nativeQuery.getResultList());
    }
}
