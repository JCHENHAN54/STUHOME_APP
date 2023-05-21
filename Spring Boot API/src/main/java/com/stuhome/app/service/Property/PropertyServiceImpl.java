package com.stuhome.app.service.Property;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stuhome.app.dao.PropertyDao;
import com.stuhome.app.model.Property;

@Service
public class PropertyServiceImpl implements PropertyService {
	
	@Autowired
	private PropertyDao proDao;

	@Override
	//Una transaccion de solo lectura. no va cambiar el estado de BBDD
	@Transactional(readOnly = true)
	public Iterable<Property> findAll() {
		// TODO Auto-generated method stub
		return proDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Property> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return proDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Property> findById(Long id) {
		// TODO Auto-generated method stub
		return proDao.findById(id);
	}

	@Override
	@Transactional
	public Property save(Property property) {
		// TODO Auto-generated method stub
		return proDao.save(property);
	}

	@Override
    @Transactional
    public void delete(Property property) {
        proDao.delete(property);
    }

}
