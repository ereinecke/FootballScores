package barqsoft.footballscores.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Vector;

import barqsoft.footballscores.Constants;
import barqsoft.footballscores.DatabaseContract.ScoresTable;
import barqsoft.footballscores.R;

/**
 * Created by yehya khaled on 3/2/2015.  Many updates by Erik Reinecke.
 */
public class FetchScoreService extends IntentService
{
    public static final String LOG_TAG = "FetchScoreService";

    public FetchScoreService()
    {
        super("FetchScoreService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        getData("n" + Constants.FUTURE_DAYS);  // Next days
        getData("p" + Constants.PAST_DAYS);    // Previous days
    }

    private void getData (String timeFrame)
    {
        //Creating fetch URL
        Uri fetch_build = Uri.parse(Constants.MATCH_LINK).buildUpon().
                appendQueryParameter(Constants.QUERY_TIME_FRAME, timeFrame).build();
        Log.v(LOG_TAG, "The url we are looking at is: "+fetch_build.toString()); //log spam
        HttpURLConnection m_connection = null;
        BufferedReader reader = null;
        String JSON_data = null;
        // Opening Connection
        try {
            URL fetch = new URL(fetch_build.toString());
            m_connection = (HttpURLConnection) fetch.openConnection();
            m_connection.setRequestMethod("GET");
            m_connection.addRequestProperty("X-Auth-Token",getString(R.string.api_key));
            m_connection.connect();

            // Read the input stream into a String
            InputStream inputStream = m_connection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            JSON_data = buffer.toString();
        }
        catch (Exception e)
        {
            Log.e(LOG_TAG,"Exception here" + e.getMessage());
        }
        finally {
            if(m_connection != null)
            {
                m_connection.disconnect();
            }
            if (reader != null)
            {
                try {
                    reader.close();
                }
                catch (IOException e)
                {
                    Log.e(LOG_TAG,"Error Closing Stream");
                }
            }
        }
        try {
            if (JSON_data != null) {
                //This bit is to check if the data contains any matches. If not, we call processJson on the dummy data
                JSONArray matches = new JSONObject(JSON_data).getJSONArray("fixtures");
                if (matches.length() == 0) {
                    //if there is no data, call the function on dummy data
                    //this is expected behavior during the off season.
                    processJSONdata(getString(R.string.dummy_data), getApplicationContext(), false);
                    return;
                }


                processJSONdata(JSON_data, getApplicationContext(), true);
            } else {
                //Could not Connect
                Log.d(LOG_TAG, "Could not connect to server.");
            }
        }
        catch(Exception e)
        {
            Log.e(LOG_TAG,e.getMessage());
        }
    }
    private void processJSONdata (String JSONdata,Context mContext, boolean isReal)
    {
        //JSON data
        // Modified code to use league definitions in Constants.java

        //Match data
        String League = null;
        String mDate = null;
        String mTime = null;
        String Home = null;
        String Away = null;
        String Home_goals = null;
        String Away_goals = null;
        String match_id = null;
        String match_day = null;

        try {
            JSONArray matches = new JSONObject(JSONdata).getJSONArray(Constants.FIXTURES);

            //ContentValues to be inserted
            Vector<ContentValues> values = new Vector <ContentValues> (matches.length());
            for(int i = 0;i < matches.length();i++)
            {

                JSONObject match_data = matches.getJSONObject(i);
                League = match_data.getJSONObject(Constants.LINKS)
                        .getJSONObject(Constants.SOCCER_SEASON).getString("href");
                League = League.replace(Constants.SEASON_LINK,"");
                // This if statement controls which leagues we're interested in the data from.
                // add leagues here in order to have them be added to the DB.
                // If you are finding no data in the app, check that this contains all the leagues.
                // If it doesn't, that can cause an empty DB, bypassing the dummy data routine.
                if( League.equals(Constants.BUNDESLIGA1)         ||
                    League.equals(Constants.BUNDESLIGA2)         ||
                    League.equals(Constants.LIGUE1)              ||
                    League.equals(Constants.LIGUE2)              ||
                    League.equals(Constants.PREMIER_LEAGUE)      ||
                    League.equals(Constants.PRIMERA_DIVISION)    ||
                    League.equals(Constants.SEGUNDA_DIVISION)    ||
                    League.equals(Constants.SERIE_A)             ||
                    League.equals(Constants.PRIMEIRA_LIGA)       ||
                    League.equals(Constants.BUNDESLIGA3)         ||
                    League.equals(Constants.EREDIVISIE)          ||
                    League.equals(Constants.CHAMPIONS2015_2016) )
                {
                    match_id = match_data.getJSONObject(Constants.LINKS)
                            .getJSONObject(Constants.SELF).getString("href");
                    match_id = match_id.replace(Constants.MATCH_LINK, "");
                    if(!isReal){
                        //This if statement changes the match ID of the dummy data so that it all goes into the database
                        match_id=match_id+Integer.toString(i);
                    }

                    mDate = match_data.getString(Constants.MATCH_DATE);
                    mTime = mDate.substring(mDate.indexOf("T") + 1, mDate.indexOf("Z"));
                    mDate = mDate.substring(0,mDate.indexOf("T"));
                    SimpleDateFormat match_date = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
                    match_date.setTimeZone(TimeZone.getTimeZone("UTC"));
                    try {
                        Date parseddate = match_date.parse(mDate+mTime);
                        SimpleDateFormat new_date = new SimpleDateFormat("yyyy-MM-dd:HH:mm");
                        new_date.setTimeZone(TimeZone.getDefault());
                        mDate = new_date.format(parseddate);
                        mTime = mDate.substring(mDate.indexOf(":") + 1);
                        mDate = mDate.substring(0,mDate.indexOf(":"));

                        if(!isReal){
                            //This if statement changes the dummy data's date to match our current date range.
                            Date fragmentdate = new Date(System.currentTimeMillis()+((i-2)*86400000));
                            SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
                            mDate=mformat.format(fragmentdate);
                        }
                    }
                    catch (Exception e)
                    {
                        Log.d(LOG_TAG, "error here!");
                        Log.e(LOG_TAG,e.getMessage());
                    }
                    Home = match_data.getString(Constants.HOME_TEAM);
                    Away = match_data.getString(Constants.AWAY_TEAM);
                    Home_goals = match_data.getJSONObject(Constants.RESULT).
                            getString(Constants.HOME_GOALS);
                    Away_goals = match_data.getJSONObject(Constants.RESULT).
                            getString(Constants.AWAY_GOALS);
                    match_day = match_data.getString(Constants.MATCH_DAY);
                    ContentValues match_values = new ContentValues();
                    match_values.put(ScoresTable.MATCH_ID,match_id);
                    match_values.put(ScoresTable.DATE_COL,mDate);
                    match_values.put(ScoresTable.TIME_COL,mTime);
                    match_values.put(ScoresTable.HOME_COL,Home);
                    match_values.put(ScoresTable.AWAY_COL,Away);
                    match_values.put(ScoresTable.HOME_GOALS_COL,Home_goals);
                    match_values.put(ScoresTable.AWAY_GOALS_COL,Away_goals);
                    match_values.put(ScoresTable.LEAGUE_COL,League);
                    match_values.put(ScoresTable.MATCH_DAY,match_day);

                    // Log.v(LOG_TAG,match_id + "; " + mDate + "; " + mTime + "; " + Home  + "; " +
                    //      Away + "; " + Home_goals + "; " + Away_goals);

                    values.add(match_values);
                }
            }
            ContentValues[] insert_data = new ContentValues[values.size()];
            values.toArray(insert_data);
            int inserted_data = mContext.getContentResolver().bulkInsert(
                    Constants.BASE_CONTENT_URI,insert_data);

            // Log.v(LOG_TAG,"Succesfully Inserted : " + String.valueOf(inserted_data));
        }
        catch (JSONException e)
        {
            Log.e(LOG_TAG,e.getMessage());
        }

    }
}
