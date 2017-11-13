package com.extr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.extr.controller.domain.QuestionFilter;
import com.extr.domain.exam.ExamPaper;
import com.extr.domain.exam.Paper;
import com.extr.domain.exam.PracticePaper;
import com.extr.domain.question.Question;
import com.extr.domain.question.QuestionHistory;
import com.extr.domain.question.QuestionStruts;
import com.extr.domain.question.UserQuestionHistory;

/**
 * 
 * @author scar
 * 
 */
public class PracticeServiceTest {

	@Test
	public void testAddExecisePaper() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		PracticeService practiceService = (PracticeService) ctx
				.getBean("PracticeService");
		PracticePaper ep = new PracticePaper();
		ep.setName("aaa");
		practiceService.insertPracticePaper(ep);
	}

	@Test
	public void getExecisePaper() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		PracticeService practiceService = (PracticeService) ctx
				.getBean("PracticeService");
		PracticePaper ep = practiceService.getPracticePaperByUserID(1);

		System.out.println(ep.getName());
	}

	@Test
	public void getQuestionList() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		PracticeService PracticeService = (PracticeService) ctx
				.getBean("PracticeService");
		List<Question> questionList = PracticeService
				.getQuestionListByQuestionTypeIdAndReferenceId(2, 1, 10);

		System.out.println(questionList.size());
	}

	@Test
	public void getQuestionMap() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		List<Integer> idList = new ArrayList<Integer>();
		idList.add(1);
		idList.add(2);
		idList.add(3);
		idList.add(4);
		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");
		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> hm = questionService
				.getQuestionStrutsMap(null);

		System.out.println(hm.size());
	}
	
	@Test
	public void addUserQuestionHistoryTest() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");
		Map<Integer,Map<Integer,List<QuestionHistory>>> map = new HashMap<Integer,Map<Integer,List<QuestionHistory>>>();
		Map<Integer,List<QuestionHistory>> map1 = new HashMap<Integer,List<QuestionHistory>>();
		List<QuestionHistory> list = new ArrayList<QuestionHistory>();
		QuestionHistory q1 = new QuestionHistory();
		q1.setAnswer("111");
		q1.setExamPaperId(1);
		q1.setMyAnswer("aaa");
		q1.setRight(false);
		q1.setTime(new Date());
		list.add(q1);
		map1.put(1, list);
		map.put(0, map1);
		map.put(1, map1);
		UserQuestionHistory u = new UserQuestionHistory();
//		u.setHistory(map);
		u.setUserId(1);
		u.setModifyTime(new Date());
		questionService.addUserQuestionHistory(u);
	}
	
	@Test
	public void getUserQuestionHistoryTest() throws Exception {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");
		
		UserQuestionHistory u = questionService.getUserQuestionHistoryByUserId(1);
		//questionService.addUserQuestionHistory(u);
		System.out.println(u.getHistory());
		u.setUserId(2);
		questionService.addUserQuestionHistory(u);
	}

	@Test
	public void paperTest() {
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");
		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap = questionService
				.getQuestionStrutsMap(null);
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		hm.put(3, 2);
		hm.put(2, 3);
		hm.put(7, 3);
		HashMap<Integer, Double> hm1 = new HashMap<Integer, Double>();
		for(int i = 1 ; i <=15 ; i++){
			hm1.put(i, 0.01);
		}

		Paper paper = null;
		try {
			paper = new Paper(questionMap, hm, null,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		paper.initPaper();
		System.out.println("Fitness=" + paper.getFitness());
		HashMap<Integer, QuestionStruts> map = paper.getPaperQuestionMap();
		Iterator<Integer> it = map.keySet().iterator();
		while(it.hasNext())
			System.out.println(it.next());
	}
	
	
	@Test
	public void questionServiceTest(){
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");
		
		QuestionFilter qf = new QuestionFilter();
		qf.setSearchParam("1");
		List<Question> questionList = questionService.getQuestionList(null, qf);
	}
	
	@Test
	public void paperCreateServiceTest(){
		ApplicationContext ctx = new FileSystemXmlApplicationContext(
				"src/main/webapp/WEB-INF/spring/root-context.xml");

		ExamService examService = (ExamService) ctx
				.getBean("examService");
		QuestionService questionService = (QuestionService) ctx
				.getBean("questionService");
		
		HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap;
		HashMap<Integer, Integer> questionTypeNum = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> questionTypePoint = new HashMap<Integer, Integer>();
		
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(1);
		questionMap = questionService.getQuestionStrutsMap(null);
		questionTypeNum.put(3, 3);
		questionTypeNum.put(7, 1);
		
		ExamPaper examPaper = new ExamPaper();
		examService.createExamPaper(questionMap, questionTypeNum, null, null,examPaper);
	}
}
