package com.extr.file.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import com.extr.util.Roulette;

public class RouletteTest {

	@Test
	public void RouletteTest1(){
		List<String> nameList = new ArrayList<String>();
		nameList.add("刘攀");
		nameList.add("瓜欢");
		nameList.add("小二");
		nameList.add("张三丰");
		nameList.add("张曼玉");
		
		HashMap<Integer, Float> hm = new HashMap<Integer,Float>();
		hm.put(0, 0.3f);
		hm.put(1, 0.2f);
		hm.put(2, 0.1f);
		hm.put(3, 0.3f);
		hm.put(4, 0.1f);
		
		HashMap<String,Integer> result = new HashMap<String,Integer>();
		result.put("刘攀", 0);
		result.put("瓜欢", 0);
		result.put("小二", 0);
		result.put("张三丰", 0);
		result.put("张曼玉", 0);
		
		Roulette<String> r = new Roulette<String>(nameList,hm);
		for(int i = 0 ; i < 10000 ; i ++){
			String name = "";
			try {
				name = r.getResult();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				
				e.printStackTrace();
				return;
			}
			
			result.put(name, result.get(name) + 1);
		}
		
		System.out.println(result);
		
	}
	
	
}
