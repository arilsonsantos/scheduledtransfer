package br.com.orion.scheduledtransfer.application.utils;

import br.com.orion.scheduledtransfer.application.enumeration.MessageApplicationEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils {

    @Autowired
    MessageSource messageSource;

    public String getMessage(MessageApplicationEnum messageApplicationEnum){
        return messageSource.getMessage(messageApplicationEnum.getValue(), null, LocaleContextHolder.getLocale());
    }

    public String getMessage(MessageApplicationEnum messageApplicationEnum, String complement){
        return messageSource.getMessage(messageApplicationEnum.getValue(), null, LocaleContextHolder.getLocale()).concat( " - ").concat(complement);
    }

}


