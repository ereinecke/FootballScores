package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/* WidgetProvider for Football Scores widget
 *
 * Created by ereinecke on 11/24/15.
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
            RemoteViews views = new RemoteViews(context.getPackageName(),
                                    R.layout.scores_widget_list_item);
            // TODO: figure out what to use for a button on the view.
            Log.d(LOG_TAG, "Calling setOnClickPendingIntent");
            views.setOnClickPendingIntent(R.id.scores_widget_list_item, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

}
