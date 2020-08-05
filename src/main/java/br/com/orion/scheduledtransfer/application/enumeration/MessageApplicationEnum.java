package br.com.orion.scheduledtransfer.application.enumeration;

import lombok.Getter;

public enum MessageApplicationEnum {
    INVALID_ARGUMENT("invalid.arguments"),
    USER_HAS_SAVED("username.saved"),
    USER_ALREADY_EXISTS("username.already.exists"),
    USER_NOT_FOUND("username.not.found"),
    ACCOUNT_MUST_HAVE_SIX_NUMBERS("account.must.have.six.numbers"),
    ACCOUNT_SAVED("account.saved"),
    ACCOUNT_NOT_FOUND("account.not.found"),
    ACCOUNT_ALREADY_EXISTS("account.already.exists"),
    ACCOUNT_NOT_EXIST("account.not.exist"),
    TRANSFER_EXECUTED_SUCCESS("transfer.executed.success"),
    CONDITION_NOT_ACCEPTABLE("condition.not.acceptable"),
    SCHEDULED_DATE_LESS_TODAY("scheduled.date.less.today");

    private String keyMessage;

    MessageApplicationEnum(String keyMessage){
        this.keyMessage = keyMessage;
    }

    public String getValue(){
        return this.keyMessage;
    }

}
