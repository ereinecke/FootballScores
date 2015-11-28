package barqsoft.footballscores;

/**
 * Created by yehya khaled on 3/3/2015.  Many updates by Erik Reinecke.
 */
public class Utilities
{

    public static String getLeague(int league_num)
    {
        switch (league_num)
        {
            // Added all leagues supported by the API
            case Constants.BUNDESLIGA1 :          return "1. Bundesliga";
            case Constants.BUNDESLIGA2 :          return "2. Bundesliga";
            case Constants.LIGUE1 :               return "Ligue 1";
            case Constants.LIGUE2 :               return "Ligue 2";
            case Constants.PREMIER_LEAGUE:        return "Premier League";
            case Constants.PRIMERA_DIVISION :     return "Primera Division";
            case Constants.SEGUNDA_DIVISION :     return "Segunda Division";
            case Constants.SERIE_A :              return "Serie A";
            case Constants.PRIMEIRA_LIGA :        return "Primeira Liga";
            case Constants.BUNDESLIGA3 :          return "3. Bundesliga";
            case Constants.EREDIVISIE :           return "Eredivisie";
            case Constants.CHAMPIONS2015_2016 :   return "UEFA Champions League";
            default: return "Unknown League please report";
        }
    }
    public static String getMatchDay(int match_day,int league_num)
    {
        if(league_num == Constants.CHAMPIONS2015_2016)
        {
            if (match_day <= 6)
            {
                return "Group Stages, Matchday : 6";
            }
            else if(match_day == 7 || match_day == 8)
            {
                return "First Knockout round";
            }
            else if(match_day == 9 || match_day == 10)
            {
                return "QuarterFinal";
            }
            else if(match_day == 11 || match_day == 12)
            {
                return "SemiFinal";
            }
            else
            {
                return "Final";
            }
        }
        else
        {
            return "Matchday : " + String.valueOf(match_day);
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

    public static int getTeamCrestByTeamName (String teamname)
    {
        if (teamname==null){return R.drawable.no_icon;}
        switch (teamname)
        { //This is the set of icons that are currently in the app. Feel free to find and add more
          // as you go.
            case "Arsenal London FC" : return R.drawable.arsenal;
            case "Manchester United FC" : return R.drawable.manchester_united;
            case "Swansea City" : return R.drawable.swansea_city_afc;
            case "Leicester City" : return R.drawable.leicester_city_fc_hd_logo;
            case "Everton FC" : return R.drawable.everton_fc_logo1;
            case "West Ham United FC" : return R.drawable.west_ham;
            case "Tottenham Hotspur FC" : return R.drawable.tottenham_hotspur;
            case "West Bromwich Albion" : return R.drawable.west_bromwich_albion_hd_logo;
            case "Sunderland AFC" : return R.drawable.sunderland;
            case "Stoke City FC" : return R.drawable.stoke_city;
            default: return R.drawable.football;
        }
    }
}
