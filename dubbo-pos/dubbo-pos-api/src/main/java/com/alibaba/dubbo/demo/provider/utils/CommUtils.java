package com.alibaba.dubbo.demo.provider.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

public class CommUtils {

	public static final boolean DEPLOY_SERVER = !((System.getProperties().getProperty("os.name"))
			.indexOf("Windows") >= 0);

	/**
	 * 数据copy，主要是为数据库更新准备的copy方法。source为仅有target中的一部分更新数据
	 * 为了不影响target中不需要更新的数据，只拷贝source中不为空的数据
	 * 
	 * @param target
	 * @param source
	 */
	@SuppressWarnings("rawtypes")
	public static void beanCopy(Object target, Object source) {
		Field[] fields = source.getClass().getDeclaredFields();
		Class targetClz = target.getClass();
		Class sourceClz = source.getClass();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			Field targetField = null;
			// 得到targetClz对象所表征的类的名为fieldName的属性，不存在就进入下次循环
			try {
				targetField = targetClz.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {

			}
			if (fields[i].getType() == targetField.getType()) {

				// 由属性名字得到对应get和set方法的名字
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

				// 由方法的名字得到get和set方法的Method对象

				Method getMethod = null;
				Method setMethod = null;
				try {

					try {
						getMethod = sourceClz.getDeclaredMethod(getMethodName, new Class[] {});
					} catch (NoSuchMethodException e) {
					}
					try {
						setMethod = targetClz.getDeclaredMethod(setMethodName, fields[i].getType());

					} catch (NoSuchMethodException e) {
					}

					// 调用source对象的getMethod方法

					Object result = getMethod.invoke(source, new Object[] {});

					// 调用target对象的setMethod方法
					if (result != null && !"".equals(result.toString())) {
						setMethod.invoke(target, result);
					}

				} catch (SecurityException e) {
					e.printStackTrace();

				} catch (IllegalArgumentException e) {
					e.printStackTrace();

				} catch (IllegalAccessException e) {
					e.printStackTrace();

				} catch (InvocationTargetException e) {
					e.printStackTrace();

				}

			} else {
				// 同属性类型不同
			}

		}
	}

	/**
	 * 获取MD5加密字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString();
	}

	/**
	 * 替换参数{:key}
	 * 
	 * @param pattern
	 * @param arguments
	 * @return
	 */
	public static String format(String pattern, Map<String, Object> arguments) {
		String formatedStr = pattern;
		for (String key : arguments.keySet()) {
			String replacement = "\\{:" + key + "\\}";
			formatedStr = formatedStr.replaceAll(replacement, arguments.get(key).toString());
			System.out.println(replacement + arguments.get(key).toString());
		}
		return formatedStr;
	}

