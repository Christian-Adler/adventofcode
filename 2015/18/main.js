import {hslToRgbStr, out, string2Array, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";
import {Pos} from "./pos.js";
import {Svg} from "./svg.js";

const useExample = 0;

const grid = new Map();
const activeSinceStep = new Map();

const input = (useExample ? input0 : input1);
const animate = 1;
const animateDelay = 50;
const useTask2 = 1;
const maxCount = (useExample ? (useTask2 ? 5 : 4) : 100);

// out(string2Array(input));

const xMin = 0;
const yMin = 0;
let xMax = 0;
let yMax = 0;

const task1 = () => {
    const lines = string2SplitLinesArray(input);
    for (let y = 0; y < lines.length; y++) {
        yMax = y;
        const line = lines[y];
        const chars = string2Array(line);
        xMax = chars.length - 1;
        for (let x = 0; x < chars.length; x++) {
            const char = chars[x];
            if (char === '#') {
                const pos = new Pos(x, y);
                grid.set(pos.toString(), pos);
            }
        }
    }

    if (useTask2)
        activateCorners();

    updateActiveSince(0);

    // out(grid);
    gridToSvg(0);

    let count = 0;

    if (animate) {
        let timer = setInterval(() => {
            count++;
            if (count >= maxCount) {
                clearInterval(timer);
                return;
            }
            nextStep();
            updateActiveSince(count);
            gridToSvg(count);

        }, animateDelay);
    } else {
        for (let i = 0; i < maxCount; i++) {
            nextStep();
            gridToSvg();
        }
        out('active:', grid.size);
    }
};

function activateCorners() {
    let pos = new Pos(xMin, yMin);
    grid.set(pos.toString(), pos);
    pos = new Pos(xMin, yMax);
    grid.set(pos.toString(), pos);
    pos = new Pos(xMax, yMin);
    grid.set(pos.toString(), pos);
    pos = new Pos(xMax, yMax);
    grid.set(pos.toString(), pos);
}

function nextStep() {
    if (useTask2)
        activateCorners();

    const nextActive = [];
    for (let y = yMin; y <= yMax; y++) {
        for (let x = xMin; x <= xMax; x++) {
            const pos = new Pos(x, y);
            const possibleNeighbors = calcNeighbors(pos);
            const activeNeighbors = countActiveNeighbors(possibleNeighbors);
            if (grid.has(pos.toString())) // selbst an?
            {
                if (activeNeighbors === 2 || activeNeighbors === 3)
                    nextActive.push(pos);
            } else  // selbst aus?
            {
                if (activeNeighbors === 3)
                    nextActive.push(pos);
            }
        }
    }

    grid.clear();
    for (const active of nextActive) {
        grid.set(active.toString(), active);
    }

    if (useTask2)
        activateCorners();
}

function updateActiveSince(step) {
    const nextActive = [];
    for (let y = yMin; y <= yMax; y++) {
        for (let x = xMin; x <= xMax; x++) {
            const pos = new Pos(x, y);

            if (grid.has(pos.toString())) // an?
            {
                if (!activeSinceStep.has(pos.toString()))
                    nextActive.push(pos);
            } else
                activeSinceStep.delete(pos.toString());
        }
    }

    for (const active of nextActive) {
        activeSinceStep.set(active.toString(), step);
    }
}

function gridToSvg(actStep) {
    const startL = 20;
    const endL = 50;
    const startS = 20;
    const endS = 100;
    const startH = 260;
    const endH = 70;

    const steps = actStep; // maxCount; // Anzahl schritte aus Task...

    const stepL = (endL - startL) / steps;
    const stepS = (endS - startS) / steps;
    const stepH = (endH - startH) / steps;


    const svg = new Svg();
    for (const active of grid.values()) {
        const activeSince = activeSinceStep.get(active.toString());
        const h = startH + activeSince * stepH;
        const s = startS + activeSince * stepS;
        const l = startL + activeSince * stepL;
        let rgb = hslToRgbStr(h, s, l);

        svg.add(active, rgb);
    }
    svg.setMinMax(xMin, xMax, yMin, yMax);
    // document.getElementById('out').innerHTML = svg.toSVGStringAged();
    document.getElementById('out').innerHTML = svg.toSVGString();
}

function countActiveNeighbors(neighbors) {
    let count = 0;
    for (const neighbor of neighbors) {
        if (grid.has(neighbor.toString()))
            count++;
    }
    return count;
}

function calcNeighbors(pos) {
    const res = [];
    for (let y = -1; y <= 1; y++) {
        for (let x = -1; x <= 1; x++) {
            if (x === 0 && y === 0) continue;
            let p = new Pos(pos.x + x, pos.y + y);
            if (p.x < xMin || p.x > xMax || p.y < yMin || p.y > yMax) continue;
            res.push(p);
        }
    }
    return res;
}


task1();
