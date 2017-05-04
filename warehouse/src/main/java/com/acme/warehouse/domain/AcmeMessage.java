package com.acme.warehouse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity to store the JMS message
 *
 * @author ajorritsma
 */
@Entity
public class AcmeMessage {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;

	@Column
	private String payload;

	@Column
	private String messageId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
