package huo.chen.util;

import java.util.List;

//ʵ�ù�����
public class StringUtil {
	
	
	
	//��ָ����list����ָ���ķָ����ָ���ַ�������

	public static String joinString(List<Long> list,String split) {
	
		String ret="";
		for(Long l:list) {
			ret+=l+split;
		}
		if (!"".equals(ret)) {
			ret=ret.substring(0, ret.length()-split.length());
		}
		return ret;
	}

	public static String generateSn(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}
}
