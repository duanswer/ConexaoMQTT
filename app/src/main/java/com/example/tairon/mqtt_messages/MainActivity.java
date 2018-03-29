package com.example.tairon.mqtt_messages;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class MainActivity extends AppCompatActivity implements MqttCallback {
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
                new MqttAndroidClient(this.getApplicationContext(), "tcp://10.165.213.61:1883",
                        clientId);
        client.setCallback(this);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                    Log.d(TAG, "Conectado");

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                   Log.d(TAG, "onFailure");
                    Log.d(TAG, "Não Conectado");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
        btnsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String topic = "#";
                int qos = 1;
                try {
                    IMqttToken subToken = client.subscribe(topic, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // The message was published
                            Log.d(TAG, "Sucesso");

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

        btnsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Log.d(TAG, "Preparar Mensagem para Envio");

                //Mensagem a ser enviada
                EditText inputTXT = (EditText) findViewById(R.id.editText3);
                String mens = inputTXT.getText().toString();

                //Tópico da Mensagem a ser enviada
                EditText inputtexto = (EditText) findViewById(R.id.editopico);
                String topico = inputtexto.getText().toString();


                String payload = "the payload";
                byte[] encodedPayload = new byte[0];

                try {
                    encodedPayload = mens.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topico, message);
                    Log.d(TAG,"Tópico: "+ topico + ". Mensagem: " + mens);
                    //Log.d(TAG,"epaaaa "+ topico);
                    Log.d(TAG, "Mensagem Enviada Com sucesso");

                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
            }
        });


    }



    @Override
    public void connectionLost(Throwable cause) {
        Log.d(TAG, "connectionLost");
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.d(TAG, "messageArrived");

       // Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_LONG).show();


        TextView text = (TextView) findViewById(R.id.editText2);
        text.setText(message.toString());
        Log.d(TAG, ""+ topic);
        Toast toast = Toast.makeText(MainActivity.this, text.getText(), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
       // toast.show();

        //===================Topico da mensagem=============================


        TextView text2 = (TextView) findViewById(R.id.editText5);
        text2.setText(topic);
        Log.d(TAG, ""+ topic);
        Toast toast1 = Toast.makeText(MainActivity.this, text2.getText(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER_HORIZONTAL,0,0);

            Log.d(TAG, "" + topic + "" + message.toString());


    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e(TAG, "deliveryComplete:  ");
    }
}
