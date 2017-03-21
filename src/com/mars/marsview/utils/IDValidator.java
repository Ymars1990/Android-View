package com.mars.marsview.utils;

import java.util.HashMap;
import java.util.Map;

public class IDValidator {
	private Map<String, String> gb2260 = GB2260.getInstance();
	private static Map<String, IDCodeInfo> cache = new HashMap<String, IDCodeInfo>();

	/**
	 * 身份证是否有效
	 * 
	 * @param id
	 *            {@link String}
	 * @return {@link Boolean}
	 */
	public boolean isValid(String id) {
		try {
			IDCodeInfo code = MyUtils.checkArg(id);
			if (code == null) {
				return false;
			}
			// 查询cache
			if (cache.containsKey(id)) {
				return cache.get(id).isValid();
			}

			MyUtils.parseCode(code);

			if (!(MyUtils.checkAddr(code.getAddrCode())
					&& MyUtils.checkBirth(code.getBirthCode()) && MyUtils
						.checkOrder(code.getOrder()))) {
				code.setValid(false);
				cache.put(id, code);
				return false;
			}

			// 15位不含校验码，到此已结束
			if (code.getType() == 15) {
				code.setValid(true);
				cache.put(id, code);
				return true;
			}

			/* 校验位部分 */
			// 位置加权
			int[] posWeight = new int[17];
			for (int i = 18; i > 1; i--) {
				int wei = MyUtils.weight(i);
				posWeight[18 - i] = wei;
			}

			// 累加body部分与位置加权的积
			int bodySum = 0;
			String[] bodyArr = code.getBody().split("");
			for (int j = 0; j < bodyArr.length; j++) {
				bodySum += (Integer.valueOf(bodyArr[j], 10) * posWeight[j]);
			}

			// 得出校验码
			int tempCheckBit = 12 - (bodySum % 11);
			String checkBit = String.valueOf(tempCheckBit);
			if (tempCheckBit == 10) {
				checkBit = "X";
			} else if (tempCheckBit > 10) {
				checkBit = String.valueOf(tempCheckBit % 11);
			}

			// 检查校验码
			if (!checkBit.equals(code.getCheckBit())) {
				code.setValid(false);
				cache.put(id, code);
				return false;
			} else {
				code.setValid(true);
				cache.put(id, code);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 分析详细信息
	 * 
	 * @param id
	 *            {@link String} 身份证号
	 * @return {@link IDCodeInfo}
	 */
	public IDCodeInfo getInfo(String id) {
		// 号码必须有效
		if (this.isValid(id) == false) {
			return null;
		}
		// TODO 复用此部分
		IDCodeInfo code = MyUtils.checkArg(id);

		// 查询cache
		// 到此时通过isValid已经有了cache记录
		if (cache.containsKey(id)) {
			return cache.get(id);
		}

		MyUtils.parseCode(code);

		// 记录cache
		cache.put(id, code);

		return code;
	}

	/**
	 * 仿造一个号
	 * 
	 * @param isFifteen
	 *            是否生成15位数
	 * @return
	 */
	public String makeID(boolean isFifteen) {
		// 地址码
		String addr = "";
		if (gb2260 != null) {
			int loopCnt = 0;
			while (addr == "") {
				// 防止死循环
				if (loopCnt > 10) {
					addr = "110101";
					break;
				}
				String prov = MyUtils.strPad(String.valueOf(MyUtils.rand(50, 1)),
						2, '0', false);
				String city = MyUtils.strPad(String.valueOf(MyUtils.rand(60, 1)),
						2, '0', false);
				String area = MyUtils.strPad(String.valueOf(MyUtils.rand(20, 1)),
						2, '0', false);
				String addrTest = prov + city + area;
				if (gb2260.containsKey(addrTest)) {
					addr = addrTest;
					break;
				}
			}
		} else {
			addr = "110101";
		}

		// 出生年
		String yr = MyUtils.strPad(String.valueOf(MyUtils.rand(99, 50)), 2, '0',
				false);
		String mo = MyUtils.strPad(String.valueOf(MyUtils.rand(12, 1)), 2, '0',
				false);
		String da = MyUtils.strPad(String.valueOf(MyUtils.rand(28, 1)), 2, '0',
				false);
		if (isFifteen) {
			return addr
					+ yr
					+ mo
					+ da
					+ MyUtils.strPad(String.valueOf(MyUtils.rand(999, 1)), 3, '1',
							false);
		}

		yr = "19" + yr;
		String body = addr
				+ yr
				+ mo
				+ da
				+ MyUtils.strPad(String.valueOf(MyUtils.rand(999, 1)), 3, '1',
						false);

		// 位置加权
		int[] posWeight = new int[17];
		for (int i = 18; i > 1; i--) {
			int wei = MyUtils.weight(i);
			posWeight[18 - i] = wei;
		}

		// 累加body部分与位置加权的积
		int bodySum = 0;
		String[] bodyArr = body.split("");
		for (int j = 0; j < bodyArr.length; j++) {
			bodySum += (Integer.valueOf(bodyArr[j], 10) * posWeight[j]);
		}

		// 得出校验码
		int tempCheckBit = 12 - (bodySum % 11);
		String checkBit = String.valueOf(tempCheckBit);
		if (tempCheckBit == 10) {
			checkBit = "X";
		} else if (tempCheckBit > 10) {
			checkBit = String.valueOf(tempCheckBit % 11);
		}

		return (body + checkBit);
	}
}
