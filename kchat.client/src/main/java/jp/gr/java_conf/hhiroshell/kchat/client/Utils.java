package jp.gr.java_conf.hhiroshell.kchat.client;

class Utils {

    private static final String JSTD_MESSAGE_HEADER = "[JSTD] ";

    static void kchatPrintln(String message) {
        System.out.println(JSTD_MESSAGE_HEADER + message);
    }

}
