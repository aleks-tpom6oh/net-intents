package ru.netology.intents;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainService extends Service {

	public static final String inputKey = "input key";

	public static final String answerKey = "answer key";

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			final String extra = intent.getStringExtra(inputKey);

			if ("question".equals(extra)) {
				sendAnswerInBroadcast();
			} else {
				Toast.makeText(MainService.this, "Service agrees with: " + extra, Toast.LENGTH_LONG).show();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void sendAnswerInBroadcast() {
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(MainActivity.MainBroadcastReceiver.ACTION_RESP);
		broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);

		broadcastIntent.putExtra(answerKey, "The answer is 42.");


		// sendBroadcast(broadcastIntent);

		// sendStickyBroadcast(); // Deprecated, should not be used anymore
		// sendOrderedBroadcast(broadcastIntent, null);

		LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
	}
}
