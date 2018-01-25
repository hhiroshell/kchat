package jp.gr.java_conf.hhiroshell.kchat.server.kconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequestMapping("/sse")
public class MessageController {

    @Autowired
    private AsyncHelper asyncHelper;

    @KafkaListener(topics = "test", group = "foo")
//    @KafkaListener(topics = "test")
    public void listen(String message) {
        System.out.println("Received Messasge in topic test: " + message);
        SseEmitter.SseEventBuilder event =
                SseEmitter.event().name("test").data(message);
        asyncHelper.sendToAllClients(event);
    }

    @RequestMapping(
            value = "/connect",
            method = RequestMethod.GET)
    public SseEmitter sse() throws IOException {
        System.out.println("connected.");
        return asyncHelper.getSseEmitter();
    }

}
