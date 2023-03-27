package com.stuhome.app.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.stuhome.app.model.Property;
import com.stuhome.app.model.User;
import com.stuhome.app.service.Property.PropertyService;
import com.stuhome.app.service.User.UserService;

@RestController
//Va devolber formato json.
@RequestMapping("/api/property")
public class propertyController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private PropertyService proService;
	
	/* =============== API Rest Property =============== */

	// Find all properties:
	@GetMapping("/readProperties")
	public List<Property> readAllProperties() {
		List<Property> properties = StreamSupport.stream(proService.findAll().spliterator(), false)
				.collect(Collectors.toList()); // Nos transforme en una lista.
		return properties;
	}

	// Create a new property (Crear)
	@PostMapping("/createProperty")
	public ResponseEntity<?> createProperty(@RequestBody Property property) {
		//Crear nuevo objeto de user.
		User user = new User();
		List<User> lUser = userService.findByEmail(property.getUserEmail());
		if (!lUser.isEmpty()) {
			for (User e : lUser) {
				user = e;
			}
			property.setUser_id(user);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(proService.save(property));
	}
}
