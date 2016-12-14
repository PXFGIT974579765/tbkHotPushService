package com.pxf.first.frame.enty.dic.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dic_state")
public class DicState {
	 @Id
	 @Column(name = "state_id", nullable = false, insertable = true, updatable = true, length = 5)
	private String stateId;
	 @Column(name = "state_name",length=20,nullable=true)
	private String stateName;
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
