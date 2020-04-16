package com.bb.mybooksapi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText editBook;
    private TextView titleB;
    private TextView authorB;
    private TextView queryResult;
    private android.net.Network Network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editBook = findViewById(R.id.edittext1);
        titleB = findViewById(R.id.textview1);
        authorB = findViewById(R.id.textview2);
    }

    public void searchBooks(View view) {
        final String queryString = editBook.getText().toString();
        Log.d(TAG, "Searched " + queryString);

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback();

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return;
            }

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkRequest.Builder builder = new NetworkRequest.Builder();

            connectivityManager.registerNetworkCallback(
                    builder.build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onAvailable(Network network) {

                            sendBroadcast(
                                    getConnectivityIntent(false)
                            );
                        }

                        @Override
                        public void onLost(Network network) {
                            sendBroadcast(
                                    getConnectivityIntent(true)
                            );
                        }

                        private Intent getConnectivityIntent(boolean noConnection) {

                            Intent intent = new Intent();

                            intent.setAction("mypackage.CONNECTIVITY_CHANGE");
                            intent.putExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, noConnection);

                            return intent;

                        }
                    });
        }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        return;
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        return;
    }
}