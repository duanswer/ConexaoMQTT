package com.example.tairon.mqtt_messages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private Button btnsub;
    private Button btnsen;
    private final String TAG = "mainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsub = (Button) findViewById(R.id.btnsubscribe);
        btnsen = (Button) findViewById(R.id.btnsend);
        String clientId = MqttClient.generateClientId();
      final  MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), "tcp://10.165.213.24:1883",
                        clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                   Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "foo/bar";
                int qos = 1;
                try {
                    IMqttToken subToken = client.subscribe(topic, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // The message was published
                            Log.d(TAG, "Sucesso");
            //aqui================================================//===================================================
                            //String topic = "foo/bar";
                            String topic = "Entrei";
//                            String payload = "the payload";
//                            byte[] encodedPayload = new byte[0];
//                            try {
//                                encodedPayload = payload.getBytes("UTF-8");
//                                MqttMessage message = new MqttMessage(encodedPayload);
//                                client.publish(topic, message);
//                                Log.d(TAG, topic);
//                            } catch (UnsupportedEncodingException | MqttException e) {
//                                e.printStackTrace();
//                            }
                            //aqui===================================//===================================================

                        }

                        @Override
                        public void onFailure(IMqttToken asyncActionToken,
                                              Throwable exception) {
                            // The subscription could not be performed, maybe the user was not
                            // authorized to subscribe on the specified topic e.g. using wildcards
                            Log.d(TAG, "Falhou");

                        }
                    });
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
        //=========================================================//==========================================================
        btnsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d(TAG, "Preparar Mensagem para Envio");
                EditText inputTXT = (EditText) findViewById(R.id.editText3);
                String topic = inputTXT.getText().toString();
                String payload = "the payload";
                byte[] encodedPayload = new byte[0];
                try {
                    encodedPayload = payload.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                    Log.d(TAG, topic);
                    Log.d(TAG, "Mensagem Enviada Com sucesso");
                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }

            }
        });


        //=========================================================//==========================================================
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
