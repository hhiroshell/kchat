package jp.gr.java_conf.hhiroshell.kchat.client;

import org.glassfish.jersey.media.sse.EventInput;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Console;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SseClient {

    private final URI producer, consumer;

    private final String name;

    SseClient(URI producer, URI consumer, String name) {
        this.producer = producer;
        this.consumer = consumer;
        this.name = name;
    }

    void connectAndWait() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(() -> {
            Client client = ClientBuilder.newBuilder().register(new SseFeature()).build();
            WebTarget target = client.target(consumer);
            EventInput connection = null;
            while (true) {
                if (connection == null || connection.isClosed()) {
                    // (re)connect
                    connection = target.request().get(EventInput.class);
                    connection.setChunkType("text/event-stream");
                }
                final InboundEvent inboundEvent = connection.read();
                if (inboundEvent != null) {
                    onMessage(inboundEvent.readData());
                }
            }
        });

        try {
            while (true) {
                Console c = System.console();
                String s = null;
                if (c != null) {
                    s = c.readLine(name +": "); // 1
                }
                final Client client = ClientBuilder.newClient();
                // TODO: handle error
                Response result = client.target(producer)
                        .request()
                        .post(Entity.entity(name + ": " + s, MediaType.TEXT_PLAIN_TYPE));

            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void onMessage(String message) {
        if (message.startsWith(name + ": ")) {
            return;
        }
        System.out.print("            \r");
        System.out.println(message);
        System.out.print(name + ": ");
    }

}
