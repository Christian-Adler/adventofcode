export class Device {
    constructor(element, type) {
        this.element = element;
        this.type = type;
    }

    getMapKey() {
        return `${this.element}-${this.type}`;
    }

    toString() {
        return `${this.element} ${this.type}`;
    }

}

