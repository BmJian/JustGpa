package com.just.md5;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class StringMD5 {

			
		public static char[] getMD5(String Text) throws UnsupportedEncodingException, NoSuchAlgorithmException{
			//测试明文
			
			String salt="加盐算法防止破解";
			//将明文转换为一组2进制数据,摘要算法只能针对2进制数据进行转换
			byte[] data=Text.getBytes("utf-8");
			byte[] data2=salt.getBytes("utf-8");
			//获得Java提供的MD5消息摘要算法
			MessageDigest md5=MessageDigest.getInstance("MD5");
			//将数据data提交到消息摘要类中计算,如果有很多数据,可以多次提交数据
			md5.update(data);
			md5.update(data2);
			//获取摘要计算结果,结果有16个byte组成,128为数据			
			byte[] digest=md5.digest();
			//为了便于查看结果,利用commons-coding提供的函数转换为16进制字符串
			//每个byte转换为2个16进制字符,一共32个字符 
			char[] hex=Hex.encodeHex(digest);
			return hex;
		}


}
