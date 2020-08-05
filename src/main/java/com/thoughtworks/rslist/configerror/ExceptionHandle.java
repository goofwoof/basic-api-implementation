package com.thoughtworks.rslist.configerror;


import com.thoughtworks.rslist.exception.Error;
import com.thoughtworks.rslist.exception.ErrorIndexException;
import com.thoughtworks.rslist.exception.ErrorInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionHandle{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler({ErrorIndexException.class, ErrorInputException.class, MethodArgumentNotValidException.class})
    public ResponseEntity handlerInputException(Exception e) {
        Error er = new Error();
        if(e instanceof MethodArgumentNotValidException){
            er.setError("invalid param");
        }
        else{
            er.setError(e.getMessage());
        }
        LOGGER.error(e.getMessage());
        //.header("error", er.getError())
        return ResponseEntity.badRequest().body(er);
    }

}
