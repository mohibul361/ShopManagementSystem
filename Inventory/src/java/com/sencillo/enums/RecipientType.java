package com.sencillo.enums;

public enum RecipientType
{
	WHOLESALER("recipientTypes.WHOLESALER"), 
	RETAILER("recipientTypes.RETAILER");
	
	private String messageCode;

    private RecipientType(final String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageCode() {
        return messageCode;
    }
			
	@Override
	public String toString()
	{
		switch (this)
		{
			case WHOLESALER:
				return "Wholesaler";
			case RETAILER:
				return "Retailer";			
			default:
				throw new AssertionError();
		}
	}
}
