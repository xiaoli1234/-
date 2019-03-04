package org.zw.android.framework.util;

public class JSONUitlForNET {
	public static String ConvertUnicodeToJsonFormatL(String json) {
		String str = json.replace("\\", "");
		// 处理完成后赋值回去
		json = str.substring(1, str.length() - 1);

		return json;

	}
}
