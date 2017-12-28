package com.dly.app.pojo;

import java.util.List;

public class City {
	private Integer id;
	private String name;

	private List<?> countys;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<?> getCountys() {
		return countys;
	}
	public void setCountys(List<?> countys) {
		this.countys = countys;
	}

}
