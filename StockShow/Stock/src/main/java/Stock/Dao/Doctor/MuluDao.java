package Stock.Dao.Doctor;

import Stock.Entity.Doctor.Mulu;
import Stock.Entity.FiveDang;
import Stock.Entity.Ticai;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class MuluDao {
    public static void Add(Mulu entity){
        List<Mulu> mulus=Query("select * from mulu where url='" + entity.getUrl() + "'");
        if(mulus==null || mulus.size()==0){
            SessionFactory sf= HibernateUtil.getSessionFactory();
            Session session=sf.openSession();
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            session.close();
        }
    }
    public static List<Mulu> Query(String sql) {
        List l;
        List<Mulu> result=new LinkedList<Mulu>();
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session=sf.openSession();
        session.beginTransaction();
        l=session.createSQLQuery(sql).addEntity(Mulu.class).list();
        for (Object o : l) {
            result.add((Mulu)o);
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
