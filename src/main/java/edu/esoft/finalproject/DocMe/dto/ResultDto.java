package edu.esoft.finalproject.DocMe.dto;


import lombok.Data;
import org.apache.lucene.document.Document;

import java.io.Serializable;
import java.util.List;


public class ResultDto implements Serializable {
    private List<EmitDto> emits;

    public ResultDto() {
    }

    public ResultDto(List<EmitDto> emits) {
        this.emits = emits;
    }

    public List<EmitDto> getEmits() {
        return emits;
    }

    public void setEmits(List<EmitDto> emits) {
        this.emits = emits;
    }
}
