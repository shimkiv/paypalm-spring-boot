import { expect, test } from '@playwright/test';
import LoginPage from '../pageObject/LoginPage.js';
import ProductsPage from '../pageObject/ProductsPage.js';
import CheckoutTablePage from '../pageObject/CheckoutTablePage.js';
import PaymentCardPage from '../pageObject/PaymentCardPage.js';
import PaymentProcessingPage from '../pageObject/PaymentProcessingPage.js';
import StatisticsPage from '../pageObject/StatisticsPage.js';
import * as consts from '../testData/constants.js';
import * as functions from '../helpers/functions.js';

/**
 * Current test was implemented without hardcoded Product values
 */
test('Order Single Product Journey', async ({ page }) => {
  let loginPage = new LoginPage(page);
  let productsPage = new ProductsPage(page);
  let checkoutTablePage = new CheckoutTablePage(page);
  let paymentCardPage = new PaymentCardPage(page);
  let paymentProcessingPage = new PaymentProcessingPage(page);
  let statisticsPage = new StatisticsPage(page);

  console.log('Log in the app');
  await loginPage.login();

  console.log('Get values of the 1-st product');
  let productId = await productsPage.firstProductId.inputValue();
  let productTitle = await productsPage.firstProductTitle.innerText();
  let productPrice = parseFloat(
    functions
      .replaceUsdSign(await productsPage.firstProductPrice.innerText())
      .split(':')
      .pop()
  );
  console.log('productId=' + productId);
  console.log('productTitle=' + productTitle);
  console.log('productPrice=' + productPrice);

  /**
   * If Statistics is empty
   * We need to make an order to see data
   */
  console.log('Verify content on *Statistics* page');
  await productsPage.firstProductTitle.click();
  expect(page.url()).toContain(`account/statistics/?productId=${productId}`);
  if ((await statisticsPage.noDataText.isVisible()) === true) {
    await statisticsPage.returnBackButton.click();
    console.log('Need to make an order to see data in Statistics section');
    await productsPage.firstProductCheckbox.click();
    await productsPage.proceedButton.click();
    await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
    await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
    await paymentCardPage.payButton.click();
    await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();
    await paymentProcessingPage.closeModalButton.click();
  }

  console.log('Get initial details from *Statistics* page');
  let totalOrdersInitial = parseInt(functions.leaveNumbersOnly(await statisticsPage.totalOrders.innerText()));
  let totalAmountInitital = parseFloat(
    functions
      .replaceUsdSign(await statisticsPage.totalAmount.innerText())
      .split(':')
      .pop()
  );
  await statisticsPage.returnBackButton.click();

  //-------VISA-------//
  console.log('Make one more Order using *Visa*');
  await productsPage.firstProductCheckbox.click();
  await productsPage.proceedButton.click();
  await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
  await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
  await paymentCardPage.payButton.click();
  console.log('    Verify *Payment processing* modal is displayed and close it');
  await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();
  await paymentProcessingPage.closeModalButton.click();

  console.log('Open *Statistics* page');
  await productsPage.firstProductTitle.click();
  console.log('    Get updated details from *Statistics* page');
  let totalOrdersUpd = parseInt(functions.leaveNumbersOnly(await statisticsPage.totalOrders.innerText()));
  let totalAmountUpd = parseFloat(
    functions
      .replaceUsdSign(await statisticsPage.totalAmount.innerText())
      .split(':')
      .pop()
  );
  expect(totalOrdersUpd).toBe(totalOrdersInitial + 1);
  expect(totalAmountUpd).toBe(totalAmountInitital + productPrice);
  console.log('    Verify the latest order details in the table');
  expect(await statisticsPage.latestOrderTitle.innerText()).toContain(productTitle);
  expect(await statisticsPage.latestOrderCardType.innerText()).toBe(consts.VISA);
  expect(await statisticsPage.latestOrderCardNumber.innerText()).toBe('************1111');
  expect(await statisticsPage.latestOrderCardAmount.innerText()).toContain(productPrice.toString());
  await statisticsPage.returnBackButton.click();

  //-------MASTERCARD-------//
  console.log('Make one more Order using *Mastercard*');
  await productsPage.firstProductCheckbox.click();
  await productsPage.proceedButton.click();
  await paymentCardPage.mastercardRadiobutton.click();
  await paymentCardPage.cardNumberInput.type(consts.NUMBER_INVALID);
  await paymentCardPage.cvvInput.type(consts.CVV_INVALID);
  await paymentCardPage.payButton.click();
  console.log('    Verify *Payment processing* modal is displayed and close it');
  await expect(await paymentProcessingPage.paymentProcessingModal).toBeVisible();
  await paymentProcessingPage.closeModalButton.click();

  console.log('Open *Statistics* page');
  await productsPage.firstProductTitle.click();
  console.log('    Get updated details from *Statistics* page');
  let totalOrdersUpd2 = parseInt(functions.leaveNumbersOnly(await statisticsPage.totalOrders.innerText()));
  let totalAmountUpd2 = parseFloat(
    functions
      .replaceUsdSign(await statisticsPage.totalAmount.innerText())
      .split(':')
      .pop()
  );
  expect(totalOrdersUpd2).toBe(totalOrdersInitial + 2);
  expect(totalAmountUpd2).toBe(totalAmountInitital + productPrice * 2);
  console.log('    Verify the latest order details in the table');
  expect(await statisticsPage.latestOrderTitle.innerText()).toContain(productTitle);
  expect(await statisticsPage.latestOrderCardType.innerText()).toBe(consts.MASTERCARD);
  expect(await statisticsPage.latestOrderCardNumber.innerText()).toBe('************1111');
  expect(await statisticsPage.latestOrderCardAmount.innerText()).toContain(productPrice.toString());
});
