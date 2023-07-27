Feature: Task One
  Task One feature

  Scenario Outline: List Users
    Given Request parameters are set
    When I send GET request to "<uri>"
    Then Response code should be <statusCode>
    And Print users

    Examples:
      |uri    | statusCode |
      |/users | 200        |

  Scenario Outline: Register Unsuccessful
    Given Request parameters are set
    When I send POST request to "<uri>" with "<email>" and "<password>"
    Then Response code should be <statusCode>
    And Message should be "<message>"

    Examples:
      |uri      | email                   | password          | statusCode  | message                                       |
      |/register| hrvoje.simic@gmail.com  | secret            | 400         | Note: Only defined users succeed registration |
      |/register| no email passed         | secret            | 400         | Missing email or username                     |
      |/register| george.bluth@reqres.in  | no password passed| 400         | Missing password                              |

  Scenario Outline: Delete User
    Given Request parameters are set
    When I send DELETE request to "<uri>"/<userId>
    Then Response code should be <statusCode>

    Examples:
      |uri   | userId | statusCode |
      |/users| 2      | 204        |

  Scenario Outline: Delayed Response
    Given Request parameters are set
    When I send GET request with "<delay>" delay to "<uri>"
    Then Response code should be <statusCode>
    And Check if response took "<delay>"
    And Print users

    Examples:
      |uri    | statusCode | delay |
      |/users | 200        | 5s    |



