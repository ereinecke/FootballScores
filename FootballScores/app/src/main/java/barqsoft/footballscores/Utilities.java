package barqsoft.footballscores;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by yehya khaled on 3/3/2015.  Many updates by Erik Reinecke.
 */
public class Utilities
{
    private static final String LOG_TAG = Utilities.class.getSimpleName();

    public static String getLeague(int league_num)
    {
        Log.d(LOG_TAG, "League#: " + league_num);
        switch (league_num)
        {
            // Added all leagues supported by the API
            case Constants.BUNDESLIGA1 :
                return App.getContext().getString(R.string.bundesliga_1);
            case Constants.BUNDESLIGA2 :
                return App.getContext().getString(R.string.bundesliga_2);
            case Constants.BUNDESLIGA3 :
                return App.getContext().getString(R.string.bundesliga_3);
            case Constants.LIGUE1 :
                return App.getContext().getString(R.string.ligue_1);
            case Constants.LIGUE2 :
                return App.getContext().getString(R.string.ligue_2);
            case Constants.PREMIER_LEAGUE:
                return App.getContext().getString(R.string.premier_league);
            case Constants.PRIMERA_DIVISION :
                return App.getContext().getString(R.string.primera_division);
            case Constants.SEGUNDA_DIVISION :
                return App.getContext().getString(R.string.segunda_division);
            case Constants.SERIE_A :
                return App.getContext().getString(R.string.serie_a);
            case Constants.PRIMEIRA_LIGA :
                return App.getContext().getString(R.string.primeira_liga);
            case Constants.EREDIVISIE :
                return App.getContext().getString(R.string.eredivisie);
            case Constants.CHAMPIONS2015_2016 :
                return App.getContext().getString(R.string.uefa_champions_league);
            default: return App.getContext().getString(R.string.unknown_league);
        }
    }

    public static String getMatchDay(int match_day,int league_num)
    {
        if(league_num == Constants.CHAMPIONS2015_2016)
        {
            if (match_day <= 6)
            {
                return App.getContext().getString(R.string.group_stage_text);
            }
            else if(match_day == 7 || match_day == 8)
            {
                return App.getContext().getString(R.string.first_knockout_round);
            }
            else if(match_day == 9 || match_day == 10)
            {
                return App.getContext().getString(R.string.quarter_final);
            }
            else if(match_day == 11 || match_day == 12)
            {
                return App.getContext().getString(R.string.semi_final);
            }
            else
            {
                return App.getContext().getString(R.string.final_text);
            }
        }
        else
        {
            return App.getContext().getString(R.string.matchday_text) + " : " +
                        String.valueOf(match_day);
        }
    }

    public static String getScores(int home_goals,int awaygoals)
    {
        if(home_goals < 0 || awaygoals < 0)
        {
            return " - ";
        }
        else
        {
            return String.valueOf(home_goals) + " - " + String.valueOf(awaygoals);
        }
    }

    // TODO: Use Glide to get and cache logos.  Image URLs are available from API
    public static int getTeamCrestByTeamName (String teamname) {
        if (teamname == null) {
            return R.drawable.no_icon;
        }
        switch (teamname) { //This is the set of icons that are currently in the app. Feel free to find and add more
            // as you go.
            case "Arsenal London FC":
                return R.drawable.arsenal;
            case "Manchester United FC":
                return R.drawable.manchester_united;
            case "Swansea City":
                return R.drawable.swansea_city_afc;
            case "Leicester City":
                return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC":
                return R.drawable.everton_fc_logo1;
            case "West Ham United FC":
                return R.drawable.west_ham;
            case "Tottenham Hotspur FC":
                return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion":
                return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC":
                return R.drawable.sunderland;
            case "Stoke City FC":
                return R.drawable.stoke_city;
            default:
                return R.drawable.football;
        }
    }

    public static String getDayName(Context context, long dateInMillis) {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.

        Time t = new Time();
        t.setToNow();
        int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
        int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
        if (julianDay == currentJulianDay) {
            return context.getString(R.string.today);
        } else if ( julianDay == currentJulianDay +1 ) {
            return context.getString(R.string.tomorrow);
        }
        else if ( julianDay == currentJulianDay -1)
        {
            return context.getString(R.string.yesterday);
        }
        else
        {
            Time time = new Time();
            time.setToNow();
            // Otherwise, the format is just the day of the week (e.g "Wednesday".
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE",
                    Locale.getDefault());
            return dayFormat.format(dateInMillis);
        }
    }
}

