package com.extr.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.extr.controller.domain.Message;
import com.extr.domain.question.Field;
import com.extr.domain.user.User;
import com.extr.security.UserInfo;
import com.extr.service.QuestionService;
import com.extr.service.UserService;
import com.extr.util.Page;
import com.extr.util.PagingUtil;
import com.extr.util.StandardPasswordEncoderForSha1;

@Controller
public class UserController {

	public static final String SUCCESS_MESSAGE = "success";
	public static final String ERROR_MESSAGE = "failed";

	@Autowired
	private UserService userService;
	@Autowired
	private QuestionService questionService;

	/**
	 * 用户登录页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/user-login-page" }, method = RequestMethod.GET)
	public String loginPage(Model model, @RequestParam(value = "result", required = false, defaultValue = "") String result) {
		if("failed".equals(result)){
			model.addAttribute("result", "无效的用户名或者密码");
		}
		return "login";
	}

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = { "/user-login" }, method = RequestMethod.POST)
	public @ResponseBody
	Message userLogin(@RequestBody User user) {
		user.setCreate_date(new Date());
		Message message = new Message();
		try {
			userService.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			message.setResult("error");
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * 用户登录成功页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/regist-success/{username}" }, method = RequestMethod.GET)
	public String registerSuccessPage(@PathVariable String username, Model model) {
		model.addAttribute("username", username);
		return "regist-success";
	}
	
	
	/**
	 * 用户信息查看
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/user-detail/{username}" }, method = RequestMethod.GET)
	public String userDetailPage(@PathVariable String username, Model model) {
		model.addAttribute("username", username);
		return "redirect:/student/usercenter";
	}

	/**
	 * 用户注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/user-register" }, method = RequestMethod.GET)
	public String registerPage(Model model) {
		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "register";
	}

	/**
	 * 用户注册
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = { "/user-reg" }, method = RequestMethod.POST)
	public @ResponseBody
	Message userRegister(@RequestBody User user) {
		user.setCreate_date(new Date());
		String password = user.getPassword() + "{" + user.getUsername() + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String resultPassword = passwordEncoder.encode(password);
		user.setPassword(resultPassword);
		user.setEnabled("1");
		Message message = new Message();
		try {
			userService.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			if(e.getMessage().equals("duplicate-username")){
				message.setResult(e.getMessage());
				message.setMessageInfo("用户名：" + user.getUsername() + "已经存在");
			}else
				message.setResult("错误！" + e.getClass().getName());
			e.printStackTrace();
		}
		return message;
	}
	
	
	/**
	 * 修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "student/change-password" }, method = RequestMethod.GET)
	public String changePasswordPage() {
		return "student/change-password";
	}
	
	@RequestMapping(value = { "change-pwd" }, method = RequestMethod.POST)
	public @ResponseBody Message changePassword(@RequestBody User user){
		Message message = new Message();
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		try{
			String password = user.getPassword() + "{" + userInfo.getUsername() + "}";
			PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
			String resultPassword = passwordEncoder.encode(password);
			user.setPassword(resultPassword);
			user.setUsername(userInfo.getUsername());
			userService.updateUser(user, null);
			System.out.println(user.getFieldId());
		}catch(Exception e){
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}
		
		return message;
	}
	/**
	 * 修改密码页面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "student/setting" }, method = RequestMethod.GET)
	public String settingPage(Model model) {
		
		UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		User user = userService.getUserById(userInfo.getUserid());
		model.addAttribute("user", user);
		return "student/setting";
	}
	
	@RequestMapping(value = { "setting" }, method = RequestMethod.POST)
	public @ResponseBody Message setting(@RequestBody User user){
		Message message = new Message();
		try{
			userService.updateUser(user, null);
		}catch(Exception e){
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}
		return message;
	}
	
	
	/**
	 * 显示用户管理界面
	 * 
	 * @return
	 */
	@RequestMapping(value = { "admin/user-list" }, method = RequestMethod.GET)
	public String showUserListPage(Model model, HttpServletRequest request) {
		
		int index = 1;
		if(request.getParameter("page") != null)
			index = Integer.parseInt(request.getParameter("page"));
		Page<User> page = new Page<User>();
		page.setPageNo(index);
		page.setPageSize(20);
		
		List<User> userList = userService.getUserListByRoleId(3, page);
		String pageStr = PagingUtil.getPagelink(index, page.getTotalPage(), "", "admin/user-list");
		model.addAttribute("userList", userList);
		model.addAttribute("pageStr", pageStr);
		return "admin/user-list";
	}
	
	/**
	 * 添加用户界面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/add-user", method = RequestMethod.GET)
	private String addUserPage(Model model, HttpServletRequest request) {
			
		List<Field> fieldList = questionService.getAllField(null);
		model.addAttribute("fieldList", fieldList);
		return "admin/add-user";
	}
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = { "/admin/add-user" }, method = RequestMethod.POST)
	public @ResponseBody Message addUser(@RequestBody User user){
		user.setCreate_date(new Date());
		String password = user.getPassword() + "{" + user.getUsername() + "}";
		PasswordEncoder passwordEncoder = new StandardPasswordEncoderForSha1();
		String resultPassword = passwordEncoder.encode(password);
		user.setPassword(resultPassword);
		user.setEnabled("1");
		Message message = new Message();
		try {
			userService.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			if(e.getMessage().equals("duplicate-username")){
				message.setResult(e.getMessage());
				message.setMessageInfo("用户名：" + user.getUsername() + "已经存在");
			}else
				message.setResult("错误！" + e.getClass().getName());
			e.printStackTrace();
		}
		return message;
	}
	
	/**
	 * 禁用用户
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/disable-user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Message disableUser(Model model, HttpServletRequest request, @PathVariable Integer userId) {
		
		Message message = new Message();
		try{
			User user = new User();
			user.setId(userId);
			user.setEnabled("0");
			userService.updateUser(user, null);
		}catch(Exception e){
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}
		
		return message;
	}
	
	/**
	 * 启用用户
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/admin/enable-user/{userId}", method = RequestMethod.GET)
	public @ResponseBody Message enableUser(Model model, HttpServletRequest request, @PathVariable Integer userId) {
		
		Message message = new Message();
		try{
			User user = new User();
			user.setId(userId);
			user.setEnabled("1");
			userService.updateUser(user, null);
		}catch(Exception e){
			e.printStackTrace();
			message.setResult(e.getClass().getName());
		}
		
		return message;
	}
}
