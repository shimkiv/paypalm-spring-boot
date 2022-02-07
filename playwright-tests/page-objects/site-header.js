module.exports = class SiteHeader {
    static headingSelector = '.list-group-item-heading';

    constructor(page) {
        this.page = page;
        this.heading = page.locator(this.constructor.headingSelector);
    }
};