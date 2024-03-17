package io.vital.mathematicalhelper.queries;

public class ExpressionQueries {
    public static String CREATE_EXPRESSION_SQL = "INSERT INTO EXPRESSIONS (expression_text) VALUES(:expressionText);";
    public static String SET_EXPRESSION_ROOT_SQL = "UPDATE EXPRESSIONS SET root_id=:rootId WHERE id=:id;";
    public static String SELECT_EXPRESSIONS_WITH_SOLUTIONS_SQL = "SELECT * FROM EXPRESSIONS WHERE root_id IS NOT NULL;";
    public static String SELECT_EXPRESSIONS_WITH_SPECIFIC_SOLUTION_SQL = "SELECT * FROM EXPRESSIONS WHERE root_id=:rootId;";
}
