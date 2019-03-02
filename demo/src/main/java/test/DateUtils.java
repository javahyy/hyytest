package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    // 将 2017-03-12 日期转换成 20170312格式 （去掉横杠） MM月份  mm秒数
    public static Date formatDate(Date date) {
        Date date2 = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String date1 = sdf.format(date);
            date2 = sdf.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }


    //Date 与Calendar 的转换
    public static void testDateAdd() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 12);
        System.out.println(calendar.getTime());
    }

    public static void main(String[] args) {
        testDateAdd();
    }
}
