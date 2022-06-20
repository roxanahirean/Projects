package com.utcn.roxana.dataAccess;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.utcn.roxana.connection.ConnectionFactory;
import com.utcn.roxana.model.Product;

public class AbstractDA<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDA.class.getName());

    private final Class<T> type;


    @SuppressWarnings("unchecked")
    public AbstractDA() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append("tema3tp." + type.getSimpleName());
        sb.append(" WHERE " + field + " =");
        return sb.toString();
    }

    private String createSelectAll() {
        StringBuilder s = new StringBuilder();
        s.append("SELECT ");
        s.append(" * ");
        s.append(" FROM ");
        s.append(type.getSimpleName());
        return s.toString();
    }

    public List<T> findAll() {
        String findAllString = new String("SELECT * FROM ");
        findAllString = findAllString + "tema3tp." + type.getSimpleName().toLowerCase();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllString);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);

        } catch (SQLException  | NullPointerException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DA:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id") + id;
        List<Product> list = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            //statement.setInt(1, id);
            resultSet = statement.executeQuery();

            list = (List<Product>) createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DA:findById " + e.getMessage());
        } finally {
            //ConnectionFactory.close(resultSet);
            //ConnectionFactory.close((Connection) statement);
            //ConnectionFactory.close(connection);
        }
        return (T) list.get(0);
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                assert ctor != null;
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String createInsertQuery(int l, Object[] args, String[] fields){
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName() +"(");
        for( int i = 0 ; i < fields.length; ++i){
            sb.append(fields[i]);
            if(i != fields.length-1){
                sb.append(",");
            }
        }
        sb.append(") VALUES(");
        for( int i = 0 ; i < args.length; ++i){
            sb.append("?");
            if(i != fields.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public T insert(T t) {
        String s = "INSERT INTO " +  "tema3tp." + type.getSimpleName() + " VALUES ( ";
        int i = 0;
        for (Field field : type.getDeclaredFields()) {
            i++;
            String fieldName = field.getName();
            for (Method method : type.getMethods()) {
                try{
                    if(method.getName()=="getId")
                        if(verificareID((Integer) method.invoke(t)) == 1) {
                            LOGGER.log(Level.WARNING, "Inserare - exista deja un client cu acest ID");
                            return null;
                        }
                    if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)))
                        if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                            s = s + "'" + method.invoke(t) + "'";
                            if (i < type.getDeclaredFields().length)
                                s = s + ", ";
                            else {
                                s = s + " )";
                            }
                        }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(s);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DA:INSERT " + e.getMessage());
            return null;
        } finally {
            System.out.println(s);
            //ConnectionFactory.close(statement);
            //ConnectionFactory.close(connection);
        }
        return t;
    }


    public int verificareID(int id){
        List<T> l = findAll();
        if(l.size() != 0)
            for(T t: l)
                for (Method method : type.getMethods())
                    if (method.getName() =="getId")
                        try {
                            if (method.invoke(t).equals(id))
                                return 1;
                        }catch (Exception e){
                            System.out.println("Illegal Acces - identificare id\n");
                        }
        return 0;
    }



    public T update(T t) {
        int id = 0;
        for (Field field : type.getDeclaredFields()) {
            String s = "Update " + type.getSimpleName() + " SET ";
            String fieldName = field.getName();
            if (fieldName.equals("id")) {
                for (Method method : type.getMethods()) {
                    try {
                        if (method.getName().startsWith("getId")) {
                            id = (Integer) method.invoke(t);
                            if (verificareID(id) == 0) {
                                LOGGER.log(Level.WARNING, "Update - Nu a fost gasit niciun client cu acest ID");
                                return null;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else{
                for (Method method : type.getMethods()) {
                    if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)))
                        if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                            try {
                                s = s + fieldName + " = " + "'" + method.invoke(t) + "'" + " WHERE id = " + id;
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                }
                PreparedStatement statement = null;
                ResultSet resultSet = null;
                Connection connection = null;
                try {
                    connection = ConnectionFactory.getConnection();
                    statement = connection.prepareStatement(s);
                    statement.executeUpdate();
                } catch (SQLException e) {
                    LOGGER.log(Level.WARNING, type.getName() + "DA:UPDATE " + e.getMessage());
                } finally {
                    System.out.println(s);
                    ConnectionFactory.close(statement);
                    ConnectionFactory.close(connection);
                }
            }}
        return t;
    }


    public int delete(int id) {
        String s = "DELETE FROM " + type.getSimpleName() + " WHERE id = " +id;
        if(verificareID(id) == 0) {
            LOGGER.log(Level.WARNING, type.getName() + "Delete - nu a fot gasita nicio persoana cu acest id");
            return 0;
        }
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(s);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DA:Delete " + e.getMessage());
            return 0;
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 1;
    }


    public String createUpdateQuery(int l, Object[] args, String[] fields){
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        for(int i = 0; i < fields.length; ++i) {
            sb.append(fields[i]);
            if(i != fields.length - 1)
                sb.append("=?");
        }
        return sb.toString();
    }


}

