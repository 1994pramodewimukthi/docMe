package edu.esoft.finalproject.DocMe.dto;


import edu.esoft.finalproject.DocMe.entity.DocCategoryMaster;
import edu.esoft.finalproject.DocMe.entity.RecordStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DocCategoryMasterWebix implements Serializable {

    private Integer docCategoryMstId;
    private DocCategoryMaster parentDocCategoryMst;
    private String docCategoryName;
    private String docDescription;
    private String publishDate;
    private int sortingOrder = 0;
    private RecordStatus recordStatus;
    private String inpUserId;
    private Date inpDateTime;
    private String authUserId;
    private Date authDateTime;
    private String reason;
    private String addcol;
    private String modcol;
    private int tableConfig = 0;
    private boolean optionView;
    private boolean optionInsert = true;
    private boolean optionModify = true;
    private boolean optionDelete;
    private boolean optionGrant;
    private boolean optionActive;
    private boolean optionAuthorize;

    private List<DocCategoryMasterWebix> data = new ArrayList<>();

    public Integer getDocCategoryMstId() {
        return docCategoryMstId;
    }

    public void setDocCategoryMstId(Integer docCategoryMstId) {
        this.docCategoryMstId = docCategoryMstId;
    }

    public DocCategoryMaster getParentDocCategoryMst() {
        return parentDocCategoryMst;
    }

    public void setParentDocCategoryMst(DocCategoryMaster parentDocCategoryMst) {
        this.parentDocCategoryMst = parentDocCategoryMst;
    }

    public String getDocCategoryName() {
        return docCategoryName;
    }

    public void setDocCategoryName(String docCategoryName) {
        this.docCategoryName = docCategoryName;
    }

    public int getSortingOrder() {
        return sortingOrder;
    }

    public void setSortingOrder(int sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public RecordStatus getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(RecordStatus recordStatus) {
        this.recordStatus = recordStatus;
    }

    public String getInpUserId() {
        return inpUserId;
    }

    public void setInpUserId(String inpUserId) {
        this.inpUserId = inpUserId;
    }

    public Date getInpDateTime() {
        return inpDateTime;
    }

    public void setInpDateTime(Date inpDateTime) {
        this.inpDateTime = inpDateTime;
    }

    public String getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(String authUserId) {
        this.authUserId = authUserId;
    }

    public Date getAuthDateTime() {
        return authDateTime;
    }

    public void setAuthDateTime(Date authDateTime) {
        this.authDateTime = authDateTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<DocCategoryMasterWebix> getData() {
        return data;
    }

    public void setData(List<DocCategoryMasterWebix> data) {
        this.data = data;
    }

    public String getAddcol() {
        return addcol;
    }

    public void setAddcol(String addcol) {
        this.addcol = addcol;
    }

    public String getModcol() {
        return modcol;
    }

    public void setModcol(String modcol) {
        this.modcol = modcol;
    }

    public int getTableConfig() {
        return tableConfig;
    }

    public void setTableConfig(int tableConfig) {
        this.tableConfig = tableConfig;
    }

    public boolean isOptionView() {
        return optionView;
    }

    public void setOptionView(boolean optionView) {
        this.optionView = optionView;
    }

    public boolean isOptionInsert() {
        return optionInsert;
    }

    public void setOptionInsert(boolean optionInsert) {
        this.optionInsert = optionInsert;
    }

    public boolean isOptionModify() {
        return optionModify;
    }

    public void setOptionModify(boolean optionModify) {
        this.optionModify = optionModify;
    }

    public boolean isOptionDelete() {
        return optionDelete;
    }

    public void setOptionDelete(boolean optionDelete) {
        this.optionDelete = optionDelete;
    }

    public boolean isOptionGrant() {
        return optionGrant;
    }

    public void setOptionGrant(boolean optionGrant) {
        this.optionGrant = optionGrant;
    }

    public boolean isOptionActive() {
        return optionActive;
    }

    public void setOptionActive(boolean optionActive) {
        this.optionActive = optionActive;
    }

    public boolean isOptionAuthorize() {
        return optionAuthorize;
    }

    public void setOptionAuthorize(boolean optionAuthorize) {
        this.optionAuthorize = optionAuthorize;
    }

    public String getDocDescription() {
        return docDescription;
    }

    public void setDocDescription(String docDescription) {
        this.docDescription = docDescription;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
}
