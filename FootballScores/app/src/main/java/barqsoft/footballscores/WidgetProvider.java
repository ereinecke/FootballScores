package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

/* WidgetProvider for Football Scores widget
 *
 * Created by Erik Reinecke on 11/24/15.
 */

public class WidgetProvider extends AppWidgetProvider {
    final static String LOG_TAG = WidgetProvider.class.getSimpleName();

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                    R.layout.scores_widget_list_item);

            // Update widget.  This should show favorite team, though there's no way for the
            // user to set at this time.

            remoteViews.setTextViewText(R.id.widget_home_name, "Home");
            remoteViews.setTextViewText(R.id.widget_away_name, "Away");
            remoteViews.setTextViewText(R.id.widget_score_textview, "0 - 0");

            Log.d(LOG_TAG, "Calling setOnClickPendingIntent");
            remoteViews.setOnClickPendingIntent(R.id.scores_widget_list_item, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    // query
    private Cursor getLatestTeamScore(int favorite) {

        Cursor retCursor;



        return matchScore;
    }

}
