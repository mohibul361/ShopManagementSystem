package com.sencillo.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.sencillo.validator.FieldMatch;

@FieldMatch.List({ @FieldMatch(first = "password", second = "confirmPassword", errorMessage = "The password fields must match"),
// @FieldMatch(first = "email", second = "confirmEmail", errorMessage = "The email fields must match")
})
public class ChangePassword
{

	@NotNull
	private String oldPassword;
	
	@NotNull
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}", message = "Must match password policy")
	private String password;

	@NotNull
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}", message = "Must match password policy")
	private String confirmPassword;

	public String getOldPassword()
	{
		return oldPassword;
	}

	public void setOldPassword(String oldPassword)
	{
		this.oldPassword = oldPassword;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getConfirmPassword()
	{
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword)
	{
		this.confirmPassword = confirmPassword;
	}

}
