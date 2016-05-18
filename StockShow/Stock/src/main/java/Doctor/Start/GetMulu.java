package Doctor.Start;

import Stock.Dao.Doctor.AnswerDao;
import Stock.Dao.Doctor.DoctorInfoDao;
import Stock.Dao.Doctor.MuluDao;
import Stock.Dao.Doctor.QuestionDao;
import Stock.Dao.StockInfoDao;
import Stock.Entity.Doctor.*;
import Stock.Entity.Doctor.Question;
import Stock.Entity.StockInfo;
import Util.BTree;
import Util.BTreeUtil;
import Util.FormatUtil;
import Util.JsoupUtil;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/5/6.
 */
public class GetMulu {
    private static Logger logger = Logger.getLogger(GetMulu.class);
    public static void main(String[] args) {
        getMulu();
//        getQuestionList("waike");
//        getQuestionContent("64727853");
//        SpiderQuestion();
    }
    public static void getMulu(){
        System.out.print("--------------------getmulu 开始----------------------");
        System.out.println("120 get mulu start");
        String url = "http://www.120ask.com/list/";
        Document doc = JsoupUtil.GetDocument(url);
        Elements lis=doc.select(".h-left");
        Element e=lis.get(0).getElementsByTag("ul").get(0);
        System.out.print(e);
        for(Element li : e.children()){
            System.out.print(li.text());
            System.out.print(li.children().get(0).attr("href"));
            Mulu mulu=new Mulu();
            mulu.setName(li.text());
            mulu.setUrl(li.children().get(0).attr("href"));
            mulu.setSpiderFlag(0);
            mulu.setLevel(0);
            mulu.setParentId(0);
            mulu.setSource("120ask");
            mulu.setType(mulu.getUrl().substring(27,mulu.getUrl().length()-1));
            MuluDao.Add(mulu);
        }
        getMuluByLevel(0);
        getMuluByLevel(1);
        getMuluByLevel(2);
        System.out.print("--------------------getmulu结束----------------------");
    }

    public static void SpiderQuestion(){
        String sql="select  * from question where GetContentFlag=0 limit 0,10";
        List<Question> questions=new QuestionDao().Query(sql);
        while(questions!=null && questions.size()>0){
            for(Question question : questions){
                getQuestionContent(question);
            }
            questions=new QuestionDao().Query(sql);
        }
    }

