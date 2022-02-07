module.exports = class AccountDropdown {
    static accountBtnSelector = 'button:has-text("ACCOUNT")';
    static logoutBtnSelector = '[href="/logout"]';

    constructor(page) {
        this.page = page;
        this.accountBtn = page.locator(this.constructor.accountBtnSelector);
        this.logoutBtn = page.locator(this.constructor.logoutBtnSelector);
    }

    /**
     * Performs logout by clicking logout button in account dropdown
     * @returns {Promise<void>}
     */
    async logout() {
        await this.accountBtn.click();
        await this.logoutBtn.click();
    }
};