package br.com.orion.scheduledtransfer.application.helper;

import br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum;

import static br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum.*;

public class TransferTypeEnumHelper {

    public static TransferTypeEnum valueOf(int days) {
        if (days == 0) {
            return TYPE_A;
        } else if (days > 0 && days <= 10) {
            return TYPE_B;
        } else if (days > 10) {
            return TYPE_C;
        }

        return null;
    }
}