    public static void getQuestionContent(Question question){
        String url="http://www.120ask.com/question/"+question.getAskId()+".htm";
        Document doc = JsoupUtil.GetDocument(url);
        Element daohang=doc.select(".b_route").get(0);
        Elements daohangHrefs=daohang.getElementsByTag("a");
        for(Element href : daohangHrefs){
            if(href.attr("itemprop").equals("link")){
                String type=href.attr("href");
                String[] ts=type.split("/");
                question.setType(ts[4]);
            }
        }
        Element h1Title=doc.select("#d_askH1").get(0);
        question.setTitle(h1Title.text());
//        System.out.println(h1Title.text());
        Elements spans=doc.select(".b_askab1").get(0).children();
        String sex="";
        String laiyuan="";
        String sj="";
        for(Element span : spans){
            if(span.text().contains("|")){
                sex=span.text().trim();
            }else if(span.text().contains("来自")){
                laiyuan=span.text().trim();
            }else if(span.text().contains("人回复")){

            }else{
                sj=span.text().trim();
            }
        }
        question.setAuthorSex(sex);
        question.setLaiyuan(laiyuan);
        try{
            question.setAuthorTime(FormatUtil.GetDateTimeByString(sj));
        }catch (Exception e){
//            logger.error("question.setAuthorTime(FormatUtil.GetDateTimeByString(sj));"+question.getId(), e);
        }

//        spans.forEach(x-> System.out.println(x.text()));
        Element divContent=doc.select("#d_msCon").get(0);
        for(Element e : divContent.children()){
            if(e.text().startsWith("健康咨询描述")){
                question.setContent(e.text());
//                System.out.println(e.text());
            }
            if(e.text().startsWith("想得到的帮助：")){
                question.setNeedHelp(e.text());
//                System.out.println(e.text());
            }
        }
        String askauthortext="";
        try {
            Element askAuthor = doc.select(".ask_Author").get(0);
            askauthortext=askAuthor.text();
        }catch (Exception e){

        }
        question.setAuthorId(askauthortext);
        question.setGetContentFlag(1);
        QuestionDao.Update(question);
//        System.out.println(askAuthor.text());
//        System.out.println(divContent.text());
        //回答区
        Elements answerDivs=doc.select(".b_answerli");
        for(Element authDiv : answerDivs){
            if(authDiv.select(".b_sp1").size()>0){
                String docName=authDiv.select(".b_sp1").get(0).getElementsByTag("a").get(0).text();
                String docUrl=authDiv.select(".b_sp1").get(0).getElementsByTag("a").get(0).attr("href");
                String title=authDiv.select(".b_sp1").get(0).text().replace(docName,"");
//                System.out.print(docName);
                Elements sp2s=authDiv.select(".b_sp2");
                String expertise="";
                for(Element sp2 : sp2s){
                    if(sp2.text().startsWith("擅长:")){
                        expertise=sp2.text().substring(3);
                    }
                }
                String answerContent=authDiv.select(".b_anscont_cont").text();
                System.out.println(answerContent);
                Element eDate=authDiv.select(".b_anscont_time").get(0);
                Date answerTime=null;
                try{
                    answerTime=FormatUtil.GetDateTimeByString(eDate.text());
                }catch (Exception e){

                }
                System.out.println(eDate.text());
                try{
                    DoctorInfo doctorInfo=new DoctorInfo();
                    doctorInfo.setName(docName);
                    doctorInfo.setTitle(title);
                    doctorInfo.setExpertise(expertise);
                    if(!docUrl.contains("doctor")){
                        doctorInfo.setDocId(docUrl.split("/")[5]);
                    }else{
                        doctorInfo.setType(1);
                        doctorInfo.setDocId(docUrl.split("/")[4]);
                    }
                    if(!BTreeUtil.CheckIfExist("docId",doctorInfo.getDocId())){
                        new DoctorInfoDao().Add(doctorInfo);
                        BTreeUtil.AddValue("docId",doctorInfo.getDocId());
                    }
                    Answer answer=new Answer();
                    answer.setDoctorId(doctorInfo.getDocId());
                    answer.setQuestionId(question.getId());
                    answer.setContent(answerContent);
                    answer.setAnswerTime(answerTime);
                    new AnswerDao().Add(answer);
                }catch (Exception e){
                    logger.error("add doctorinfo error!" + question.getId() + "|" + docUrl, e);
                }

            }
        }
        //end 回答区
    }
    public static void getQuestionList(String type){
        String url="http://www.120ask.com/list/"+type+"/all/1";
        Document doc = JsoupUtil.GetDocument(url);
        Elements lis=doc.select(".q-quename");
        Element pagDiv=doc.select(".h-page").get(0);
        Elements pages=pagDiv.getElementsByTag("a");
        int pageCount=1;
        for(Element e : pages) {
            if (e.text().contains("最后一页")) {
                try {
                    String lastPageUrl = e.attr("href");
                    pageCount = Integer.parseInt(lastPageUrl.substring(lastPageUrl.indexOf("/all")+5,lastPageUrl.lastIndexOf("/")));
                } catch (Exception ex) {
                }
            }
        }
        for(int i=pageCount;i>=1;i--){
            url="http://www.120ask.com/list/"+type+"/all/"+i;
            doc = JsoupUtil.GetDocument(url);
            lis=doc.select(".q-quename");
            System.out.print(BTreeUtil.getBtree("askId").GetContents());
            for(Element li : lis){
                String questionUrl=li.attr("href");
                String askId=questionUrl.substring(questionUrl.lastIndexOf("/") + 1, questionUrl.lastIndexOf("."));
                if(!BTreeUtil.CheckIfExist("askId",askId)){
                    Question quesion=new Question();
                    quesion.setAskId(Long.parseLong(askId));
                    QuestionDao.Add(quesion);
                    BTreeUtil.AddValue("askId",askId);
                    System.out.print("askid"+askId+"add success ++++++++++++++");
                }{
                    System.out.print("askid"+askId+"already exists-------------");
                }
            }
        }

    }


    public static void getMulu(Mulu mulu) {
        Document doc = JsoupUtil.GetDocument(mulu.getUrl());
        Elements ul = doc.select(".h-ul1");
        if (ul != null && ul.size()>0) {
            for (Element li : ul.get(0).children()) {
                System.out.print(li.text());
                System.out.print(li.children().get(0).attr("href"));
                Mulu entity = new Mulu();
                entity.setName(li.text());
                entity.setUrl(li.children().get(0).attr("href"));
                entity.setSpiderFlag(0);
                entity.setLevel(mulu.getLevel() + 1);
                entity.setParentId(mulu.getId());
                entity.setSource("120ask");
                entity.setType(entity.getUrl().substring(27, entity.getUrl().length() - 1));
                MuluDao.Add(entity);
            }
        }
    }

    public static void getMuluByLevel(int level){
        List<Mulu> mulus=MuluDao.Query("select * from mulu where source='120ask' and level="+level);
        for(Mulu mulu : mulus){
            getMulu(mulu);
        }
    }
}
