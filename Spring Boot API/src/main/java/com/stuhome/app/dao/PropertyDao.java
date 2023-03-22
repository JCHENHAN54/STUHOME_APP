package com.stuhome.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stuhome.app.model.Property;

public interface PropertyDao extends JpaRepository<Property, Long>{

}
