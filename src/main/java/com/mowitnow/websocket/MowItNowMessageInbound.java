package com.mowitnow.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Observable;
import java.util.Observer;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mowitnow.model.Lawn;
import com.mowitnow.model.Mower;
import com.mowitnow.parser.Parser;

public class MowItNowMessageInbound extends MessageInbound implements Observer {

	private static Logger logger = LoggerFactory.getLogger(MowItNowMessageInbound.class);
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	private static Parser parser = new Parser();

	@Override
	public void update(Observable o, Object arg) {
		try {
			String observableJson = mapper.writeValueAsString(o);
			WsOutbound outbound = getWsOutbound();
			outbound.writeTextMessage(CharBuffer.wrap(observableJson));
		} catch (IOException e) {
			logger.error("Error while broadcasting", e);
		}
	}

	@Override
	protected void onBinaryMessage(ByteBuffer bb) throws IOException {
	}

	@Override
	protected void onTextMessage(CharBuffer cb) throws IOException {
		WsOutbound outbound = getWsOutbound();
		String input = cb.toString();
		try {
			Lawn lawn = parser.parse(input, this);
			outbound.writeTextMessage(CharBuffer.wrap(mapper.writeValueAsString("start")));
			outbound.writeTextMessage(CharBuffer.wrap(mapper.writeValueAsString(lawn)));
			for (Mower mower : lawn.getMowers()) {
				outbound.writeTextMessage(CharBuffer.wrap(mapper.writeValueAsString(mower)));
			}
			lawn.mow();
			outbound.writeTextMessage(CharBuffer.wrap(mapper.writeValueAsString("end")));
		} catch (Exception e) {
			outbound.writeTextMessage(CharBuffer.wrap(mapper.writeValueAsString(e.getMessage())));
		}
	}

}
