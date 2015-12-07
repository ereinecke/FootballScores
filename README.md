# SuperDuo
Project for Udacity Android Nanodegree.

This repository contains two separate apps:
  - Alexandria - This application gets book information from a Google API. All titles, cover images, and author information come from there.
  - Football Scores - This application displays football match scores for the Premier League, Seria A, the Bundesligas, Primera Division, Segunda Division, the Ligues, Primeira Liga, Eridivisie and the UEFA Champions League. All match data are acquired from football-data.org API. 
  
## Notes for reviewer
  - The football-data.org API key has been stripped out of the submitted code. If no data is appearing, you may have hit the API's limit request.  You can insert your API key in strings.xml at name="api_key" to avoid this issue.
  - I elected not to localize the League names, based on my experience in Mexico, where all the European leagues are covered using the name in its native language.  UEFA Champions League is not translated either.
