package Demo.Android;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WorkingScheduleActivity extends AppCompatActivityExtended {
    Button logout, startSchedule;
    ListView list_view;
    private WebSocketManager webSocketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ---------------- Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workingschedule);
        // ---------------- Create object to handle button
        logout = (Button) findViewById(R.id.logout);
        startSchedule = findViewById(R.id.startSchedule);
        list_view = findViewById(R.id.list_view);

        // ---------------- Create Websocket
        webSocketManager = new WebSocketManager(WorkingScheduleActivity.this);
        webSocketManager.start();

        // ---------------- Init View
        List<WorkingScheduleData> dataList = new ArrayList<>();
        dataList.add(new WorkingScheduleData(1));
        dataList.add(new WorkingScheduleData(2));
        dataList.add(new WorkingScheduleData(3));
        dataList.add(new WorkingScheduleData(4));
        WorkingScheduleAdapter adapter = new WorkingScheduleAdapter(this, dataList);
        list_view.setAdapter(adapter);
        list_view.setChoiceMode(ListView.CHOICE_MODE_SINGLE);


        // ---------------- Init listener
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
        startSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoWorkingSession();
            }
        });
    }
    //  ---------------- Addition Method
    public void LogOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        webSocketManager.closeSocket();
        finish();
    }
    public void gotoWorkingSession() {
        Intent intent = new Intent(this, WorkingSessionActivity.class);
        startActivity(intent);
        webSocketManager.closeSocket();
        finish();
    }
}
