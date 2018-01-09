package com.crescendocollective.emailform;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

public class EmailForm <RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<ConfiguredTemplateDefinition> {
	
	public EmailForm(Node content, ConfiguredTemplateDefinition definition, RenderingModel<?> parent) {
		super(content, definition, parent);
		firstName = "";
		lastName = "";
		email = "";
		statusMessage = "";

		// TODO Auto-generated constructor stub
	}
	
	private String firstName;
	private String lastName;
	private String email;
	private String statusMessage;
	private boolean join = false;

	private static boolean isNullOrEmpty(String value) {
		if (value == null) {
			return false;
		}
		else if (value.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public String getStatusMessage() {

		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {

		this.statusMessage = statusMessage;
	}

	public String getFirstName() {

		return firstName;
	}
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}
	public boolean isFirstNameValid() {
		return EmailForm.isNullOrEmpty(firstName);
	}

	public String getLastName() {

		return lastName;
	}
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}
	public boolean isLastNameValid() {
		return EmailForm.isNullOrEmpty(lastName);
	}

	public String getEmail() {

		return email;
	}
	public void setEmail(String email) {

		this.email = email;
	}
	public boolean isEmailValid() {
		if (email == null) {
			return false;
		}
		else {
			return email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
		}
	}

	public boolean isJoin() {

		return join;
	}
	public void setJoin(boolean join) {

		this.join = join;
	}



	public void confirmSubmit(String recipient){

		if (this.isFirstNameValid() && this.isLastNameValid() && this.isEmailValid()) {
			statusMessage = "Thank you";

			String message = "Please contact me";
			if (join) {
				message += " and add " + email + " to the mailing list.";
			}

			message += ".\n Best regards, \n" + firstName + " " + lastName;

			EmailService.sendEmail(EmailForm.isNullOrEmpty(recipient) ? "ostestbox@gmail.com" : recipient, email, message);
		}

	}

}
