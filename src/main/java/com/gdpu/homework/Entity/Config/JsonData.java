package com.gdpu.homework.Entity.Config;
/*
封装自定义状态码
 */
public class JsonData {
    private Integer statusCode; // 自定义状态码 ，1 表示成功，-1表示失败
    private Object data; // 数据
    private String msg;// 描述
    public JsonData() {
    }
    public JsonData(Integer code, Object data, String msg) {
        this.statusCode = code;
        this.data = data;
        this.msg = msg;
    }
    //成功后返回数据
    public static JsonData success(Object data){
        return new JsonData(1,data,null);
    }
    //失败
    public static JsonData error(String message){
        return  new JsonData(-1,null,message);
    }
    //成功操作后返回操作信息
   public static  JsonData progress(String message){return  new JsonData(0,null,message);}

    @Override
    public String toString() {
        return "JsonData{" +
                "statusCode=" + statusCode +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer code) {
        this.statusCode = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
