package com.dly.app.commons.security;

import java.io.IOException;
import java.util.Arrays;




public class AlipaySignTest {

    // 签名密钥
    private static final String SIGN_KEY = "abcdefghijk";

    /**
     * 签名算法
     *
     * @param args
     * @throws Exception 
     * @throws IOException
     */
    public static void main(String[] args) throws Exception {
        String[] billNos = { "4403101092604773", "4403062709507321", "4403061093040707",
                             "4403081701486046" };
//
//        for (String billNo : billNos) {
//            System.out.println("缴费单号：" + billNo);
            // billNo加上密钥一起签名，data数组中的值无排列顺序。签名时会按字典序自动拼接
            String[] data = new String[] { "password123456phone15832199886timeStamp1512472587157" };
            String[] dat = new String[] { "123", "sygjj","dsss" ,"42","9999","dsdsdas","SADAD","注册"};
            // 生成签名
            String sign = SHA1.genSign(data);
            for (int i = 0; i < data.length; i++) {
				System.out.println("排序前"+data[i]);
			}
            Arrays.sort(data);
            for (int i = 0; i < data.length; i++) {
				System.out.println("排序后"+data[i]);
			}
            System.out.println("签名结果：" + sign);
            // 校验签名
//            String sign2="6d10b596d61ae8982db9b8fc09940486c7db5914";
            boolean verifySign = SHA1.verifySign(data, "56a7d9c499c0f096179df94adc1ecfce6b1af385");
            System.out.println("签名校验结果：" + verifySign);
//        }

    }

}
