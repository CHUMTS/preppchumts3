package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties xml = new Properties();
                xml.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                xml.put(Environment.URL, "jdbc:mysql://localhost:3306/usersschema");
                xml.put(Environment.USER, "root");
                xml.put(Environment.PASS, "root");
                xml.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                xml.put(Environment.SHOW_SQL, "true");
                xml.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                configuration.setProperties(xml);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static Connection getConnecton() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/usersschema", "root", "root");
    }

}
