package io.vital.mathematicalhelper.mapper;

import io.vital.mathematicalhelper.model.Root;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RootMapper implements RowMapper<Root> {
    @Override
    public Root mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Root(rs.getLong("id"), rs.getDouble("value"));
    }
}
