import {out, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";
import {Pos} from "./pos.js";
import {Svg} from "./svg.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

const screenSizeX = useExample ? 7 : 50;
const screenSizeY = useExample ? 3 : 6;

const task1 = () => {
    const map = new Map();
    const lines = string2SplitLinesArray(input);
    for (const line of lines) {
        if (line.startsWith('rect')) {
            const aXb = line.split(" ")[1].split('x');
            const a = parseInt(aXb[0]);
            const b = parseInt(aXb[1]);
            for (let y = 0; y < b; y++) {
                for (let x = 0; x < a; x++) {
                    const pos = new Pos(x, y);
                    map.set(pos.toString(), pos);
                }
            }
        } else if (line.startsWith('rotate column')) {
            const aBYb = line.replaceAll('rotate column x=', '').split(" by ");
            const a = parseInt(aBYb[0]);
            const b = parseInt(aBYb[1]);
            const moves = [];
            for (let y = 0; y < screenSizeY; y++) {
                const pos = new Pos(a, y);
                if (map.delete(pos.toString()))
                    moves.push(pos);
            }
            for (const move of moves) {
                const newPos = new Pos(a, (move.y + b) % screenSizeY);
                map.set(newPos.toString(), newPos);
            }
        } else if (line.startsWith('rotate row')) {
            const aBYb = line.replaceAll('rotate row y=', '').split(" by ");
            const a = parseInt(aBYb[0]);
            const b = parseInt(aBYb[1]);
            const moves = [];
            for (let x = 0; x < screenSizeX; x++) {
                const pos = new Pos(x, a);
                if (map.delete(pos.toString()))
                    moves.push(pos);
            }
            for (const move of moves) {
                const newPos = new Pos((move.x + b) % screenSizeX, a);
                map.set(newPos.toString(), newPos);
            }
        }

        outMap(map);
    }

    out('lit: ', map.size);

    const svg = new Svg();
    for (const pos of map.values()) {
        svg.add(pos);
    }
    document.getElementById('out').innerHTML = svg.toSVGString();
};

function outMap(map) {
    out('----');
    for (let y = 0; y < screenSizeY; y++) {
        let line = 'y' + y + ': ';
        for (let x = 0; x < screenSizeX; x++) {
            const pos = new Pos(x, y);
            if (map.has(pos.toString()))
                line += '#';
            else
                line += '.';
        }
        out(line);
    }
}


const task2 = () => {

};

task1();
task2();
