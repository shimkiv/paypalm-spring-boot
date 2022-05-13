export default class StatisticsPage {
  constructor(page) {
    this.page = page;
    this.noDataText = page.locator('.infobox h4');
    this.totalOrders = page.locator('.infobox p:nth-child(1)');
    this.totalAmount = page.locator('.infobox p:nth-child(2)');
    this.returnBackButton = page.locator('#returnBack');
    // Latest order (last row in the table)
    this.latestOrderTitle = page.locator('//tbody/tr[last()]/td[2]');
    this.latestOrderCardType = page.locator('//tbody/tr[last()]/td[5]');
    this.latestOrderCardNumber = page.locator('//tbody/tr[last()]/td[6]');
    this.latestOrderCardAmount = page.locator('//tbody/tr[last()]/td[7]');
  }
}
