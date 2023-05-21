package com.stuhome.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "booking")
public class Booking implements Serializable {

	private static final long serialVersionUID = -2831827323106851060L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Transient
	private String userEmail;
	
	@ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;
	
	@Column(length = 50)
	private String property_booking_name;
	private String property_booking_description;
	private int property__price;
	
	
	public Booking() {
	}
	
	public Booking(Long id, User user_id, String property_booking_name, String property_booking_description,
			int property__price) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.property_booking_name = property_booking_name;
		this.property_booking_description = property_booking_description;
		this.property__price = property__price;
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

	public String getProperty_booking_name() {
		return property_booking_name;
	}

	public void setProperty_booking_name(String property_booking_name) {
		this.property_booking_name = property_booking_name;
	}

	public String getProperty_booking_description() {
		return property_booking_description;
	}

	public void setProperty_booking_description(String property_booking_description) {
		this.property_booking_description = property_booking_description;
	}

	public int getProperty__price() {
		return property__price;
	}

	public void setProperty__price(int property__price) {
		this.property__price = property__price;
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", user_id=" + user_id + ", property_booking_name=" + property_booking_name
				+ ", property_booking_description=" + property_booking_description + ", property__price="
				+ property__price + "]";
	}

}
