package ro.pub.cs.systems.eim.practicaltest02.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.eim.practicaltest02.general.Constants;
import ro.pub.cs.systems.eim.practicaltest02.general.Utilities;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ClientThread extends Thread {

    private String address;
    private int port;
    private String number1;
    private String number2;
    private String operation;

    private Socket socket;
	private TextView result;

    public ClientThread(
      
            String number1,
            String number2,
            String operation,
            TextView result) {
 
   
        this.number1 = number1;
        this.number2 = number2;
     	this.operation = operation;
        this.result = result;
    }

 

	@Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                printWriter.println(number1);
                printWriter.flush();
                printWriter.println(number2);
                printWriter.flush();
                printWriter.println(operation);
                printWriter.flush();
                String res;
                while ((res = bufferedReader.readLine()) != null) {
                    final String finalResult = res;
                    result.post(new Runnable() {
                        @Override
                        public void run() {
                            result.append(finalResult + "\n");
                        }
                    });
                }
            } else {
                Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            if (Constants.DEBUG) {
                ioException.printStackTrace();
            }
        }
    }

}
