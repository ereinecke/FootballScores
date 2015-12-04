package barqsoft.footballscores;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yehya khaled on 2/25/2015. Updates by Erik Reinecke.
 */
public class DatabaseContract
{
    public static final class ScoresTable implements BaseColumns
    {
        // Database table constants
        public static final String SCORES_TABLE = "scores_table";
        public static final int COL_DATE        = 1;
        public static final int COL_TIME        = 2;
        public static final int COL_HOME        = 3;
        public static final int COL_AWAY        = 4;
        public static final int COL_LEAGUE      = 5;
        public static final int COL_HOME_GOALS  = 6;
        public static final int COL_AWAY_GOALS  = 7;
        public static final int COL_ID          = 8;
        public static final int COL_MATCHDAY    = 9;

        public static final String DATE_COL         = "date";
        public static final String TIME_COL         = "time";
        public static final String HOME_COL         = "home";
        public static final String AWAY_COL         = "away";
        public static final String LEAGUE_COL       = "league";
        public static final String HOME_GOALS_COL   = "home_goals";
        public static final String AWAY_GOALS_COL   = "away_goals";
        public static final String MATCH_ID_COL     = "match_id";
        public static final String MATCH_DAY_COL    = "match_day";

        // Types
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + Constants.CONTENT_AUTHORITY +
                        "/" + Constants.PATH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + Constants.CONTENT_AUTHORITY +
                        "/" + Constants.PATH;

        public static Uri buildScoreWithLeague()
        {
            return Constants.BASE_CONTENT_URI.buildUpon().appendPath("league").build();
        }

        public static Uri buildScoreWithId()
        {
            return Constants.BASE_CONTENT_URI.buildUpon().appendPath("id").build();
        }

        public static Uri buildScoreWithDate()
        {
            return Constants.BASE_CONTENT_URI.buildUpon().appendPath("date").build();
        }

        // TODO: implement getting scores for a specific team
        public static Uri buildScoreWithTeam()
        {
            return Constants.BASE_CONTENT_URI.buildUpon().appendPath("team").build();
        }
    }
}
