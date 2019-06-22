package edu.esoft.finalproject.DocMe.dto;

import java.io.Serializable;

public class AccessUserTypeDto implements Serializable {

    private String rankId;
    private String rank;

    public AccessUserTypeDto() {
    }

    public AccessUserTypeDto(String rankId, String rank) {
        this.rankId = rankId;
        this.rank = rank;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
