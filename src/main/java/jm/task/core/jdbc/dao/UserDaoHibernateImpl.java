package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();
    private Transaction transaction;


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


        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE_SQL).executeUpdate();
            transaction.commit();

            System.out.println("Таблица создана!");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();

            session.createNativeQuery(DROP_TABLE_SQL)
                    .executeUpdate();
            transaction.commit();

            System.out.println("Таблица удалена!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();

            User user = new User();
            user.setAge(age);
            user.setName(name);
            user.setLastName(lastName);

            session.save(user);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {

        try (Session session = SESSION_FACTORY.openSession()) {
            List<User> users = session.createQuery(FROM_TABLE_SQL, User.class).getResultList();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return List.of();
    }

    @Override
    public void cleanUsersTable() {

        try (Session session = SESSION_FACTORY.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(CLEAN_TABLE_SQL).executeUpdate();
            transaction.commit();

            System.out.println("Таблица очищена!");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
