package com.jit.aquaculture.dto.FeedTemplateDto;

public class FeedTemplateDto<T> {
    //响应状态，比如200，400之类的
    private Integer status;

    //状态信息，比如“添加成功！”“添加失败”
    private String msg;

    //数据，比如查看信息的时候可以把信息字段放进去，如果是增删改操作没有信息的时候可以用Void
    private T data;
    //时间，也可以不写这个
    private Long timestamp;

    public FeedTemplateDto() {
    }

    public FeedTemplateDto(Integer status, String msg, T data, Long timestamp) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
