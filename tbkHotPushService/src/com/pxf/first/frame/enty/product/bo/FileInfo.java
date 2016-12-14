package com.pxf.first.frame.enty.product.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_file")
public class FileInfo {
	@Id
	@Column(name = "file_id", nullable = false, insertable = true, updatable = true, length = 32)
	private String fileId;
	@Column(name = "file_rel_name",length=50,nullable=false)
	private String fileRelName;
	@Column(name = "file_save_name",length=100,nullable=false)
	private String fileSaveName;
	@Column(name = "file_describe",length=100,nullable=false)
	private String fileDesc;
	@Column(name = "create_time",length=32,nullable=false)
	private String createTime;
	@Column(name = "order_basic_id",length=32,nullable=false)
	private String orderBasicId;
	@Column(name = "file_per_acc",length=10,nullable=false)
	private String filePerAcc;
	@Column(name = "stage_id",length=5,nullable=false)
	private String stageId;
	@Column(name = "file_flag",length=10,nullable=false)
	private String fileFlag;
	public String getFileFlag() {
		return fileFlag;
	}
	public void setFileFlag(String fileFlag) {
		this.fileFlag = fileFlag;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileRelName() {
		return fileRelName;
	}
	public void setFileRelName(String fileRelName) {
		this.fileRelName = fileRelName;
	}
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getOrderBasicId() {
		return orderBasicId;
	}
	public void setOrderBasicId(String orderBasicId) {
		this.orderBasicId = orderBasicId;
	}
	public String getFilePerAcc() {
		return filePerAcc;
	}
	public void setFilePerAcc(String filePerAcc) {
		this.filePerAcc = filePerAcc;
	}
	public String getStageId() {
		return stageId;
	}
	public void setStageId(String stageId) {
		this.stageId = stageId;
	}

}
