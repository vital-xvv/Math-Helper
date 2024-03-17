package io.vital.mathematicalhelper.repository;

import io.vital.mathematicalhelper.model.Expression;
import io.vital.mathematicalhelper.model.Root;

import java.util.List;

public interface ExpressionRepository {
    void setExpressionRoot(Long id, Long rootId);
    Long createExpression(String expression);
    List<Expression> findExpressionsByRoot(Root root);
    List<Expression> findExpressionsWithSolution();
}
