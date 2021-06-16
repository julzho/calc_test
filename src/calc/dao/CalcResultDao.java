package calc.dao;

import calc.model.CalcResult;
import calc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CalcResultDao {

    public void save(CalcResult calcResult) {
        Session session = HibernateUtil.getOurSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        calcResult.setId((int) HibernateUtil.getOurSessionFactory().openSession().createQuery("select max(id) + 1 from CalcResult ").setMaxResults(1).uniqueResult());
        session.save(calcResult);
        tx.commit();
        session.close();
    }

    public List<CalcResult> getHistory(int limit) {
        List<CalcResult> calcResults = (List<CalcResult>)  HibernateUtil.getOurSessionFactory().openSession().createQuery("From CalcResult ORDER BY resultDate desc ").setMaxResults(limit).list();
        return calcResults;
    }
}
