package io.vital.mathematicalhelper.repository.implementation;

import io.vital.mathematicalhelper.model.Expression;
import io.vital.mathematicalhelper.model.Root;
import io.vital.mathematicalhelper.repository.ExpressionRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import io.vital.mathematicalhelper.mapper.ExpressionMapper;

import static io.vital.mathematicalhelper.queries.ExpressionQueries.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ExpressionRepositoryImpl implements ExpressionRepository {
    private NamedParameterJdbcTemplate jdbc;

    public ExpressionRepositoryImpl(NamedParameterJdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    @Override
    public void setExpressionRoot(Long id, Long rootId) {
        jdbc.update(SET_EXPRESSION_ROOT_SQL, Map.of("rootId", rootId, "id", id));
    }

    @Override
    public Long createExpression(String expression) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(CREATE_EXPRESSION_SQL, new MapSqlParameterSource().addValue("expressionText", expression), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Expression> findExpressionsByRoot(Root root) {
        return jdbc.query(SELECT_EXPRESSIONS_WITH_SPECIFIC_SOLUTION_SQL, Map.of("rootId", root.getId()), new ExpressionMapper());
    }

    @Override
    public List<Expression> findExpressionsWithSolution() {
        return jdbc.query(SELECT_EXPRESSIONS_WITH_SOLUTIONS_SQL, new ExpressionMapper());
    }
}
