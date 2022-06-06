package com.example.consensus;

import lombok.SneakyThrows;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomGenerator implements IdentifierGenerator {
    @SneakyThrows
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj)  throws HibernateException {
        Connection connection = session.connection();
        Statement statement = connection.createStatement();

        String tableName = "Users";

        String sql = "SELECT * FROM " + tableName + " ORDER BY id DESC LIMIT 1;";
        ResultSet rs = statement.executeQuery(sql);
        Long id = 0L;

        if(rs.next()) {
            id = rs.getLong("id");
        }
        id += 1;
        return id;
    }
}
