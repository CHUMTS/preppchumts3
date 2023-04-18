package jm.task.core.jdbc.dao;

import jakarta.persistence.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session sessionVar = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            sessionVar = session;
            sessionVar.beginTransaction();
            Query query = sessionVar.createNativeQuery("CREATE TABLE IF NOT EXISTS userstable (id bigint AUTO_INCREMENT, name varchar(20), lastName varchar(20), age tinyint, primary key (id))");
            query.executeUpdate();
            sessionVar.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            sessionVar = session;
            sessionVar.beginTransaction();
            Query query = sessionVar.createNativeQuery("DROP TABLE IF EXISTS userstable");
            query.executeUpdate();
            sessionVar.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            sessionVar = session;
            sessionVar.beginTransaction();
            sessionVar.persist(new User(name, lastName, age));
            sessionVar.getTransaction().commit();
        } catch (Exception hql) {
            sessionVar.getTransaction().rollback();
            hql.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            sessionVar = session;
            sessionVar.beginTransaction();
            sessionVar.remove(sessionVar.get(User.class, id));
            sessionVar.getTransaction().commit();
        } catch (Exception hql) {
            sessionVar.getTransaction().rollback();
            hql.printStackTrace();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try (Session session = Util.getSessionFactory().openSession()) {
            result = session.createQuery("FROM User", User.class).list();
        } catch (Exception hql) {
            hql.printStackTrace();
        }
        return result;
    }
    @Override
    public void cleanUsersTable () {
        try (Session session = Util.getSessionFactory().openSession()) {
            sessionVar = session;
            sessionVar.beginTransaction();
            Query query = sessionVar.createQuery("DELETE FROM User");
            query.executeUpdate();
            sessionVar.getTransaction().commit();
        } catch (Exception hql) {
            sessionVar.getTransaction().rollback();
            hql.printStackTrace();
        }
    }
}

