package com.Wesleywesp.Ecomart.controller;

import com.Wesleywesp.Ecomart.configuration.ContarTokens;
import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorizador")
public class CategorizadorDeprodutosController {
    private final ChatClient chatClient;


    public CategorizadorDeprodutosController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultOptions(ChatOptionsBuilder.builder()
                .withModel("gpt-3.5-turbo-instruct")
                        .build()).
                build();
    }

    @PostMapping
    public String categorizarProdutos(@RequestBody String pergunta){
        var system = """
                Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado
                
                Escolha uma categoria dentra a lista abaixo:
                
                1. Higiene pessoal
                
                2. Eletronicos
                
                3. Esportes
                
                4. Outros
                
                exemplo de uso:
                Pergunta: Bola de futebol
                
                Resposta: Esportes
                """;

        var contar =contartoken(system, pergunta);
        System.out.println("QTD =" + contar);

        return this.chatClient.prompt()
                .system(system)
                .user(pergunta)
                .options(ChatOptionsBuilder.builder()
                        .withTemperature(0.85)
                        .build())
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .content();
    }
    private int contartoken(String user, String promp){
        var registry = Encodings.newDefaultEncodingRegistry();
        var enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);
        return enc.countTokens(user + promp);
    }

}
