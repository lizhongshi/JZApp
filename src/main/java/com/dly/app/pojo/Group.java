package com.dly.app.pojo;

public class Group {
	private String id;
	private String title;
	private String text;
	private String commentId;
	private String moudleId;
	private String imageId;
	private String istp;
	private int issc;//是否收藏 0:否 1:是
	private String videoUrl;
	private Integer videoId;
	private String imageUrl;
	private String imageName;
	private String width;
	private String height;
	private String userId;
	private Integer index;
	private Integer size;
	private String level;
	private Integer levelId;
	private Integer countyId;
	
	public Integer getCountyId() {
		return countyId;
	}
	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}
	

	public int getIssc() {
		return issc;
	}
	public void setIssc(int issc) {
		this.issc = issc;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	
	public String getMoudleId() {
		return moudleId;
	}
	public void setMoudleId(String moudleId) {
		this.moudleId = moudleId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getIstp() {
		return istp;
	}
	public void setIstp(String istp) {
		this.istp = istp;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", title=" + title + ", text=" + text + ", commentId=" + commentId + ", moudleId="
				+ moudleId + ", imageId=" + imageId + ", istp=" + istp + ", issc=" + issc + ", videoUrl=" + videoUrl
				+ ", videoId=" + videoId + ", imageUrl=" + imageUrl + ", imageName=" + imageName + ", width=" + width
				+ ", height=" + height + ", userId=" + userId + ", index=" + index + ", size=" + size + ", level="
				+ level + ", levelId=" + levelId + ", countyId=" + countyId + "]";
	}

	
	

}
