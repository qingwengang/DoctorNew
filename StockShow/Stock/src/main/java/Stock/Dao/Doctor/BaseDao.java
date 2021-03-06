package Stock.Dao.Doctor;

import Stock.Entity.Doctor.Mulu;
import Stock.Entity.StockInfo;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class BaseDao<T> {
    private Class<T> entityClass;
    public BaseDao() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }
    public static <T> void Add(T entity) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
        session.close();
    }
    public  <T> List<T> Query(String sql) {
        List l;
        List<T> result=new LinkedList<T>();
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session=sf.openSession();
        session.beginTransaction();
        l=session.createSQLQuery(sql).addEntity(entityClass).list();
        for (Object o : l) {
            result.add((T)o);
        }
        session.getTransaction().commit();
        session.close();
        return result;
    }
    public static <T> void Update(T entity){
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session=sf.openSession();
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
        session.close();
    }
    public static List<String> GetOneColumn(String columnName,String tableName){
        SessionFactory sf=HibernateUtil.getSessionFactory();
        Session session=sf.openSession();
        List<String> ls=new LinkedList<String>();
        List list=session.createSQLQuery(String.format("select DISTINCT %s from %s",columnName,tableName)).list();
        for(Iterator iterator =list.iterator();iterator.hasNext();){
            ls.add(iterator.next().toString());
        }
        session.close();
        return ls;
    }
}
