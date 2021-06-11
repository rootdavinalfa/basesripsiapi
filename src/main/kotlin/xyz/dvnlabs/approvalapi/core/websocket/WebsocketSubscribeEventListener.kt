/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.core.websocket

import org.springframework.context.ApplicationListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent


@Component
class WebsocketSubscribeEventListener : ApplicationListener<SessionSubscribeEvent> {
    override fun onApplicationEvent(event: SessionSubscribeEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        //println(headerAccessor.sessionAttributes?.get("sessionId")?.toString())
    }

}