### Commands

All commands must be executed in console form the root of the `playwright-tests` directory.

All mentioned paths are relative to `playwright-tests` directory.

First time, to install all the dependencies, run `npm install`.

To run automated testing execute `npm test` command (Note that you should have a project already started on your localhost:8080).

For headed run execute `npm run headed` command.

### Test-reports

Project uses allure-reporter.

Reports sources are automatically generated to `allure-report` directory; viewable reports are generated to `allure-results` directory.

To generate a viewable report from sources execute `npm run allure-generate` command.

To see the report in your browser execute `npm run allure-show` command.

### Notes about framework decisions

#### Page Objects
Selectors are used as a static fields inside Page Objects because it prooved to be a usefull practice with larger Page Object hierarchies,
 where you can make parametrised Page Objects and construct them using other PO class selectors (without unnecessary object construction).
Also this gives more freedom of reusing (or hiding) selectors in child class methods,
 and finally it allows to choose more options while creating expectations (for example like in `Login works with right credentials` test).
That's why some additional lines of code in each Page Object seems to be worth of it.

#### Linter
Linter should be added later, but for this task is out-of-scope.
When adding linter one should investigate and find one specific to playwrite/test testrunner,
 so that missed `test.only` and other framework-specific bugs can be found by it.

#### Parallelism
Decision about best way of tests parallelisation is out-of-scope of this task.
And because only one user credentials is listed in projects reedme,
 different workers will create race conditions while running tests under the same user.
That's why workers number is hardcoded to 1 right now.