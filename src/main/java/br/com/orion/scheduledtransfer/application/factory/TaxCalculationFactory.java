package br.com.orion.scheduledtransfer.application.factory;

import br.com.orion.scheduledtransfer.domain.enumaration.TransferTypeEnum;
import br.com.orion.scheduledtransfer.application.service.calculation.CalculationTransferTypeA;
import br.com.orion.scheduledtransfer.application.service.calculation.CalculationTransferTypeB;
import br.com.orion.scheduledtransfer.application.service.calculation.CalculationTransferTypeC;
import br.com.orion.scheduledtransfer.domain.interfaces.ITaxCalculation;

public class TaxCalculationFactory {

    public static ITaxCalculation getTaxCalculationTransfer(TransferTypeEnum typeEnum){

        ITaxCalculation taxCalculation = switch (typeEnum){
            case TYPE_A -> new CalculationTransferTypeA();
            case TYPE_B -> new CalculationTransferTypeB();
            case TYPE_C -> new CalculationTransferTypeC();
            default -> null;
        };

        return taxCalculation;
    }

}
