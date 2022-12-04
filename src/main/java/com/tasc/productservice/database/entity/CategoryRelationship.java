package com.tasc.productservice.database.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "category_relationship")
public class CategoryRelationship {
    @EmbeddedId
    private Pk pk;

    @Data
    @Embeddable
    public static class Pk implements Serializable {
        @Column(name = "parent_id")
        private long parentId;
        @Column(name = "child_id")
        private long childId;

        public Pk() {

        }

        public Pk(long parentId, long childId) {
            this.parentId = parentId;
            this.childId = childId;
        }


    }
}
