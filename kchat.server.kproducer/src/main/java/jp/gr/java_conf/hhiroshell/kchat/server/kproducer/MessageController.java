package jp.gr.java_conf.hhiroshell.kchat.server.kproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/topics")
public class MessageController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @RequestMapping(value = "/{topic}", method = RequestMethod.POST)
    public void send (@PathVariable("topic") String topic, @RequestBody String message) {
        System.out.println("topic: " + topic);
        System.out.println("message: " + message);
        kafkaTemplate.send(topic, message);
    }

}
