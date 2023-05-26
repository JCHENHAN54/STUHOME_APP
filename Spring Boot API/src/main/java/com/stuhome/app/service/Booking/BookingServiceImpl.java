package com.stuhome.app.service.Booking;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.stuhome.app.dao.BookingDao;
import com.stuhome.app.model.Booking;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDao bookDao;

	@Override
	// Una transaccion de solo lectura. no va cambiar el estado de BBDD
	@Transactional(readOnly = true)
	public Iterable<Booking> findAll() {
		// TODO Auto-generated method stub
		return bookDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Booking> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return bookDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Booking> findById(Long id) {
		// TODO Auto-generated method stub
		return bookDao.findById(id);
	}

	@Override
	@Transactional
	public Booking save(Booking booking) {
		// TODO Auto-generated method stub
		return bookDao.save(booking);
	}

}
