package com.tasc.productservice.models.dto;

import com.google.gson.Gson;
import com.tasc.productservice.database.entity.Category;
import lombok.*;

import javax.persistence.Lob;
import java.math.BigInteger;
import java.util.Date;
import java.util.Set;

//@Getter
//@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInfo {

    private BigInteger id;

    private String name;

    private String icon;

    private String description;

    private int is_root;

    private Date create_at;
    private Date update_at;

    @Lob
    private Set<Category> child;

    @Lob
    private Set<Category> parent;

    public void setChild(String child) {
        Gson gson = new Gson();
        Set<Category> childSet = gson.fromJson(child, Set.class);
        this.child = childSet;
    }

    public void setParent(String parent) {
        Gson gson = new Gson();
        Set<Category> parentSet = gson.fromJson(parent, Set.class);
        this.parent = parentSet;
    }
}
