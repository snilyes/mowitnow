package com.mowitnow.websocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

@WebServlet(urlPatterns = "/websocket")
public class MowItNowWebSocketServlet extends WebSocketServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected StreamInbound createWebSocketInbound(String string, HttpServletRequest hsr) {
		return new MowItNowMessageInbound();
	}

}