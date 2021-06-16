package calc.util;

import calc.model.CalcResult;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;

public class HibernateUtil {
    private static SessionFactory ourSessionFactory;

    private HibernateUtil() {}

    public static SessionFactory getOurSessionFactory() {
        if (ourSessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(CalcResult.class);
                configuration.configure();

                ourSessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return ourSessionFactory;
    }
}
