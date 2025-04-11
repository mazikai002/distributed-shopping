package com.itheima.ds.common.exception;

import com.itheima.ds.common.result.CodeMsg;
import lombok.Getter;

/**
 * 全局异常类
 */
@Getter
public class GlobalException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private CodeMsg codeMsg;
    
    public GlobalException(String message) {
        super(message);
        this.codeMsg = new CodeMsg(500, message);
    }
    
    public GlobalException(CodeMsg codeMsg) {
        super(codeMsg.getMessage());
        this.codeMsg = codeMsg;
    }
    
    public GlobalException(int code, String message) {
        super(message);
        this.codeMsg = new CodeMsg(code, message);
    }
    
    /**
     * 保留原有方法，兼容旧代码
     */
    public CodeMsg getCm() {
        return codeMsg;
    }
}