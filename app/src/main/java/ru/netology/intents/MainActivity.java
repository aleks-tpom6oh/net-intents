package ru.netology.intents;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

	private BroadcastReceiver mainBroadcastReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent serviceIntent = new Intent(MainActivity.this, MainService.class);

				serviceIntent.putExtra(MainService.inputKey, "statement");

				startService(serviceIntent);
			}
		});

		findViewById(R.id.question_button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent serviceIntent = new Intent(MainActivity.this, MainService.class);

				serviceIntent.putExtra(MainService.inputKey, "question");

				startService(serviceIntent);
			}
		});

		findViewById(R.id.open_netology).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// System implicit intents examples https://developer.android.com/guide/components/intents-common
				Uri netologyWebpage = Uri.parse("https://www.netology.ru");
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_BROWSABLE);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(netologyWebpage);
				intent.putExtra("some string", "Some String!");
				if (intent.resolveActivity(getPackageManager()) != null) {
					startActivity(intent);
				}
			}
		});

		mainBroadcastReceiver = new MainBroadcastReceiver();
	}

	@Override
	protected void onStart() {
		super.onStart();

		IntentFilter filter = new IntentFilter(MainBroadcastReceiver.ACTION_RESP);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		filter.setPriority(1);

	//	registerReceiver(mainBroadcastReceiver,filter); // Register global broadcast receiver

		LocalBroadcastManager.getInstance(this).registerReceiver(mainBroadcastReceiver, filter); // Register broadcast receiver inside our app
	}

	@Override
	protected void onStop() {
		super.onStop();

	//	unregisterReceiver(mainBroadcastReceiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mainBroadcastReceiver);
	}

	class MainBroadcastReceiver extends BroadcastReceiver {
		public static final String ACTION_RESP =
				"ru.netology.itnents.mainBroadcastReceiverAction";

		@Override
		public void onReceive(Context context, Intent intent) {
			String text = intent.getStringExtra(MainService.answerKey);
			if (text != null) {
				Snackbar.make(findViewById(R.id.fab), text, Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		}
	}
}