package com.iwe3.hzl.controller;

import com.iwe3.hzl.service.AiService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * AI智能点餐助手控制器
 */
@RestController
@RequestMapping("/ai/chat")
@CrossOrigin(origins = "*")
public class AiController {

    private final AiService aiService;


    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    /**
     * 流式响应AI对话
     * @param question 用户问题
     * @return 流式响应的AI回答
     */
    @GetMapping(value = "/sync", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String chatStream(@RequestParam("question") String question) {
        return aiService.chatStream(question);
    }


}