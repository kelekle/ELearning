package com.star.e_learning.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Teacher implements Serializable {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.userid
	 * @mbg.generated
	 */
	@PrimaryKey
	@NonNull
	private String userid;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.course_id
	 * @mbg.generated
	 */
	@ColumnInfo(name = "course_id")
	private String courseId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.name
	 * @mbg.generated
	 */
	private String name;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.photo
	 * @mbg.generated
	 */
	private String photo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.telephone
	 * @mbg.generated
	 */
	private String telephone;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.email
	 * @mbg.generated
	 */
	private String email;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column teacher.description
	 * @mbg.generated
	 */
	private String description;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table teacher
	 * @mbg.generated
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.userid
	 * @return  the value of teacher.userid
	 * @mbg.generated
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.userid
	 * @param userid  the value for teacher.userid
	 * @mbg.generated
	 */
	public void setUserid(String userid) {
		this.userid = userid == null ? null : userid.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.course_id
	 * @return  the value of teacher.course_id
	 * @mbg.generated
	 */
	public String getCourseId() {
		return courseId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.course_id
	 * @param courseId  the value for teacher.course_id
	 * @mbg.generated
	 */
	public void setCourseId(String courseId) {
		this.courseId = courseId == null ? null : courseId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.name
	 * @return  the value of teacher.name
	 * @mbg.generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.name
	 * @param name  the value for teacher.name
	 * @mbg.generated
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.photo
	 * @return  the value of teacher.photo
	 * @mbg.generated
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.photo
	 * @param photo  the value for teacher.photo
	 * @mbg.generated
	 */
	public void setPhoto(String photo) {
		this.photo = photo == null ? null : photo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.telephone
	 * @return  the value of teacher.telephone
	 * @mbg.generated
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.telephone
	 * @param telephone  the value for teacher.telephone
	 * @mbg.generated
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone == null ? null : telephone.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.email
	 * @return  the value of teacher.email
	 * @mbg.generated
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.email
	 * @param email  the value for teacher.email
	 * @mbg.generated
	 */
	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column teacher.description
	 * @return  the value of teacher.description
	 * @mbg.generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column teacher.description
	 * @param description  the value for teacher.description
	 * @mbg.generated
	 */
	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table teacher
	 * @mbg.generated
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", userid=").append(userid);
		sb.append(", courseId=").append(courseId);
		sb.append(", name=").append(name);
		sb.append(", photo=").append(photo);
		sb.append(", telephone=").append(telephone);
		sb.append(", email=").append(email);
		sb.append(", description=").append(description);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		return sb.toString();
	}
}