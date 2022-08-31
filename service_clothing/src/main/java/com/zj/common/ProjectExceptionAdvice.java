package com.zj.common;



import com.zj.exception.BusinessException;
import com.zj.exception.SystemException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice用于标识当前类为REST风格对应的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {

    //@ExceptionHandler用于设置当前处理器类对应的异常类型
    @ExceptionHandler(SystemException.class)
    public R doSystemException(SystemException ex){
        return new R(ex.getCode(),null,ex.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public R doBusinessException(BusinessException ex){

        ex.printStackTrace();
        return new R(ex.getCode(),null,ex.getMessage());
    }

    //除了自定义的异常处理器，保留对Exception类型的异常处理，用于处理非预期的异常
    @ExceptionHandler(Exception.class)
    public R doOtherException(Exception ex){
        ex.printStackTrace();
        return new R(Code.ERROR,null,"系统繁忙，请稍后再试！");
    }

    //捕获参数异常
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R doIllegalArgumentException(HttpMessageNotReadableException ex){
        ex.printStackTrace();
        return new R(Code.PARAM_ERROR,null,"参数异常,请正确输入！");
    }
}
