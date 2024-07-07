package com.ccp.local.testings.implementations;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.email.CcpEmailSender;

public class LocalEmailSender implements CcpEmailSender {

	public CcpJsonRepresentation send(CcpJsonRepresentation emailApiParameters) {
		System.out.println(emailApiParameters);
		return emailApiParameters;
	}

}
