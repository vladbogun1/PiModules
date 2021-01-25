package ua.bogun.pi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TgSender {
    private static final String BOT_TOKEN = "1589931079:AAF_H9qAppyQfgft-8Is-Btt3Hxbr2VGyg8";
    private static final StringBuilder WEBSITE = new StringBuilder("https://api.telegram.org/bot" + BOT_TOKEN);

    public static void sendMessage(String chat, String message) {
        try {
            String url = WEBSITE + "/sendMessage?chat_id=" + chat +
                    "&parse_mode=html" +
                    "&text=" + URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            sendURL(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void sendURL(String url) {
        try {
            URL con = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
