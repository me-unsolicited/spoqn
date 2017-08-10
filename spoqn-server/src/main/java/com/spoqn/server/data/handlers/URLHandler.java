package com.spoqn.server.data.handlers;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import com.spoqn.server.core.exceptions.SpoqnException;

@MappedTypes(URL.class)
public class URLHandler implements TypeHandler<URL> {

    private static final String MSG_PARSE_ERROR = "Unable to parse URL from result set [%s]";

    @Override
    public void setParameter(PreparedStatement ps, int i, URL parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.toString());
    }

    @Override
    public URL getResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }

    @Override
    public URL getResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public URL getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private URL parse(String spec) {
        try {
            return spec == null ? null : new URL(spec);
        } catch (MalformedURLException e) {
            throw new SpoqnException(String.format(MSG_PARSE_ERROR, spec), e);
        }
    }
}
