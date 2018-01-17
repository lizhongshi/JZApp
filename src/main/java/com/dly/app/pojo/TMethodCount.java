package com.dly.app.pojo;

import java.util.Date;

public class TMethodCount {
    private Integer id;

    private String methodName;

    private Date cteateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName == null ? null : methodName.trim();
    }

    public Date getCteateTime() {
        return cteateTime;
    }

    public void setCteateTime(Date cteateTime) {
        this.cteateTime = cteateTime;
    }

	@Override
	public String toString() {
		return "TMethodCount [id=" + id + ", methodName=" + methodName + ", cteateTime=" + cteateTime + "]";
	}
    
}