package com.extr.file.util;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class EncodeTest {

	@Test
	public void encodeTest() throws UnsupportedEncodingException{
		System.out.println("中文");//1
	    System.out.println("中文".getBytes());//2
	    System.out.println("中文".getBytes("GB2312"));//3
	    System.out.println("中文".getBytes("ISO8859_1"));//4
	    System.out.println(new String("中文".getBytes()));//5
	    System.out.println(new String("中文".getBytes(),"GB2312"));//6
	    System.out.println(new String("中文".getBytes(),"ISO8859_1"));//7
	    System.out.println(new String("中文".getBytes("GB2312")));//8
	    System.out.println(new String("中文".getBytes("GB2312"),"GB2312"));//9
	    System.out.println(new String("中文".getBytes("GB2312"),"ISO8859_1"));//10
	    System.out.println(new String("中文".getBytes("ISO8859_1")));//11
	    System.out.println(new String("中文".getBytes("ISO8859_1"),"GB2312"));//12
	    System.out.println(new String("中文".getBytes("ISO8859_1"),"ISO8859_1"));//13
	    //test
	}
}
