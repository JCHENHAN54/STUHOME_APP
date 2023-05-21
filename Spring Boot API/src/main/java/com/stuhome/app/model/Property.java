package com.stuhome.app.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "property")
public class Property implements Serializable {

	private static final long serialVersionUID = 7041049548309487340L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
	
	@Transient
	private String userEmail;

	@Column(length = 50)
	private String property_name;
	private String property_address;
	private String property_city;
	private String property_description;
	private boolean airConditioning;
	private boolean petfriendly;
	private boolean parking;
	private boolean wifi;
	private boolean washer;
	private boolean smoking;
	private int property_price;
	
	//Guardar Imagenes en Mysql.
	@Lob
	private byte[] image;

	@Column(length = 100)
	private String additional_notes;

	// Contructors:
	public Property() {	
	}

	public Property(Long id, User user_id, String userEmail, String property_name, String property_address,
			String property_city, String property_description, byte[] image, boolean airConditioning,
			boolean petfriendly, boolean parking, boolean wifi, boolean washer, boolean smoking, int property_price,
			String additional_notes) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.userEmail = userEmail;
		this.property_name = property_name;
		this.property_address = property_address;
		this.property_city = property_city;
		this.property_description = property_description;
		this.image = image;
		this.airConditioning = airConditioning;
		this.petfriendly = petfriendly;
		this.parking = parking;
		this.wifi = wifi;
		this.washer = washer;
		this.smoking = smoking;
		this.property_price = property_price;
		this.additional_notes = additional_notes;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public String getProperty_name() {
		return property_name;
	}

	public void setProperty_name(String property_name) {
		this.property_name = property_name;
	}

	public String getProperty_address() {
		return property_address;
	}

	public void setProperty_address(String property_address) {
		this.property_address = property_address;
	}

	public String getProperty_city() {
		return property_city;
	}

	public void setProperty_city(String property_city) {
		this.property_city = property_city;
	}

	public String getProperty_description() {
		return property_description;
	}

	public void setProperty_description(String property_description) {
		this.property_description = property_description;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public boolean isAirConditioning() {
		return airConditioning;
	}

	public void setAirConditioning(boolean airConditioning) {
		this.airConditioning = airConditioning;
	}

	public boolean isPetfriendly() {
		return petfriendly;
	}

	public void setPetfriendly(boolean petfriendly) {
		this.petfriendly = petfriendly;
	}

	public boolean isParking() {
		return parking;
	}

	public void setParking(boolean parking) {
		this.parking = parking;
	}

	public boolean isWifi() {
		return wifi;
	}

	public void setWifi(boolean wifi) {
		this.wifi = wifi;
	}

	public boolean isWasher() {
		return washer;
	}

	public void setWasher(boolean washer) {
		this.washer = washer;
	}

	public boolean isSmoking() {
		return smoking;
	}

	public void setSmoking(boolean smoking) {
		this.smoking = smoking;
	}

	public int getProperty_price() {
		return property_price;
	}

	public void setProperty_price(int property_price) {
		this.property_price = property_price;
	}

	public String getAdditional_notes() {
		return additional_notes;
	}

	public void setAdditional_notes(String additional_notes) {
		this.additional_notes = additional_notes;
	}

	@Override
	public String toString() {
		return "Property [id=" + id + ", user_id=" + user_id + ", userEmail=" + userEmail + ", property_name="
				+ property_name + ", property_address=" + property_address + ", property_city=" + property_city
				+ ", property_description=" + property_description + ", image=" + image + ", airConditioning="
				+ airConditioning + ", petfriendly=" + petfriendly + ", parking=" + parking + ", wifi=" + wifi
				+ ", washer=" + washer + ", smoking=" + smoking + ", property_price=" + property_price
				+ ", additional_notes=" + additional_notes + "]";
	}
	

}
