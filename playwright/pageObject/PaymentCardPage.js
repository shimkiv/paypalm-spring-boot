export default class PaymentCardPage {
  constructor(page) {
    this.page = page;
    this.title = page.locator('#paymentForm h3');
    this.visaRadiobutton = page.locator('//input[@id="visa"]/../span[1]');
    this.mastercardRadiobutton = page.locator('//input[@id="mastercard"]/../span[1]');
    this.cardNumberInput = page.locator('#cardNumber');
    this.expMonthSelect = page.locator('#expMonth');
    this.expYearSelect = page.locator('#expYear');
    this.cvvInput = page.locator('#cardCvv');
    this.cardHolderNameInput = page.locator('#cardHolderName');
    this.payButton = page.locator('#payButton');
    this.returnBackButton = page.locator('#returnBack');
  }
}
