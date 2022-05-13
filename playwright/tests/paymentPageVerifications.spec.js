import { expect, test } from '@playwright/test';
import LoginPage from '../pageObject/LoginPage.js';
import ProductsPage from '../pageObject/ProductsPage.js';
import PaymentCardPage from '../pageObject/PaymentCardPage.js';
import PaymentProcessingPage from '../pageObject/PaymentProcessingPage.js';
import * as consts from '../testData/constants.js';

test.describe('Payment Page Verifications', () => {
  test('Impossible to pay with empty and invalid Payment Card details', async ({ page }) => {
    let loginPage = new LoginPage(page);
    let productsPage = new ProductsPage(page);
    let paymentCardPage = new PaymentCardPage(page);
    let paymentProcessingPage = new PaymentProcessingPage(page);

    console.log('Log in the app');
    await loginPage.login();

    console.log('Select a product and proceed to the *Payment* page');
    await productsPage.beerCheckbox.click();
    await productsPage.proceedButton.click();
    expect(page.url()).toContain('/account/checkout');

    let cardholderName = await paymentCardPage.cardHolderNameInput.inputValue();

    console.log('Impossible to pay with empty Card Number, CVV and Name');
    await paymentCardPage.cardHolderNameInput.fill('');
    await paymentCardPage.payButton.click();
    await expect(await paymentProcessingPage.paymentProcessingModal).not.toBeVisible();

    console.log('Impossible to pay with empty Card Number, CVV');
    await paymentCardPage.cardHolderNameInput.type(cardholderName);
    await paymentCardPage.payButton.click();
    await expect(await paymentProcessingPage.paymentProcessingModal).not.toBeVisible();

    console.log('Impossible to pay with empty Card Number');
    await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
    await paymentCardPage.payButton.click();
    await expect(await paymentProcessingPage.paymentProcessingModal).not.toBeVisible();

    console.log('Impossible to pay with invalid Card Details');
    await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
    await paymentCardPage.payButton.click();
    await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();

    console.log('Verify that Payment was failed');
    expect(await paymentProcessingPage.errorText.innerText()).toBe(consts.FAILED);
    expect(await paymentProcessingPage.errorReason.innerText()).toBe(consts.REASON);
  });
});
