package com.nowstartjava.tutorials.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tutorials") @JsonIgnoreProperties({"tutorialsContents"})
public class Tutorials implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String image;
	@Column(name = "description", length= 4000)
	private String description;
	private Date dateCreated;
	private int numberOfViewers;
	private int displayOrder;
	private String slug;
	private boolean isActive;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "categoryId")
	private Category category;

	@OneToMany(cascade=CascadeType.REMOVE,mappedBy="tutorials")
	private List<TutorialsContent> tutorialsContents;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = new Date();
	}

	public int getNumberOfViewers() {
		return numberOfViewers;
	}

	public void setNumberOfViewers(int numberOfViewers) {
		this.numberOfViewers = numberOfViewers;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int order) {
		this.displayOrder = order;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		int num = (int)(Math.random() * 1001);
		String slug1 = title.replace(" ", "-")+num;
		this.slug = slug1;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public String toString(){
		return id+" "+title+" "+description+" "+category;
	}

	public List<TutorialsContent> getTutorialsContents() {
		return tutorialsContents;
	}

	public void setTutorialsContents(List<TutorialsContent> tutorialsContents) {
		this.tutorialsContents = tutorialsContents;
	}
	
}
