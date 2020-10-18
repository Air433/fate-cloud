package com.fate;

import com.alibaba.fastjson.JSON;
import com.fate.common.utils.FileUtil;
import com.fate.modules.sys.entity.SysRole;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

/**
 * @Author oyg
 * @Date 2018/8/19/12:21
 */
public class ListTest {

    @Test
    public void t1(){
        LinkedList linkedList = new LinkedList();
        linkedList.add(5);

        SysRole role = new SysRole();
         SysRole sysRole = role;

        role = null;

        System.err.println(sysRole);
        System.err.println(role);
        Iterator iterator = linkedList.iterator();

        iterator.hasNext();
        new ArrayList<>().iterator();
    }

    @Test
    public void t2() throws IOException {
        String username = "admin";
        String password = "admin";
         final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        String json = "{\"username\":\""+username+"\",\"password\":\""+password+"\"}";
        RequestBody body = RequestBody.create(JSON, json);
        String url = "http://localhost:8099/sys/login";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        System.err.println(response.body().toString());
    }
    @Test
    public void t3() throws IOException {
        File file = new File("D:\\work\\测试.txt");

        FileUtils.writeStringToFile(file, "睡觉", "UTF-8");
    }
    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
    public static void main(String[] args) throws IOException {
        //json 字符串
        String s = "{\"code\":10000,\"msg\":null,\"data\":{\"id\":\"7aa0eb56-1026-4497-a42e-4c39f5e3dcf1\",\"topicId\":\"0876ab84-a478-417b-91bc-849843c191a5\",\"title\":null,\"commentId\":null,\"content\":\"" +
                "开发者平台自动化测试：针对帖子发表评论" +
                "\",\"images\":\"\",\"time\":\"2017-10-15 18:09:56\",\"userId\":\"61028f94-de92-4c65-aad3-2fc8614e1d34\",\"userName\":\"devautotest\",\"commentNum\":0,\"status\":0}}";

        s= "{\n" +
                "  \"clientType\":\"WEB\",\n" +
                "  \"menu\":{\n" +
                "    \"code\":\"HDW\",\n" +
                "    \"name\":\"思创·仓储管理\",\n" +
                "    \"type\":\"MENU\",\n" +
                "    \"permissionCode\":\"\",\n" +
                "    \"menus\":[\n" +
                "      {\n" +
                "        \"code\":\"MPRODUCT\",\n" +
                "        \"name\":\"商品档案\",\n" +
                "        \"type\":\"MENU\",\n" +
                "        \"permissionCode\":\"PRODUCT\",\n" +
                "        \"menus\":[\n" +
                "          {\n" +
                "            \"code\":\"BRAND\",\n" +
                "            \"name\":\"品牌档案\",\n" +
                "            \"type\":\"MENU\",\n" +
                "            \"permissionCode\":\"BRAND\",\n" +
                "            \"menus\":[\n" +
                "              {\n" +
                "                \"code\":\"BRAND_CREATE\",\n" +
                "                \"name\":\"新建品牌\",\n" +
                "                \"type\":\"BUTTON\",\n" +
                "                \"permissionCode\":\"BRAND\",\n" +
                "                \"menus\":[\n" +
                "\n" +
                "                ]\n" +
                "              }\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      },\n" +
                "      {\n" +
                "        \"code\":\"MBUSINESS\",\n" +
                "        \"name\":\"业务管理\",\n" +
                "        \"type\":\"MENU\",\n" +
                "        \"permissionCode\":\"\",\n" +
                "        \"menus\":[\n" +
                "          {\n" +
                "            \"code\":\"OUTBOUND_ORDER\",\n" +
                "            \"name\":\"出库单管理\",\n" +
                "            \"type\":\"MENU\",\n" +
                "            \"permissionCode\":\"OUTBOUND_ORDER\",\n" +
                "            \"menus\":[\n" +
                "\n" +
                "            ]\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n";
        MenuVOBiz menuVOBiz = JSON.parseObject(s, MenuVOBiz.class);
       // System.err.println(JSON.toJSONString(menuVOBiz));
        s= JSON.toJSONString(menuVOBiz);
        int level = 0;
        //存放格式化的json字符串
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int index=0;index<s.length();index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = s.charAt(index);
//          System.out.println(s.charAt(index));

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
//                System.out.println("123"+jsonForMatStr);
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\r\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\r\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\r\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        System.out.println(jsonForMatStr);
        File file = new File("D:\\work\\测试.json");

        FileUtils.writeStringToFile(file, jsonForMatStr.toString(), "UTF-8");
    }

    @Test
    public void tclass(){
        System.err.println(this.getClass().getName());
    }
    @Test
    public void t9() throws IOException {
        String a = "D:\\abc.txt";
        FileUtil.saveFileByBytes(a, "abc".getBytes());
    }

    @Test
    public void t10(){
        String sql = "  SELECT  uPDATE dc.data_value AS '客户类别',os.site_full_name AS '装卸点全称' FROM tb_order o    INNER JOIN  `tm_operate_site` os ON os.site_code = o.dest_loc_id   INNER JOIN `tm_data_dictionary` dc ON dc.data_code = os.receiving_customer_type  WHERE o.id = #{sqlCond.orderId}; ";

        System.err.println(isValid(sql));

    }

    private boolean isValid(String str)
    {
        /**正则表达式**/
//         String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
//                + "(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
        String reg = "(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
                + "(\\b(update|delete|trancate|char|into|substr|ascii|declare|exec|master|drop|execute)\\b)";

       Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

        if (sqlPattern.matcher(str).find())
        {
            
            System.err.println("未能通过过滤器：str=" + str);
            return false;
        }
        return true;
    }

    @Test
    public void t11(){
//        ReentrantLock lock = new ReentrantLock();
//        lock.lock();
        int i = 0;
        System.err.println(i);

        for (int j = 0; j < 30; j++) {
            List list = null;
        }

    }

    @Test
    public void t12(){
        Double d = 0.00000000000000;

        System.err.println(d);
    }
}
