package com.crescendocollective.emailform;

import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.model.RenderingModelImpl;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import javax.jcr.Node;

public class EmailForm<RD extends ConfiguredTemplateDefinition> extends RenderingModelImpl<ConfiguredTemplateDefinition> {

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
	private boolean addToMailingList = false;

	private static boolean isNullOrEmpty(String value) {
		return value != null && !value.isEmpty();
	}

	public String getStatusMessage() {return statusMessage;}
	public void setStatusMessage(String statusMessage) {this.statusMessage = statusMessage;}

	public String getFirstName() {return firstName;}
	public void setFirstName(String firstName) {this.firstName = firstName;}

	public boolean isFirstNameValid() {return EmailForm.isNullOrEmpty(firstName);}

	public String getLastName() {return lastName;}
	public void setLastName(String lastName) {this.lastName = lastName;}

	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}

	public boolean isAddToMailingList() {return addToMailingList;}
	public void setAddToMailingList(boolean addToMailingList) {this.addToMailingList = addToMailingList;}

	public boolean isLastNameValid() {
		return EmailForm.isNullOrEmpty(lastName);
	}

	public boolean isEmailValid() {
		return email != null && email.matches("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	}

	public void confirmSubmit(String recipient) {
		if (!this.isFirstNameValid() || !this.isLastNameValid()) {
			statusMessage = "Please specify fist name and last name";
			return;
		} else if (!this.isEmailValid()) {
			statusMessage = "Wrong e-mail address format";
			return;
		}

		statusMessage = "Thank you";
		String message = "Please contact me";
		if (addToMailingList) {
			message += " and add " + email + " to the mailing list.";
		}

		message += ".\n Best regards, \n" + firstName + " " + lastName;
		EmailService.sendEmail(EmailForm.isNullOrEmpty(recipient) ? "ostestbox@gmail.com" : recipient, email, message);
	}

}
