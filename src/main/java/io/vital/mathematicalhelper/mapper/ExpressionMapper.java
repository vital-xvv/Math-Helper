package io.vital.mathematicalhelper.mapper;

import io.vital.mathematicalhelper.model.Expression;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpressionMapper implements RowMapper<Expression> {
    @Override
    public Expression mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Expression(rs.getString("expression_text"), rs.getLong("root_id"), rs.getLong("id"));
    }
}
