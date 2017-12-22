package com.dly.app.pojo;

public class Comment {
	private String id;
	private String commentBody;
	private String groupId;
	private String nickname;
	private String iconUrl;
	private String timeStamp;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCommentBody() {
		return commentBody;
	}
	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	

	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public String toString() {
		return "Comment id=" + id + ", commentBody=" + commentBody + ", groupId=" + groupId + ", nickname=" + nickname
				+ ", iconUrl=" + iconUrl + ", timeStamp=" + timeStamp + "";
	}

	

}
