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
	private String surname;
	
	//unique: no se puede aparecer dos mails iguales.
	@Column(name = "email", length = 50, unique = true)
	private String email;
	
	private String description;
	private String studies;
	private String direction;
	private String image;
	
	
	//Contructors:
	public User() {
	}
	
	public User(String password, String name, String surname, String email, String description, String studies,
			String direction, String image) {
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.description = description;
		this.studies = studies;
		this.direction = direction;
		this.image = image;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
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


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", password=" + password + ", name=" + name + ", surname=" + surname + ", email="
				+ email + ", description=" + description + ", studies=" + studies + ", direction=" + direction
				+ ", image=" + image + "]";
	}

	
	
}
