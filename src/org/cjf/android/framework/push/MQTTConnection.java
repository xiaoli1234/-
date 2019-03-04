package org.cjf.android.framework.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttPersistenceException;
import com.ibm.mqtt.MqttSimpleCallback;

public class MQTTConnection implements MqttSimpleCallback {

	public static final String TAG = "PushService";
	// 服务上下文
	private Context mcontext = null;

	private SharedPreferences mPrefs;

	// Ip地址
	private static final String MQTT_HOST = "192.168.1.89";
	// 端口号
	private static int MQTT_BROKER_PORT_NUM = 1883;

	private static MqttPersistence MQTT_PERSISTENCE = null;

	// 客户端ID 发送到服务端 新建对象的时候传过来的
	private static String MQTT_CLIENT_ID = "tokudu";

	public static final String PREF_DEVICE_ID = "deviceID";

	private static boolean MQTT_CLEAN_START = true;

	private static short MQTT_KEEP_ALIVE = 5;



	private static int[] MQTT_QUALITIES_OF_SERVICE = { 0 };

	private static int MQTT_QUALITY_OF_SERVICE = 0;

	private static boolean MQTT_RETAINED_PUBLISH = false;


	private static final long KEEP_ALIVE_INTERVAL = 1000 * 60 * 28;



	// 声明回调接口
	private IPushMsgCallBack mPushMsgCallBack;

	IMqttClient mqttClient = null;

	// Creates a new connection given the broker address and initial topic
	public MQTTConnection(String brokerHostName, String initTopic,
			Context context, IPushMsgCallBack pushmsgcallback)
			throws MqttException {

		mPushMsgCallBack = pushmsgcallback;
		mcontext = context;

		mPrefs = mcontext.getSharedPreferences(TAG, mcontext.MODE_PRIVATE);
		// Create connection spec
		String mqttConnSpec = "tcp://" + brokerHostName + "@"
				+ MQTT_BROKER_PORT_NUM;
		// Create the client and connect
		mqttClient = MqttClient
				.createMqttClient(mqttConnSpec, MQTT_PERSISTENCE);
		String clientID = MQTT_CLIENT_ID + "/"
				+ mPrefs.getString(PREF_DEVICE_ID, "");
		mqttClient.connect(clientID, MQTT_CLEAN_START, MQTT_KEEP_ALIVE);

		// register this client app has being able to receive messages
		mqttClient.registerSimpleHandler(this);

		// Subscribe to an initial topic, which is combination of client ID and
		// device ID.
		initTopic = MQTT_CLIENT_ID + "/" + initTopic;
		subscribeToTopic(initTopic);

		// log("Connection established to " + brokerHostName + " on topic " +
		// initTopic);

		// Save start time
		//mStartTime = System.currentTimeMillis();
		// startKeepAlives();
	}

	// Disconnect
	public void disconnect() {
		try {
			if (mPushMsgCallBack != null) {
				mPushMsgCallBack.disconnect();
			}
			mqttClient.disconnect();
		} catch (MqttPersistenceException e) {
			// log("MqttException" + (e.getMessage() != null?
			// e.getMessage():" NULL"), e);
		}
	}

	/*
	 * Send a request to the message broker to be sent messages published with
	 * the specified topic name. Wildcards are allowed.
	 */
	private void subscribeToTopic(String topicName) throws MqttException {

		if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
			// quick sanity check - don't try and subscribe if we don't have
			// a connection
			// log("Connection error" + "No connection");
		} else {
			String[] topics = { topicName };
			mqttClient.subscribe(topics, MQTT_QUALITIES_OF_SERVICE);
		}
	}

	/*
	 * Sends a message to the message broker, requesting that it be published to
	 * the specified topic.
	 */
	private void publishToTopic(String topicName, String message)
			throws MqttException {
		if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
			// quick sanity check - don't try and publish if we don't have
			// a connection
			// log("No connection to public to");
		} else {
			mqttClient.publish(topicName, message.getBytes(),
					MQTT_QUALITY_OF_SERVICE, MQTT_RETAINED_PUBLISH);
		}
	}

	/*
	 * Called if the application loses it's connection to the message broker.
	 */
	public void connectionLost() throws Exception {
		if (mPushMsgCallBack != null) {
			mPushMsgCallBack.connectionLost();
		}
	}

	/*
	 * Called when we receive a message from the message broker.
	 */
	public void publishArrived(String topicName, byte[] payload, int qos,
			boolean retained) {
		if (mPushMsgCallBack != null) {
			mPushMsgCallBack.publishMsg(payload);
		}
	}

	public void sendKeepAlive() throws MqttException {
		// log("Sending keep alive");
		// publish to a keep-alive topic
		publishToTopic(MQTT_CLIENT_ID + "/keepalive",
				mPrefs.getString(PREF_DEVICE_ID, ""));
	}



	public String getMQTT_CLIENT_ID() {
		return MQTT_CLIENT_ID;
	}

	public void setMQTT_CLIENT_ID(String mQTT_CLIENT_ID) {
		MQTT_CLIENT_ID = mQTT_CLIENT_ID;
	}

}
