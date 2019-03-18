package com.alibaba.dubbo.demo.bean;
 
import java.io.Serializable;
import java.util.List;
 
public class DwzPage implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 964198192390713221L;
	private int currentPage = 1; // 当前页数，默认第一页
    private int numPerPage = 20; // 每页个数，默认20
    private int totalCount = 0; // 总数，默认0
    private int pageNumShown = 10; // 页标数字多少个，默认10
    private String orderField; // 排序方式
    private String orderDirection; // 升序降序
    private List<Object> list; // 返回的集合
 
    public int getNumPerPage() {
        return numPerPage;
    }
 
    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
 
    public int getTotalCount() {
        return totalCount;
    }
 
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
 
    public String getOrderField() {
        return orderField;
    }
 
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
 
    public String getOrderDirection() {
        return orderDirection;
    }
 
    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }
 
    public List<Object> getList() {
        return list;
    }
 
    public void setList(List<Object> list) {
        this.list = list;
    }
 
    public int getPageNumShown() {
        return pageNumShown;
    }
 
    public void setPageNumShown(int pageNumShown) {
        this.pageNumShown = pageNumShown;
    }
 
    public int getCurrentPage() {
        return currentPage;
    }
 
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
 
}