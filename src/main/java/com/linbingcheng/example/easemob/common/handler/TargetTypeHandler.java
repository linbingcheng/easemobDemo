package com.linbingcheng.example.easemob.common.handler;

import com.linbingcheng.example.easemob.common.enumtype.TargetType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by bingchenglin on 2016/12/15.
 */
public class TargetTypeHandler extends BaseTypeHandler<TargetType> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TargetType parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public TargetType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return TargetType.fromTo(rs.getInt(columnName));
    }

    @Override
    public TargetType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return TargetType.fromTo(rs.getInt(columnIndex));
    }

    @Override
    public TargetType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return TargetType.fromTo(cs.getInt(columnIndex));
    }
}
