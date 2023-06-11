package com.epra.wiki.exception;

/**
 * @Author: Guotao Li
 * @DateTime: 2023/6/11 7:36 下午
 * @Description: TODO
 */
public class BusinessException extends RuntimeException{
    private BusinessExceptionCode code;

    public BusinessException(BusinessExceptionCode code) {
        super(code.getDesc());
        this.code = code;
    }

    public BusinessExceptionCode getCode() {
        return code;
    }

    public void setCode(BusinessExceptionCode code) {
        this.code = code;
    }

    /**
     * 不写入堆栈信息，提高性能
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
