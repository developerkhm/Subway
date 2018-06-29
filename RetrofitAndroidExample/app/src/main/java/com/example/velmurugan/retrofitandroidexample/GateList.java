
package com.example.velmurugan.retrofitandroidexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GateList {

    @SerializedName("beginRow")
    @Expose
    private Object beginRow;
    @SerializedName("endRow")
    @Expose
    private Object endRow;
    @SerializedName("curPage")
    @Expose
    private Object curPage;
    @SerializedName("pageRow")
    @Expose
    private Object pageRow;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;
    @SerializedName("rowNum")
    @Expose
    private Integer rowNum;
    @SerializedName("selectedCount")
    @Expose
    private Integer selectedCount;
    @SerializedName("statnId")
    @Expose
    private String statnId;
    @SerializedName("statnNm")
    @Expose
    private String statnNm;
    @SerializedName("ectrcNo")
    @Expose
    private String ectrcNo;
    @SerializedName("subwayId")
    @Expose
    private String subwayId;
    @SerializedName("subwayNm")
    @Expose
    private String subwayNm;
    @SerializedName("ectrcCnt")
    @Expose
    private String ectrcCnt;
    @SerializedName("cfrBuild")
    @Expose
    private String cfrBuild;
    @SerializedName("subwayXcnts")
    @Expose
    private Object subwayXcnts;
    @SerializedName("subwayYcnts")
    @Expose
    private Object subwayYcnts;
    @SerializedName("ectrcId")
    @Expose
    private Object ectrcId;

    public Object getBeginRow() {
        return beginRow;
    }

    public void setBeginRow(Object beginRow) {
        this.beginRow = beginRow;
    }

    public Object getEndRow() {
        return endRow;
    }

    public void setEndRow(Object endRow) {
        this.endRow = endRow;
    }

    public Object getCurPage() {
        return curPage;
    }

    public void setCurPage(Object curPage) {
        this.curPage = curPage;
    }

    public Object getPageRow() {
        return pageRow;
    }

    public void setPageRow(Object pageRow) {
        this.pageRow = pageRow;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getSelectedCount() {
        return selectedCount;
    }

    public void setSelectedCount(Integer selectedCount) {
        this.selectedCount = selectedCount;
    }

    public String getStatnId() {
        return statnId;
    }

    public void setStatnId(String statnId) {
        this.statnId = statnId;
    }

    public String getStatnNm() {
        return statnNm;
    }

    public void setStatnNm(String statnNm) {
        this.statnNm = statnNm;
    }

    public String getEctrcNo() {
        return ectrcNo;
    }

    public void setEctrcNo(String ectrcNo) {
        this.ectrcNo = ectrcNo;
    }

    public String getSubwayId() {
        return subwayId;
    }

    public void setSubwayId(String subwayId) {
        this.subwayId = subwayId;
    }

    public String getSubwayNm() {
        return subwayNm;
    }

    public void setSubwayNm(String subwayNm) {
        this.subwayNm = subwayNm;
    }

    public String getEctrcCnt() {
        return ectrcCnt;
    }

    public void setEctrcCnt(String ectrcCnt) {
        this.ectrcCnt = ectrcCnt;
    }

    public String getCfrBuild() {
        return cfrBuild;
    }

    public void setCfrBuild(String cfrBuild) {
        this.cfrBuild = cfrBuild;
    }

    public Object getSubwayXcnts() {
        return subwayXcnts;
    }

    public void setSubwayXcnts(Object subwayXcnts) {
        this.subwayXcnts = subwayXcnts;
    }

    public Object getSubwayYcnts() {
        return subwayYcnts;
    }

    public void setSubwayYcnts(Object subwayYcnts) {
        this.subwayYcnts = subwayYcnts;
    }

    public Object getEctrcId() {
        return ectrcId;
    }

    public void setEctrcId(Object ectrcId) {
        this.ectrcId = ectrcId;
    }

}
