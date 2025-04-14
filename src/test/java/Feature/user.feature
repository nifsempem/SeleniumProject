Feature: Basketball England Supporter Registration

  Scenario: Successful user registration
    Given I open the registration page
    When I enter "15/06/1995" into the date of birth field
    Then The DOB field should contain "15/06/1995"
    When I enter correct user details
    And I accept the account confirmation terms and conditions
    And I accept the code of ethics terms and conditions
    And I accept age responsibility terms and conditions
    And I submit the form
   #Then The user should be registered successfully

  Scenario: Last name is missing
    Given I open the registration page
    When I enter "15/06/1995" into the date of birth field
    Then The DOB field should contain "15/06/1995"
    When I enter details with missing last name
    And I accept the account confirmation terms and conditions
    And I accept the code of ethics terms and conditions
    And I accept age responsibility terms and conditions
    And I submit the form
    Then An error message for last name should be displayed

  Scenario: Password mismatch
    Given I open the registration page
    When I enter "15/06/1995" into the date of birth field
    Then The DOB field should contain "15/06/1995"
    When I enter mismatched passwords
    And I accept the account confirmation terms and conditions
    And I accept the code of ethics terms and conditions
    And I accept age responsibility terms and conditions
    And I submit the form
    Then An error message for password mismatch should be displayed

  Scenario: Terms and conditions not accepted
    Given I open the registration page
    When I enter "15/06/1995" into the date of birth field
    Then The DOB field should contain "15/06/1995"
    When I enter correct user details
    And I do not accept the terms and conditions
    And I accept the code of ethics terms and conditions
    And I accept age responsibility terms and conditions
    And I submit the form
    Then An error message for terms and conditions should be displayed

  Scenario Outline: Registration attempt with browser
    Given I open the registration page in "<browser>"
    When I enter "15/06/1995" into the date of birth field
    Then The DOB field should contain "15/06/1995"
    When I enter correct user details
    And I accept the account confirmation terms and conditions
    And I accept the code of ethics terms and conditions
    And I accept age responsibility terms and conditions
    And I submit the form
   #Then The user should be registered successfully

    Examples:
      | browser  |
      | chrome   |
      | firefox  |
