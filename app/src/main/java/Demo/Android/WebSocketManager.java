package Demo.Android;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class WebSocketManager extends WebSocketListener {
    private WebSocket webSocket;
    private AppCompatActivityExtended activity;
    private final String SOCKET_URL = "ws://192.168.1.223:8000/ws/test_FE_Home/";

    public WebSocketManager(AppCompatActivityExtended activity) {
        this.activity = activity;
    }

    public void start() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(SOCKET_URL).build();
        this.webSocket = client.newWebSocket(request, this);
        client.dispatcher().executorService().shutdown();
    }
    // Function that send message
    public void sendMessage(String message) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Type", message);
            Log.w("WebSocket", "Send Success.");
            String jsonString = jsonObject.toString();
            this.webSocket.send(jsonString);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    // Function that called when open the socket
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        // WebSocket connection opened
        this.activity.displayIncomingText("Connected!");
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        // Received message in JSON
        try {
            JSONObject jsonObject = new JSONObject(text);
            Log.w("WebSocket", "WebSocket Received JSON File success.");
            String messageType = jsonObject.optString("Type");
            switch (messageType) {
                case "UpdateSenSor":
                    this.activity.updateSensorValue(jsonObject);
                    break;
                default: break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        // Received message in bytes
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        // WebSocket connection closing
        webSocket.close(1000, null);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        // WebSocket connection closed
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        // WebSocket connection failure
        Log.e("WebSocket", "Connection failure: " + t.getMessage(), t);
    }

}

