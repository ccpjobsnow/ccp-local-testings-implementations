package com.ccp.local.testings.implementations;


import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;

public class LocalMensageriaSender implements CcpMensageriaSender {

	public LocalMensageriaSender() {

	}

	public CcpMensageriaSender send(String topic, String... msgs) {

		for (String msg : msgs) {
			//FIXME
			CcpJsonRepresentation messageDetails = new CcpJsonRepresentation(msg);
//			new Thread(() -> {
//				Function<CcpJsonRepresentation, CcpJsonRepresentation> process = CcpAsyncTask.getProcess(topic);
//				process.apply(messageDetails); 
//			}).start();

			
			
		}
		return this;
	}

}
