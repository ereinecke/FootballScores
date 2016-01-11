package barqsoft.footballscores;

import android.net.Uri;

/**
 * All constants should be defined here.
 * Exceptions: DatabaseContract.java contains db field names; LOG_TAGS
 */
public class Constants {
    public static final String API_KEY = "";

    // Number of days in the past and future to request.  Number of pager fragments must agree.
    // NOTE: if PAST_DAYS or FUTURE_DAYS are greater than 6, the fragment label in
    // PagerFragment.GetPageTitle will have to be updated to display date rather than day of week
    public static final int PAST_DAYS = 2;
    public static final int FUTURE_DAYS = 2;
    public static final int NUM_PAGES = PAST_DAYS + FUTURE_DAYS + 1;

    // Added all leagues supported by the API
    public static final int BUNDESLIGA1        = 394;
    public static final int BUNDESLIGA2        = 395;
    public static final int LIGUE1             = 396;
    public static final int LIGUE2             = 397;
    public static final int PREMIER_LEAGUE     = 398;
    public static final int PRIMERA_DIVISION   = 399;
    public static final int SEGUNDA_DIVISION   = 400;
    public static final int SERIE_A            = 401;
    public static final int PRIMEIRA_LIGA      = 402;
    public static final int BUNDESLIGA3        = 403;
    public static final int EREDIVISIE         = 404;
    public static final int CHAMPIONS2015_2016 = 405;

    // Favorite team.  This should be set in Options, but for now will be hardcoded to FC Barcelona
    public static final String FAVORITE_TEAM   = "FC Barcelona";
    // public static final String FAVORITE_TEAM   = "Atalanta BC";

    // URI building blocks
    public static final String CONTENT_AUTHORITY = "barqsoft.footballscores";
    public static final Uri BASE_CONTENT_URI     = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH = "scores";

    public static final String SEASON_LINK = "http://api.football-data.org/alpha/soccerseasons/";
    public static final String MATCH_LINK  = "http://api.football-data.org/alpha/fixtures/";
    public static final String FIXTURES    = "fixtures";
    public static final String LINKS       = "_links";
    public static final String SOCCER_SEASON = "soccerseason";
    public static final String SELF        = "self";
    public static final String QUERY_TIME_FRAME = "timeFrame";
    public static final String MATCH_DATE  = "date";
    public static final String HOME_TEAM   = "homeTeamName";
    public static final String AWAY_TEAM   = "awayTeamName";
    public static final String RESULT      = "result";
    public static final String HOME_GOALS  = "goalsHomeTeam";
    public static final String AWAY_GOALS  = "goalsAwayTeam";
    public static final String MATCH_DAY   = "matchday";

    public static final int MATCHES = 100;
    public static final int MATCHES_WITH_LEAGUE = 101;
    public static final int MATCHES_WITH_ID = 102;
    public static final int MATCHES_WITH_DATE = 103;
    public static final int MATCHES_WITH_TEAM = 104;

    public static final int SCORES_LOADER = 0;
}
