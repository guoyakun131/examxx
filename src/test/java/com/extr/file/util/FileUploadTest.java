package com.extr.file.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.extr.domain.question.KnowledgePoint;
import com.extr.domain.question.Question;
import com.extr.domain.question.QuestionContent;
import com.extr.service.QuestionService;
import com.extr.util.xml.Object2Xml;

public class FileUploadTest {

	@Test
	public void uploadExcelTest() {

		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");

		String filePath = "C:\\Users\\mars\\Desktop\\国网试题录入终稿3-hsh-20140825\\国网试题录入完成版4-hsh-20140825.xlsx";
		List<Question> questionList = new ArrayList<Question>();
		try {
			List<Map<String, String>> questionMapList = ExcelUtil
					.ExcelToList(filePath);
			
			for (Map<String, String> map : questionMapList) {
				try{
					System.out.println(map);
					Question question = new Question();
					question.setName(map.get("题目").length() > 10 ? map.get("题目")
							.substring(0, 10) + "..." : map.get("题目"));
					if (map.get("类型").equals("单选题") || map.get("类型").equals("单项选择题"))
						question.setQuestion_type_id(1);
					else if (map.get("类型").equals("多选题") || map.get("类型").equals("多项选择题"))
						question.setQuestion_type_id(2);
					else if (map.get("类型").equals("判断题"))
						question.setQuestion_type_id(3);
					else if (map.get("类型").equals("填空题"))
						question.setQuestion_type_id(4);
					else if (map.get("类型").equals("简答题"))
						question.setQuestion_type_id(5);
					else if (map.get("类型").equals("论述题"))
						question.setQuestion_type_id(6);
					else if (map.get("类型").equals("分析题"))
						question.setQuestion_type_id(7);

					question.setAnalysis(map.get("解析"));
					question.setAnswer(map.get("答案"));
					if(question.getQuestion_type_id() == 3){
						if(map.get("答案").equals("对"))
							question.setAnswer("T");
						if(map.get("答案").equals("错"))
							question.setAnswer("F");
					}
					
					String z = map.get("知识类");
					String s = map.get("所属专业");
					KnowledgePoint kp = questionService.getKnowledgePointByName(
							map.get("知识类"), map.get("所属专业"));
					List<Integer> pointList = new ArrayList<Integer>();
					pointList.add(kp.getPointId());
					question.setReferenceName(map.get("出处"));
					question.setExamingPoint(map.get("知识点"));
					question.setKeyword(map.get("知识关键点"));
					question.setPoints(map.get("分值").equals("") ? 0 :Float.parseFloat(map.get("分值")));
					QuestionContent qc = new QuestionContent();
					
					Iterator<String> it = map.keySet().iterator();
					List<String> keyStr = new ArrayList<String>();
					while(it.hasNext()){
						String key = it.next();
						if(key.contains("选项"))
							keyStr.add(key.replace("选项", ""));
					}
					
					Collections.sort(keyStr);
					LinkedHashMap<String, String> choiceList = new LinkedHashMap<String, String>();
					for(int i=0;i<keyStr.size();i++){
						if(!map.get("选项" + keyStr.get(i)).trim().equals(""))
							choiceList.put(keyStr.get(i), map.get("选项" + keyStr.get(i)));
					}
					if(question.getQuestion_type_id() == 1 || question.getQuestion_type_id() == 2)
						qc.setChoiceList(choiceList);
					qc.setTitle(map.get("题目"));
					String xmlStr = Object2Xml.toXml(qc);
					question.setContent(xmlStr);
					question.setCreator("admin");
					question.setPointList(pointList);
					questionService.addQuestion(question);
				}catch(Exception e){
					e.printStackTrace();
					continue;
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
