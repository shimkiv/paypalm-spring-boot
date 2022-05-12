export default {
  timeout: 30000,
  expect: { timeout: 5000 },
  reporter: [['html', { open: 'never', outputFolder: 'reports' }]],
  use: {
    actionTimeout: 10000,
    viewport: { width: 1024, height: 1080 },
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
  },
};
