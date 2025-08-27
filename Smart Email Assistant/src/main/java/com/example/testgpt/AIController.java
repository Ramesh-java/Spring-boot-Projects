package com.example.testgpt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AIController {
    private final OpenService openService;

    public AIController(OpenService openService){
        this.openService = openService;
    }
    @PostMapping("/generate")
    public ResponseEntity<String> generateMessage(@RequestBody Data data){
        String result= openService.GenerateReply(data);
        return ResponseEntity.ok(result);
    }
}