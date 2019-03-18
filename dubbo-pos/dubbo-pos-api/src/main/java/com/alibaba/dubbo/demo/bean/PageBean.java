package com.alibaba.dubbo.demo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author LangXianWei 
 * @version 创建时间：May 28, 2008 12:19:53 PM 类说明
 */

@SuppressWarnings("serial")
public class PageBean implements Serializable{

	private int page = 1;//当前页码
	private int start = 1;//开始序号
	private int pageSize = 20;//每页记录数
	private Long total = 0l;//记录条数
	@SuppressWarnings("rawtypes")
	private List resultList = null;
	private String url = null;
	private int  showPageNum = 5;//显示的页码个数
	private int startPage = 1;   
	private int endPage = 1;

	public PageBean(String page, String pageSize, String url) {
		if (!"".equals(page) && null != page)
			this.page = Integer.parseInt(page);
		if (!"".equals(pageSize) && null != pageSize)
			this.pageSize = Integer.parseInt(pageSize);
	}
	
	public PageBean(String start, String limit) {
		if (start != null) {
			if (limit != null) {
				page = Integer.parseInt(start) / Integer.parseInt(limit) + 1;
				pageSize = Integer.parseInt(limit);
			} else {
				page = Integer.parseInt(start) / pageSize + 1;
			}
		}
	}

	public void compute() {
		startPage = page > 3 ? page - 3 : 1;
		endPage = (int) (total / pageSize > startPage + 9 ? startPage + 9 : total
				% pageSize == 0 ? total / pageSize : total / pageSize + 1);
	}

	public int getPage() {
		return this.page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Long getTotal() {
		return this.total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@SuppressWarnings("rawtypes")
	public List getResultList() {
		return this.resultList;
	}

	@SuppressWarnings("rawtypes")
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public int getStartPage() {
		return this.startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return this.endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getShowPageNum() {
		return showPageNum;
	}

	public void setShowPageNum(int showPageNum) {
		this.showPageNum = showPageNum;
	}
}