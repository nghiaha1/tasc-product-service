package com.tasc.productservice.models.dto;

import lombok.*;

import javax.persistence.SqlResultSetMapping;
import java.util.List;

@Data
public class CategoryInfo {

    private Integer id;

    private String name;

    private String icon;

    private String description;

    private int is_root;

    private List<CategoryInfo> parentId;

    private List<CategoryInfo> children;

    public CategoryInfo() {
    }

    public CategoryInfo(Integer id, String name, String icon, String description, int is_root) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.is_root = is_root;
    }

    public CategoryInfo(Integer id, String name, String icon, String description, int is_root, List<CategoryInfo> parentId, List<CategoryInfo> children) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
        this.is_root = is_root;
        this.parentId = parentId;
        this.children = children;
    }
}
