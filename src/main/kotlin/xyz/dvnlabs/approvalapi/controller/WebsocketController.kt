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
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import xyz.dvnlabs.approvalapi.dto.TestDTO
import xyz.dvnlabs.approvalapi.entity.Notification
import xyz.dvnlabs.approvalapi.service.NotificationService

@Controller
class WebsocketController {

    @Autowired
    private lateinit var simpMessagingTemplate: SimpMessagingTemplate

    @Autowired
    private lateinit var notificationService: NotificationService

    /**
     * WEBSOCKET ENDPOINT FOR TESTING
     * EXPERIMENTAL
     * Currently support principal from token from header
     */
    //@SendTo("/topic/global-message/tick")
    @SendToUser("/topic/global-message/tick")
    @MessageMapping("/from-client")
    @Throws(Exception::class)
    fun fromClient(content: String, headerAccessor: StompHeaderAccessor): TestDTO {
        //log.info("Message from client: {}", content);

        return TestDTO(
            username = headerAccessor.user?.name!!
        )
    }

    @SubscribeMapping("/notification")
    fun notification(headerAccessor: StompHeaderAccessor): List<Notification> {
        println("Notification")
        headerAccessor.user?.let {
            return notificationService.listenTransaction(null, it.name, null)
        }
        return emptyList()
    }

}