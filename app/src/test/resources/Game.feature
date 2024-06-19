Feature: Game

  Scenario: Empty League
    Given the league has no players
    When I print the league
    Then I should see "No players yet"

  Scenario: A Full League
      Given I add players to the league
      When I print the league
      Then I should see a correctly displayed league
      When The winner is printed
      Then The winner should be "Neo"

  Scenario: A Win Is Record
      Given I add players to the league
      When A player wins the match
      Then The win is recorded
      When The winner is printed
      Then The winner should be "Joe"

