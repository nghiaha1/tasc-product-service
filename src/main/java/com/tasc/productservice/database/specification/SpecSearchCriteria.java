package com.tasc.productservice.database.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpecSearchCriteria {
    private String key;
    private SearchCriteriaOperator operator;
    private Object value;
}
