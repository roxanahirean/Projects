package com.utcn.roxana.dataAccess;

import com.utcn.roxana.connection.ConnectionFactory;
import com.utcn.roxana.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class ProductDA extends AbstractDA<Product> {

    public int setAmount(int id, int amount) {
        String str = "UPDATE PRODUCT SET AMOUNT = " + amount + " WHERE id = " + id;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(str);
            statement.executeUpdate();
        } catch(SQLException exception) {
            LOGGER.log(Level.WARNING, "UPDATE" + exception.getMessage());
            return 0;
        } finally {
            //ConnectionFactory.close(statement);
            //ConnectionFactory.close(connection);
        }
        return 1;
    }
}
