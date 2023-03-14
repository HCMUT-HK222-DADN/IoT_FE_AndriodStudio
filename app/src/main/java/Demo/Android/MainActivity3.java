package Demo.Android;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.DayNightSwitch;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;

public class MainActivity3 extends AppCompatActivity {
    MQTTHelper mqttHelper;
    TextView txtTemp,txtHumi,txtLight,tView,motion;
    SeekBar sBar;
    Button logout;
    DayNightSwitch btnLight ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        motion = findViewById(R.id.motiondetect);
        txtTemp = findViewById(R.id.Temperature);
        txtHumi = findViewById(R.id.Humidity);
        txtLight=findViewById(R.id.light);
        btnLight =  findViewById(R.id.lightswitch);
        tView = (TextView) findViewById(R.id.textview1);
        sBar = (SeekBar) findViewById(R.id.seekBar1);
        logout = (Button) findViewById(R.id.logout);

        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tView.setText(pval + "/" + seekBar.getMax());
                String s = Integer.toString(pval);
                sendDataMQTT("LamVinh/feeds/fan\n",s);

            }
        });
        //Quay ve trang dang nhap
       logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogOut();
            }
        });

        btnLight.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn == true){
                    sendDataMQTT("LamVinh/feeds/button1\n", "1");
                }
                else{
                    sendDataMQTT("LamVinh/feeds/button1\n", "0");
                }
            }
        });
        startMQTT();
    }
    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);
        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this    );
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {

            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST",topic + "---" + message.toString());
                if(topic.contains("humi-info")){
                    txtHumi.setText(message.toString()+"  %");
                }
                else if(topic.contains("temp-info")){
                    txtTemp.setText(message.toString()+"  °C");
                }
                else if(topic.contains("light2")){
                    txtLight.setText(message.toString()+"   lux");
                }
                else if(topic.contains("motion")){
                    if(message.toString().equals("1")){
                        motion.setText("Detected");
                    }
                    else
                        motion.setText("None");

                }
                else if(topic.contains("button1")){
                    if(message.toString().equals("1")){
                        btnLight.setOn(true);
                    }
                    else btnLight.setOn(false);
                }
                else if(topic.contains("fan")){
                    sBar.setProgress(Integer.parseInt(message.toString()));
                    tView.setText((message.toString())+"/" + sBar.getMax());
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
    public void LogOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
