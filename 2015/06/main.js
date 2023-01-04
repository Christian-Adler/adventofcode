import {input0, input1} from "./input.js";
import {out, string2SplitLinesArray} from "./util.js";
import {Pos} from "./pos.js";
import {Svg} from "./svg.js";

const useExample = 0;
const showSVG = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task1 = () => {
    const xMin = 0;
    const xMax = 999;
    const yMin = 0;
    const yMax = 999;

    const active = new Set();

    const lines = string2SplitLinesArray(input);
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i];

        const operation = (line.includes('turn on') ? 1 : (line.includes('turn off') ? -1 : 0));

        const positions = line.replaceAll('turn on ', '').replaceAll('toggle ', '').replaceAll('turn off ', '').split(' through ');
        const topLeft = Pos.posFrom(positions[0]);
        const bottomRight = Pos.posFrom(positions[1]);

        for (let y = topLeft.y; y <= bottomRight.y; y++) {
            for (let x = topLeft.x; x <= bottomRight.x; x++) {
                const p = new Pos(x, y).toString();
                if (operation > 0)
                    active.add(p);
                else if (operation < 0)
                    active.delete(p);
                else {
                    if (active.has(p))
                        active.delete(p);
                    else
                        active.add(p);
                }
            }
        }
    }

    out("active lights: ", active.size);

    if (showSVG) {

        const svg = new Svg();

        for (let y = yMin; y <= yMax; y++) {
            for (let x = xMin; x <= xMax; x++) {
                const p = new Pos(x, y);
                if (active.has(p.toString())) {
                    svg.add(p);
                }
            }
        }

        document.getElementById('out').innerHTML = svg.toSVGStringAged();
    }
};
const task2 = () => {
    const xMin = 0;
    const xMax = 999;
    const yMin = 0;
    const yMax = 999;

    const active = new Map();

    const lines = string2SplitLinesArray(input);
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i];

        const operation = (line.includes('turn on') ? 1 : (line.includes('turn off') ? -1 : 0));

        const positions = line.replaceAll('turn on ', '').replaceAll('toggle ', '').replaceAll('turn off ', '').split(' through ');
        const topLeft = Pos.posFrom(positions[0]);
        const bottomRight = Pos.posFrom(positions[1]);

        for (let y = topLeft.y; y <= bottomRight.y; y++) {
            for (let x = topLeft.x; x <= bottomRight.x; x++) {
                const p = new Pos(x, y).toString();
                if (operation > 0) {
                    let soFar = active.get(p);
                    if (typeof soFar === 'number')
                        soFar++;
                    else
                        soFar = 1;
                    active.set(p, soFar);
                } else if (operation < 0) {
                    let soFar = active.get(p);
                    if (typeof soFar === 'number')
                        soFar--;
                    else
                        soFar = 0;
                    if (soFar <= 0)
                        active.delete(p);
                    else
                        active.set(p, soFar);
                } else {
                    let soFar = active.get(p);
                    if (typeof soFar === 'number')
                        soFar += 2;
                    else
                        soFar = 2;
                    active.set(p, soFar);
                }
            }
        }
    }

    out("active lights: ", active.size);

    // sumBrightness
    let maxValue = 0;
    let sumBrightness = 0;
    for (const value of active.values()) {
        sumBrightness += value;
        maxValue = Math.max(maxValue, value);
    }

    out('sumBrightness: ', sumBrightness);
    out('maxValue: ', maxValue);


    if (showSVG) {

        const svg = new Svg();

        for (let y = yMin; y <= yMax; y++) {
            for (let x = xMin; x <= xMax; x++) {
                const p = new Pos(x, y);
                if (active.has(p.toString())) {
                    const brightness = active.get(p.toString());
                    // if (brightness > 40) out('brightness: ', brightness);
                    let color = (brightness * 4).toString(16);
                    if (color.length < 2)
                        color = '0' + color;
                    p.color = '#' + color + color + color;
                    // if (brightness > 40) out(p.color);
                    svg.add(p);
                }
            }
        }

        document.getElementById('out2').innerHTML = svg.toSVGString();
        // downloadText("aoc6.svg", svg.toSVGString());
    }
};

task1();
task2();
