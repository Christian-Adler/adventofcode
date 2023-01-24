export class Pos {
    constructor(x, y, color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    toString() {
        return `${this.x},${this.y}`;
    }

    add(x, y) {
        this.x += x;
        this.y += y;
    }

    addPos(other) {
        this.x += other.x;
        this.y += other.y;
    }

    addToNew(x, y) {
        return new Pos(this.x + x, this.y + y);
    }

    multToNew(x, y) {
        return new Pos(this.x * x, this.y * y);
    }

    addPosToNew(other) {
        return new Pos(this.x + other.x, this.y + other.y);
    }

    equals(other) {
        return this.x === other.x && this.y === other.y;
    }

    manhattenDist(other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }
}

