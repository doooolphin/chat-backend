package com.seona.chat.backend.api

import io.swagger.v3.oas.annotations.Operation
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatApi {
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    fun sendMessage(@Payload body: String) : String? {
        return "hi";
    }

    @Operation(summary = "채팅 테스트 api")
    @PostMapping("/topic/public")
    fun testChat(@RequestBody body: String) : String? {
        return "hi";
    }
}