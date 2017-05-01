package barqsoft.footballscores;

import android.net.Uri;

/**
 * All constants should be defined here.
 * Exceptions: DatabaseContract.java contains db field names; LOG_TAGS
 */
public class Constants {
    public static final String API_KEY = "bdca269500c94587bf6b8be404459a33";

    // Number of days in the past and future to request.  Number of pager fragments must agree.
    // NOTE: if PAST_DAYS or FUTURE_DAYS are greater than 6, the fragment label in
    // PagerFragment.GetPageTitle will have to be updated to display date rather than day of week
    public static final int PAST_DAYS = 2;
    public static final int FUTURE_DAYS = 2;
    public static final int NUM_PAGES = PAST_DAYS + FUTURE_DAYS + 1;


    /* TODO: These must be updated periodically, as the numbers indicate seasons or tournaments,
     *       not leagues.  Also update FetchScoreService.java, where these are used in a case
     *       statement, in Utilities.java, where we get localized names, and in string.xml for
     *       any languages supported.
     */

    public static final int EUROCHAMP2016      = 424;
    public static final int PREMIER_LEAGUE     = 426;
    public static final int CHAMP_2016_17      = 427;
    public static final int LEAGUE_ONE         = 428;
    public static final int FA_CUP_2016_17     = 429;
    public static final int BUNDESLIGA_1       = 430;
    public static final int BUNDESLIGA_2       = 431;
    public static final int DFB_POKAL_2016_17  = 432;
    public static final int EREDIVISIE         = 433;
    public static final int LIGUE_1            = 434;
    public static final int LIGUE_2            = 435;
    public static final int PRIMERA_DIVISION   = 436;
    public static final int SEGUNDA_DIVISION   = 437;
    public static final int SERIE_A            = 438;
    public static final int PRIMEIRA_LIGA      = 439;
    public static final int CHAMPIONS_2016_17  = 440;
    public static final int SERIE_B            = 441;
    public static final int ENGLISH_NATIONAL   = 442;
    public static final int LEAGUE_TWO         = 443;

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
