package Demo.Android;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class WorkingSessionActivity extends AppCompatActivityExtended {
    Button logout, startSession, skipPeriod;
    TextView countdown;
    ProgressBar progressBar;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        int progress = 15;
        @Override
        public void run() {
            if (progress > 0) {
                progress--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(progress);
                        countdown.setText(String.valueOf(progress));
                    }
                });
            }
        }
    };

    private WebSocketManager webSocketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ---------------- Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workingsession);
        // ---------------- Create object to handle button
        logout = (Button) findViewById(R.id.logout);
        startSession = findViewById(R.id.startSession);
        skipPeriod = findViewById(R.id.skipPeriod);
        progressBar = findViewById(R.id.progressBar);
        countdown = findViewById(R.id.countdown);

        // ---------------- Create Websocket
        webSocketManager = new WebSocketManager(WorkingSessionActivity.this);
        webSocketManager.start();

        // ---------------- Init View



        // ---------------- Init listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
        startSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timer.schedule(timerTask, 0, 1000);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timerTask.cancel();
    }

    //  ---------------- Addition Method
    public void LogOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        webSocketManager.closeSocket();
        finish();
    }

}
