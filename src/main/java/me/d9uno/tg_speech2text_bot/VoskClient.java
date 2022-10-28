package me.d9uno.tg_speech2text_bot;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;

public class VoskClient {
    private CountDownLatch recieveLatch;
    private String webSocketUri;

    public VoskClient(String webSocketUri) {
        this.webSocketUri = webSocketUri;
    }

    public String recognize(File sourceFile) {
        var words = new ArrayList<String>();
        try {
            var ws = new WebSocketFactory().createSocket(webSocketUri);
            ws.addListener(new WebSocketAdapter() {
                @Override
                public void onTextMessage(WebSocket websocket, String message) {
                    words.add(message);
                    recieveLatch.countDown();
                }
            });
            ws.connect();

            var fis = new FileInputStream(sourceFile);
            try (var dis = new DataInputStream(fis)) {
                var buffer = new byte[8000];
                while (true) {
                    int nbytes = dis.read(buffer);
                    if (nbytes < 0) break;
                    recieveLatch = new CountDownLatch(1);
                    ws.sendBinary(buffer);
                    recieveLatch.await();
                }
            }

            recieveLatch = new CountDownLatch(1);
            ws.sendText("{\"eof\" : 1}");
            recieveLatch.await();
            ws.disconnect();
        } catch (IOException | InterruptedException | WebSocketException e) {
            e.printStackTrace();
        }

        return String.join(" ", words);
    }
}
