export class Pos {
    constructor(x, y, color) {
        this.x = x;
        this.y = y;
        this.color = color || '#000000';
    }

    toString() {
        return `${this.x},${this.y}`;
    }

    add(x, y) {
        this.x += x;
        this.y += y;
    }

    addToNewPos(x, y) {
        return new Pos(this.x + x, this.y + y);
    }
}

