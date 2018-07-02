package com.yinhu.controller.com.yinhu.tools;

/**
 * @ClassName Message
 * @auther 魏星
 * @DATE 2018/6/19
 */
public class Message {
    private String status;
    private String info;
    private Object object;

    /**
     *
     * @param status
     * @param info
     */
    public Message(String status, String info) {
        super();
        this.status = status;
        this.info = info;
    }
    public Message(String status, String info, Object object) {
        super();
        this.status = status;
        this.info = info;
        this.object = object;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
}
