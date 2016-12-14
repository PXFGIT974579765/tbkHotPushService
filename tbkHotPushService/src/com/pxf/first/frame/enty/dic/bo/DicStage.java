package com.pxf.first.frame.enty.dic.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="dic_stage")
public class DicStage {

	@Id
	 @Column(name = "stage_id", nullable = false, insertable = true, updatable = true, length = 5)
	private String stageId;
	@Column(name = "stage_name",length=20,nullable=true)
	private String stageName;
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}
	public String getStageName() {
		return stageName;
	}
	public void setStageName(String stageName) {
		this.stageName = stageName;
	}
}
