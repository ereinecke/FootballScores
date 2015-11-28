package barqsoft.footballscores;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import barqsoft.footballscores.DatabaseContract.ScoresTable;

/**
 * Created by yehya khaled on 2/25/2015.
 */
public class ScoresProvider extends ContentProvider
{
    private static ScoresDBHelper mOpenHelper;

    private UriMatcher muriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder ScoreQuery =
            new SQLiteQueryBuilder();
    private static final String SCORES_BY_LEAGUE = ScoresTable.LEAGUE_COL + " = ?";
    private static final String SCORES_BY_DATE   = ScoresTable.DATE_COL + " LIKE ?";
    private static final String SCORES_BY_ID     = ScoresTable.MATCH_ID + " = ?";


    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = Constants.BASE_CONTENT_URI.toString();
        matcher.addURI(authority, null , Constants.MATCHES);
        matcher.addURI(authority, "league" , Constants.MATCHES_WITH_LEAGUE);
        matcher.addURI(authority, "id" , Constants.MATCHES_WITH_ID);
        matcher.addURI(authority, "date" , Constants.MATCHES_WITH_DATE);
        return matcher;
    }

    private int match_uri(Uri uri)
    {
        String link = uri.toString();
        {
           if(link.contentEquals(Constants.BASE_CONTENT_URI.toString()))
           {
               return Constants.MATCHES;
           }
           else if(link.contentEquals(ScoresTable.buildScoreWithDate().toString()))
           {
               return Constants.MATCHES_WITH_DATE;
           }
           else if(link.contentEquals(ScoresTable.buildScoreWithId().toString()))
           {
               return Constants.MATCHES_WITH_ID;
           }
           else if(link.contentEquals(ScoresTable.buildScoreWithLeague().toString()))
           {
               return Constants.MATCHES_WITH_LEAGUE;
           }
        }
        return -1;
    }
    @Override
    public boolean onCreate()
    {
        mOpenHelper = new ScoresDBHelper(getContext());
        return false;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        return 0;
    }

    @Override
    public String getType(Uri uri)
    {
        final int match = muriMatcher.match(uri);
        switch (match) {
            case Constants.MATCHES:
                return ScoresTable.CONTENT_TYPE;
            case Constants.MATCHES_WITH_LEAGUE:
                return ScoresTable.CONTENT_TYPE;
            case Constants.MATCHES_WITH_ID:
                return ScoresTable.CONTENT_ITEM_TYPE;
            case Constants.MATCHES_WITH_DATE:
                return ScoresTable.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri :" + uri );
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        Cursor retCursor;
        //Log.v(FetchScoreTask.LOG_TAG,uri.getPathSegments().toString());
        int match = match_uri(uri);
        //Log.v(FetchScoreTask.LOG_TAG,SCORES_BY_LEAGUE);
        //Log.v(FetchScoreTask.LOG_TAG,selectionArgs[0]);
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(match));
        switch (match)
        {
            case Constants.MATCHES: retCursor = mOpenHelper.getReadableDatabase().query(
                    ScoresTable.SCORES_TABLE, projection,null,null,null,null,sortOrder); break;
            case Constants.MATCHES_WITH_DATE:
                //Log.v(FetchScoreTask.LOG_TAG,selectionArgs[1]);
                //Log.v(FetchScoreTask.LOG_TAG,selectionArgs[2]);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        ScoresTable.SCORES_TABLE, projection, SCORES_BY_DATE, selectionArgs,
                        null, null, sortOrder);
                break;
            case Constants.MATCHES_WITH_ID:
                retCursor = mOpenHelper.getReadableDatabase().query(
                    ScoresTable.SCORES_TABLE, projection, SCORES_BY_ID, selectionArgs,
                    null, null, sortOrder);
                break;
            case Constants.MATCHES_WITH_LEAGUE:
                retCursor = mOpenHelper.getReadableDatabase().query(
                    ScoresTable.SCORES_TABLE, projection, SCORES_BY_LEAGUE, selectionArgs,
                    null, null, sortOrder);
                break;
            default: throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(),uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        return null;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values)
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        //db.delete(ScoresTable,null,null);
        //Log.v(FetchScoreTask.LOG_TAG,String.valueOf(muriMatcher.match(uri)));
        switch (match_uri(uri))
        {
            case Constants.MATCHES:
                db.beginTransaction();
                int returncount = 0;
                try
                {
                    for(ContentValues value : values)
                    {
                        long _id = db.insertWithOnConflict(ScoresTable.SCORES_TABLE, null, value,
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (_id != -1)
                        {
                            returncount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return returncount;
            default:
                return super.bulkInsert(uri,values);
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }
}
