import { expect, test } from '@playwright/test';
import LoginPage from '../pageObject/LoginPage.js';
import CommonPage from '../pageObject/CommonPage.js';
import SettingsPage from '../pageObject/SettingsPage.js';
import ProductsPage from '../pageObject/ProductsPage.js';
import PaymentCardPage from '../pageObject/PaymentCardPage.js';

test.describe('Settings Verifications', () => {
  test('Verify User`s details from settings are applied where necessary', async ({ page }) => {
    let loginPage = new LoginPage(page);
    let commonPage = new CommonPage(page);
    let settingsPage = new SettingsPage(page);
    let productsPage = new ProductsPage(page);
    let paymentCardPage = new PaymentCardPage(page);

    console.log('Log in the app');
    await loginPage.login();

    console.log('Open *Settings* modal');
    await commonPage.openSettingsModal();

    console.log('Get User`s details');
    let firstName = await settingsPage.firstNameInput.inputValue();
    let lastName = await settingsPage.lastNameInput.inputValue();
    await settingsPage.closeButton.click();

    console.log('Verify valid Name is displayed on *Products* page');
    expect(await commonPage.userName.innerText()).toContain(firstName);
    expect(await commonPage.userName.innerText()).toContain(lastName);

    console.log('Verify valid Name is displayed on *Checkout* page');
    await productsPage.beerCheckbox.click();
    await productsPage.proceedButton.click();
    expect(await paymentCardPage.cardHolderNameInput.inputValue()).toBe(`${firstName} ${lastName}`);
  });
});
