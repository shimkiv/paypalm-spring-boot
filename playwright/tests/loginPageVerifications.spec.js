import { expect, test } from '@playwright/test';
import LoginPage from '../pageObject/LoginPage.js';
import ProductsPage from '../pageObject/ProductsPage.js';
import CommonPage from '../pageObject/CommonPage.js';
import * as creds from '../testData/creds.js';

test.describe('Login Page Verifications', () => {
  test('Login Functionality', async ({ page }) => {
    let loginPage = new LoginPage(page);
    let productsPage = new ProductsPage(page);
    let commonPage = new CommonPage(page);

    console.log('Impossible to login with empty *Username/Password* inputs');
    await loginPage.openLoginPage();
    await loginPage.signInButton.click();
    await page.waitForTimeout(1000);
    await expect(await productsPage.productsTable).not.toBeVisible();

    console.log('Impossible to login with empty *Username* input');
    await loginPage.passwordInput.type(creds.PASSWORD_INVALID);
    await loginPage.signInButton.click();
    await page.waitForTimeout(1000);
    await expect(await productsPage.productsTable).not.toBeVisible();

    console.log('Impossible to login with empty *Password* input');
    await loginPage.passwordInput.fill('');
    await loginPage.userNameInput.type(creds.USERNAME_INVALID);
    await loginPage.signInButton.click();
    await page.waitForTimeout(1000);
    await expect(await productsPage.productsTable).not.toBeVisible();

    console.log('Impossible to login with invalid credentials');
    await loginPage.passwordInput.type(creds.PASSWORD_INVALID);
    await loginPage.signInButton.click();
    await expect(await productsPage.productsTable).not.toBeVisible();
    expect(await loginPage.alert.innerText()).toContain('Incorrect account details !');

    console.log('Possible to login with valid credentials');
    await loginPage.login();

    console.log('Possible to logout');
    await commonPage.logout();
  });
});
