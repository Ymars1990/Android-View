package com.mars.marsview.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Pinyin4jUtil {

	/**
	 * ����ת��λ����ƴ������ĸ��Ӣ���ַ����䣬�����ַ���ʧ ֧�ֶ����֣����ɷ�ʽ�磨��ɳ�г�:cssc,zssz,zssc,cssz��
	 * 
	 * @param chines
	 *            ����
	 * @return ƴ��
	 */
	public static String converterToFirstSpell(String chines) {
		StringBuffer pinyinName = new StringBuffer();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					// ȡ�õ�ǰ���ֵ�����ȫƴ
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat);
					Arrays.sort(strs);
					if (strs != null) {
						for (int j = 0; j < strs.length; j++) {
							// ȡ����ĸ
//							Utils.LogShow("strs", strs[j]);
							pinyinName.append(strs[j].charAt(0));
							if (j != strs.length - 1) {
								pinyinName.append(",");
							}
						}
					}
					// else {
					// pinyinName.append(nameChar[i]);
					// }
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
			pinyinName.append(" ");
		}
		// return pinyinName.toString();
		return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
	}

	/**
	 * ����ת��λ����ȫƴ��Ӣ���ַ����䣬�����ַ���ʧ
	 * ֧�ֶ����֣����ɷ�ʽ�磨�ص���:zhongdangcen,zhongdangcan,chongdangcen
	 * ,chongdangshen,zhongdangshen,chongdangcan��
	 * 
	 * @param chines
	 *            ����
	 * @return ƴ��
	 */
	public static String converterToSpell(String chines) {
		StringBuffer pinyinName = new StringBuffer();
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				try {
					// ȡ�õ�ǰ���ֵ�����ȫƴ
					String[] strs = PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat);
					Arrays.sort(strs);
					if (strs != null) {
						for (int j = 0; j < strs.length; j++) {
//							Utils.LogShow("strs", strs[j]);
							pinyinName.append(strs[j]);
							if (j != strs.length - 1) {
								pinyinName.append(",");
							}
						}
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName.append(nameChar[i]);
			}
			pinyinName.append(" ");
		}
		// return pinyinName.toString();
		return parseTheChineseByObject(discountTheChinese(pinyinName.toString()));
	}

	/**
	 * ȥ���������ظ�����
	 * 
	 * @param theStr
	 * @return
	 */
	private static List<Map<String, Integer>> discountTheChinese(String theStr) {
		// ȥ���ظ�ƴ�����ƴ���б�
		List<Map<String, Integer>> mapList = new ArrayList<Map<String, Integer>>();
		// ���ڴ���ÿ���ֵĶ����֣�ȥ���ظ�
		Map<String, Integer> onlyOne = null;
		String[] firsts = theStr.split(" ");
//		Arrays.sort(firsts);
		// ����ÿ�����ֵ�ƴ��
		for (String str : firsts) {
			onlyOne = new Hashtable<String, Integer>();
			String[] china = str.split(",");
			Arrays.sort(china);
			// �����ִ���
			for (String s : china) {
				Integer count = onlyOne.get(s);
				if (count == null) {
					onlyOne.put(s, new Integer(1));
				} else {
					onlyOne.remove(s);
					count++;
					onlyOne.put(s, count);
				}
				Utils.LogShow("s", s);
			}
			mapList.add(onlyOne);
		}
		return mapList;
	}

	/**
	 * ���������ƴ��������ϲ�����(�Ƽ�ʹ��)
	 * 
	 * @return
	 */
	private static String parseTheChineseByObject(
			List<Map<String, Integer>> list) {
		Map<String, Integer> first = null; // ����ͳ��ÿһ��,�����������
		// ����ÿһ�鼯��
		for (int i = 0; i < list.size(); i++) {
			// ÿһ�鼯������һ����ϵ�Map
			Map<String, Integer> temp = new Hashtable<String, Integer>();
			// ��һ��ѭ����firstΪ��
			if (first != null) {
				// ȡ���ϴ������˴μ��ϵ��ַ���������
				for (String s : first.keySet()) {
					for (String s1 : list.get(i).keySet()) {
						String str = s + s1;
						temp.put(str, 1);
					}
				}
				// ������һ���������
				if (temp != null && temp.size() > 0) {
					first.clear();
				}
			} else {
				for (String s : list.get(i).keySet()) {
					String str = s;
					temp.put(str, 1);
				}
			}
			// ������������Ա��´�ѭ��ʹ��
			if (temp != null && temp.size() > 0) {
				first = temp;
			}
		}
		String returnStr = "";
		StringBuffer sbBuffer = new StringBuffer();
		if (first != null) {
			// ����ȡ������ַ���
		
			Object[] keys = first.keySet().toArray();
			Arrays.sort(keys);
			for(int i=0;i<keys.length;i++){
				sbBuffer.append(keys[i]);
				if(i<keys.length-1){
				sbBuffer.append(",");
				}
			}
		}
		returnStr = sbBuffer.toString();		
		return returnStr;
	}

}
