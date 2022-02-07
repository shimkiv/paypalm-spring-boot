const {chromium} = require('@playwright/test');
const {baseURL} = require('./test-data/url');
const {username, password} = require('./test-data/credentials').default;
const LoginPage = require('./page-objects/login-page');

module.exports = async () => {

    // saving the context of logged-in default user
    const browser = await chromium.launch();
    const page = await browser.newPage();
    await page.goto(baseURL);
    const loginPage = new LoginPage(page);
    await loginPage.loginAs({username, password});
    await page.context().storageState({path: 'application-state/logged-in.json'});
    await browser.close();

};