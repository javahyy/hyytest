package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class RegexpTest {
	public static void main(String[] args) throws ParseException {
		String s1 = "2017-02-22";
		s1 = s1.replaceAll("[^0-9]", "");
		System.out.println(s1);

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		//Wed Feb 22 00:00:00 CST 2017 	 yyyyMMdd  M月      m秒 
		//Sun Jan 22 00:02:00 CST 2017   yyyymmdd
		Date s2 = sdf.parse(s1);
		
		System.out.println(s2);
		System.out.println(sdf.format(s2));
//		
//		Date date1 = new Date();
//		String string1 = sdf.format(date1);
//		System.out.println(string1);
//		System.out.println(sdf.format(sdf.parse(string1)));
//		
		String s4="20170322";
		System.out.println(s4.matches("\\d{8}"));
		
		//手机号
//		String s4="13085054652";
//		System.out.println(s4.matches("^1[3|4|5|7|8][0-9]{9}$"));

		//邮箱号
//		String s5="1308505@qq.cc";
//		System.out.println(s5.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"));

		
		//邮箱号
//		String s5="023-88888888";
//		System.out.println(s5.matches("\\d{3,4}\\-\\d{8}"));
		String  s = "500521199812303123";
		System.out.println(s.substring(16, 17));
		System.out.println(s.substring(6, 14));
		
//		int a=10;
//		System.out.println(Double.valueOf(a));
//		
//		String s2="123ab";
//		System.out.println(s2.toUpperCase());
		
		
//		String s6 = "21"; 
//		System.out.println(s6.matches("00||10||20||30||40||50||60||70||80||90||99"));
//		
//		//身份证校验
//		String  s7 = "500521199810303123";
//		//15位：[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}  18位如下：
//		System.out.println(s7.matches("[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)"));
//		
//		
//		String s8="3";
//		if(!(s8.matches("\\d{2}")||s8.matches("\\d{3}"))){//|| && 短路与 短路或
//			System.out.println("不满足2位数或者3位数");
//		}else{
//			System.out.println("通过(对比15位的身份证 与18位的身份证)");
//		}
		
//		String t1 = "210";
//		System.out.println(t1.matches("[1-5][0-7][0]"));
//		System.out.println(t1.matches("100|110|120|130|140|150|160|170|200|300|400|500"));
		
		
		String t3 = "500221199112303113";
		if(!(t3.matches("[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)")
				||t3.matches("[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}")
				)){
			System.out.println(t3.length()+"身份正格式错误");
		}else{
			System.out.println("身份证证件号码验证通过!!");
		}

		
		String t4="3";
		System.out.println(t4.matches("[1234]"));
		
		//注意double的范围
//		double d=12345678901;
		
		Integer i1 = 01;
		System.out.println(i1);//忽略了前面的 0 变成1
	
		String t5="9";
		System.out.println(t5.matches("0|1|2|3|4|5|6|8|9|C|K|L|X"));
		if(t5.length()>1|| !t5.matches("0|1|2|3|4|5|6|8|9|C|K|L|X")){
			System.out.println("不成立");
		}else{
			System.out.println("yyyy成立");
			
		}
	
	
	    String s8="100.2a";		
		System.out.println(s8.matches("[1-9](\\d+)(\\.\\d{1,2})"));
		
		
		//参考格式：国别号/地区号－区号－电话号码（－分机号）。  3-4位区号，7-8位直播号码，1－4位分机号   ?表示出现0次或一次
		String s9 = "123-12345678-2345";
		System.out.println("电话："+s9.matches("\\d{3,4}\\-\\d{7,8}([-0-9]{2,5})?"));
		
		
		System.out.println("保留两位小数 "+"0.22".matches("\\d+\\.\\d{2}"));
		
		
	}
	
	public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
        return pattern.matcher(str).matches();
    }
	
	
	
	
	
}
