package com.example.bluetooth2.service;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import static android.content.ContentValues.TAG;
import static androidx.core.app.ActivityCompat.startActivityForResult;

public class BluetoothConnection extends Service {

    // SPP UUID
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // MAC- Bluetooth
    private static String address = "00:15:FF:F2:19:4C";

    Handler h;
    Context mContext;

    private static final int REQUEST_ENABLE_BT = 1;
    final int RECIEVE_MESSAGE = 1;        //  Handler
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    private ConnectedThread mConnectedThread;


    private int startMode;       // indicates how to behave if the service is killed
    private IBinder binder;      // interface for clients that bind
    private boolean allowRebind; // indicates whether onRebind should be used

    public BluetoothConnection(Activity activity) {
        mContext = activity;
    }

    @Override
    public void onCreate() {
        // The service is being created
        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:                                                    // Handler
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        sb.append(strIncom);                                                //
                        int endOfLineIndex = sb.indexOf("\r\n");                            //
                        if (endOfLineIndex > 0) {                                            //
                            String sbprint = sb.substring(0, endOfLineIndex);                //
                            sb.delete(0, sb.length());                                        //  sb
//                            txtArduino.setText(" Arduino: " + sbprint);            // TextView
//                            btnOff.setEnabled(true);
//                            btnOn.setEnabled(true);
                        }
                        Log.d(TAG, "..:" + sb.toString() + ":" + msg.arg1 + "...");
                        System.err.println("!!!! From bluetooth ..:" + sb.toString() + ":" + msg.arg1 + "...");
                        break;
                }
            }

            ;
        };

        btAdapter = BluetoothAdapter.getDefaultAdapter();        // Bluetooth
        checkBTState();

        onReeeesume();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // The service is starting, due to a call to startService()
//        return mStartMode;
        // If we get killed, after returning from here, restart

        //TODO sendMessage()
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // A client is binding to the service with bindService()
//        return mBinder;
        return null;
    }
//    @Override
//    public boolean onUnbind(Intent intent) {
//        // All clients have unbound with unbindService()
//        return mAllowRebind;
//    }
//    @Override
//    public void onRebind(Intent intent) {
//        // A client is binding to the service with bindService(),
//        // after onUnbind() has already been called
//    }

    @Override
    public void onDestroy() {
        // The service is no longer used and is being destroyed

        super.onDestroy();

        Log.d(TAG, "...In onPause()...");

        try {
            btSocket.close();
        } catch (IOException e2) {
            errorExit("Fatal Error", "In onPause() and failed to close socket." + e2.getMessage() + ".");
        }
    }

    public void sendMessage() {
        mConnectedThread.write("1");    // Bluetooth 1
        Toast.makeText(mContext, "LED", Toast.LENGTH_SHORT).show();
    }

    private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if (btAdapter == null) {
            errorExit("Fatal Error", "Bluetooth ");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult((Activity) mContext, enableBtIntent, REQUEST_ENABLE_BT, null);
            }
        }
    }

    private void errorExit(String title, String message) {
        Toast.makeText(mContext, title + " - " + message, Toast.LENGTH_LONG).show();
//        finish();
    }

    private class ConnectedThread extends Thread {

        final int RECIEVE_MESSAGE = 1;

        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[256];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);        // "buffer"
                    h.obtainMessage(RECIEVE_MESSAGE, bytes, -1, buffer).sendToTarget();        //  Handler
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void write(String message) {
            Log.d(TAG, "...blblblbl msg: " + message + "...");
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                Log.d(TAG, "...Exception blblblbllb msg: " + e.getMessage() + "...");
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }

    }


    private void onReeeesume() { //TODO where?
//        super.onResume();

        Log.d(TAG, "...onResume - ...");

//        Set up a pointer to the remote node using it 's address.
        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        // Two things are needed to make a connection:
        //   A MAC address, which we got above.
        //   A Service ID or UUID.  In this case we are using the
        //     UUID for SPP.
        try {
            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            errorExit("Fatal Error", "In onResume() and socket create failed: " + e.getMessage() + ".");
        }

        // Discovery is resource intensive.  Make sure it isn't going on
        // when you attempt to connect and pass your message.
        btAdapter.cancelDiscovery();

        // Establish the connection.  This will block until it connects.
        Log.d(TAG, "......");
        try {
            btSocket.connect();
            Log.d(TAG, ".....");
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {
                errorExit("Fatal Error", "In onResume() and unable to close socket during connection failure" + e2.getMessage() + ".");
            }
        }

        // Create a data stream so we can talk to server.
        Log.d(TAG, ".. Socket...");

        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
    }
}