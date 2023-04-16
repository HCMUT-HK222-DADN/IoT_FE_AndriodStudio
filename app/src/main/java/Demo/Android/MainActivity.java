package Demo.Android;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivityExtended {
    private WebSocketManager webSocketManager;
    private Button Login_button;
    private TextView serverStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ---------------------------- Create buttons and views
        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);
        Login_button = (Button) findViewById(R.id.Button_login);
        serverStatus = findViewById(R.id.serverStatus);

        // ---------------------------- Create new websocket
        webSocketManager = new WebSocketManager(MainActivity.this);
        webSocketManager.start();

        // ---------------------------- Create listener
        Login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginsuccess();
            }
        });
    }

    // ---------------------------- Additional method
    public void Loginsuccess() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
    }
    public void updateServerStatus(String noti) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                serverStatus.setText(noti);
            }
        });
    }
}
