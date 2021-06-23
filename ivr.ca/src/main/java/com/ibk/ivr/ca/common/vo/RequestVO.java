package com.ibk.ivr.ca.common.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class RequestVO extends DataVO {

	private static final long serialVersionUID = -6433735342750348639L;

	private int pageNo = 1;
	
	private int startRow = 0;

	private int endRow = 0;
	
	private transient int pagingRow = 10;
	
	private transient int pageingUnitCount = 10;
	
	private transient String orderBy;
	
	private long size;
	
	private List<?> list;
	
	private transient String limit;
	
	private Map<String, Object> param;
	
	public RequestVO() {
		param = new HashMap<String, Object>();
	}
	
	public RequestVO(Map<String, Object> param) {
		this.param = param;
	}

	public RequestVO result(List<?> list) {
		this.size = list.size();
		this.list = this.size > 0 ? list : null;
		return this;
	}
	
	public RequestVO result(List<?> list, long size) {
		this.size = size;
		this.list = list;
		return this;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		
		if(pageNo == 0) {
			startRow = 0;
			endRow = Integer.MAX_VALUE;
			limit = null;
			return;
		}

		startRow = ((pageNo - 1) * pagingRow);
		endRow = startRow + pagingRow;
		initLimit();
	}
	
	protected void initLimit() {
		StringBuilder sb = new StringBuilder();
		sb.append(startRow);
		sb.append(",");
		sb.append(pagingRow);
		this.limit = sb.toString();
	}

	public String getLimit() {
		if(limit == null && pageNo != 0)
			this.setPageNo(pageNo);
		return limit;
	}

	public void setPagingRow(int pagingRow) {
		this.pagingRow = pagingRow;
		setPageNo(this.pageNo);
	}
	
	public int getPageCount() {
        int pageCount = (int)(size / pagingRow);
        int rest = (int)(size % pagingRow);
        if(rest > 0)
            pageCount = pageCount + 1;
        return pageCount;
    }

	public int getStartPageNo() {
        int mok = pageNo / pageingUnitCount;
        int mod = pageNo % pageingUnitCount;
        int rest = 0;
        if(mod == 0)
            rest = 1;

        return (mok - rest) * pageingUnitCount + 1;
	}

	public int getEndPageNo() {
        int mok = pageNo / pageingUnitCount;
        int mod = pageNo % pageingUnitCount;
        int rest = 0;
        if(mod == 0)
            rest = 1;

        int start = (mok - rest) * pageingUnitCount + 1;
        int end = start + (pageingUnitCount - 1);
        int totalPageCount = getPageCount();
        if(end > totalPageCount)
            end = totalPageCount;

        return end;
	}
	
	public int[] getPages() {
		int start = getStartPageNo();
		int end = getEndPageNo();
		int[] pages = new int[end-start+1];
		for(int i = 0; i < pages.length; i++) {
			pages[i] = start+i;
        }		
		return pages;
	}
}
