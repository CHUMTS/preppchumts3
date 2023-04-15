package jm.task.core.jdbc.dao;
import jakarta.persistence.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createNativeQuery("CREATE TABLE IF NOT EXISTS userstable (id bigint AUTO_INCREMENT, name varchar(20), lastName varchar(20), age tinyint, primary key (id))");
            query.executeUpdate();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createNativeQuery("DROP TABLE IF EXISTS userstable");
            query.executeUpdate();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.persist(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (Exception hql) {
            session.getTransaction().rollback();
            hql.printStackTrace();
        } finally {
            session.close();
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception hql) {
            session.getTransaction().rollback();
            hql.printStackTrace();
        } finally {
            session.close();
        }
    }


    @Override
    public List<User> getAllUsers() {
        List<User> result = null;
        try {
            session = Util.getSessionFactory().openSession();
            result = session.createQuery("FROM User", User.class).list();
        } catch (Exception hql) {
            session.getTransaction().rollback();
            hql.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
    @Override
    public void cleanUsersTable () {
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception hql) {
            session.getTransaction().rollback();
            hql.printStackTrace();
        } finally {
            session.close();
        }
    }
}

