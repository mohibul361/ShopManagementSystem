package com.sencillo.controller;

import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sencillo.editors.RoleEditor;
import com.sencillo.forms.ChangePassword;
import com.sencillo.model.Role;
import com.sencillo.model.User;
import com.sencillo.service.RoleService;
import com.sencillo.service.UserService;

@Controller
public class UserController
{
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleEditor roleEditor;

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(Role.class, this.roleEditor);
	}

	@ModelAttribute
	public void loadDefault(Model m)
	{
		m.addAttribute("userList", this.userService.getUserList());
		m.addAttribute("roleList", this.roleService.getRoleList());
	}

	@RequestMapping(value = "/user")
	public String loadPage(Model m)
	{
		m.addAttribute("user", new User());
		return "user";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String save(@Valid User user, BindingResult bindingResult, Model m)
	{
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if (bindingResult.hasErrors())
		{
			return "user";
		}
		if (user.getId() == null)
		{
			String salt = getSalt();
			String userPassword = user.getPassword();
			String combined = userPassword + salt;
			String hashedPassword = DigestUtils.sha512Hex(combined);
			user.setPassword(hashedPassword);
			user.setSalt(salt);
			this.userService.save(user);
		}
		else
		{
			this.userService.edit(user);
		}
		m.addAttribute("user", new User());
		return "user";
	}

	public String getSalt()
	{
		String salt = "";
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		salt = org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
		return salt;
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public String getUser(@PathVariable("id") Integer id, Model model)
	{
		model.addAttribute("user", this.userService.getUser(id));
		return "user";
	}

	@RequestMapping(value = "/changePassword")
	public String getChangePassword(Model model)
	{
		model.addAttribute("changePassword", new ChangePassword());
		return "changePassword";
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@Valid ChangePassword changePassword, BindingResult bindingResult, HttpSession session, Model model)
	{
		String msg = "";
		List<FieldError> errors = bindingResult.getFieldErrors();
		for (FieldError error : errors)
		{
			System.out.println(error.getObjectName() + " - " + error.getDefaultMessage());
		}
		if (bindingResult.hasErrors())
		{
			return "changePassword";
		}
		String oldPassword = changePassword.getOldPassword();
		String newPassword = changePassword.getPassword();

		System.out.println("oldPassword>> " + oldPassword);
		User user = this.userService.getUser((Integer) session.getAttribute("userId"));
		String retrievedOldPasswordHash = DigestUtils.sha512Hex(oldPassword + user.getSalt());
		System.out.println("retrievedOldPasswordHash>>"+retrievedOldPasswordHash);
		System.out.println("user.getPassword()>>>"+user.getPassword());
		if (retrievedOldPasswordHash.equals(user.getPassword()))
		{
			String salt = getSalt();
			String combined = newPassword + salt;
			String hashedPassword = DigestUtils.sha512Hex(combined);
			
			this.userService.changePassword(user.getId(), hashedPassword, salt);
			msg = "Password changed successfully!";
		}
		else
		{
			msg = "Old password not matched!";
		}
		System.out.println("MSG>>>" + msg);
		model.addAttribute("msg", msg);
		return "changePassword";
	}
	

}
