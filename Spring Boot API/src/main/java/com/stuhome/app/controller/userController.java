package com.stuhome.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
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

import com.stuhome.app.model.User;
import com.stuhome.app.service.User.UserService;

@RestController
//Va devolber formato json.
@RequestMapping("/api/users")
public class userController {

	@Autowired
	private UserService userService;

	// Create a new user (Crear) Signin
	@PostMapping("/signin")
	public ResponseEntity<?> create(@RequestBody User user) {
		// Almacenar la contraseña cifrada usando la clase DigestUtils.
		String pass = user.getPassword();
		String passCifrada = DigestUtils.md5Hex(pass);
		user.setPassword(passCifrada);
		List<User> lUser = userService.findByEmail(user.getEmail());
		//Solo inserta cuando el mail no esta duplicada.
		if(lUser.isEmpty())
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
		return (ResponseEntity<?>) ResponseEntity.notFound();
	}

	// Read an user (Leer) Login
	@PostMapping("/login")
	public ResponseEntity<?> read(@RequestBody User user) {
		// Buscar el nombre de usuario si exise o no.
		// Ciframos la contraseña que hemos introducido y comparalo con el de BBDD.
		String pass = user.getPassword();
		String passCifrada = DigestUtils.md5Hex(pass);
		List<User> lUser = userService.findByEmailAndPassword(user.getEmail(), passCifrada);
		// Si encuentra el nombre de usuario y la contraseña esta correcta.
		if (!lUser.isEmpty()) {
			// Devolvel que todo ha ido bien. 200.
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		// Si no encuentra, pues 404 not found.
		return ResponseEntity.notFound().build();
	}
	
	//Read profile information.
	@PostMapping("/pLogin")
	public ResponseEntity<?> readProfile(@RequestBody User user) {
		// Buscar el nombre de usuario si exise o no.
		// Ciframos la contraseña que hemos introducido y comparalo con el de BBDD.
		String pass = user.getPassword();
		String passCifrada = DigestUtils.md5Hex(pass);
		List<User> lUser = userService.findByEmailAndPassword(user.getEmail(), passCifrada);
		for(User e :lUser) {
			user = e;
		}
		// Si encuentra el nombre de usuario y la contraseña esta correcta.
		if (!lUser.isEmpty()) {
			// Devolvel que todo ha ido bien. 200.
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		// Si no encuentra, pues 404 not found.
		return ResponseEntity.notFound().build();
	}

	// Update user password: (Actualizar contraseña del usuario)
	@PutMapping("/changeUserPwd")
	public ResponseEntity<?> update(@RequestBody User user) {

		List<User> lUser = userService.findByEmail(user.getEmail());
		// Si existe el username, pues podriamos cambiar el password.
		if (lUser.isEmpty()) {
			// Devolver 404 not found.
			return ResponseEntity.notFound().build();
		}
		// Ciframos la contraseña introducida y guardamos en BBDD.
		String pass = user.getPassword();
		String passCifrada = DigestUtils.md5Hex(pass);
		// Lo actulizamos en ese usuario.
		lUser.get(0).setPassword(passCifrada);
		System.out.println("Contraseña actualizada correctamente.");
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(lUser.get(0)));
	}

	// Delete User: (Eleminar un usuario y dar baja el usuario en el aplicacion)
	@DeleteMapping("/user")
	public ResponseEntity<?> deleteUser(@RequestBody User user) {
		List<User> lUser = userService.findByEmail(user.getEmail());
		// Si existe el username, pues eleminamos ese usuario.
		if (lUser.isEmpty()) {
			System.err.println("No se ha encontrado el usuario.");
			// Devolver 404 not found.
			return ResponseEntity.notFound().build();
		}
		userService.deleteById(lUser.get(0).getId());
		System.out.println("Hemos eleminado el usuario" + lUser.get(0).getEmail());
		return ResponseEntity.ok().build();
	}

	// Muestra información de perfil de usuario almacenada en la BBDD
	@GetMapping("/profile/{idUser}")
	public ResponseEntity<?> read(@PathVariable(value = "idUser") Long userId) {
		Optional<User> oUser = userService.findById(userId);
		// Exception para controlar si el id no existe.
		if (!oUser.isPresent()) {
			// Codigo: 404 not found.
			return ResponseEntity.notFound().build();
		}
		// Devolvel que todo ha ido bien. 200.

		return ResponseEntity.ok("Nombre: " + oUser.get().getName() + "\n" + "Apellido: " + oUser.get().getApellido()
				+ "\n" + "Description: " + oUser.get().getDescription() + "\n" + "Direction: "
				+ oUser.get().getDireccion() + "\n" + "Email: " + oUser.get().getEmail() + "\n" + "Estudios: "
				+ oUser.get().getStudies());
	}

	// Editar el perfil del usuario:
	@PutMapping("/profile")
	public ResponseEntity<?> updateProfile(@RequestBody User user) {
		List<User> lUser = userService.findByEmail(user.getEmail());
		// Si existe el username, pues podriamos editar el perfil del usuario.
		if (lUser.isEmpty()) {
			System.err.println("No se ha encontrado el usuario.");
			// Devolver 404 not found.
			return ResponseEntity.notFound().build();
		}
		// Cogemos los datos del body y lo guardamos en BBDD.
		lUser.get(0).setName(user.getName());
		lUser.get(0).setApellido(user.getApellido());
		lUser.get(0).setDescription(user.getDescription());
		lUser.get(0).setDireccion(user.getDireccion());
		lUser.get(0).setStudies(user.getStudies());
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(lUser.get(0)));
	}

	// Mostrar todos los usuarios.
	// Find all users:
	@GetMapping
	public List<User> readAll() {

		List<User> users = StreamSupport.stream(userService.findAll().spliterator(), false)
				.collect(Collectors.toList()); // Nos transforme en una lista.

		return users;
	}

}
