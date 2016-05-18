package Doctor.Start;

import Stock.Dao.Doctor.JiBingDao;
import Stock.Entity.Doctor.JiBing;
import Util.JsoupUtil;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class GetJibing {
    private static Logger logger = Logger.getLogger(GetJibing.class);
    public static void main(String[] args) {
//        GetMulu();
        GetContent();
    }
    private static void GetMulu(){
        for(int i=0;i<=808;i++){
            GetMulu(i);
        }
    }
    private static void GetMulu(int page){
        String url="http://jbk.39.net/bw_t1_p"+page+"#ps";
        Document doc = JsoupUtil.GetDocument(url);
        Element eDiv=doc.select("#res_tab_2").get(0);
        Elements edivs=eDiv.select(".res_list");
        for(Element e : edivs){
            JiBing jb=new JiBing();
            jb.setName(e.getElementsByTag("h3").get(0).getElementsByTag("a").get(0).text());
            jb.setUrl(e.getElementsByTag("h3").get(0).getElementsByTag("a").get(0).attr("href"));
            new JiBingDao().Add(jb);
        }
    }

    private static void GetContent(){
        List<JiBing> ls=new JiBingDao().Query("select * from jibing where GetContentFlag=0 LIMIT 0,10");
        while(ls!=null && ls.size()>0){
            for(JiBing jb : ls){
                GetContent(jb);
            }
            ls=new JiBingDao().Query("select * from jibing where GetContentFlag=0 LIMIT 0,10");
        }

    }
    private static void GetContent(JiBing jb){
        try {
            String url=jb.getUrl()+"jbzs/";
            Document doc = JsoupUtil.GetDocument(url);
            String intro=doc.select(".intro").get(0).getElementsByTag("dd").get(0).text();
            url=jb.getUrl()+"zztz/";
            doc = JsoupUtil.GetDocument(url);
            String zhengzhuang=doc.select(".art-box").get(0).html();
            url=jb.getUrl()+"blby/";
            doc = JsoupUtil.GetDocument(url);
            String FBYY=doc.select(".art-box").get(0).html();
            jb.setJianjie(intro);
            jb.setZhengzhuang(zhengzhuang);
            jb.setFBYY(FBYY);
            jb.setGetContentFlag(1);
            JiBingDao.Update(jb);
        }catch (Exception e){
            logger.error(e);
        }

    }
}
