package ro.pub.cs.systems.eim.practicaltest02.graphicuserinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import ro.pub.cs.systems.eim.practicaltest02.R;
import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.network.ClientThread;
import ro.pub.cs.systems.eim.practicaltest02.network.ServerThread;

public class PracticalTest02MainActivity extends Activity {

	// Server widgets
	private EditText serverPortEditText = null;
	private Button connectButton = null;

	// Client widgets
	private EditText number1 = null;
	private EditText number2 = null;
	private Button mul = null;
	private Button add = null;
	private TextView result = null;

	private ServerThread serverThread = null;
	private ClientThread clientThread = null;

	private String operation = null;

	private ConnectButtonClickListener connectButtonClickListener = new ConnectButtonClickListener();

	private class ConnectButtonClickListener implements Button.OnClickListener {

		@Override
		public void onClick(View view) {
			String serverPort = serverPortEditText.getText().toString();
			if (serverPort == null || serverPort.isEmpty()) {
				Toast.makeText(getApplicationContext(),
						"Server port should be filled!", Toast.LENGTH_SHORT)
						.show();
				return;
			}

			serverThread = new ServerThread(Integer.parseInt(serverPort));
			if (serverThread.getServerSocket() != null) {
				serverThread.start();
			} else {
				Log.e(Constants.TAG,
						"[MAIN ACTIVITY] Could not creat server thread!");
			}

		}
	}

	private OperationListener operationListener = new OperationListener();

	private class OperationListener implements Button.OnClickListener {

		@Override
		public void onClick(View view) {
			String n1 = number1.getText().toString();
			String n2 = number2.getText().toString();
			if (n1 == null || n2 == null || operation == null) {
				Toast.makeText(
						getApplicationContext(),
						"Parameters from client (number1 / number2 / operation) should be filled!",
						Toast.LENGTH_SHORT).show();
				return;
			}

			if (serverThread == null || !serverThread.isAlive()) {
				Log.e(Constants.TAG,
						"[MAIN ACTIVITY] There is no server to connect to!");
				return;
			}

			switch (view.getId()) {
			case R.id.add:
				operation = "add";
			case R.id.mul:
				
			operation = "mul";
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			result.setText(Constants.EMPTY_STRING);

			clientThread = new ClientThread(n1, n2, operation, result);
			clientThread.start();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_main);

		serverPortEditText = (EditText) findViewById(R.id.server_port_edit_text);
		connectButton = (Button) findViewById(R.id.connect_button);
		connectButton.setOnClickListener(connectButtonClickListener);

		number1 = (EditText) findViewById(R.id.number1);
		number2 = (EditText) findViewById(R.id.number2);
		;
		add = (Button) findViewById(R.id.add);
		add.setOnClickListener((OnClickListener) add);
		mul = (Button) findViewById(R.id.mul);
		mul.setOnClickListener((OnClickListener) mul);
	}

	@Override
	protected void onDestroy() {
		if (serverThread != null) {
			serverThread.stopThread();
		}
		super.onDestroy();
	}
}
