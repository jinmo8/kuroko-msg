package com.kuroko.util;

public class MD5Encrypt {

    /**
     * 把32位md5按两位一组，转为十进制byte数组
     *
     * @param md5
     * @return
     */
    public static byte[] encryptMd5(String md5) {
        byte[] encryptedMd5 = new byte[16];
        int k = 0;
        for (int i = 0; i < encryptedMd5.length; i++) {
            encryptedMd5[i] = Byte.valueOf(convertHexToByte(md5.substring(k, k + 2)));
            k += 2;
        }
        return encryptedMd5;
    }

    /**
     * 把十六进制字符转为十进制。
     * 注意：范围是 byte [-128~127]，溢出按补码处理
     *
     * @param hex 两位十六进制字符
     * @return
     */
    public static String convertHexToByte(String hex) {
        // 把十六进制换成二进制字符
        String binaryStr = Integer.toBinaryString(Integer.valueOf(hex, 16));
        if (binaryStr.length() == 8) {

            byte[] binaryStrArray = new byte[8];

            //取反
            for (int i = 1; i < binaryStrArray.length; i++) {
                String item = binaryStr.substring(i, i + 1);
                binaryStrArray[i] = Byte.valueOf(item.equals("1") ? "0" : "1");
            }

            //加一
            for (int i = binaryStrArray.length - 1; i >= 1; i--) {

                if (binaryStrArray[i] + 1 > 1) {
                    binaryStrArray[i] = 0;
                } else {
                    binaryStrArray[i] = 1;
                    break;
                }
            }
            //拼接字符
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("-");
            for (int i = 1; i < binaryStrArray.length; i++) {
                stringBuilder.append(binaryStrArray[i]);
            }

            return Integer.valueOf(stringBuilder.toString(), 2).toString();

        } else {
            return Integer.valueOf(hex, 16).toString();
        }

    }

}
