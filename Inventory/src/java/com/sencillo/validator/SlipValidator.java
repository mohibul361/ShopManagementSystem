package com.sencillo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sencillo.enums.SlipType;
import com.sencillo.model.Slip;

/**
 * This validator will be used to check delivered to/ receive from. This will be used with @valid annotation
 * @author Philip
 * 
 */
@Component
public class SlipValidator implements Validator
{

	@Override
	public boolean supports(Class<?> clazz)
	{
		return Slip.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors)
	{
		Slip slip = (Slip) target;	
		if (slip.getDeliveredTo() == null)
		{
			if(slip.getSlipType() == SlipType.DELIVERY)
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveredTo", "valid.deliveredTo");
			}
//			else if(slip.getSlipType() == SlipType.RETURN)
//			{
//				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "deliveredTo", "valid.returnFrom");
//			}
		}
		if(slip.getSlipType()==SlipType.INCOMING)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendor", "valid.vendor");
		}
		
	}
}
