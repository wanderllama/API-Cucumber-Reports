Feature:

  @j
  Scenario Outline: test
    Given a <requestType> request is prepared for <uri> uri and <endpoint> endpoint
    And the <queryParamKey> parameter key and <queryParamValue> parameter value was added to the request
#    And the <body> is added to the request
    When the <requestType> request is sent
    Then the response will have <responseCode> code
    And the response <schema> will match


    Examples:
      | requestType | endpoint | uri    | schema | responseCode | queryParamKey | queryParamValue |
      | GET         | BOOKING  | booker |        | 200          |               |                 |