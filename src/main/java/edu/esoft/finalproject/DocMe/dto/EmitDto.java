package edu.esoft.finalproject.DocMe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;


public class EmitDto implements Serializable {

    private Integer id;
    private Integer page_no;
    private String keyword;
    private String filename;

    public EmitDto() {
    }

    public EmitDto(Integer page_no, String keyword, String filename) {
        this.page_no = page_no;
        this.keyword = keyword;
        this.filename = filename;
    }

    public EmitDto(Integer id, Integer page_no, String keyword, String filename) {
        this.id = id;
        this.page_no = page_no;
        this.keyword = keyword;
        this.filename = filename;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage_no() {
        return page_no;
    }

    public void setPage_no(Integer page_no) {
        this.page_no = page_no;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
