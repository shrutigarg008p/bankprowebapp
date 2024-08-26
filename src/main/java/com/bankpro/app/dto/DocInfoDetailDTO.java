/**
 * 
 */
package com.bankpro.app.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.bankpro.app.model.DocInfoDetail;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Admin
 *
 */
public class DocInfoDetailDTO {
	private int docInfoId;
	private String fileName;
	private String manuallyGivenFileName;
	private int referenceId;
	private String refType;

	@JsonFormat(pattern = "dd/MM/yyyy hh:mm a", timezone = "IST")
	private Date uploadedDate;

	public DocInfoDetailDTO() {

	}

	public DocInfoDetailDTO(DocInfoDetail docDetail) {
		this.fileName = docDetail.getDocFileName();
		this.manuallyGivenFileName = docDetail.getDocManuallyGivenFileName();
		this.docInfoId = docDetail.getDocId();
		this.referenceId = docDetail.getDocReferenceId();
		this.refType = docDetail.getDocReferenceType();
		this.uploadedDate = docDetail.getDocCreationDate();

	}

	public static DocInfoDetailDTO mapAuditEntity(DocInfoDetail docDetail) {
		return new DocInfoDetailDTO(docDetail);
	}

	public static List<DocInfoDetailDTO> mapDocInfoList(
			List<DocInfoDetail> mapDocList) {
		return mapDocList.stream()
				.map((mapDocListt) -> mapAuditEntity(mapDocListt))
				.collect(Collectors.toList());
	}

	/**
	 * @return the uploadedDate
	 */
	public Date getUploadedDate() {
		return uploadedDate;
	}

	/**
	 * @param uploadedDate
	 *            the uploadedDate to set
	 */
	public void setUploadedDate(Date uploadedDate) {
		this.uploadedDate = uploadedDate;
	}

	/**
	 * @return the docInfoId
	 */
	public int getDocInfoId() {
		return docInfoId;
	}

	/**
	 * @param docInfoId
	 *            the docInfoId to set
	 */
	public void setDocInfoId(int docInfoId) {
		this.docInfoId = docInfoId;
	}

	/**
	 * @return the referenceId
	 */
	public int getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId
	 *            the referenceId to set
	 */
	public void setReferenceId(int referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the refType
	 */
	public String getRefType() {
		return refType;
	}

	/**
	 * @param refType
	 *            the refType to set
	 */
	public void setRefType(String refType) {
		this.refType = refType;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the manuallyGivenFileName
	 */
	public String getManuallyGivenFileName() {
		return manuallyGivenFileName;
	}

	/**
	 * @param manuallyGivenFileName
	 *            the manuallyGivenFileName to set
	 */
	public void setManuallyGivenFileName(String manuallyGivenFileName) {
		this.manuallyGivenFileName = manuallyGivenFileName;
	}

}
