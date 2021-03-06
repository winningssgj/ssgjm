package cn.com.winning.ssgj.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenshijie
 * @title 字符串工具类
 * @email chensj@winning.com.cm
 * @package cn.com.winning.ssgj.base.util
 * @date 2018-02-08 20:08
 */
public class StringUtil {

    /**
     * ID字符串生成动态SQL 实例 156,1;156,2
     * @param idList  ID字符串，使用分号作为分隔符，使用逗号作为ID分隔符
     * @param firstColName 第一个ID列名
     * @param lastColName  第二个ID列名
     * @return
     */
    public  static  String  generateSqlString(String idList,String firstColName,String lastColName){
        String[] objectList = idList.split(";");
        StringBuilder idString = new StringBuilder();
        idString.append("AND ( ");
        for (int i=0;i < objectList.length ; i++) {
            String[] strArr = objectList[i].split(",");
            if( i == objectList.length -1  ) {
                idString.append("( "+firstColName+" =" + strArr[0] + " and "+lastColName+" = " + strArr[1] + ") ");
            }else{
                idString.append("( "+firstColName+" =" + strArr[0] + " and "+lastColName+" = " + strArr[1] + ") or ");
            }
        }
        idString.append(" ) ");
        return  idString.toString();
    }
    /**
     * ID字符串生成动态SQL 实例 156,1;156,2
     * @param idList  ID字符串，使用分号作为分隔符，使用逗号作为ID分隔符
     * @param firstColName 第一个ID列名
     * @return
     */
    public  static  String  generateDeleteSqlString(String idList,String firstColName){
        String[] objectList = idList.split(";");
        StringBuilder idString = new StringBuilder();
        idString.append(firstColName+" IN ( ");
        for (int i=0;i < objectList.length ; i++) {
            String[] strArr = objectList[i].split(",");
            if( i == objectList.length -1  ) {
                idString.append( strArr[0]  );
            }else{
                idString.append( strArr[0] + " , ");
            }
        }
        idString.append(" ) ");
        return  idString.toString();
    }

    /**
     * 检查字符串是否为NULL或“”
     * @param s
     * @return
     */
    public static boolean isEmptyOrNull(String s){
        return isEmpty(s)||isNull(s);
    }

    /**
     * 检查字符串是否为""
     * @param s
     * @return
     */
    public static boolean isEmpty(String s){
        return "".equals(s);
    }

    /**
     * 检查字段是否为Null
     * @param s
     * @return
     */
    public static boolean isNull(String s){
        return s==null;
    }

    /**
     * 检查是否是空对象
     * @param obj
     * @return
     */
    public static boolean isNullOrNullString(Object obj) {

        return obj == null || obj.toString().equals("")||obj.toString().equals("null")||obj.toString().equals("undefined");
    }

    /**
     * 填充字符串
     * @param str
     * @param length
     * @param c
     * @return
     */
    public static String fillStr(String str,int length,char c){
        if(str==null){
            str = "";
        }
        if(str.length()>length){
            return str.substring(0, length);
        }
        int oldLength = str.length();
        for(int i=oldLength;i<length;i++){
            str += c;
        }
        return str;
    }
    /**
     * 在字符串上添加左右两侧指定字符串c
     * @param str 原字符串
     * @param left 左侧添加几位
     * @param right 右侧添加几位
     * @param c 添加的字符串
     * @return
     */
    public static String fillStrForCore(String str,int left,int right,char c){
        if(str==null){
            str = "";
        }
        String lstr = "";
        for(int i = 0;i < left ; i++){
            lstr += c;
        }
        str = lstr + str;
        for(int i = 0;i < right ; i++){
            str += c;
        }
        return str;
    }

    /**
     * 将字符串中括号和短线半角转为全角
     * @param str
     * @return
     */
    public static String replaceAllSpecificSign(String str){
        if(str!=null){
            str=str.trim();
            try{
                str=replaceParentheses(str);
                str=replaceDash(str);
            }catch(Exception e){

            }
        }
        return str;
    }

    /**
     * 装换字符中括号字符由半角转为全角,()
     * @param text
     * @return
     */
    public static String replaceParentheses(String text){
        String regex= "\\(";
        Pattern pattern =Pattern.compile(regex);
        Matcher match= pattern.matcher(text);
        while(match.find()){
            text=text.replace("(", "（");
            match= pattern.matcher(text);
        }

        regex="\\)";
        pattern =Pattern.compile(regex);
        match= pattern.matcher(text);
        while(match.find()){
            text=text.replace(")", "）");
            match= pattern.matcher(text);
        }
        return text;
    }

    /**
     * 装换字符中短线字符由半角转为全角, -
     * @param text
     * @return
     */
    public static String replaceDash(String text){
        String regex="--";
        Pattern pattern =Pattern.compile(regex);
        Matcher match= pattern.matcher(text);

        while(match.find()){
            text=text.replace(regex, "－");
            match= pattern.matcher(text);
        }

        regex="-";
        pattern =Pattern.compile(regex);
        match= pattern.matcher(text);
        while(match.find()){
            text=text.replace(regex, "－");
            match= pattern.matcher(text);
        }

        regex="——";
        pattern =Pattern.compile(regex);
        match= pattern.matcher(text);
        while(match.find()){
            text=text.replace(regex, "－");
            match= pattern.matcher(text);
        }

        regex="—";
        pattern =Pattern.compile(regex);
        match= pattern.matcher(text);
        while(match.find()){
            text=text.replace(regex, "－");
            match= pattern.matcher(text);
        }

        regex="－－";
        pattern =Pattern.compile(regex);
        match= pattern.matcher(text);
        while(match.find()){
            text=text.replace(regex, "－");
            match= pattern.matcher(text);
        }

        return text;
    }

    public static List<String> compareStringWithList(String idList, List<String> idsList) {
        String[] objectList = idList.split(";");
        List<String> resIdList = new ArrayList<String>();
        List<String> addIdList = new ArrayList<String>();
        for (String s : objectList) {
            resIdList.add(s);
        }

        for (int i = 0; i < idsList.size(); i++) {
            for (int j = 0; j < resIdList.size(); j++) {
                if(idsList.get(i).equals(resIdList.get(j))){
                    resIdList.remove(j);
                }
            }
        }

        return  resIdList;
    }

    public static List<String> generateStringList(String idList) {
        String[] objectList = idList.split(";");
        List<String> resIdList = new ArrayList<String>();
        for (String s : objectList) {
            resIdList.add(s);
        }
        return  resIdList;
    }


    public static String generateString(Set<String> strings) {
        StringBuilder sb = new StringBuilder();
        String[] strs = new String[strings.size()];
        strings.toArray(strs);
        for (int i = 0; i < strs.length; i++) {
            if(i == strs.length-1){
                sb.append(strs[i]);
            }else{
                sb.append(strs[i]+",");
            }
        }
        return  sb.toString();
    }



    public static String generateStringSql(List<Long> idLists){
        StringBuilder sb = new StringBuilder();
        sb.append(" (");
        for (int i = 0; i < idLists.size(); i++) {
            if( i == idLists.size() -1){
                sb.append( idLists.get(i) + "");
            }else{
                sb.append( idLists.get(i) + ",");
            }
        }
        sb.append(" )");
        return sb.toString();
    }

    public static void main(String[] args){
       String idList = "156,1;156,2";
       List<String> idsList = new ArrayList<String>();
       idsList.add("156,1");
       idsList = compareStringWithList(idList,idsList);
        for (String s : idsList) {
            System.out.println(s);
        }
    }
}
