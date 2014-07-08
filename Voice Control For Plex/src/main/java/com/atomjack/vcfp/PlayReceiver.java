package com.atomjack.vcfp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.atomjack.vcfp.services.PlexSearchService;

public class PlayReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		Logger.d("PLAYRECEIVER");
		Bundle bundle = intent.getExtras();
		String queryText = bundle.getString(VoiceControlForPlexApplication.Intent.ARGUMENTS);
		if(queryText == null && intent.getStringExtra(GoogleSearchApi.KEY_QUERY_TEXT) != null)
			queryText = intent.getStringExtra(GoogleSearchApi.KEY_QUERY_TEXT);

		if(queryText != null && queryText.matches(context.getResources().getString(R.string.pattern_recognition))) {
			queryText = queryText.toLowerCase();
			Intent sendIntent = new Intent(context, PlexSearchService.class);
			sendIntent.setAction("com.atomjack.vcfp.intent.ACTION_SEARCH");
			sendIntent.putExtra(VoiceControlForPlexApplication.Intent.EXTRA_QUERYTEXT, queryText);
			sendIntent.putExtra("ORIGIN", "Tasker");
			sendIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);
			sendIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(sendIntent);
		}
	}
}
