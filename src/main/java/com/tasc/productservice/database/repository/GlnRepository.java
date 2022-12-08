package com.tasc.productservice.database.repository;

import com.tasc.productservice.database.entity.Gln;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlnRepository extends JpaRepository<Gln, Long> {
}
