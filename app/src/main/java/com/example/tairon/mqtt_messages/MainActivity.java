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
                new MqttAndroidClient(this.getApplicationContext(), "tcp://10.165.213.61:1883",
                        clientId);

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
                String topic = "foo/bar";
                int qos = 1;
                try {
                    IMqttToken subToken = client.subscribe(topic, qos);
                    subToken.setActionCallback(new IMqttActionListener() {
                        @Override
                        public void onSuccess(IMqttToken asyncActionToken) {
                            // The message was published
                            Log.d(TAG, "Sucesso");


//                            Context context = getApplicationContext();
//                            CharSequence text = "Hello toast!";
//                            int duration = Toast.LENGTH_LONG;
//
//                            Toast toast = Toast.makeText(context, text, duration);
//                            toast.show();

                            //======================CODIGO TOAST  PORTUGUES========
//                            LayoutInflater layoutInflater = getLayoutInflater();
//
//                            int layout = R.layout.activity_main;
//                            ViewGroup viewGroup = (ViewGroup) findViewById(R.id.activity_mai);
//                            View view = layoutInflater.inflate(layout, viewGroup);
//
//                            TextView tv_texto = (TextView) view.findViewById(R.id.editText2);
//                            tv_texto.setText("45212");
//
//                            Toast toast = new Toast(getApplicationContext());
//                            toast.setDuration(Toast.LENGTH_LONG);
//                            toast.setView(view);
//                            toast.show();

                            //==============================

                          //  CÓDIO TOAST INGLES
               // ========================================================================
                            LayoutInflater inflater = getLayoutInflater();
                            View layout = inflater.inflate(R.layout.activity_main,
                                    (ViewGroup) findViewById(R.id.activity_mai));

                            TextView text = (TextView) layout.findViewById(R.id.editText2);
                            text.setText("485214");


                          Toast toast = Toast.makeText(MainActivity.this, text.getText(), Toast.LENGTH_LONG);
                          toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                          toast.show();


//                            Toast toast = (Toast) new Toast(getApplicationContext());
//
//                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                            toast.setDuration(Toast.LENGTH_LONG);
//                            toast.setView(layout);
//                            toast.show();
              //  ==============================================================

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
                    encodedPayload = topic.getBytes("UTF-8");
                    MqttMessage message = new MqttMessage(encodedPayload);
                    client.publish(topic, message);
                    Log.d(TAG, topic);
                    Log.d(TAG, "Mensagem Enviada Com sucesso");

                } catch (UnsupportedEncodingException | MqttException e) {
                    e.printStackTrace();
                }
//
            }
        });




        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {


            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {

//                LayoutInflater inflater = getLayoutInflater();
//                View layout = inflater.inflate(R.layout.activity_main,
//                        (ViewGroup) findViewById(R.id.activity_mai));
//
//                TextView text =(TextView) layout.findViewById(R.id.editText2);
//                text.setText("kjhhhh");

//
               Toast.makeText(MainActivity.this, new String(message.getPayload()), Toast.LENGTH_LONG).show();
               Log.d(TAG, message.toString());
               // Toast.makeText(MainActivity.this, topic, Toast.LENGTH_LONG).show();

               // Toast.makeText(MainActivity.this, text.getText(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
