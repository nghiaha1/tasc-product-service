package com.tasc.productservice.database.specification;

import com.tasc.productservice.database.entity.Category;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;

public class CategorySpecification implements Specification<Category> {

    private SpecSearchCriteria criteria;

    public CategorySpecification(SpecSearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        switch (criteria.getOperator()) {
            case EQUALS:
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());

            case LIKE:
                return builder.like(root.get(criteria.getKey()), String.valueOf(criteria.getValue()));

            case GREATER_THAN:
                return builder.greaterThan(root.get(criteria.getKey()), String.valueOf(criteria.getValue()));

            case GREATER_THAN_OR_EQUALS:
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return builder.greaterThanOrEqualTo(
                            root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                }
                return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), String.valueOf(criteria.getValue()));

            case LESS_THAN:
                return builder.lessThan(root.get(criteria.getKey()), String.valueOf(criteria.getValue()));

            case LESS_THAN_OR_EQUALS:
                if (root.get(criteria.getKey()).getJavaType() == LocalDateTime.class) {
                    return builder.lessThanOrEqualTo(
                            root.get(criteria.getKey()), (LocalDateTime) criteria.getValue());
                }
                return builder.lessThanOrEqualTo(root.get(criteria.getKey()), String.valueOf(criteria.getValue()));
        }

        return null;
    }
}
