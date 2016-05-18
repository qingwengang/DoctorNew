package Stock.Entity.Doctor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Administrator on 2016/5/11.
 */
@Entity
public class JiBing {
    private long Id;
    private String Name;
    private String Suoxie;
    private String Jianjie;
    private String Zhengzhuang;
    private String FBYY;
    private int GetContentFlag;
    private String Url;
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSuoxie() {
        return Suoxie;
    }

    public void setSuoxie(String suoxie) {
        Suoxie = suoxie;
    }

    public String getJianjie() {
        return Jianjie;
    }

    public void setJianjie(String jianjie) {
        Jianjie = jianjie;
    }

    public String getZhengzhuang() {
        return Zhengzhuang;
    }

    public void setZhengzhuang(String zhengzhuang) {
        Zhengzhuang = zhengzhuang;
    }

    public String getFBYY() {
        return FBYY;
    }

    public void setFBYY(String FBYY) {
        this.FBYY = FBYY;
    }

    public int getGetContentFlag() {
        return GetContentFlag;
    }

    public void setGetContentFlag(int getContentFlag) {
        GetContentFlag = getContentFlag;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
