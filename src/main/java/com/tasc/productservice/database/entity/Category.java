package com.tasc.productservice.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tasc.productservice.database.entity.base.BaseEntity;
import com.tasc.productservice.models.dto.CategoryInfo;
import com.tasc.productservice.utils.Constant;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String icon;

    @ManyToMany(mappedBy = "subCategories")
    @JsonBackReference
    private Set<Category> parent;

    @ManyToMany
    @JoinTable(
            name = "category_relationship",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "child_id")
    )
    @JsonBackReference
    private Set<Category> subCategories;

    private Integer isRoot;

    public boolean checkIsRoot() {
        return isRoot == Constant.ONOFF.ON;
    }
}
