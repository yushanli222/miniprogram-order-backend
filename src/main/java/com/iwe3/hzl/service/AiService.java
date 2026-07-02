package com.iwe3.hzl.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * AI智能点餐助手服务
 */
@Service
public class AiService {

    private final ChatClient chatClient;

    @Autowired
    public AiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // 系统提示词 - 定义AI助手的角色和行为
    private static final String SYSTEM_PROMPT = "你是一个智能点餐助手，名为\"饿了么AI小助手\"。\n" +
            "你的任务是帮助用户进行点餐，提供友好、专业的服务。\n" +
            "你需要：\n" +
            "1. 友好地问候用户\n" +
            "2. 回答关于菜品的问题（名称、价格、描述等）\n" +
            "3. 推荐热门菜品\n" +
            "4. 提供优惠券信息\n" +
            "5. 帮助查询订单状态\n" +
            "注意事项：\n" +
            "- 保持回答简洁明了\n" +
            "- 使用自然、亲切的语言\n" +
            "- 如果不知道答案，诚实地告诉用户\n" +
            "- 不要讨论与点餐无关的话题\n" +
            "可用的菜品信息：\n" +
            "- 蒜香辣子鸡：35元，超多鸡肉的蒜香辣子鸡，口感紧实有嚼劲\n" +
            "- 腌笃鲜：40元，鲜嫩春笋配猪肉慢炖，充满春日气息\n" +
            "- 鱼尾炖豆腐：30元，豆腐和鱼完美搭配，营养美味\n" +
            "- 芥菜炒红萝卜丝：18元，简单美味的健康快手菜";

    /**
     * 流式响应AI对话
     */
    public String chatStream(String question) {
        return chatClient.prompt()
                .system(this.SYSTEM_PROMPT)
                .user(question)
                .call().content();

    }


}