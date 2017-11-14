package com.extr.domain.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * 角色
 * @author 郭亚坤
 *
 */

@XmlRootElement
public class Role implements Serializable {
	private static final long serialVersionUID = -6541723313940343320L;
	private int id;
	/**
	 * 角色权限
	 */
	private String authority;
	/**
	 * 角色名称
	 */
	private String name;
	private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	
}
