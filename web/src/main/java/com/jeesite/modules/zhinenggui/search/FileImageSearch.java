package com.jeesite.modules.zhinenggui.search;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
public class FileImageSearch{

    private String fileId; //文件名
    private String createDate; //创建时间
    private String filePath; //子集目录
    private String fileContentType; //文件类型
    private String fileExtension; //文件后缀名
    private String orderId;	//订单ID  OrderId
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	
	
}
