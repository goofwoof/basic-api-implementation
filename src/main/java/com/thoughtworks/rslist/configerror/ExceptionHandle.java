package com.thoughtworks.rslist.configerror;


import com.thoughtworks.rslist.exception.ErrorInputException;
import com.thoughtworks.rslist.exception.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandle{
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handlerInputException(Exception e) {
        Error er = new Error();
        er.setError(e.getMessage());
        LOGGER.error(e.getMessage());
        //.header("error", er.getError())
        return ResponseEntity.badRequest().body(er);
    }

}
