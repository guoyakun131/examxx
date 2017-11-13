package com.extr.service;

import java.util.List;

import com.extr.domain.user.User;
import com.extr.util.Page;

/**
 * @author Ocelot
 * @date 2014年6月8日 下午5:52:55
 */
public interface UserService {

	int addUser(User user);
	
	int addAdmin(User user);

	public List<User> getUserListByRoleId(int roleId,Page<User> page);
	
	public void updateUser(User user,String oldPassword);
	
	public User getUserById(int user_id);
	
	public void disableUser(int user_id);
}
