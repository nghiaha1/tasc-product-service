package com.tasc.productservice.models.response;

import com.tasc.productservice.models.BasePagingData;
import com.tasc.productservice.models.dto.CategoryInfo;
import lombok.Data;

import java.util.List;

@Data
public class SearchCategoryResponse extends BaseResponse{

    private Data data;

    public SearchCategoryResponse() {
        super();
    }

    @lombok.Data
    public static class Data extends BasePagingData {
        private List<CategoryInfo> items;
    }
}
