import { expect } from '@playwright/test';
import LoginPage from './LoginPage.js';

export default class CheckoutTablePage {
  constructor(page) {
    this.page = page;
    this.heading = page.locator('.row-content h4');
    this.userName = page.locator('.row-content i');
    this.accountButton = page.locator('.dropdown-toggle');
    this.settingsButton = page.locator('[data-target="#settings-dialog"]');
    this.logoutButton = page.locator('[href="/logout"]');

    // Table
    this.tableTitle = page.locator('h3:nth-child(1)');
    this.currencyTitle = page.locator('h3:nth-child(2)');
  }

  async logout() {
    const loginPage = new LoginPage(this.page);
    await this.accountButton.click();
    await this.logoutButton.click();
    await expect(await loginPage.signInButton).toBeVisible();
  }

  async openSettingsModal() {
    await this.accountButton.click();
    await this.settingsButton.click();
  }
}
