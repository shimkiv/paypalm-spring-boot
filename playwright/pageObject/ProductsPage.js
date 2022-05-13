import { expect } from '@playwright/test';
import * as functions from '../helpers/functions.js';

export default class ProductsPage {
  constructor(page) {
    this.page = page;
    // TABLE
    this.productsTable = page.locator('#productEntityListForm');
    this.checkboxes = page.locator('.check');
    this.products = page.locator('tbody tr td:nth-child(2) a');
    this.prices = page.locator('tbody tr td:nth-child(3)');
    // First Product
    this.firstProductCheckbox = page.locator('tbody tr:nth-child(1) td:nth-child(1) .check');
    this.firstProductId = page.locator('tbody tr:nth-child(1) td:nth-child(1) input');
    this.firstProductTitle = page.locator('tbody tr:nth-child(1) td:nth-child(2) a');
    this.firstProductPrice = page.locator('tbody tr:nth-child(1) td:nth-child(3)');
    // Beer
    this.beerCheckbox = page.locator('//input[@name="DUHDZJHGYY"]/..//span[@class="check"]');
    this.beerProduct = page.locator('//a[@href="/account/statistics/?productId=DUHDZJHGYY"]');
    this.beerPrice = page.locator('//a[@href="/account/statistics/?productId=DUHDZJHGYY"]/../../td[3]');
    // Cigar
    this.cigarCheckbox = page.locator('//input[@name="JDN4ILZB0T"]/..//span[@class="check"]');
    this.cigarProduct = page.locator('//a[@href="/account/statistics/?productId=JDN4ILZB0T"]');
    this.cigarPrice = page.locator('//a[@href="/account/statistics/?productId=JDN4ILZB0T"]/../../td[3]');
    // Suicide booth services
    this.suicideCheckbox = page.locator('//input[@name="M50Z86T758"]/..//span[@class="check"]');
    this.suicideProduct = page.locator('//a[@href="/account/statistics/?productId=M50Z86T758"]');
    this.suicidePrice = page.locator('//a[@href="/account/statistics/?productId=M50Z86T758"]/../../td[3]');
    // One way ticket
    this.oneWayCheckbox = page.locator('//input[@name="M4MY49Z5JB"]/..//span[@class="check"]');
    this.oneWayProduct = page.locator('//a[@href="/account/statistics/?productId=M4MY49Z5JB"]');
    this.oneWayPrice = page.locator('//a[@href="/account/statistics/?productId=M4MY49Z5JB"]/../../td[3]');
    // Ice cream
    this.iceCreamCheckbox = page.locator('//input[@name="DSX9JW5ACM"]/..//span[@class="check"]');
    this.iceCreamProduct = page.locator('//a[@href="/account/statistics/?productId=DSX9JW5ACM"]');
    this.iceCreamPrice = page.locator('tbody tr:nth-child(5) td:nth-child(3)');

    this.totalProductsSelected = page.locator('#productEntityListForm p:nth-child(1)');
    this.totalAmount = page.locator('#productEntityListForm p:nth-child(2)');
    this.proceedButton = page.locator('#productEntityListForm button[type="submit"]');
    this.clearShoppingCartButton = page.locator('#clearShoppingCartButton');
  }

  async selectAllCheckboxes() {
    let checkboxesNumber = await this.checkboxes.count();
    for (let i = 0; i < checkboxesNumber; i++) {
      await this.checkboxes.nth(i).click();
      expect(await this.checkboxes.nth(i).isChecked()).toBeTruthy();
    }
    return checkboxesNumber;
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
