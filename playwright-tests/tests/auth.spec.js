const {test, expect} = require('@playwright/test');
const SiteHeader = require('../page-objects/site-header');
const LoginPage = require('../page-objects/login-page');
const paths = require('../test-data/paths');

const PLEASE_SIGN_IN = 'Please sign in';

test.describe('With logged-in state', () => {
    test.use({ storageState: `${paths.applicationState}/logged-in.json` });

    test('Login works with right credentials', async ({ page }) => {
        await page.goto('/');
        expect(await page.innerText(SiteHeader.headingSelector)).toBe('Welcome back,');
    });
});

test.describe('With logged-out state and wrong credentials', () => {

    let loginPage;

    test.beforeEach(async ({page}) => {
        await page.goto('/');
        loginPage = new LoginPage(page);
        await loginPage.loginAs({username: 'foo', password: 'bar'});
    });

    test('Cannot login', async () => {
        await expect(loginPage.signInHeading).toContainText(PLEASE_SIGN_IN);
    });

    test('Shows alert', async () => {
        await expect(loginPage.incorrectCredentialsAlert).toContainText('Incorrect account details');
    });

    test('Can close alert', async () => {
        await loginPage.incorrectCredentialsAlertCloseBtn.click();
        await expect(loginPage.incorrectCredentialsAlert).not.toBeVisible();
    });
});

test.describe('With logged-out state', () => {

    let loginPage;

    test.beforeEach(async ({page}) => {
        await page.goto('/');
        loginPage = new LoginPage(page);
    });

    test('Cannot login without username', async () => {
        await loginPage.loginAs({username: 'foo'});
        await expect(loginPage.signInHeading).toContainText(PLEASE_SIGN_IN);
    });

    test('Cannot login without password', async () => {
        await loginPage.loginAs({password: 'bar'});
        await expect(loginPage.signInHeading).toContainText(PLEASE_SIGN_IN);
    });
});