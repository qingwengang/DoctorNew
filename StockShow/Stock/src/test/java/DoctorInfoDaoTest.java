import Stock.Dao.Doctor.DoctorInfoDao;
import Stock.Entity.Doctor.DoctorInfo;

import javax.print.Doc;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class DoctorInfoDaoTest {
    public static void main(String[] a){
//        DoctorInfo info=new DoctorInfo();
//        info.setName("qwg");
//        info.setDocId("fds");
//        info.setExpertise("fdsfs");
//        DoctorInfoDao.Add(info);
        List<DoctorInfo> ls=new DoctorInfoDao().Query("select * from DoctorInfo");
        System.out.print(ls.get(0).getName());
    }
}
