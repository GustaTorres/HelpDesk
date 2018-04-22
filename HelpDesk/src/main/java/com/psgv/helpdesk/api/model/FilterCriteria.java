package com.psgv.helpdesk.api.model;

public class FilterCriteria<T> {
	
	private T example;
	private Integer pageSize = 10;
	private Integer pageNumber = 0;
	private String sort;
	private String sortElement;

	public T getExample() {
		return example;
	}

	public void setExample(T example) {
		this.example = example;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	public String getSortElement() {
		return sortElement;
	}
	
	public void setSortElement(String sortElement) {
		this.sortElement = sortElement;
	}
}
