package com.stuhome.app.model;

import java.io.Serializable;
import javax.persistence .*;

@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 6190662663257570573L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 50)
	private String password;
	private String name;
	private String apellido;
	
	//unique: no se puede aparecer dos mails iguales.
	@Column(name = "mail", length = 50, unique = true)
	private String email;
	
	private String description;
	private String studies;
	private String direccion;
	
	
	//Contructors:
	public User() {
	}
	
	public User(String password,String name, String apellido, String email, String description, String studies, String direccion) {
		this.password = password;	
		this.name = name;
		this.apellido = apellido;
		this.email = email;
		this.description = description;
		this.studies = studies;
		this.direccion = direccion;
	}
	
	//Getters and Setters:
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStudies() {
		return studies;
	}
	public void setStudies(String studies) {
		this.studies = studies;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name=" + name + ", apellido=" + apellido + ", email="
				+ email + ", description=" + description + ", studies=" + studies + ", direccion=" + direccion + "]";
	}
	
}
