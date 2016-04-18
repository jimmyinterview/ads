package com.edu.ads.bean.ggp;

import java.util.List;

public class Message {
    private Status status;
    private String statusMsg = "";
    private List<Integer> errorKys;
    private String error = "";

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

	public List<Integer> getErrorKys() {
		return errorKys;
	}

	public void setErrorKys(List<Integer> errorKys) {
		this.errorKys = errorKys;
	}

}




