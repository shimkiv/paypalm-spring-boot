# Getting Started

1. Run the `paypalm-spring-boot` application

2. Install dependencies from `playwright` directory:

```
npm i
```

# Run Tests

1. To run tests use commands from `package.json`.
   To run all tests locally, in headed mode, in 1 thread, use:

```
npm run test-local:headed:1thread
```

To run all tests locally, in headless mode, in 1 thread, use:

```
npm run test-local:headless:1thread
```

2. To run a specific test locally use:

```
npx cross-env ENV=LOCAL playwright test pathToTest --headed --workers=1
```

# Reporting

1. After each run an `index.html` report will be generated in `reports` directory.
   Possible to open it manually or using next command:

```
npx playwright show-report reports
```
