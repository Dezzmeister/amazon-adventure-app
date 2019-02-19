package toob.boob.kylgrrcvr.rcvrthrdz;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient extends AsyncTask<Void, String, Void> {
    private final TextView textView;
    private final String ipAddress;
    private final int port;
    private volatile boolean isRunning = true;
    private InetAddress serverAddress = null;
    private Socket socket = null;
    private BufferedReader in = null;
    private String serverMessageIn = "";

    private final StringBuilder builder;

    public TCPClient(final TextView _textView, String _ipAddress, int _port) {
        textView = _textView;
        ipAddress = _ipAddress;
        port = _port;
        builder = new StringBuilder("");

        try {
            serverAddress = InetAddress.getByName(ipAddress);
            socket = new Socket(serverAddress, port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Void doInBackground(Void... nothing) {

        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (isRunning) {
                serverMessageIn = in.readLine();

                if (serverMessageIn != null) {
                    builder.append(serverMessageIn);
                    publishProgress(builder.toString());
                }
                serverMessageIn = null;
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(builder.toString() + "\nTCP CLIENT: A SOCKET ERROR HAS OCCURRED");
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... strings) {
        super.onProgressUpdate(strings);
        textView.setText(strings[0]);
    }
}