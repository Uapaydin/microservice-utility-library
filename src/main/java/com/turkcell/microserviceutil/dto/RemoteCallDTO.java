package com.turkcell.microserviceutil.dto;


public class RemoteCallDTO<T> {

    private  int returnCode;
    private String returnMessage;
    private String error;
    private Boolean isPaginated;
    private T content;

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Boolean getPaginated() {
        return isPaginated;
    }

    public void setPaginated(Boolean paginated) {
        isPaginated = paginated;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
