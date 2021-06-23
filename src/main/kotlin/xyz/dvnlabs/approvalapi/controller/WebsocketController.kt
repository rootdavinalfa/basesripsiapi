/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.messaging.simp.annotation.SubscribeMapping
import org.springframework.stereotype.Controller
import xyz.dvnlabs.approvalapi.dto.TestDTO

@Controller
class WebsocketController {

    @Autowired
    private lateinit var simpMessagingTemplate: SimpMessagingTemplate

    /**
     * WEBSOCKET ENDPOINT FOR TESTING
     * EXPERIMENTAL
     * Currently support principal from token from header
     */
    //@SendTo("/topic/global-message/tick")
    @SendToUser("/topic/global-message/tick")
    @MessageMapping("/from-client")
    @Throws(Exception::class)
    fun fromClient(content: String, headerAccessor: SimpMessageHeaderAccessor): TestDTO {
        //log.info("Message from client: {}", content);

        return TestDTO(
            username = headerAccessor.user?.name!!
        )
    }

    @SubscribeMapping("/topic/notification")
    fun notification(headerAccessor: SimpMessageHeaderAccessor) {

    }

}