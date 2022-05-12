import { expect } from '@playwright/test';
import ProductsPage from './ProductsPage.js';
import * as creds from '../testData/creds.js';
import * as urls from '../testData/urls.js';

export default class LoginPage {
  constructor(page) {
    this.page = page;
    this.heading = page.locator('.form-signin-heading');
    this.userNameInput = page.locator('#userName');
    this.passwordInput = page.locator('#userPassword');
    this.signInButton = page.locator('button[type="submit"]');
    this.alert = page.locator('.alert');
  }

  async login() {
    const productsPage = new ProductsPage(this.page);
    let url, username, password;
    switch (process.env.ENV) {
      case 'LOCAL':
        url = urls.LOCAL;
        username = creds.USERNAME_LOCAL;
        password = creds.PASSWORD_LOCAL;
        break;
      case 'STAGE':
        url = urls.STAGE;
        username = creds.USERNAME_STAGE;
        password = creds.PASSWORD_STAGE;
        break;
    }
    await this.page.goto(url);
    await this.userNameInput.type(username, { delay: 100 });
    await this.passwordInput.type(password, { delay: 100 });
    await this.signInButton.click();
    await expect(await productsPage.productsTable).toBeVisible();
  }

  async openLoginPage() {
    let url;
    switch (process.env.ENV) {
      case 'LOCAL':
        url = urls.LOCAL;
        break;
      case 'STAGE':
        url = urls.STAGE;
        break;
    }
    await this.page.goto(url);
  }

  async loginWithSpecificCreds(username, password) {
    this.openLoginPage();
    await this.userNameInput.type(username, { delay: 100 });
    await this.passwordInput.type(password, { delay: 100 });
    await this.signInButton.click();
  }
}
