import {input0, input1} from "./input.js";
import {Svg} from "./svg.js";
import {Pos} from "./pos.js";
import {out, string2SplitLinesArray} from "./util.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

const lines = string2SplitLinesArray(input);

const favNumber = parseInt(lines[0]);
const targetPos = Pos.parse(lines[1]);
let minStepsFound = Number.MAX_SAFE_INTEGER;
let minStepsPath = [];

const part2Visited = new Map();

const task1 = () => {
    // out(isWall(1, 0));

    const startPos = new Pos(1, 1);
    // out(startPos);

    const minSteps2Target = minSteps(startPos, 0, []);
    out('Min steps to target:', minSteps2Target);

    if (minStepsPath.length === 0) {
        out('found no solution!');
        return;
    }

    part2Visited.set(startPos.toString(), 0);
    countPositions(startPos, 0);
    out('visitable in 50 steps:', part2Visited.size); // 128 ist zu klein, 141 zu gross

    const pathPositions = [];

    for (const minStepsPathElement of minStepsPath) {
        pathPositions.push(Pos.parse(minStepsPathElement));
    }
    // out(pathPositions);
    for (const minStepsPathElement of part2Visited.keys()) {
        pathPositions.push(Pos.parse(minStepsPathElement));
    }

    let actStep = 0;
    const drawPositions = [];

    let actStepPos = pathPositions[actStep];
    actStepPos.color = '#ff0000';
    drawPositions.push(actStepPos);
    drawStep(drawPositions);

    setTimeout(() => {
        let timer = setInterval(() => {
            if (actStep > pathPositions.length - 1)
                clearInterval(timer);
            else {
                //>>
                actStepPos = pathPositions[actStep];
                drawPositions.push(actStepPos)

                drawStep(drawPositions);
                //<<
                actStep++;
            }
        }, 10);
    }, 2000);
};

function drawStep(drawPositions) {
    let maxX = 0;
    let maxY = 0;
    for (const drawPosition of drawPositions) {
        maxX = Math.max(maxX, drawPosition.x);
        maxY = Math.max(maxY, drawPosition.y);
    }

    const svg = new Svg(16);
    for (let y = -1; y <= maxY + 2; y++) {
        for (let x = -1; x < maxX + 2; x++) {
            const pos = new Pos(x, y);
            const wall = isWallPos(pos);
            if (wall)
                svg.add(pos, '#888');
        }
    }
    for (const drawPosition of drawPositions) {
        svg.add(drawPosition);
    }

    document.getElementById('out').innerHTML = svg.toSVGStringAged();
}

function minSteps(actPos, soFarSteps, visited) {
    if (soFarSteps > minStepsFound)
        return Number.MAX_SAFE_INTEGER;

    if (actPos.equals(targetPos)) {
        if (soFarSteps < minStepsFound) {
            minStepsFound = soFarSteps;
            minStepsPath = [...visited, actPos.toString()];
        }
        return soFarSteps;
    }

    const possibleNextSteps = getPossibleNextSteps(actPos);
    // out(possibleNextSteps);

    const nextVisited = [...visited];
    nextVisited.push(actPos.toString());

    let tmpMin = Number.MAX_SAFE_INTEGER;
    for (let i = 0; i < possibleNextSteps.length; i++) {
        const possibleNextStep = possibleNextSteps[i];
        if (nextVisited.includes(possibleNextStep.toString()))
            continue;
        const min = minSteps(possibleNextStep, soFarSteps + 1, nextVisited);
        if (min < tmpMin) tmpMin = min;
    }

    return tmpMin;
}

function countPositions(actPos, soFarSteps) {
    if (soFarSteps >= 50)
        return;

    const possibleNextSteps = getPossibleNextSteps(actPos);

    for (let i = 0; i < possibleNextSteps.length; i++) {
        const possibleNextStep = possibleNextSteps[i];
        let nextStepKey = possibleNextStep.toString();
        if (part2Visited.has(nextStepKey)) {
            const soFarMinSteps = part2Visited.get(nextStepKey);
            if (soFarMinSteps <= soFarSteps + 1)
                continue;
        }
        part2Visited.set(nextStepKey, soFarSteps + 1);
        countPositions(possibleNextStep, soFarSteps + 1);
    }
}

function getPossibleNextSteps(pos) {
    const result = [];

    function handlePossibleNextStep(p) {
        if (!isWallPos(p))
            result.push(p);
    }

    handlePossibleNextStep(pos.addToNewPos(1, 0));
    handlePossibleNextStep(pos.addToNewPos(-1, 0));
    handlePossibleNextStep(pos.addToNewPos(0, 1));
    handlePossibleNextStep(pos.addToNewPos(0, -1));

    return result;
}

function isWallPos(pos) {
    return isWall(pos.x, pos.y);
}

function isWall(x, y) {
    if (x < 0 || y < 0) return true;
    const find = x * x + 3 * x + 2 * x * y + y + y * y;
    const added = find + favNumber;
    const bin = dec2bin(added);
    const amount1 = countLetters(bin, '1');
    return amount1 % 2 !== 0;
}

function dec2bin(dec) {
    return (dec >>> 0).toString(2);
}

function countLetters(str, l) {
    let result = 0, i = 0;
    for (i; i < str.length; i++) if (str[i] === l) result++;
    return result;
}

task1();
