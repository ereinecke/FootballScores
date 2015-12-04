package barqsoft.footballscores;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/* WidgetProvider for Football Scores widget
 *
 * Created by Erik Reinecke on 11/24/15.
 */

public class WidgetProvider extends AppWidgetProvider {
    private final static String LOG_TAG = WidgetProvider.class.getSimpleName();

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int appWidgetId : appWidgetIds) {
            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener to the button
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.scores_widget_list_item);

            // Update widget with the most recent score for FAVORITE_TEAM or most recent completed
            // match.
            Cursor latestMatch = getLatestMatch(Constants.FAVORITE_TEAM);
            logCursor(latestMatch, latestMatch.getPosition());
            remoteViews.setTextViewText(R.id.widget_home_name,
                    latestMatch.getString(DatabaseContract.ScoresTable.COL_HOME));
            remoteViews.setTextViewText(R.id.widget_away_name,
                    latestMatch.getString(DatabaseContract.ScoresTable.COL_AWAY));
            remoteViews.setTextViewText(R.id.widget_score_textview,
                    Utilities.getScores(latestMatch.getInt(DatabaseContract.ScoresTable.COL_HOME_GOALS),
                            latestMatch.getInt(DatabaseContract.ScoresTable.COL_AWAY_GOALS)));
            remoteViews.setTextViewText(R.id.widget_league,
                    Utilities.getLeague(latestMatch.getInt(DatabaseContract.ScoresTable.COL_LEAGUE)));


            long dateInMillis = dateToMillis(App.getContext(),
                    latestMatch.getString(DatabaseContract.ScoresTable.COL_DATE));
            String dayName = Utilities.getDayName(App.getContext(), dateInMillis);
            remoteViews.setTextViewText(R.id.widget_date, dayName);

            remoteViews.setOnClickPendingIntent(R.id.scores_widget_list_item, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    // Return a cursor with the most recent completed match for the widget
    public Cursor getLatestMatch(String team) {

        String select = "null";
        String order = DatabaseContract.ScoresTable.DATE_COL + " DESC";
        ScoresProvider scoresProvider = new ScoresProvider();
        Cursor retCursor = scoresProvider.query(Constants.BASE_CONTENT_URI, null, select, null, order);

        // Get most recent match with a score
        if (retCursor != null) {
            retCursor.moveToFirst();
            while (retCursor.getString(DatabaseContract.ScoresTable.COL_HOME_GOALS).equals("-1")) {
                if (!retCursor.moveToNext()) break;
            }
            // if we don't find favorite team, use most recently completed match
            int lastMatch = retCursor.getPosition();
            // now look for last match with favorite team
            while (!retCursor.getString(DatabaseContract.ScoresTable.COL_HOME).equals(team) &&
                   !retCursor.getString(DatabaseContract.ScoresTable.COL_AWAY).equals(team)) {
                if (!retCursor.isLast()) {

                    retCursor.moveToNext();
                } else {
                    retCursor.moveToPosition(lastMatch);
                    break;
                }
            }
        } else Log.d(LOG_TAG, "Latest Match: null");

        return retCursor;
    }

    // Log contents of cursor at position.  If position is < 0, log all
    private void logCursor(Cursor cursor, int position) {
        if (position > cursor.getCount() - 1) {
            Log.d(LOG_TAG, "Cursor count: " + cursor.getCount());
            return;
        }
        else if (position < 0) { // log all
            cursor.moveToFirst();
            // Log.d(LOG_TAG, "Logging all cursor entries. (Position: " + position + ")" );
            while (!cursor.isLast()) {
                Log.d(LOG_TAG, "Match date: " +
                    cursor.getString(DatabaseContract.ScoresTable.COL_DATE) +
                    ", " + cursor.getString(DatabaseContract.ScoresTable.COL_HOME) +
                    " v. " + cursor.getString(DatabaseContract.ScoresTable.COL_AWAY) + "; " +
                    Utilities.getScores(cursor.getShort(DatabaseContract.ScoresTable.COL_HOME_GOALS),
                            cursor.getShort(DatabaseContract.ScoresTable.COL_AWAY_GOALS)) + "; " +
                    Utilities.getLeague(cursor.getShort(DatabaseContract.ScoresTable.COL_LEAGUE)));
                cursor.moveToNext();
            }
        } else {    // single row
            cursor.moveToPosition(position);
            Log.d(LOG_TAG, "Match date: " +
                    cursor.getString(DatabaseContract.ScoresTable.COL_DATE) +
                    ", " + cursor.getString(DatabaseContract.ScoresTable.COL_HOME) +
                    " v. " + cursor.getString(DatabaseContract.ScoresTable.COL_AWAY) + "; " +
                    Utilities.getScores(cursor.getShort(DatabaseContract.ScoresTable.COL_HOME_GOALS),
                            cursor.getShort(DatabaseContract.ScoresTable.COL_AWAY_GOALS)) + "; " +
                    Utilities.getLeague(cursor.getShort(DatabaseContract.ScoresTable.COL_LEAGUE)));
        }
    }

    private long dateToMillis(Context context, String date) {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.

        if (date == null) return -1l;
        Date day;

        try {
            day = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            return -1l;
        }

        return day.getTime();
    }

}
