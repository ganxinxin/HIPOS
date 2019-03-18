package com.point.community.bean;

public class dwzPageData {
	//记录总条数
	public Long totalCount;
	//每页记录条数
	public String numPerPage;
	//显示页码数
	public String pageNumShown;
	//当前页码
	public String currentPage;
	
	
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}
	public String getPageNumShown() {
		return pageNumShown;
	}
	public void setPageNumShown(String pageNumShown) {
		this.pageNumShown = pageNumShown;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public dwzPageData(Long totalCount, String numPerPage, String pageNumShown, String currentPage) {
		super();
		this.totalCount = totalCount;
		this.numPerPage = numPerPage;
		this.pageNumShown = pageNumShown;
		this.currentPage = currentPage;
	}
	
	public dwzPageData() {
		super();
		this.totalCount = 0L;
		this.numPerPage = "20";
		this.pageNumShown = "5";
		this.currentPage = "1";
	}
}
