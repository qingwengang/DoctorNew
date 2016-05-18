package Stock.Entity.Doctor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

/**
 * Created by Administrator on 2016/5/10.
 */
@Entity
public class Answer {
    private long Id;
    private String DoctorId;
    private long QuestionId;
    private  String Content;
    private Date AnswerTime;
    @javax.persistence.Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getDoctorId() {
        return DoctorId;
    }

    public void setDoctorId(String doctorId) {
        DoctorId = doctorId;
    }

    public long getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(long questionId) {
        QuestionId = questionId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Date getAnswerTime() {
        return AnswerTime;
    }

    public void setAnswerTime(Date answerTime) {
        AnswerTime = answerTime;
    }
}
