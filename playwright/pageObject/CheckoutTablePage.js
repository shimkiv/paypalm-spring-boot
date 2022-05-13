import * as functions from '../helpers/functions.js';

export default class CheckoutTablePage {
  constructor(page) {
    this.page = page;
    this.checkoutTable = page.locator('.table-striped');
    this.products = page.locator('tbody tr td:nth-child(1)');
    this.prices = page.locator('tbody tr td:nth-child(2)');
    this.totalProductsSelected = page.locator('#paymentForm > div:nth-child(2) p:nth-child(1)');
    this.totalAmount = page.locator('#paymentForm > div:nth-child(2) p:nth-child(2)');
  }

  async getAllProductsTitles() {
    let titles = [];
    let productsCount = await this.products.count();
    for (let i = 0; i < productsCount; i++) {
      let title = await this.products.nth(i).innerText();
      titles.push(title);
    }
    return titles;
  }

  async getAllProductsPrices() {
    let totalCost = 0;
    let pricesNumber = await this.prices.count();
    for (let i = 0; i < pricesNumber; i++) {
      let price = parseFloat(await functions.replaceUsdSign(await this.prices.nth(i).innerText()).trim());
      totalCost += price;
    }
    return totalCost;
  }
}
