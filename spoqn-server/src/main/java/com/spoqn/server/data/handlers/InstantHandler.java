package com.spoqn.server.data.handlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class InstantHandler implements TypeHandler<Instant> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Instant parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Timestamp.from(parameter));
    }

    @Override
    public Instant getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getTimestamp(columnName).toInstant();
    }

    @Override
    public Instant getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getTimestamp(columnIndex).toInstant();
    }

    @Override
    public Instant getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getTimestamp(columnIndex).toInstant();
    }

}
