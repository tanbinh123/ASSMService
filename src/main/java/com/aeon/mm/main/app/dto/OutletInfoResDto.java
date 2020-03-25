package com.aeon.mm.main.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class OutletInfoResDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1946752761842732829L;

	@Id
	@Column
	private int id;
	
	@Column
	private String outletName;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOutletName() {
		return outletName;
	}

	public void setOutletName(String outletName) {
		this.outletName = outletName;
	}

}
