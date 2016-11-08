package com.rossbailey.objects;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class RossponsysEvent implements Serializable{

	private Integer id;
	private String cid;
	private Map<String, Object> event;
	private Date created;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public Map<String, Object> getEvent() {
		return event;
	}

	public void setEvent(Map<String, Object> event) {
		this.event = event;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
