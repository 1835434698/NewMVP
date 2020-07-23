package com.tangzy.tzymvp.bean.base;

import java.io.Serializable;

/**
 * Description:接口基础结构
 *
 * @author: GuoYongPing
 * @date: 2016/6/22 14:18
 */

public class BaseResponse<D> implements Serializable {

	public static final String RESPONSE_CODE_NO_DATA = "fail";

	private static final long serialVersionUID = -7283622639574450309L;

	private String responseMessage;
	private D responseData;
	private String responseCode;
	private boolean responseStatus;
	private String responsePk;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public D getResponseData() {
		return responseData;
	}

	public void setResponseData(D responseData) {
		this.responseData = responseData;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public boolean isResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(boolean responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponsePk() {
		return responsePk;
	}

	public void setResponsePk(String responsePk) {
		this.responsePk = responsePk;
	}

	@Override
	public String toString() {
		return "BaseResponse{" +
				"responseMessage='" + responseMessage + '\'' +
				", responseData=" + responseData +
				", responseCode='" + responseCode + '\'' +
				", responseStatus=" + responseStatus +
				", responsePk=" + responsePk +
				'}';
	}
}
