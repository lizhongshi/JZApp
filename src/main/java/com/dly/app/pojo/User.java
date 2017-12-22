package com.dly.app.pojo;

import java.sql.Date;

public class User {  
	private String userId; //用户id
	private String username;//用户名
	private String password;//密码
	private String newPassword;//新密码
	private String nickname;//昵称
	private String salt;//盐值
	private String tokenId;//令牌
	private String phone;//手机号
	private String newPhone;
	private String weixin;//微信
	private String qq;//qq
	private String struts;//用户状态
	private String email;//邮箱
	private String iconUrl;//头像
	private String verificationCode;//验证码
	private String timeStamp;//时间戳
	private String creationTime;//注册时间
	private String sex;//性别
	private Date birthDate;//出生日期
	private String ffbm;//方法编码
	
	
	public String getFfbm() {
		return ffbm;
	}

	public void setFfbm(String ffbm) {
		this.ffbm = ffbm;
	}

	public String getNewPhone() {
		return newPhone;
	}

	public void setNewPhone(String newPhone) {
		this.newPhone = newPhone;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public User() {
		
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	




	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconurl) {
		this.iconUrl = iconurl;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getStruts() {
		return struts;
	}
	public void setStruts(String struts) {
		this.struts = struts;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	@Override
	public String toString() {
		return "userId=" + userId + ", username=" + username + ", password=" + password + ", newPassword="
				+ newPassword + ", nickname=" + nickname + ", salt=" + salt + ", tokenId=" + tokenId + ", phone="
				+ phone + ", newPhone=" + newPhone + ", weixin=" + weixin + ", qq=" + qq + ", struts=" + struts
				+ ", email=" + email + ", iconUrl=" + iconUrl + ", verificationCode=" + verificationCode
				+ ", timeStamp=" + timeStamp + ", creationTime=" + creationTime + ", sex=" + sex + ", birthDate="
				+ birthDate + ", ffbm=" + ffbm + "";
	}





	


	
	
	
}
