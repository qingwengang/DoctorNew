package Stock.Dao.Doctor;

import Stock.Entity.Doctor.Mulu;
import Stock.Entity.Doctor.Question;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class QuestionDao extends BaseDao<Question>{

    public static List<String> GetProductIds(){
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session=sf.openSession();
        List<String> ls=new LinkedList<String>();
        List list=session.createSQLQuery("select DISTINCT askid from question").list();
        for(Iterator iterator =list.iterator();iterator.hasNext();){
            ls.add(iterator.next().toString());
        }
        session.close();
        return ls;
    }
}
