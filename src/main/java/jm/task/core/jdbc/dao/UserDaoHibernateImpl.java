package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();
    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS usrs( " +
            "id INTEGER GENERATED ALWAYS AS IDENTITY, " +
            "PRIMARY KEY (id)," +
            "name VARCHAR NOT NULL," +
            "lastName VARCHAR NOT NULL," +
            "age INTEGER NOT NULL) ";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS usrs";
    private static final String CLEAN_TABLE_SQL = "TRUNCATE TABLE usrs";
    private static final String FROM_TABLE_SQL = "FROM User";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = SESSION_FACTORY.openSession();
        try  {
            session.beginTransaction();

            session.createNativeQuery(CREATE_TABLE_SQL).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = SESSION_FACTORY.openSession();
        try {
            session.beginTransaction();

            session.createNativeQuery(DROP_TABLE_SQL)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = SESSION_FACTORY.openSession();
        try{
            session.beginTransaction();

            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setLastName(lastName);

            session.save(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = SESSION_FACTORY.openSession();
        try{

            session.beginTransaction();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = SESSION_FACTORY.openSession();
        try{
            session.beginTransaction();

            List<User> userList = session.createQuery(FROM_TABLE_SQL).getResultList();

            session.getTransaction().commit();
            return userList;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        Session session = SESSION_FACTORY.openSession();
        try{
            session.beginTransaction();
            session.createNativeQuery(CLEAN_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }
}
