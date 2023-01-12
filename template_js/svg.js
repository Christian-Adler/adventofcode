import {Pos} from "./pos.js";
import {hslToRgbStr} from "./util.js";

export class Svg {
    constructor() {
        this.xMin = Number.MAX_SAFE_INTEGER;
        this.xMax = Number.MIN_SAFE_INTEGER;
        this.yMin = Number.MAX_SAFE_INTEGER;
        this.yMax = Number.MIN_SAFE_INTEGER;
        this.posMap = new Map();
    }

    add(pos, color) {
        const x = pos.x;
        const y = pos.y;
        this.xMax = Math.max(this.xMax, x);
        this.xMin = Math.min(this.xMin, x);
        this.yMax = Math.max(this.yMax, y);
        this.yMin = Math.min(this.yMin, y);

        const p = new Pos(x, y, color ? color : pos.color);

        this.posMap.set(p.toString(), p);
    }

    toSVGStringAged() {
        const startL = 20;
        const endL = 50;
        const startS = 20;
        const endS = 100;
        const startH = 260;
        const endH = 70;

        const steps = this.posMap.size; // Anzahl schritte aus Task...

        const stepL = (endL - startL) / steps;
        const stepS = (endS - startS) / steps;
        const stepH = (endH - startH) / steps;


        let res = '\r\n';

        res += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + (this.xMax - this.xMin + 3) + "\" height=\"" + (this.yMax - this.yMin + 3) + "\">\r\n";
        res += "<rect style=\"fill:#000000;\" width=\"" + (this.xMax - this.xMin + 3) + "\" height=\"" + (this.yMax - this.yMin + 3) + "\" x=\"0\" y=\"0\" />\r\n";
        res += "<g transform=\"translate(1,1)\">\r\n";

        let count = 0;

        for (const pos of this.posMap.values()) {
            let rgb = pos.color;
            if (!rgb) {
                const h = startH + count * stepH;
                const s = startS + count * stepS;
                const l = startL + count * stepL;
                rgb = hslToRgbStr(h, s, l);
                // if (pos.x === 0 && pos.y === 0)
                //     rgb = '#ff0000';
            }
            count++;
            res += "<rect style=\"fill:" + rgb + ";\" width=\"1\" height=\"1\" x=\"" + (pos.x - this.xMin) + "\" y=\"" + (pos.y - this.yMin) + "\" />\r\n";
        }
        res += "</g>\r\n";
        res += "</svg>\r\n";

        return res;
    }

    toSVGString() {
        let res = '\r\n';

        res += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + (this.xMax - this.xMin + 3) + "\" height=\"" + (this.yMax - this.yMin + 3) + "\">\r\n";
        res += "<rect style=\"fill:#000000;\" width=\"" + (this.xMax - this.xMin + 3) + "\" height=\"" + (this.yMax - this.yMin + 3) + "\" x=\"0\" y=\"0\" />\r\n";
        res += "<g transform=\"translate(1,1)\">\r\n";
        for (const pos of this.posMap.values()) {
            let color = pos.color || '#ff0000';
            res += "<rect style=\"fill:" + color + ";\" width=\"1\" height=\"1\" x=\"" + (pos.x - this.xMin) + "\" y=\"" + (pos.y - this.yMin) + "\" />\r\n";
        }
        res += "</g>\r\n";
        res += "</svg>\r\n";

        return res;
    }
}