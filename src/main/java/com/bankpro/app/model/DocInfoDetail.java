package com.bankpro.app.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "pv_doc_info_detail")
@NamedQueries({
	@NamedQuery(name = DocInfoDetail.put_doc_Detail, query = "from DocInfoDetail where docReferenceId = :docReferenceId and docReferenceType = :docReferenceType"),
	@NamedQuery(name = DocInfoDetail.delete_docInfo, query = "delete from DocInfoDetail where docId = :docId") })
public class DocInfoDetail {
	public static final String put_doc_Detail = "DocInfoDetail.put_doc_Detail";
	public static final String delete_docInfo = "DocInfoDetail.delete_docInfo";
	

	@Id
	@GeneratedValue
	@Column(name = "doc_Id")
	private int docId;

	@Column(name = "doc_fileName")
	private String docFileName;

	@Column(name = "doc_manualGivenFileName")
	private String docManuallyGivenFileName;

	@Column(name = "doc_referenceType")
	private String docReferenceType;

	@Column(name = "doc_referenceId")
	private int docReferenceId;

	@Column(name = "doc_creationDate")
	private Date docCreationDate = new Date();

	/**
	 * @return the docId
	 */
	public int getDocId() {
		return docId;
	}

	/**
	 * @param docId
	 *            the docId to set
	 */
	public void setDocId(int docId) {
		this.docId = docId;
	}

	/**
	 * @return the docFileName
	 */
	public String getDocFileName() {
		return docFileName;
	}

	/**
	 * @param docFileName
	 *            the docFileName to set
	 */
	public void setDocFileName(String docFileName) {
		this.docFileName = docFileName;
	}

	/**
	 * @return the docManuallyGivenFileName
	 */
	public String getDocManuallyGivenFileName() {
		return docManuallyGivenFileName;
	}

	/**
	 * @param docManuallyGivenFileName
	 *            the docManuallyGivenFileName to set
	 */
	public void setDocManuallyGivenFileName(String docManuallyGivenFileName) {
		this.docManuallyGivenFileName = docManuallyGivenFileName;
	}

	/**
	 * @return the docReferenceType
	 */
	public String getDocReferenceType() {
		return docReferenceType;
	}

	/**
	 * @param docReferenceType
	 *            the docReferenceType to set
	 */
	public void setDocReferenceType(String docReferenceType) {
		this.docReferenceType = docReferenceType;
	}

	/**
	 * @return the docReferenceId
	 */
	public int getDocReferenceId() {
		return docReferenceId;
	}

	/**
	 * @param docReferenceId
	 *            the docReferenceId to set
	 */
	public void setDocReferenceId(int docReferenceId) {
		this.docReferenceId = docReferenceId;
	}

	/**
	 * @return the docCreationDate
	 */
	public Date getDocCreationDate() {
		return docCreationDate;
	}

	/**
	 * @param docCreationDate
	 *            the docCreationDate to set
	 */
	public void setDocCreationDate(Date docCreationDate) {
		this.docCreationDate = docCreationDate;
	}

}
