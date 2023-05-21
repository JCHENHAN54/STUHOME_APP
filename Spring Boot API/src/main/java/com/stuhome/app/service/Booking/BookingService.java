package com.stuhome.app.service.Booking;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.stuhome.app.model.Booking;

public interface BookingService {
	
	// @return all the properties
		public Iterable<Booking> findAll();

		// El methodo donde se puede usar la paginacion
		public Page<Booking> findAll(Pageable pageable);

		// Devolber optional. que nos encuentre usuarios por ID.
		public Optional<Booking> findById(Long id);

		// Methodo para guardar una entidad , actualizar, y va devolver el usuario.
		public Booking save(Booking booking);

		// Method to delete a property
	    public void delete(Booking booking);

}
