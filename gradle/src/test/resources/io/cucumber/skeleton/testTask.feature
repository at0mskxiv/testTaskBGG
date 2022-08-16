Feature: Belly

  Scenario: a few cukes
    Given Open homepage of the site
    When Navigate to the page of the most top game with increasing rank in "The Hotness" left side menu
    When Retrieve information about a particular game from site api
    When Parse response to get most voted option in Language Dependence poll
    Then Assert Language Dependence text presented in the game page Description block equals the most voted Language Dependence level
