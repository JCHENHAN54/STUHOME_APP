package com.stuhome.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.stuhome.app.model.Booking;
import com.stuhome.app.model.Property;
import com.stuhome.app.model.User;
import com.stuhome.app.service.Booking.BookingService;
import com.stuhome.app.service.User.UserService;

@RestController
//Va devolber formato json.
@RequestMapping("/api/booking")
public class bookingController {

	@Autowired
	private UserService userService;

	@Autowired
	private BookingService bookService;

	/* =============== API Rest Booking =============== */

	// Create a new booking (Crear)
	@PostMapping("/createBooking")
	public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
		// Crear nuevo objeto de user.
		User user = new User();
		List<User> lUser = userService.findByEmail(booking.getUserEmail());
		if (!lUser.isEmpty()) {
			for (User e : lUser) {
				user = e;
			}
			booking.setUser_id(user);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(booking));
	}

	// Read my bookings:
	@PostMapping("/readMyBookings")
	public List<Booking> readMyBookings(@RequestBody User user) {
		List<User> lUser = userService.findByEmail(user.getEmail());
		User finalUser = new User();
		for (User e : lUser) {
			finalUser = e;
		}
		final User userFinal = finalUser;
		List<Booking> bookings = StreamSupport.stream(bookService.findAll().spliterator(), false)
				.filter(property -> property.getUser_id().getId() == userFinal.getId()).collect(Collectors.toList());
		return bookings;
	}

}
