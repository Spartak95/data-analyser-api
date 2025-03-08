package com.xcoder.data.analyser.repository;

import com.xcoder.data.analyser.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataRepository extends JpaRepository<Data, Long> {
}
