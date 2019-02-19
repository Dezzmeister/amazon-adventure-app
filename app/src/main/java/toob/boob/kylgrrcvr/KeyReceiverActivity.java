package toob.boob.kylgrrcvr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import toob.boob.kylgrrcvr.rcvrthrdz.TCPClient;

public class KeyReceiverActivity extends AppCompatActivity {
    private String ipAddress;
    private int port;

    private TextView textView;

    private TCPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_receiver);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.save);
        fab.setOnClickListener(v -> {
            Snackbar.make(v, "Log saved! (NOT IMPLEMENTED YET)", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

        });

        Bundle extras = getIntent().getExtras();
        ipAddress = extras.getString("ip");
        port = extras.getInt("port");

        textView = findViewById(R.id.keyTextView);

        client = new TCPClient(textView, ipAddress, port);
        client.execute();
    }
}
