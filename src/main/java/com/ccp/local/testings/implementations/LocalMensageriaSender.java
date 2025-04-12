package com.ccp.local.testings.implementations;


import java.lang.reflect.Field;
import java.util.function.Function;

import com.ccp.decorators.CcpJsonRepresentation;
import com.ccp.especifications.mensageria.sender.CcpMensageriaSender;

public class LocalMensageriaSender implements CcpMensageriaSender {

	public LocalMensageriaSender() {

	}
	
	private Function<CcpJsonRepresentation, CcpJsonRepresentation> getProcess(String processName, CcpJsonRepresentation json){
		try {
			Class<?> forName = Class.forName("com.ccp.jn.commons.mensageria.JnMensageriaReceiver");
			Field declaredField = forName.getDeclaredField("INSTANCE");
			Object object = declaredField.get(null);
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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
