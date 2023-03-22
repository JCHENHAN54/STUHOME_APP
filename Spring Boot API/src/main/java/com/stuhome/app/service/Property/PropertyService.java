package com.stuhome.app.service.Property;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.stuhome.app.model.Property;

public interface PropertyService {

	// @return all the properties
	public Iterable<Property> findAll();

	// El methodo donde se puede usar la paginacion
	public Page<Property> findAll(Pageable pageable);

	// Devolber optional. que nos encuentre usuarios por ID.
	public Optional<Property> findById(Long id);

	// Methodo para guardar una entidad , actualizar, y va devolver el usuario.
	public Property save(Property property);

	// Methodo para borrar un property por Id.
	public void deleteById(Long id);

}
