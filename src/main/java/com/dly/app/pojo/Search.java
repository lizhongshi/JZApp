package com.dly.app.pojo;

public class Search {
	private Integer id;
	private String text;
	private Integer userId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "Search [id=" + id + ", text=" + text + ", userId=" + userId + "]";
	}
	

}
