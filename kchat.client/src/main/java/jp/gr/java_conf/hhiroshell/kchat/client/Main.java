package jp.gr.java_conf.hhiroshell.kchat.client;

import java.net.URI;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            usage();
            System.exit(1);
        }

        URI producer = null, consumer = null;
        try {
//            producer = URI.create("http://" + args[0] + ":19092/api/topics/test");
//            consumer = URI.create("http://" + args[0] + ":29092/sse/connect");
            producer = URI.create("http://" + args[0] + ":30192/api/topics/chatroom");
            consumer = URI.create("http://" + args[0] + ":30292/sse/connect");
        } catch (IllegalArgumentException e) {
            Utils.kchatPrintln("ERROR: The given uri string violates RFC2396 syntax...");
            System.exit(1);
        }

        String name = args[1];
        if (name == null || name.isEmpty()) {
            Utils.kchatPrintln("ERROR: Please enter you name...");
            usage();
            System.exit(1);
        }
        (new SseClient(producer, consumer, name)).connectAndWait();
    }

    private static void usage() {
        // TODO: implement
        System.out.println("usage");
    }

}
