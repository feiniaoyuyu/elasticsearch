package huawei.elasticsearch.tool;

import java.text.SimpleDateFormat;

public class TimeTool 
{
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getCurrentTime()
	{
		return sdf.format(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		System.out.println(TimeTool.getCurrentTime());
	}
}
