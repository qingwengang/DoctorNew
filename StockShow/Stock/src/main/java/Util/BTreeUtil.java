package Util;

import Stock.Dao.Doctor.BaseDao;
import Stock.Dao.Doctor.QuestionDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/7.
 */
public class BTreeUtil {
    private static Map<String,BTree> bTrees=new HashMap<String,BTree>();
    public static BTree getBtree(String name){
        if(bTrees.containsKey(name)){
            return bTrees.get(name);
        }else {
            BTree bTree=new BTree();
            bTrees.put(name,bTree);
            InitBtree(name,bTree);
            return bTree;
        }
    }
    private static void InitBtree(String name,BTree btree){
        String tableName="";
        String columnName="";
        if(name.equals("askId")){
            columnName="askid";
            tableName="question";
        }else if(name.equals("docId")){
            columnName="docid";
            tableName="doctorinfo";
        }
        List<String> ls=BaseDao.GetOneColumn(columnName,tableName);
        for(String id : ls){
            btree.AddString(id);
        }
    }
    public static void AddValue(String name,String value){
        getBtree(name).AddString(value);
    }
    public static boolean CheckIfExist(String name,String value){
        return getBtree(name).Check(value);
    }
}
