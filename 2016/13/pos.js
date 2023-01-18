export class Pos {
    constructor(x, y, color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    static parse(inp) {
        const parts = inp.split(',');
        return new Pos(parseInt(parts[0]), parseInt(parts[1]));
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

    equals(other) {
        return this.x === other.x && this.y === other.y;
    }
}

