package toob.boob.kylgrrcvr;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final String DEFAULT_IP = "0.0.0.0";
    /**
     * Matches valid IP addresses
     */
    private static final Pattern ipVerifierPattern = Pattern.compile("(\\d{1,3}[\\.]){3}\\d{1,3}");
    private static final int DEFAULT_PORT = 24601;

    private String ipAddress = DEFAULT_IP;
    private int port = DEFAULT_PORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        EditText ipInput = findViewById(R.id.ip_input);

        Button ipSubmit = findViewById(R.id.ipSubmit);
        ipSubmit.setOnClickListener(v -> {
            ipAddress = ipInput.getText().toString();

            String ipToastMsg = "IP set to \"" + ipAddress + "\"";
            Matcher matcher = ipVerifierPattern.matcher(ipAddress);

            boolean matched = matcher.find();
            if (!matched || (matched && !ipAddress.equals(matcher.group(0)))) {
                ipToastMsg = "Not a valid IP Address! Defaulting to \"" + DEFAULT_IP + "\"";
                ipAddress = DEFAULT_IP;
            }

            Toast toast = Toast.makeText(getApplicationContext(), ipToastMsg, Toast.LENGTH_SHORT);
            toast.show();
        });

        EditText portInput = findViewById(R.id.port_input);

        Button portSubmit = findViewById(R.id.portSubmit);
        portSubmit.setOnClickListener(v -> {
           port = Integer.parseInt(portInput.getText().toString());

           String portToastMsg = "Port set to \"" + port + "\"";
           if (port < 0 || port > 65535) {
               portToastMsg = "Not a valid TCP port! Defaulting to \"" + DEFAULT_PORT + "\"";
               port = DEFAULT_PORT;
           }
           Toast toast = Toast.makeText(getApplicationContext(), portToastMsg, Toast.LENGTH_SHORT);
           toast.show();
        });

        Button connectButton = findViewById(R.id.connectButton);
        connectButton.setOnClickListener(v -> {
           Intent intent = new Intent(MainActivity.this, KeyReceiverActivity.class);
           intent.putExtra("port", port);
           intent.putExtra("ip", ipAddress);

           startActivity(intent);
        });
    }
}
