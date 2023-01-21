import {input0, input1} from "./input.js";
import {Pos} from "./pos.js";
import {out} from "./util.js";
import {MD5} from "./md5.js";
import {Svg} from "./svg.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const startPos = new Pos(0, 0);
const targetPos = new Pos(6, 6);

const opensDoor = 'bcdef'.split("");

const dir2Pos = new Map();
dir2Pos.set('U', new Pos(0, -2));
dir2Pos.set('D', new Pos(0, 2));
dir2Pos.set('L', new Pos(-2, 0));
dir2Pos.set('R', new Pos(2, 0));

let minPathLength = Number.MAX_SAFE_INTEGER;
let minPath = null;
let maxPathLength = Number.MIN_SAFE_INTEGER;

const task1 = () => {
    const path = '' + input;
    findMinPath(startPos, path);
    out('minPathLength:', minPathLength);
    out('minPath:', minPath);

    drawStep(startPos, input, startPos.addToNew(0, 0));

    let actStep = 0;
    let actStepPos = startPos.addToNew(0, 0)
    let doorPos = false;

    setTimeout(() => {
        let timer = setInterval(() => {
            if (actStep >= minPathLength && doorPos)
                clearInterval(timer);
            else {
                const soFarPath = input + minPath.substring(0, actStep);
                const nextStepDir = minPath.substring(actStep, actStep + 1);
                if (doorPos) {
                    drawStep(actStepPos, input, actStepPos.addPosToNew(dir2Pos.get(nextStepDir).multToNew(0.5, 0.5)));
                    actStepPos.addPos(dir2Pos.get(nextStepDir));
                } else
                    drawStep(actStepPos, soFarPath, actStepPos.addToNew(0, 0));


                if (doorPos)
                    actStep++;
                doorPos = !doorPos;
            }
        }, 300);
    }, 2000);
};

function drawStep(actPos, soFarPath, drawPos) {
    let maxX = 7;
    let maxY = 7;

    const svg = new Svg(16);
    for (let y = -1; y <= maxY; y++) {
        for (let x = -1; x <= maxX; x++) {
            const pos = new Pos(x, y);
            const wall = x < 0 || y < 0 || x % 2 === 1 || y % 2 === 1;

            if (wall) {
                if (x >= 0 && y >= 0 && x < maxX && y < maxY && x % 2 !== y % 2) // door
                    svg.add(pos, '#bbb');
                else
                    svg.add(pos, '#888');
            }

        }
    }
    // for (const drawPosition of drawPositions) {
    //     svg.add(drawPosition);
    // }

    const possibleDirs = getPossibleDirections(actPos, soFarPath);

    for (const possibleDir of possibleDirs) {
        svg.add(actPos.addPosToNew(dir2Pos.get(possibleDir).multToNew(0.5, 0.5)), '#97fc00');
    }

    svg.add(drawPos, '#00dffc');

    document.getElementById('out').innerHTML = svg.toSVGStringAged();
}

function findMinPath(actPos, actPath) {
    const soFarSteps = actPath.length - input.length;
    if (soFarSteps > minPathLength)
        return Number.MAX_SAFE_INTEGER;

    if (actPos.equals(targetPos)) {
        if (soFarSteps < minPathLength) {
            minPathLength = soFarSteps;
            minPath = actPath.substring(input.length);
            // out('minPathLength', minPathLength);
        }
        return soFarSteps;
    }

    const possibleDirs = getPossibleDirections(actPos, actPath);

    let min = Number.MAX_SAFE_INTEGER;
    if (possibleDirs.length === 0)
        return min;

    for (const possibleDir of possibleDirs) {
        const m = findMinPath(actPos.addPosToNew(dir2Pos.get(possibleDir)), actPath + possibleDir);
        if (m < min)
            min = m;
    }

    return min;
}

const task2 = () => {
    const path = '' + input;
    findMaxPath(startPos, path);
    out('maxPathLength:', maxPathLength);
};

function findMaxPath(actPos, actPath) {
    const soFarSteps = actPath.length - input.length;

    if (actPos.equals(targetPos)) {
        if (soFarSteps > maxPathLength) {
            maxPathLength = soFarSteps;
            out('maxPathLength', maxPathLength);
        }
        return soFarSteps;
    }

    const possibleDirs = getPossibleDirections(actPos, actPath);

    let max = -1;
    if (possibleDirs.length === 0)
        return max;

    for (const possibleDir of possibleDirs) {
        const m = findMaxPath(actPos.addPosToNew(dir2Pos.get(possibleDir)), actPath + possibleDir);
        if (m > max)
            max = m;
    }

    return max;
}

function getPossibleDirections(actPos, actPath) {
    const hash = MD5(actPath);
    const result = [];
    let c = hash.charAt(0);
    if (opensDoor.includes(c) && isValidPos(actPos.addPosToNew(dir2Pos.get('U'))))
        result.push("U");
    c = hash.charAt(1);
    if (opensDoor.includes(c) && isValidPos(actPos.addPosToNew(dir2Pos.get('D'))))
        result.push("D");
    c = hash.charAt(2);
    if (opensDoor.includes(c) && isValidPos(actPos.addPosToNew(dir2Pos.get('L'))))
        result.push("L");
    c = hash.charAt(3);
    if (opensDoor.includes(c) && isValidPos(actPos.addPosToNew(dir2Pos.get('R'))))
        result.push("R");

    return result;
}

function isValidPos(pos) {
    return !(pos.x < 0 || pos.y < 0 || pos.x > 6 || pos.y > 6);
}


task1();
// task2();
