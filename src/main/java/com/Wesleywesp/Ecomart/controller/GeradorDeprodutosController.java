package com.Wesleywesp.Ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("gerador")
public class GeradorDeprodutosController {
    private final ChatClient chatClient;

    public GeradorDeprodutosController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    public String gerarProdutos(){
        var texto = "gere 5 produtos ecologicos";
        return this.chatClient.prompt()
                .user(texto)
                .call()
                .content();
    }

}
