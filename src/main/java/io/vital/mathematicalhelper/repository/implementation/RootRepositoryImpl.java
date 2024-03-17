package io.vital.mathematicalhelper.repository.implementation;

import io.vital.mathematicalhelper.mapper.RootMapper;
import io.vital.mathematicalhelper.model.Root;
import io.vital.mathematicalhelper.repository.RootRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;

import static io.vital.mathematicalhelper.queries.RootQueries.*;

@Repository
public class RootRepositoryImpl implements RootRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public RootRepositoryImpl(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long createRoot(double value) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(CREATE_ROOT_QUERY, new MapSqlParameterSource().addValue("value", value), keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public Root findRootByValue(double value) {
        return jdbc.queryForObject(FIND_ROOT_BY_ITS_VALUE_QUERY, Map.of("value", value), new RootMapper());
    }
}
