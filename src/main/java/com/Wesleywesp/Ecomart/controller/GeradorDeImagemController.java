package com.Wesleywesp.Ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptions;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("imagem")
public class GeradorDeImagemController {
    private final ImageModel imageModel;

    public GeradorDeImagemController(ChatClient.Builder chatClientBuilder, org.springframework.ai.image.ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @PostMapping
    public String gerarProdutos(@RequestBody String prompt){
        var option = ImageOptionsBuilder.builder()
                .withHeight(1024)
                .withWidth(1024)
                .build();

        var response = imageModel.call(
                new ImagePrompt(prompt, option));

        return response.getResult().getOutput().getUrl();

    }


}
