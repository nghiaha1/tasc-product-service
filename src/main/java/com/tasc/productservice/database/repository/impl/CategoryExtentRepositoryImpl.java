package com.tasc.productservice.database.repository.impl;

import com.tasc.productservice.database.repository.CategoryExtentRepository;
import org.hibernate.Session;

import javax.persistence.PersistenceContext;

public class CategoryExtentRepositoryImpl implements CategoryExtentRepository {
    @PersistenceContext
    Session session;
}
