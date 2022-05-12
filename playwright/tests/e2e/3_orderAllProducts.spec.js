import { expect, test } from '@playwright/test';
import LoginPage from '../../pageObject/LoginPage.js';
import ProductsPage from '../../pageObject/ProductsPage.js';
import CheckoutTablePage from '../../pageObject/CheckoutTablePage.js';
import PaymentCardPage from '../../pageObject/PaymentCardPage.js';
import PaymentProcessingPage from '../../pageObject/PaymentProcessingPage.js';
import * as consts from '../../testData/constants.js';
import * as functions from '../../helpers/functions.js';

test('Order All Products Journey', async ({ page }) => {
  let loginPage = new LoginPage(page);
  let productsPage = new ProductsPage(page);
  let checkoutTablePage = new CheckoutTablePage(page);
  let paymentCardPage = new PaymentCardPage(page);
  let paymentProcessingPage = new PaymentProcessingPage(page);

  console.log('Log in the app');
  await loginPage.login();

  let productsNumberProductsPage, productsArrayProductsPage, totalPriceProductsPage;
  let productsArrayCheckoutPage, totalPriceCheckoutPage;

  console.log('Verify *Products* page');
  console.log('    Select All products');
  productsNumberProductsPage = await productsPage.selectAllCheckboxes();
  console.log('    productsNumberProductsPage=' + productsNumberProductsPage);
  console.log('    Get all products titles');
  productsArrayProductsPage = await productsPage.getAllProductsTitles();
  console.log('    Get all products prices');
  totalPriceProductsPage = await productsPage.getAllProductsPrices();
  console.log('    totalPriceProductsPage=' + totalPriceProductsPage);
  console.log('    Verify *Total selected products* number');
  let number = parseInt(await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText()));
  expect(number).toBe(productsNumberProductsPage);
  console.log('    Verify *Total amount* price');
  expect(await productsPage.totalAmount.innerText()).toContain(totalPriceProductsPage.toString());
  console.log('    Proceed to the *Payment* page');
  await productsPage.proceedButton.click();

  console.log('Verify *Checkout* table');
  console.log('    Get all products titles');
  productsArrayCheckoutPage = await checkoutTablePage.getAllProductsTitles();
  console.log('    *Checkout* table contains all selected products');
  expect(productsArrayCheckoutPage).toEqual(productsArrayProductsPage);
  console.log('    Get all products prices');
  totalPriceCheckoutPage = await checkoutTablePage.getAllProductsPrices();
  console.log('    totalPriceCheckoutPage=' + totalPriceCheckoutPage);
  console.log('    *Checkout* table contains valid sum');
  expect(totalPriceCheckoutPage).toEqual(totalPriceProductsPage);
  console.log('    Verify *Total products selected* value equals Products number');
  let number2 = parseInt(await functions.leaveNumbersOnly(await checkoutTablePage.totalProductsSelected.innerText()));
  expect(number2).toBe(number);
  console.log('    Verify *Total amount* equals Products price');
  expect(await checkoutTablePage.totalAmount.innerText()).toContain(totalPriceProductsPage.toString());

  console.log('Fill in Card Number, CVV and click *Pay* button');
  await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
  await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
  await paymentCardPage.payButton.click();

  console.log('Verify *Payment processing* modal is displayed and close it');
  await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();
  await paymentProcessingPage.closeModalButton.click();

  console.log('*Products* page verifications');
  console.log('    Verify *Products* page is displayed in it`s initial state - witghout selections');
  expect(await productsPage.beerCheckbox.isChecked()).toBeFalsy();
  console.log('    Verify *Total selected products* number is 0');
  let number3 = await functions.leaveNumbersOnly(await productsPage.totalProductsSelected.innerText());
  expect(number3).toBe('0');
  console.log('    Verify *Total amount* equals 0');
  expect(await productsPage.totalAmount.innerText()).toContain('$0.00');
});
