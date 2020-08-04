package br.com.orion.scheduledtransfer.infrastructure.utils;

public class NumberUtils {

    public static boolean isNumberIntoRange(int numberDays, int biggerThan, int lessEqualsThan) {
        return (numberDays > biggerThan && numberDays <= lessEqualsThan);
    }

}
