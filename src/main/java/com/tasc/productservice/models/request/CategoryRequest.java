package com.tasc.productservice.models.request;

import com.tasc.productservice.utils.Constant;
import lombok.Data;

@Data
public class CategoryRequest {

    private String name;

    private String icon;

    private String description;

    private Integer isRoot;

    private Long parentId;

    public boolean checkIsRoot(){
        return isRoot != null && isRoot == Constant.ONOFF.ON;
    }
}
