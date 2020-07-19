package com.xb.util;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BaseResult {


    private int pageNo;

    private int pageSize;

    private Long totalCount;

    /*
    * 状态码，200成功，199失败，。。。。。
    * */
    private int code;

    /*
    * 返回信息
    * */
    private String msg;


    /*
    * 返回数据
    * */
    private Object data;


    public BaseResult setTotalCount(Long count){
        this.totalCount = count;
        return this;
    }

    public BaseResult setPageNo(int pageNo) {
        this.pageNo = pageNo;
        return this;
    }

    public BaseResult setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public BaseResult setCode(int code){
        this.code = code;
        return this;
    }

    public BaseResult setMsg(String msg){
        this.msg = msg;
        return this;
    }

    public BaseResult setData(Object data){
        this.data = data;
        return this;
    }

    public static BaseResult failure (String msg){
        return new BaseResult().setCode(199).setMsg(msg);
    }

    public static BaseResult success(){
        return new BaseResult().setCode(200);
    }

    public static BaseResult success(Object data){
        return new BaseResult().setCode(200).setData(data);
    }

    public static BaseResult successEmptyPage(){
        return new BaseResult().setPageNo(0);
    }

    public static BaseResult successPage(int pageNo,int pageSize,Long count,Object data){
        return new BaseResult().setPageNo(pageNo).setPageSize(pageSize).setData(data).setTotalCount(count);
    }

}
