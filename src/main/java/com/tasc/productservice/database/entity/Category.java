package com.tasc.productservice.database.entity;

import com.tasc.productservice.database.entity.base.BaseEntity;
import com.tasc.productservice.database.entity.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
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

    private String detail;

    private String icon;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

//    @ManyToMany(mappedBy = "categories")
//    private Set<Product> products;

    private int isRoot;

    private Long parentId;

//    @Column(columnDefinition = "VARCHAR(255) default = 'ACTIVE'")
//    private Status status;
}
