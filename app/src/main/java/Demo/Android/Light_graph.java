package Demo.Android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Light_graph extends AppCompatActivity {
    Button InsertButton;
    EditText Inputview;
    GraphView graphView;
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[0]);
    DBHelper LightHelper;
    SQLiteDatabase sqLiteDatabase;
    SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yy HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_graph);
        //Dòng này nên comment khi demo, dòng xóa hết dữ liệu của bảng
        LightHelper.deleteAllData();
        Inputview = (EditText) findViewById(R.id.input);
        InsertButton =(Button) findViewById(R.id.insertbutton);
        graphView = (GraphView) findViewById(R.id.lightgraph);
        LightHelper = new DBHelper(this);
        sqLiteDatabase = LightHelper.getWritableDatabase();
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(90);


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
        series.resetData(getDataPoint());
        exqInsert();
    }
    private void exqInsert() {
        InsertButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                long xValues = new Date().getTime();
                double yValues = Double.parseDouble(String.valueOf(Inputview.getText()));
                LightHelper.InsertData(xValues,yValues);
                series.resetData(getDataPoint());
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
            }


        });
    }
    private DataPoint[] getDataPoint() {
        String [] comlumns = {"xValues","yValues"};
        Cursor cursor = sqLiteDatabase.query("light",comlumns,null,null,null,null,null);
        DataPoint[] dp = new DataPoint[cursor.getCount()];
        for(int i = 0;i < cursor.getCount();i++){
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getLong(0),cursor.getDouble(1));
        }
        return dp;
    }

}