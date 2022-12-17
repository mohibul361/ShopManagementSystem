package com.sencillo.enums;

public enum SlipType
{
	INCOMING("slipTypes.INCOMING"), 
	DELIVERY("slipTypes.DELIVERY");

	private String messageCode;

    private SlipType(final String messageCode) {
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
			case INCOMING:
				return "ইনকামিং";
			case DELIVERY:
				return "ডেলিভারি";
			default:
				throw new AssertionError();
		}
	}
}
