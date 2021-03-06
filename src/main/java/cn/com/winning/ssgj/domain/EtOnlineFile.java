package cn.com.winning.ssgj.domain;

import java.io.Serializable;
import org.apache.ibatis.type.Alias;
import java.util.Date;
import java.util.List;

import cn.com.winning.ssgj.domain.BaseDomain;

/**
 * Coder AutoGenerator generate.
 *
 * @author Coder AutoGenerator
 * @date 2018-02-24 10:57:34
 */
 
@Alias("etOnlineFile")
public class EtOnlineFile extends BaseDomain implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * @val ID
	 */
	private Long id;
	
	/**
	 * @val 合同ID
	 */
	private Long cId;
	
	/**
	 * @val 项目ID
	 */
	private Long pmId;
	
	/**
	 * @val 单据号
	 */
	private String serialNo;
	
	/**
	 * @val 文件路径
	 */
	private String fileSuggestPath;
	
	/**
	 * @val 文件路径
	 */
	private String fileChangePath;
	
	/**
	 * @val 签字照片
	 */
	private String imgPath;
	
	/**
	 * @val 创建人
	 */
	private Long creator;
	
	/**
	 * @val 创建时间
	 */
	private java.sql.Timestamp createTime;
	
	/**
	 * @val 操作人
	 */
	private Long operator;
	
	/**
	 * @val 操作时间
	 */
	private java.sql.Timestamp operatorTime;
	/**
	 *@val 文件类型  1 调研报告 2 单据 3上线联调 4 上线切换方案图片 5 上线切换方案(PC端使用)
	 */
	private String fileType;

	private String dataName;
	/**
	 *@val 数据类型  1 报告  2医疗 3确认单
	 */
	private String dataType;

	private String remark;
	/**
	 * @val 状态 0 否 1 是
	 */
	private Integer status;

	private List imgs;
	
	public EtOnlineFile() {

	}

	/**
	 * @val ID
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @val ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @val 合同ID
	 */
	public Long getcId() {
		return cId;
	}

	/**
	 * @val 合同ID
	 */
	public void setcId(Long cId) {
		this.cId = cId;
	}
	
	/**
	 * @val 项目ID
	 */
	public Long getPmId() {
		return pmId;
	}
	
	/**
	 * @val 项目ID
	 */
	public void setPmId(Long pmId) {
		this.pmId = pmId;
	}
	
	/**
	 * @val 单据号
	 */
	public String getSerialNo() {
		return serialNo;
	}
	
	/**
	 * @val 单据号
	 */
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	
	/**
	 * @val 文件路径
	 */
	public String getFileSuggestPath() {
		return fileSuggestPath;
	}
	
	/**
	 * @val 文件路径
	 */
	public void setFileSuggestPath(String fileSuggestPath) {
		this.fileSuggestPath = fileSuggestPath;
	}
	
	/**
	 * @val 文件路径
	 */
	public String getFileChangePath() {
		return fileChangePath;
	}
	
	/**
	 * @val 文件路径
	 */
	public void setFileChangePath(String fileChangePath) {
		this.fileChangePath = fileChangePath;
	}
	
	/**
	 * @val 签字照片
	 */
	public String getImgPath() {
		return imgPath;
	}
	
	/**
	 * @val 签字照片
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	/**
	 * @val 创建人
	 */
	public Long getCreator() {
		return creator;
	}
	
	/**
	 * @val 创建人
	 */
	public void setCreator(Long creator) {
		this.creator = creator;
	}
	
	/**
	 * @val 创建时间
	 */
	public java.sql.Timestamp getCreateTime() {
		return createTime;
	}
	
	/**
	 * @val 创建时间
	 */
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * @val 操作人
	 */
	public Long getOperator() {
		return operator;
	}
	
	/**
	 * @val 操作人
	 */
	public void setOperator(Long operator) {
		this.operator = operator;
	}
	
	/**
	 * @val 操作时间
	 */
	public java.sql.Timestamp getOperatorTime() {
		return operatorTime;
	}
	
	/**
	 * @val 操作时间
	 */
	public void setOperatorTime(java.sql.Timestamp operatorTime) {
		this.operatorTime = operatorTime;
	}


	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List getImgs() {
		return imgs;
	}

	public void setImgs(List imgs) {
		this.imgs = imgs;
	}
}