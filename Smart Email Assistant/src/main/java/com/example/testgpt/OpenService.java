package com.example.testgpt;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;


@Service
public class OpenService {

    private final WebClient webClient;
    @Autowired
    public OpenService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }




    @Value("${gemini.api.url}")
    private   String API_URL;
    @Value("${gemini.api.key}")
    private String API_KEY;

    public String GenerateReply(Data data) {

         

        // create a prompt
        String prompt = generatePrompt(data);
        //create a request
        Map<String, Object> request = Map.of(
                "contents", new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", prompt)
                        })
                }
        );
        //Do request and get response
        String response=webClient.post()
                .uri(API_URL+API_KEY)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return extractResponse(response);
    }

    private String extractResponse(String response) {

        try {
            ObjectMapper mapper=new ObjectMapper();
            JsonNode root=mapper.readTree(response);
            return root.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text")
                    .asText();

        }catch (Exception e){
            return "error at line 48"
;        }
    }

    private String generatePrompt(Data data) {
        StringBuilder prompt=new StringBuilder();
        prompt.append("You are an AI email assistant. When given an incoming email, your task is to generate a professional, polite, and contextually appropriate reply. Adjust the style, tone, and level of formality of your response based on the tone of the received email (e.g., friendly, urgent, formal, casual, positive, negative). Ensure the reply is clear, concise, and helpful while maintaining the intended tone ,here is your content of the mail that you are going to generate a reply based on the tone given \n").append(data.content);
        if (data.tone!=null && !data.tone.isEmpty()){
            prompt.append("\n in this tone = ").append(data.tone);
        }
        return prompt.toString();
    }
}