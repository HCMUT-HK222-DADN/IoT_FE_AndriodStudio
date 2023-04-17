package Demo.Android;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TempGraph extends AppCompatActivityExtended {
    ArcGauge arcGauge;
    GraphView graphView;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    Button back, log, logout;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    private WebSocketManager webSocketManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ---------------- Init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_graph);

        // ---------------- Create object to handle view elements
        graphView = (GraphView) findViewById(R.id.tempgraph);
        back = (Button) findViewById(R.id.back);
        log = (Button) findViewById(R.id.log);
        logout = findViewById(R.id.logout);

        // ---------------- Create Websocket object
        webSocketManager = new WebSocketManager(TempGraph.this);
        webSocketManager.start();

        // ---------------- Init things for graph
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Time");
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Temperature Value");
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(100);
        series.resetData(getDataPoint());
        long xValues = new Date().getTime();
        graphView.getViewport().setMaxX(xValues);
        graphView.addSeries(series);
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double Value,boolean isValueX){
                if(isValueX){
                    return sdf.format(new Date((long)Value));
                }
                else{
                    return super.formatLabel(Value,isValueX);
                }
            }
        });
//        series.resetData(getDataPoint());

        // ---------------- Init things for Gauge
        arcGauge = findViewById(R.id.tempgauge);
        Range range1 = new Range();
        range1.setFrom(0.0);
        range1.setTo(15.0);
        range1.setColor(Color.BLACK);

        Range range2 = new Range();
        range2.setFrom(15.0);
        range2.setTo(35.0);
        range2.setColor(Color.GREEN);

        Range range3 = new Range();
        range3.setFrom(35.0);
        range3.setTo(100.0);
        range3.setColor(Color.RED);

        arcGauge.addRange(range1);
        arcGauge.addRange(range2);
        arcGauge.addRange(range3);
//        arcGauge.setValue(Helper.getLastYValue());

        // ---------------- Init button Listener
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoMainActivity3();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });
    }

    //  ---------------- Specific define methods
    public void gotoMainActivity3() {
        Intent intent = new Intent(this, MainActivity3.class);
        startActivity(intent);
        webSocketManager.closeSocket();
        finish();
    }
    public void LogOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        webSocketManager.closeSocket();
        finish();
    }
}