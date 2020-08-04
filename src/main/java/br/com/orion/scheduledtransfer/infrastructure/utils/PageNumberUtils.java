package br.com.orion.scheduledtransfer.infrastructure.utils;

public class PageNumberUtils {

    public static Integer adjustmentPagination(Integer pageNumber){
        return pageNumber == 0 ? 0 : Math.abs(pageNumber) - 1;
    }
}
