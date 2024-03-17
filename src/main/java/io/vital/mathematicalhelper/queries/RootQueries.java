package io.vital.mathematicalhelper.queries;

public class RootQueries {
    public static String CREATE_ROOT_QUERY = "INSERT INTO ROOTS (value) VALUES(:value);";
    public static String FIND_ROOT_BY_ITS_VALUE_QUERY = "SELECT * FROM ROOTS WHERE value=:value;";
}
