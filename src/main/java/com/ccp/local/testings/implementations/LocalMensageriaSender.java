package com.ccp.local.testings.implementations;


import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;
import com.ccp.exceptions.process.CcpAsyncTask;

public class LocalMensageriaSender implements CcpMensageriaSender {

	public LocalMensageriaSender() {

	}

	public void send(String topic, String... msgs) {

		for (String msg : msgs) {
			CcpJsonRepresentation messageDetails = new CcpJsonRepresentation(msg);
//			new Thread(() -> {
//				Function<CcpJsonRepresentation, CcpJsonRepresentation> process = CcpAsyncTask.getProcess(topic);
//				process.apply(messageDetails);
//			}).start();
			CcpAsyncTask.getProcess(topic).apply(messageDetails);
		}
	}

}
