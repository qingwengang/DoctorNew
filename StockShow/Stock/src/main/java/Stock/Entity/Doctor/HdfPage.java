package Stock.Entity.Doctor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/12.
 */
@Entity
public class HdfPage {
    private long Id;
    private String Riqi;
    private String Url;
    private int SpiderFlag;
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getRiqi() {
        return Riqi;
    }

    public void setRiqi(String riqi) {
        Riqi = riqi;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public int getSpiderFlag() {
        return SpiderFlag;
    }

    public void setSpiderFlag(int spiderFlag) {
        SpiderFlag = spiderFlag;
    }
}
