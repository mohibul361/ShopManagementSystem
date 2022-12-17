package com.sencillo.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.sencillo.enums.TransactionType;
import com.sencillo.model.Transaction;

@Component
public class TransactionValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Transaction.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Transaction transaction = (Transaction) target;
		
		System.out.print("type: "+transaction.getTransactionType());
		if (transaction.getTransactionType() == TransactionType.PAYMENT) {
			if (transaction.getVendor() == null) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vendor", "valid.vendor");
				return;
			}
		} else if (transaction.getTransactionType() == TransactionType.RECEIVE){
			if (transaction.getRecipient() == null) {
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recipient", "valid.recipient");
			}
		}

	}
}
