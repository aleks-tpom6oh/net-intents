package ru.netology.intents;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class WebActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		Toast.makeText(this, getIntent().getData().toString(), Toast.LENGTH_LONG).show();

		Toast.makeText(this, getIntent().getStringExtra("some string"), Toast.LENGTH_LONG).show();
	}
}