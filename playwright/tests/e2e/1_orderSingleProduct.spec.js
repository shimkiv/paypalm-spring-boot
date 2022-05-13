import { expect, test } from '@playwright/test';
import LoginPage from '../../pageObject/LoginPage.js';
import ProductsPage from '../../pageObject/ProductsPage.js';
import CheckoutTablePage from '../../pageObject/CheckoutTablePage.js';
import PaymentCardPage from '../../pageObject/PaymentCardPage.js';
import PaymentProcessingPage from '../../pageObject/PaymentProcessingPage.js';
import * as consts from '../../testData/constants.js';
import * as functions from '../../helpers/functions.js';

/**
 * Current test was implemented with hardcoded values for the 1-st Product
 */
test('Order Single Product Journey', async ({ page }) => {
  let loginPage = new LoginPage(page);
  let productsPage = new ProductsPage(page);
  let checkoutTablePage = new CheckoutTablePage(page);
  let paymentCardPage = new PaymentCardPage(page);
  let paymentProcessingPage = new PaymentProcessingPage(page);

  console.log('Log in the app');
  await loginPage.login();

  console.log('Select Beer product');
  await productsPage.beerCheckbox.click();
  expect(await productsPage.beerCheckbox.isChecked()).toBeTruthy();
  console.log('    Get Beer price');
  let price = functions.removeSpaces(await productsPage.beerPrice.innerText());
  console.log('    Verify *Total selected products* number is 1');
  let number = await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText());
  expect(number).toBe('1');
  console.log('    Verify *Total amount* equals Product`s price');
  expect(await productsPage.totalAmount.innerText()).toContain(price);
  console.log('    Proceed to the *Payment* page');
  await productsPage.proceedButton.click();

  console.log('*Checkout* table Verifications');
  console.log('    Verify Beer product is displayed and contains valid price');
  expect(await page.locator('//td[.="Beer"]').innerText()).toBe(consts.BEER);
  expect(await functions.removeSpaces(await page.locator('//td[.="Beer"]/..//td[2]').innerText())).toBe(price);
  console.log('    Verify *Total products selected* value is 1');
  let number2 = await functions.leaveNumbersOnly(await checkoutTablePage.totalProductsSelected.innerText());
  expect(number2).toBe(number);
  console.log('    Verify *Total amount* equals Product`s price');
  expect(await checkoutTablePage.totalAmount.innerText()).toContain(price);
  console.log('    Verify *Pay* button contains valid price');
  expect(await paymentCardPage.payButton.innerText()).toContain(price);

  console.log('Fill in Card Number, CVV and click *Pay* button');
  await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
  await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
  await paymentCardPage.payButton.click();

  console.log('Verify *Payment processing* modal is displayed and close it');
  await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();
  await paymentProcessingPage.closeModalButton.click();

  console.log('*Products* page verifications');
  console.log('    Verify Page is displayed in it`s initial state - witghout selections');
  expect(await productsPage.beerCheckbox.isChecked()).toBeFalsy();
  console.log('    Verify *Total selected products* number is 0');
  let number3 = await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText());
  expect(number3).toBe('0');
  console.log('    Verify *Total amount* equals 0');
  expect(await productsPage.totalAmount.innerText()).toContain('$0.00');
});
