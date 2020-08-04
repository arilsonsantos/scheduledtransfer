package br.com.orion.scheduledtransfer.domain.enumaration;

import lombok.Getter;

public enum TransferTypeEnum {
    TYPE_A("A"),
    TYPE_B("B"),
    TYPE_C("C");

    @Getter
    private String type;

    TransferTypeEnum(String type){
        this.type = type;
    }

}
