package com.stuhome.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stuhome.app.model.Property;
import com.stuhome.app.service.Property.PropertyService;

@RestController
//Va devolber formato json.
@RequestMapping("/api/property")
public class propertyController {

	@Autowired
	private PropertyService proService;

	// Create a new property (Create)
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody Property property) {
		// Almacenar la contraseña cifrada usando la clase DigestUtils.
		return ResponseEntity.status(HttpStatus.CREATED).body(proService.save(property));
	}
}