	public synchronized static String getTimeStamp() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return formatter.format(date);
	}

	/**
	 * 读取/proc/stat 获取cpu占用率 ; Linux
	 * 
	 * @return float efficiency
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static float getCpuInfo() {
		File file = new File("/proc/stat");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringTokenizer token = new StringTokenizer(br.readLine());
			token.nextToken();
			long user1 = Long.parseLong(token.nextToken());
			long nice1 = Long.parseLong(token.nextToken());
			long sys1 = Long.parseLong(token.nextToken());
			long idle1 = Long.parseLong(token.nextToken());

			Thread.sleep(1000);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			token = new StringTokenizer(br.readLine());
			token.nextToken();
			long user2 = Long.parseLong(token.nextToken());
			long nice2 = Long.parseLong(token.nextToken());
			long sys2 = Long.parseLong(token.nextToken());
			long idle2 = Long.parseLong(token.nextToken());

			return (float) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1))
					/ (float) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 根据网卡取本机配置的IP 如果是双网卡的，则取出外网IP
	 * 
	 * @return
	 */
	public static String getLocalIp() {
		String localip = null;// 返回它
		try {
			Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;// 是否找到外网IP
			while (netInterfaces.hasMoreElements() && !finded) {
				NetworkInterface ni = netInterfaces.nextElement();
				Enumeration<InetAddress> address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = address.nextElement();
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return localip;
	}

	public static Date dateMulti(Date date, int milliseconds) {
		Date newdate = new Date(date.getTime() + milliseconds);
		return newdate;

	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekOfDate(Date date) {
		int[] weekDaysCode = { 1, 2, 3, 4, 5, 6, 7 };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysCode[intWeek];
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekNameOfDate(Date date) {
		String[] weekDaysName = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		return weekDaysName[intWeek];
	}

	/**
	 * 获取下一个月的timestamp时间
	 * 
	 * @param timestamp
	 *            时间戳
	 * @return
	 */
	public static long getNextDateOfMonth(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(timestamp);
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTimeInMillis();
	}

	public static long getLastDateOfMonth(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(timestamp);
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, -1);
		return calendar.getTimeInMillis();
	}

	public static long getDateOfCurrentMonth(long timestamp) {
		// Calendar calendar = Calendar.getInstance();
		// Date date = new Date(timestamp);
		// calendar.setTime(date);
		String strDate = CommUtils.parseDate(timestamp);
		String strTime = strDate.substring(8);
		String strDate2 = CommUtils.parseDate(System.currentTimeMillis());
		String strTime2 = strDate2.substring(0, 8);

		return CommUtils.parseDate(strTime2 + strTime).getTime();
	}

	/**
	 * 获取本周周几的日期
	 * 
	 * @param date
	 * @param nDate
	 * @return
	 */
	public static long getDateOfWeek(long startTime, int nDate) {
		long lstartTime = getCurrTimestampFromHistoryTimestamp(startTime);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(lstartTime));
		c.set(Calendar.DAY_OF_WEEK, nDate);
		Date newDate = c.getTime();
		if (c.getTime().getTime() < System.currentTimeMillis()) {
			long l = c.getTime().getTime() + 7 * 24 * 60 * 60 * 1000;
			newDate = new Date(l);
		}
		return newDate.getTime();
	}

	/**
	 * 把毫秒数转化为*天*小时*分*秒
	 * 
	 * @param startDateTime
	 * @param endDateTime
	 * @return
	 */
	public static String formatMilliSecond(long startDateTime, long endDateTime) {
		long between = (endDateTime - startDateTime) / 1000;// 除以1000是为了转换成秒
		long day1 = between / (24 * 3600);
		long hour1 = between % (24 * 3600) / 3600;
		long minute1 = between % 3600 / 60;

		String msg = "";
		if (day1 > 0) {
			msg = msg + day1 + "天";
		}
		if (hour1 > 0) {
			msg = msg + hour1 + "小时";
		}
		if (minute1 > 0) {
			msg = msg + minute1 + "分钟";
		}
		return msg;
	}

	/**
	 * 
	 * @param date
	 *            参照时间
	 * @param hour
	 *            固定小时
	 * @param minute
	 *            固定分
	 * @param second
	 *            固定秒
	 * @return
	 */
	public static long getNextDaySecond(Date date, int hour, int minute) {
		long between = 0;
		SimpleDateFormat formatter1 = new SimpleDateFormat("HH");// 小时
		SimpleDateFormat formatter2 = new SimpleDateFormat("mm");// 分钟
		int hh = Integer.parseInt(formatter1.format(date));
		int mm = Integer.parseInt(formatter2.format(date));
		between = (hour - hh) * 60 * 60 + (minute - mm) * 60;
		if (between <= 0) {// 当前时间比下一个固定时间要晚，就计算下一天的当前时间
			between += 24 * 60 * 60;
		}
		return between;
	}

	/**
	 * 获得当前时间到下一个整点小时中间的秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getNextHourSecond(Date date) {
		long second = 0;
		SimpleDateFormat formatter1 = new SimpleDateFormat("mm");// 分钟
		SimpleDateFormat formatter2 = new SimpleDateFormat("ss");// 秒
		int mm = Integer.parseInt(formatter1.format(date));
		int ss = Integer.parseInt(formatter2.format(date));
		second = 60 * 60 - mm * 60 - ss;
		return second;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		if (strDate == null || "".equals(strDate)) {
			return new Date();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 指定日期的后一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getSpecifiedDayAfter(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 指定日期的前或者后N天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateAddition(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + num);

		return c.getTime();
	}

	/**
	 * 指定日期的前或者后N年
	 * 
	 * @param date
	 * @return
	 */
	public static Date getYearAddition(Date date, int num) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		c.set(Calendar.YEAR, year + num);

		return c.getTime();
	}

	/**
	 * 指定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getSpecifiedDayBefore(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 指定日期的前一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getYestoday(Date date, String formatter) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat(formatter).format(c.getTime());
		return dayBefore;
	}

	/**
	 * 用历史时间组合为 本日 yyyy-MM-dd 加历史的 HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static long getCurrTimestampFromHistoryTimestamp(long lDate) {
		long timestamp = 0;
		// 每日重启后开赛时间变为今天得日期+定点比赛的开始时间+开赛的循环周期
		if (lDate != 0) {
			Date date = new Date(lDate);
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 小写的mm表示的是分钟
			String sCurrentDate = format1.format(new Date());
			String sMatchStartDate = format2.format(date);

			try {
				date = sdf.parse(sCurrentDate + " " + sMatchStartDate);
				timestamp = date.getTime();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return timestamp;
	}

	/**
	 * 获取最近的比赛开始时间
	 * 
	 * @param l1
	 *            比赛开始时间
	 * @param l2
	 *            比赛结束时间
	 * @param nCycle
	 *            小循环 30 分钟一场
	 * @param nBigCycle
	 *            大循环 比如：1天
	 * @return
	 */
	public static long getLatestGameStartTime(long l1, long l2, int nCycle, int nBigCycle) {
		long cur = System.currentTimeMillis();
		int n = (int) (cur - l1) / nCycle;
		int dist = nCycle - ((int) (cur - l1) % nCycle);
		long latest = l1;
		System.out.println(dist);
		if (cur > l2) {
			return l1 + nBigCycle;
		} else if (cur < l1) {
			return l1;
		}
		if (dist >= 2 * 60 * 1000) {
			if ((l2 - cur) > nCycle) {
				latest = l1 + (n + 1) * nCycle;
			} else {
				System.out.println(1);
				latest = l2;
			}
		} else {
			if ((l2 - cur) >= nCycle) {
				latest = l1 + (n + 2) * nCycle;
			} else if ((l2 - cur) > 0 && (l2 - cur) < nCycle) {
				latest = l1 + (n + 2) * nCycle;
			}
		}
		return latest;
	}

	/**
	 * 是否同一天。同一天返回true ，否则返回false
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isTheSameDay(long l1, long l2) {
		if (l1 != 0 && l2 != 0) {
			Date d1 = new Date(l1);
			Date d2 = new Date(l2);
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			String s1 = sf.format(d1);
			String s2 = sf.format(d2);
			return s1.equals(s2);
		}
		return false;
	}

	public static Date strToDate(String strDate, String format) {
		if (strDate == null || "".equals(strDate)) {
			return new Date();
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date parseDate(String strDate) {
		if (strDate == null || "".equals(strDate)) {
			return new Date();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 返回时间
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date parseDate(String strDate, String format) {
		if (strDate == null || "".equals(strDate)) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 按照formater的格式，格式化date
	 * 
	 * @param date
	 * @param formater
	 *            "yyyy-MM-dd"
	 * @return
	 */
	public static String formatDate(Date date, String formater) {
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(formater);
		return formatter.format(date);
	}

	public static String parseDate(long dateTime) {
		if (dateTime == 0) {
			return "";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return formatter.format(new Date(dateTime));

	}

	/**
	 * byte Array 转换为Int
	 * 
	 * @param bytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] bytes) {

		int nTemp, nResult;

		nResult = 0;

		nTemp = bytes[0] & 0xFF;

		nTemp = nTemp << 24;

		nResult = nResult | nTemp;

		nTemp = bytes[1] & 0xFF;

		nTemp = nTemp << 16;

		nResult = nResult | nTemp;

		nTemp = bytes[2] & 0xFF;

		nTemp = nTemp << 8;

		nResult = nResult | nTemp;

		nTemp = bytes[3] & 0xFF;

		nResult = nResult | nTemp;

		return nResult;

	}

	/**
	 * int to byte数组
	 * 
	 * @param integer
	 * @return
	 */
	public static byte[] intToByteArray(final int integer) {
		int byteNum = (40 - Integer.numberOfLeadingZeros(integer < 0 ? ~integer : integer)) / 8;
		byte[] byteArray = new byte[4];

		for (int n = 0; n < byteNum; n++)
			byteArray[3 - n] = (byte) (integer >>> (n * 8));

		return (byteArray);
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	public static String timestampToStr(long n) {
		Date date = new Date(n);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String timestampToStr2(long n) {
		Date date = new Date(n);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd HH:mm");
		return formatter.format(date);
	}

	public static String timestampToStr3(long n) {
		Date date = new Date(n);
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd ");
		return formatter.format(date);
	}

	public static int parseInt(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return (int) Double.parseDouble(s);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	public static double parseDouble(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return Double.parseDouble(s);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}
	
	public static float parseFloat(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return Float.parseFloat(s);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	public static short parseShort(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return (short) Double.parseDouble(s);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	public static byte parseByte(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return (byte) Double.parseDouble(s);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	public static long parseLong(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return (long) Double.parseDouble(s);
			}
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	public static BigDecimal parseBigDecimal(String s) {
		try {
			if (s != null && !"".equals(s)) {
				return new BigDecimal(s);
			}
		} catch (Exception e) {
			return new BigDecimal(0);
		}
		return new BigDecimal(0);
	}

	/**
	 * 判断进入房间的条件
	 * 
	 * @param options
	 *            选项（金币，大师分，经验值，元宝）
	 * @param operate
	 *            操作（<,>,<=,>=,=）
	 * @param counts
	 *            数量
	 * @param gameExp
	 *            用户经验值
	 * @param masterScore
	 *            大师分
	 * @param coin
	 *            金币
	 * @param yuanBao
	 *            元宝
	 * @danVal 天梯段位，或者麻将段位
	 * @return true 通过， false 失败
	 * 
	 */
	public static boolean regCondition(String options, String operate, int counts, int masterScore, long coin,
			int yuanBao, int nTaojin, int danVal) {
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
		boolean flag5 = true;
		// 判断金币值
		if (options.equals("金币")) {
			if (operate.equals("<")) {
				if (coin < counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("=")) {
				if (coin == counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">")) {
				if (coin > counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("<=")) {
				if (coin <= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">=")) {
				if (coin >= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}
		}

		if (options.equals("荣誉值")) {
			if (operate.equals(">=")) {
				if (nTaojin >= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}
		}
		/*
		 * //判断经验值 if(options.equals("经验值")){ if(operate.equals("<")){
		 * if(gameExp<counts){ flag1 = true; }else{ flag1 = false; } }
		 * 
		 * if(operate.equals("=")){ if(gameExp == counts){ flag1 = true; }else{
		 * flag1 = false; } }
		 * 
		 * if(operate.equals(">")){ if(gameExp > counts){ flag1 = true; }else{
		 * flag1 = false; } }
		 * 
		 * if(operate.equals("<=")){ if(gameExp <= counts){ flag1 = true; }else{
		 * flag1 = false; } }
		 * 
		 * if(operate.equals(">=")){ if(gameExp <= counts){ flag1 = true; }else{
		 * flag1 = false; } } }
		 */
		// 判断大师分
		if (options.equals("大师分")) {
			if (operate.equals("<")) {
				if (masterScore < counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("=")) {
				if (masterScore == counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">")) {
				if (masterScore > counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("<=")) {
				if (masterScore <= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">=")) {
				if (masterScore >= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}
		}

		// 判断元宝
		if (options.equals("元宝")) {
			if (operate.equals("<")) {
				if (yuanBao < counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("=")) {
				if (yuanBao == counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">")) {
				if (yuanBao > counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("<=")) {
				if (yuanBao <= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">=")) {
				if (yuanBao >= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}
		}

		// 判断段位值
		if ("段位值".equals(options) || "天梯段位".equals(options)) {
			if (operate.equals("<")) {
				if (danVal < counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("=")) {
				if (danVal == counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">")) {
				if (danVal > counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals("<=")) {
				if (danVal <= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}

			if (operate.equals(">=")) {
				if (danVal >= counts) {
					flag1 = true;
				} else {
					flag1 = false;
				}
			}
		}

		return flag1 && flag2 && flag3 && flag4 && flag5;
	}

	/**
	 * 斗地主用户等级增长
	 * 
	 * @param level
	 *            用户当前等级
	 * @param curExp
	 *            用户当前经验值
	 * @return 用户等级
	 */
	public static int lordLevelIncrease(int level, int curExp) {
		if (level == 0) {
			level = 1;
		}
		int havedExp = 0;
		if (level > 1) {
			for (int i = 2; i <= level; i++) {
				havedExp += 8 * (int) Math.pow(i - 1, 2) + 2 * (i - 1) + 10; // 已有经验
			}
		}
		int exp = 8 * (int) Math.pow(level, 2) + 2 * level + 10; // 升级所需经验
		int nextExp = havedExp + exp;
		int regExp = curExp - nextExp;
		if (regExp >= 0) {
			level++;
			exp = 8 * (int) Math.pow(level, 2) + 2 * level + 10;
			while (regExp >= exp) {
				level++;
				regExp -= exp;
				exp = 8 * (int) Math.pow(level, 2) + 2 * level + 10;
			}
		}
		return level;
	}

	/**
	 * 大厅用户等级增长
	 * 
	 * @param level
	 *            用户当前等级
	 * @param curExp
	 *            用户当前经验值
	 * @return 用户等级
	 */
	public static int hallLevelIncrease(int level, int curExp) {
		if (level == 0) {
			level = 1;
		}
		int havedExp = 0;
		if (level > 1) {
			for (int i = 2; i <= level; i++) {
				havedExp += 20 * ((int) Math.pow(i - 1, 3) + 5 * (i - 1)) - 80; // 已有经验
			}
		}
		int exp = 20 * ((int) Math.pow(level, 3) + 5 * level) - 80; // 升级所需经验
		int nextExp = havedExp + exp;
		int regExp = curExp - nextExp;
		if (regExp >= 0) {
			level++;
			exp = 20 * ((int) Math.pow(level, 3) + 5 * level) - 80;
			while (regExp >= exp) {
				level++;
				regExp -= exp;
				exp = 20 * ((int) Math.pow(level, 3) + 5 * level) - 80;
			}
		}
		return level;
	}

	/**
	 * 用户第一次登录按照游戏等级奖励金币，方法通过游戏等级取得金币数
	 * 
	 * @param nGameLevel
	 * @return
	 */
	public static int getCoinsByLevelOnFirstLogin(int nGameLevel) {
		int nCoins = 0;
		if (nGameLevel > 0 && nGameLevel <= 5) {
			nCoins = 15;
		} else if (nGameLevel > 5 && nGameLevel <= 10) {
			nCoins = 30;
		} else if (nGameLevel > 10 && nGameLevel <= 15) {
			nCoins = 50;
		} else if (nGameLevel > 15 && nGameLevel <= 25) {
			nCoins = 100;
		} else if (nGameLevel > 25 && nGameLevel <= 35) {
			nCoins = 200;
		} else if (nGameLevel > 35 && nGameLevel <= 40) {
			nCoins = 350;
		} else if (nGameLevel > 40 && nGameLevel <= 45) {
			nCoins = 500;
		} else if (nGameLevel > 45 && nGameLevel <= 50) {
			nCoins = 700;
		} else {
			nCoins = 1000;
		}
		return nCoins;
	}

	/**
	 * 获得概率
	 * 
	 * @param probability
	 *            想要得到的概率，例如 probability = 20，则表示取得的概率为20%
	 * @return true，false
	 *         如果为true，则表示取得了probability相对应的概率；如果为false，则表示得到了与probability相反的概率
	 *         例如： probability = 20时，如果返回true，表示取得的概率为20%,返回false表示取得的概率为80%
	 */
	public static boolean getProbability(int probability) {
		// 20%,25,40,50,60,70,80
		Random random = new Random();
		int ran = Math.abs((random.nextInt())) % 100;

		if (ran < probability) {
			return true;
		} else {
			return false;
		}
	}

	public static int getProbability(List<Integer> probability) {
		Random random = new Random();
		int ran = random.nextInt(100);
		int min = 0;
		int max = 0;
		for (Iterator iterator = probability.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			max += integer;
			if (min <= ran && ran < max) {
				return integer;
			} else {
				min += max;
			}

		}
		return 0;
	}

	public static void main(String args[]) {
		System.out.println(formatDate(getYearAddition(new Date(), -10), "yyyy-MM-dd"));
	}

	/**
	 * 取得区间随机数
	 * 
	 * @param minRange
	 *            区间最小数
	 * @param maxRange
	 *            区间最大数
	 * @return minRange与maxRange之间的一个随机数（闭区间）
	 */
	public static int getRangeRandom(int minRange, int maxRange) {
		Random random = new Random();
		int n = random.nextInt(maxRange - minRange + 1) + minRange;
		return n;
	}

	/**
	 * 父类赋值给子类
	 * 
	 * @param fatherObj
	 * @param childObj
	 * @return
	 */
	public static Object converce(Object fatherObj, Object childObj) {
		Field[] fatherFields = fatherObj.getClass().getFields();
		Field[] childFields = childObj.getClass().getFields();
		try {
			for (Field fatherField : fatherFields) {
				for (Field childField : childFields) {
					if (childField.getName().equalsIgnoreCase(fatherField.getName())) {
						childField.set(childObj, fatherField.get(fatherObj));
					}
				}
			}
		} catch (Exception e) {
			return childObj;
		}
		return childObj;
	}

	/**
	 * 根据大师分取得称号
	 * 
	 * @param master
	 *            大师分
	 * @return 称号
	 */
	public static String getTitleByMaster(long master) {
		if (master <= 169) {
			return "赤贫";
		}
		if (master > 169 && master <= 527) {
			return "短工";
		}
		if (master > 527 && master <= 1194) {
			return "长工";
		}
		if (master > 1194 && master <= 2290) {
			return "佃户";
		}
		if (master > 2290 && master <= 3935) {
			return "贫农";
		}
		if (master > 3935 && master <= 6249) {
			return "下农";
		}
		if (master > 6249 && master <= 9352) {
			return "中农";
		}
		if (master > 9352 && master <= 13364) {
			return "富农";
		}
		if (master > 13364 && master <= 18405) {
			return "小地主";
		}
		if (master > 18405 && master <= 24595) {
			return "大地主";
		}
		if (master > 24595 && master <= 32054) {
			return "老地主";
		}
		if (master > 32054 && master <= 40902) {
			return "超级地主";
		}
		if (master > 40902 && master <= 51259) {
			return "小资本家";
		}
		if (master > 51259 && master <= 63245) {
			return "大资本家";
		}
		if (master > 63245 && master <= 76980) {
			return "金融家";
		}
		if (master > 76980 && master <= 6553500) {
			return "银行家";
		}
		if (master > 6553501) {
			return "首富";
		}
		return "";
	}

	/**
	 * 根据大师分取得称号
	 * 
	 * @param master
	 *            大师分
	 * @return 称号
	 */
	public static int getLevleByMaster(long master) {
		if (master <= 169) {
			return 1;
		}
		if (master > 169 && master <= 527) {
			return 2;
		}
		if (master > 527 && master <= 1194) {
			return 3;
		}
		if (master > 1194 && master <= 2290) {
			return 4;
		}
		if (master > 2290 && master <= 3935) {
			return 5;
		}
		if (master > 3935 && master <= 6249) {
			return 6;
		}
		if (master > 6249 && master <= 9352) {
			return 7;
		}
		if (master > 9352 && master <= 13364) {
			return 8;
		}
		if (master > 13364 && master <= 18405) {
			return 9;
		}
		if (master > 18405 && master <= 24595) {
			return 10;
		}
		if (master > 24595 && master <= 32054) {
			return 11;
		}
		if (master > 32054 && master <= 40902) {
			return 12;
		}
		if (master > 40902 && master <= 51259) {
			return 13;
		}
		if (master > 51259 && master <= 63245) {
			return 14;
		}
		if (master > 63245 && master <= 76980) {
			return 15;
		}
		if (master > 76980 && master <= 6553500) {
			return 16;
		}
		if (master > 6553501) {
			return 17;
		}
		return 1;
	}

	public static int getRandomInt(int i) {
		Random r = new Random();

		return r.nextInt(i);

	}

	/**
	 * 根据段位值得到段位
	 * 
	 * @param danVal
	 * @return
	 */
	public static String getDanName(int danVal) {
		if (danVal >= 0 && danVal < 169) {
			return "修炼";
		} else if (danVal >= 169 && danVal < 527) {
			return "一级";
		} else if (danVal >= 527 && danVal < 1194) {
			return "二级";
		} else if (danVal >= 1194 && danVal < 2290) {
			return "三级";
		} else if (danVal >= 2290 && danVal < 3935) {
			return "四级";
		} else if (danVal >= 3925 && danVal < 6249) {
			return "五级";
		} else if (danVal >= 6249 && danVal < 9352) {
			return "六级";
		} else if (danVal >= 9352 && danVal < 13364) {
			return "七级";
		} else if (danVal >= 13364 && danVal < 18405) {
			return "八级";
		} else if (danVal >= 18405 && danVal < 24595) {
			return "九级";
		} else if (danVal >= 24595 && danVal < 32054) {
			return "十级";
		} else if (danVal >= 32054 && danVal < 40902) {
			return "初段";
		} else if (danVal >= 40902 && danVal < 51259) {
			return "二段";
		} else if (danVal >= 51259 && danVal < 63245) {
			return "三段";
		} else if (danVal >= 63245 && danVal < 76980) {
			return "四段";
		} else if (danVal >= 76980 && danVal < 92584) {
			return "五段";
		} else if (danVal >= 92584 && danVal < 110177) {
			return "六段";
		} else if (danVal >= 110177 && danVal < 129879) {
			return "七段";
		} else if (danVal >= 129879 && danVal < 151810) {
			return "八段";
		} else if (danVal >= 151810 && danVal < 176090) {
			return "九段";
		} else if (danVal >= 176090 && danVal < 202839) {
			return "玄人";
		} else if (danVal >= 202839 && danVal < 232177) {
			return "名人";
		} else if (danVal >= 232177 && danVal < 264224) {
			return "达人";
		} else if (danVal >= 264224 && danVal < 299100) {
			return "超人";
		} else if (danVal >= 299100) {
			return "铁人";
		} else {
			return "";
		}
	}

	/**
	 * 根据段位值得到段位
	 * 
	 * @param danVal
	 * @return
	 */
	public static int getDan(int danVal) {
		if (danVal >= 0 && danVal < 169) {
			return 0;
		} else if (danVal >= 169 && danVal < 527) {
			return 1;
		} else if (danVal >= 527 && danVal < 1194) {
			return 2;
		} else if (danVal >= 1194 && danVal < 2290) {
			return 3;
		} else if (danVal >= 2290 && danVal < 3935) {
			return 4;
		} else if (danVal >= 3925 && danVal < 6249) {
			return 5;
		} else if (danVal >= 6249 && danVal < 9352) {
			return 6;
		} else if (danVal >= 9352 && danVal < 13364) {
			return 7;
		} else if (danVal >= 13364 && danVal < 18405) {
			return 8;
		} else if (danVal >= 18405 && danVal < 24595) {
			return 9;
		} else if (danVal >= 24595 && danVal < 32054) {
			return 10;
		} else if (danVal >= 32054 && danVal < 40902) {
			return 11;
		} else if (danVal >= 40902 && danVal < 51259) {
			return 12;
		} else if (danVal >= 51259 && danVal < 63245) {
			return 13;
		} else if (danVal >= 63245 && danVal < 76980) {
			return 14;
		} else if (danVal >= 76980 && danVal < 92584) {
			return 15;
		} else if (danVal >= 92584 && danVal < 110177) {
			return 16;
		} else if (danVal >= 110177 && danVal < 129879) {
			return 17;
		} else if (danVal >= 129879 && danVal < 151810) {
			return 18;
		} else if (danVal >= 151810 && danVal < 176090) {
			return 19;
		} else if (danVal >= 176090 && danVal < 202839) {
			return 20;
		} else if (danVal >= 202839 && danVal < 232177) {
			return 21;
		} else if (danVal >= 232177 && danVal < 264224) {
			return 22;
		} else if (danVal >= 264224 && danVal < 299100) {
			return 23;
		} else if (danVal >= 299100) {
			return 24;
		} else {
			return -1;
		}
	}

	public static int[] GetScoreRange(int score) {
		if (score >= 0 && score < 169) {
			return new int[] { 0, 169 };
		} else if (score >= 169 && score < 527) {
			return new int[] { 168, 527 };
		} else if (score >= 527 && score < 1194) {
			return new int[] { 527, 1194 };
		} else if (score >= 1194 && score < 2290) {
			return new int[] { 1194, 2290 };
		} else if (score >= 2290 && score < 3935) {
			return new int[] { 2290, 3935 };
		} else if (score >= 3925 && score < 6249) {
			return new int[] { 3925, 6249 };
		} else if (score >= 6249 && score < 9352) {
			return new int[] { 6249, 9352 };
		} else if (score >= 9352 && score < 13364) {
			return new int[] { 9352, 13364 };
		} else if (score >= 13364 && score < 18405) {
			return new int[] { 13364, 18405 };
		} else if (score >= 18405 && score < 24595) {
			return new int[] { 18405, 24595 };
		} else if (score >= 24595 && score < 32054) {
			return new int[] { 24595, 32054 };
		} else if (score >= 32054 && score < 40902) {
			return new int[] { 32054, 40902 };
		} else if (score >= 40902 && score < 51259) {
			return new int[] { 40902, 51259 };
		} else if (score >= 51259 && score < 63245) {
			return new int[] { 51259, 63245 };
		} else if (score >= 63245 && score < 76980) {
			return new int[] { 63245, 76980 };
		} else if (score >= 76980 && score < 92584) {
			return new int[] { 76980, 92584 };
		} else if (score >= 92584 && score < 110177) {
			return new int[] { 92584, 110177 };
		} else if (score >= 110177 && score < 129879) {
			return new int[] { 110177, 129879 };
		} else if (score >= 129879 && score < 151810) {
			return new int[] { 129879, 151810 };
		} else if (score >= 151810 && score < 176090) {
			return new int[] { 151810, 176090 };
		} else if (score >= 176090 && score < 202839) {
			return new int[] { 176090, 202839 };
		} else if (score >= 202839 && score < 232177) {
			return new int[] { 202839, 232177 };
		} else if (score >= 232177 && score < 264224) {
			return new int[] { 232177, 264224 };
		} else if (score >= 264224 && score < 299100) {
			return new int[] { 264224, 299100 };
		} else if (score >= 299100) {
			return new int[] { 299100, Integer.MAX_VALUE - 1 };
		} else {
			return new int[] { 0, 0 };
		}

	}

	/**
	 * 数字转换成中文130010
	 * 
	 * @param num
	 * @return
	 */
	public static String conventNumber(int num) {
		String str = "";
		String strNum = String.valueOf(num);
		int strLen = strNum.length();
		String[] cn = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String[] dw = { "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿" };
		if (strLen == 1) {
			return cn[num];
		}
		int zeroCnt = 0;
		for (int i = 0; i < strLen; i++) {
			int n = Integer.parseInt(strNum.substring(i, i + 1));
			if (strLen - i > 0) {
				if (n == 0) {
					zeroCnt++;
				} else {
					if (zeroCnt > 0) {
						str += cn[0];
					}
					zeroCnt = 0;
					str += cn[n];
				}
				if (strLen - 2 - i >= 0 && n > 0) {
					str += dw[strLen - 2 - i];
				}
			}
		}
		return str;
	}

	/**
	 * 取得给定时间的下一天
	 * 
	 * @param strTime
	 * @return
	 */
	public static String getNextDay(String strTime) {
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(strTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(cal.DATE, 1);
		strTime = sdf.format(cal.getTime());
		return strTime;
	}

	/**
	 * 格式化到今天的对应时间
	 * 
	 * @param startTime
	 * @return
	 */
	public static long formatTodayTime(long startTime) {
		Date applyDate = new Date(startTime);
		Date today = new Date();
		today.setHours(applyDate.getHours());
		today.setMinutes(applyDate.getMinutes());
		today.setSeconds(0);
		return today.getTime();
	}

	/**
	 * @param time
	 *            时间戳
	 * @param type
	 *            延迟类型 Calendar
	 * @param value
	 *            延迟时间
	 * @return
	 */
	public static long timeDelay(long time, int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.add(type, value);
		return calendar.getTimeInMillis();
	}

	public static String getVersionStr(int nVersionCode) {
		int nMainVer = (nVersionCode >> 24);
		int nSubVer = ((nVersionCode & 0x00FF0000) >> 16);
		return nMainVer + "." + nSubVer;
	}

	public static String getVersion(int nVersionCode) {
		int nMainVer = (nVersionCode >> 24);
		int nSubVer = ((nVersionCode & 0x00FF0000) >> 16);
		if (nSubVer < 10) {
			return nMainVer + "0" + nSubVer;
		} else {
			return nMainVer + "" + nSubVer;
		}
	}

	public static String getVersionWithPoint(int nVersionCode) {
		int nMainVer = (nVersionCode >> 24);
		int nSubVer = ((nVersionCode & 0x00FF0000) >> 16);
		if (nSubVer < 10) {
			return nMainVer + ".0" + nSubVer;
		} else {
			return nMainVer + "." + nSubVer;
		}
	}

	public static int[] byteArray2IntArray(byte[] ab) {
		int[] an = new int[ab.length];
		for (int i = 0; i < an.length; i++) {
			an[i] = ab[i];
		}
		return an;
	}

	public static byte[] intArray2ByteArray(int[] an) {
		byte[] ab = new byte[an.length];
		for (int i = 0; i < an.length; i++) {
			ab[i] = (byte) an[i];
		}
		return ab;
	}

	/**
	 * 获取运营商类型 0未知2联通3电信4移动
	 * 
	 * @return
	 */
	public static final int CHINA_COMMON = 0;
	public static final int CHINA_MOBILE_MM = 1;// 移动mm
	public static final int CHINA_UNICOM = 2;
	public static final int CHINA_TELECOM = 3;
	public static final int CHINA_ONLINEBASE = 4;// 网游基地

	public static final int IOS_APPSTORE = 30;// 苹果appstore
	public static final int IOS_91 = 91;// 苹果91礼包
	public static final String CHINA_MM_CHANNEL = ",161,";// mm渠道

	/**
	 * 这个函数不建议使用。仅仅礼包系统使用
	 * 
	 * @param IMSI
	 * @return
	 */
	public static int getOperators(String IMSI) {
		int type = CHINA_COMMON;// 0未知1移动2联通3电信
		if (IMSI == null) {
			return type;
		}
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
			type = CHINA_ONLINEBASE;
		} else if (IMSI.startsWith("46001")) {
			type = CHINA_UNICOM;
		} else if (IMSI.startsWith("46003")) {
			type = CHINA_TELECOM;
		}
		return type;
	}

	public static final int OPERATORS_COMMON = 0;
	public static final int OPERATORS_MOBILE = 1;// 移动mm
	public static final int OPERATORS_UNICOM = 2;// 联通
	public static final int OPERATORS_TELECOM = 3;// 电信

	/**
	 * 获取三大运营商标识
	 * 
	 * @param IMSI
	 * @return
	 */
	public static int getOperatorsFlag(String IMSI) {
		int type = OPERATORS_COMMON;// 0未知1移动2联通3电信
		if (IMSI == null) {
			return type;
		}
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
			type = OPERATORS_MOBILE;
		} else if (IMSI.startsWith("46001")) {
			type = OPERATORS_UNICOM;
		} else if (IMSI.startsWith("46003")) {
			type = OPERATORS_TELECOM;
		}
		return type;
	}

	// 本机时间误差
	private static long currentTimeMillisDiff;
	private static long lastRefreshMillisTime;

	/**
	 * 版本号字符串转换为四字节数字型版本 2.03 -> 0x02030000 2.32.0.104 -> 0x02200068
	 * 
	 * @param versionStr
	 * @return
	 */
	public static int versionStr2VersionCode(String versionStr) {
		if (versionStr == null || "".equals(versionStr)) {
			return 0;
		}
		versionStr = versionStr.replace('.', '_');
		String[] versionNumArray = versionStr.split("_");
		int[] codeArr = new int[versionNumArray.length];
		for (int i = 0; i < versionNumArray.length; i++) {
			codeArr[i] = Integer.parseInt(versionNumArray[i]);
		}

		if (codeArr.length == 2) {
			return (codeArr[0] << 24) + (codeArr[1] << 16);
		}
		if (codeArr.length == 4) {
			return (codeArr[0] << 24) + (codeArr[1] << 16) + (codeArr[2] << 8) + (codeArr[3]);
		}
		return 0;
	}

	/**
	 * Int型版本号转字符串
	 * 
	 * @param version
	 * @return
	 */
	public static String versionCode2VersionStr(int version) {
		return ((version >> 24) & 0xff) + "." + ((version >> 16) & 0xff) + "." + ((version >> 8) & 0xff) + "."
				+ (version & 0xff);
	}

	/**
	 * 得到本周一的时间戳
	 * 
	 * @return
	 */
	public static long getCurrMondayTimeStamp() {
		Calendar clNow = Calendar.getInstance();
		int n = clNow.get(Calendar.WEEK_OF_YEAR);
		System.out.println(n);
		//
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(0);
		cl.set(Calendar.YEAR, clNow.get(Calendar.YEAR));
		cl.set(Calendar.MONTH, clNow.get(Calendar.MONTH));
		cl.set(Calendar.DATE, clNow.get(Calendar.DATE));
		cl.set(Calendar.HOUR, 0);
		//
		Calendar cl2 = Calendar.getInstance();
		cl2.setTimeInMillis(cl.getTimeInMillis());
		cl2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cl2.getTimeInMillis();
	}

	/**
	 * 是否最低倍斗地主房间
	 * 
	 * @param roomID
	 * @return
	 */
	public static boolean isMinLordRoom(int roomID) {
		if (roomID == 1 || roomID == 9 || roomID == 13) {
			return true;
		}
		return false;
	}
	//判断字符串是否为数字
	public static boolean isNumeric(String str) {
        try {
            String bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }
}