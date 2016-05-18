package Stock.Entity.Doctor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/7.
 */
@Entity
public class Question {
    private long Id;
    private String Title;
    private String Content;
    private String Type;
    private int GetContentFlag;
    private long AskId;
    private String Description;
    private String NeedHelp;
    private String AuthorId;
    private Date AuthorTime;
    private Date LastSpiderTime;
    private String AuthorSex;
    private String Laiyuan;
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getGetContentFlag() {
        return GetContentFlag;
    }

    public void setGetContentFlag(int getContentFlag) {
        GetContentFlag = getContentFlag;
    }

    public long getAskId() {
        return AskId;
    }

    public void setAskId(long askId) {
        AskId = askId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getNeedHelp() {
        return NeedHelp;
    }

    public void setNeedHelp(String needHelp) {
        NeedHelp = needHelp;
    }

    public String getAuthorId() {
        return AuthorId;
    }

    public void setAuthorId(String authorId) {
        AuthorId = authorId;
    }

    public Date getAuthorTime() {
        return AuthorTime;
    }

    public void setAuthorTime(Date authorTime) {
        AuthorTime = authorTime;
    }

    public Date getLastSpiderTime() {
        return LastSpiderTime;
    }

    public void setLastSpiderTime(Date lastSpiderTime) {
        LastSpiderTime = lastSpiderTime;
    }

    public String getAuthorSex() {
        return AuthorSex;
    }

    public void setAuthorSex(String authorSex) {
        AuthorSex = authorSex;
    }

    public String getLaiyuan() {
        return Laiyuan;
    }

    public void setLaiyuan(String laiyuan) {
        Laiyuan = laiyuan;
    }
}
