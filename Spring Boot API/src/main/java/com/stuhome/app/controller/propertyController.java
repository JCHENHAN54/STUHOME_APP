package com.stuhome.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	// Read my properties:
	@PostMapping("/readMyProperties")
	public List<Property> readMyProperties(@RequestBody User user) {
		List<User> lUser = userService.findByEmail(user.getEmail());
		User finalUser = new User();
		for (User e : lUser) {
			finalUser = e;
		}
		final User userFinal = finalUser;
		List<Property> properties = StreamSupport.stream(proService.findAll().spliterator(), false)
				.filter(property -> property.getUser_id().getId() == userFinal.getId()).collect(Collectors.toList());
		return properties;
	}

	// Create a new property (Crear)
	@PostMapping("/createProperty")
	public ResponseEntity<?> createProperty(@RequestBody Property property) {
		// Crear nuevo objeto de user.
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

	// Read a user about this property
	@PostMapping("/readProperty")
	public ResponseEntity<?> readProperty(@RequestBody Property property) {
		Optional<Property> optionalProperty = proService.findById(property.getId());
		if (!optionalProperty.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalProperty.get().getUser_id());
		}
		return ResponseEntity.notFound().build();
	}

	// Delete Property
	@DeleteMapping("/deleteProperty/{id}")
	public ResponseEntity<String> deleteProperty(@PathVariable("id") Long id) {
		Optional<Property> optionalProperty = proService.findById(id);
		if (optionalProperty.isPresent()) {
			Property property = optionalProperty.get();
			proService.delete(property);
			return ResponseEntity.ok("Property deleted successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Update Property
	// Editar el perfil del usuario:
	@PutMapping("/updateProperty")
	public ResponseEntity<?> updateProperty(@RequestBody Property updatedProperty) {
		Optional<Property> optionalProperty = proService.findById(updatedProperty.getId());
		if (optionalProperty.isPresent()) {
			Property existingProperty = optionalProperty.get();

			// Actualizar los campos de la propiedad con los valores del objeto
			// updatedProperty
			existingProperty.setProperty_name(updatedProperty.getProperty_name());
			existingProperty.setProperty_description(updatedProperty.getProperty_description());
			existingProperty.setProperty_address(updatedProperty.getProperty_address());
			existingProperty.setProperty_city(updatedProperty.getProperty_city());
			existingProperty.setAdditional_notes(updatedProperty.getAdditional_notes());
			existingProperty.setProperty_price(updatedProperty.getProperty_price());

			// Actualizar property image:
			existingProperty.setImage(updatedProperty.getImage());

			// Actualizar property switch:
			existingProperty.setAirConditioning(updatedProperty.isAirConditioning());
			existingProperty.setPetfriendly(updatedProperty.isPetfriendly());
			existingProperty.setParking(updatedProperty.isParking());
			existingProperty.setWifi(updatedProperty.isWifi());
			existingProperty.setWasher(updatedProperty.isWasher());
			existingProperty.setSmoking(updatedProperty.isSmoking());
			
			// Guardar la propiedad actualizada en la base de datos
			proService.save(existingProperty);

			return ResponseEntity.ok("Property updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	//Methodo para mostrar informacion de update property:
	@PostMapping("/updateGetProperty")
	public ResponseEntity<?> updateGetProperty(@RequestBody Property property) {
		Optional<Property> optionalProperty = proService.findById(property.getId());
		if (!optionalProperty.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalProperty);
		}
		return ResponseEntity.notFound().build();
	}
	
	

}
