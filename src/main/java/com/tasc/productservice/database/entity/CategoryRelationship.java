//package com.tasc.productservice.database.entity;
//
//import java.io.Serializable;
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//@Entity
//@Table(name = "category_relationship")
//@Data
//public class CategoryRelationship {
//
//    @EmbeddedId
//    private PK pk;
//
//    @Data
//    @Embeddable
//    public static class PK implements Serializable {
//        private long parentId;
//        private long childId;
//
//        public PK(long parentId, long childId) {
//            this.parentId = parentId;
//            this.childId = childId;
//        }
//
//        public PK() {
//
//        }
//    }
//}