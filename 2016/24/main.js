import {out, permutator, string2Array, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";
import {Pos} from "./pos.js";
import {Svg} from "./svg.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));
const maze = new Map();

const locations = new Map();
let startLocation = null;

const directions = [new Pos(1, 0), new Pos(-1, 0), new Pos(0, 1), new Pos(0, -1)];

const shortestPathsLength = new Map();
const shortestPaths = new Map();

const task1 = () => {
    const lines = string2SplitLinesArray(input);
    for (let y = 0; y < lines.length; y++) {
        const line = lines[y];
        const columns = string2Array(line);
        for (let x = 0; x < columns.length; x++) {
            const column = columns[x];
            if (column === '#') {
                const pos = new Pos(x, y);
                maze.set(pos.toString(), pos);
            } else if (column !== '.') {
                const pos = new Pos(x, y);
                locations.set(parseInt(column), pos);
                if (column === '0')
                    startLocation = pos;
            }
        }
    }

    // Alle Kombinationen von Locations
    const locationCombinations = [];
    const locationKeys = [...locations.keys()];

    for (let i = 0; i < locationKeys.length - 1; i++) {
        const locationKey1 = locationKeys[i];
        for (let j = i + 1; j < locationKeys.length; j++) {
            const locationKey2 = locationKeys[j];
            let keyCombo = [locationKey1, locationKey2];
            keyCombo.sort();
            locationCombinations.push(keyCombo);
        }
    }
    out(locationCombinations);


    // Fuer jede Combination den kuerzesten Pfad finden und in Map eintragen
    for (const locationCombination of locationCombinations) {
        const combo = locationCombination[0] + '-' + locationCombination[1];
        const from = locations.get(locationCombination[0]);
        const to = locations.get(locationCombination[1]);
        out(combo, from, to);
        const global = {
            visited: new Map(),
            visitedPositions: [],
            minSteps: from.manhattenDist(to) * 4, // Number.MAX_SAFE_INTEGER,
            minStepsPath: null
        };
        const d1 = new Date().getTime();
        const minFromTo = findMin(from, to, global, 0, []);
        const d2 = new Date().getTime();
        out((d2 - d1), 'ms');
        // out(minFromTo);
        // out(global.minStepsPath);

        shortestPathsLength.set(combo, minFromTo);
        shortestPaths.set(combo, global.minStepsPath);

        // printMaze(global.minStepsPath);
        // printMaze(global.visitedPositions);
    }

    out(shortestPathsLength);

    const locs = [];
    for (const locationKey of locations.keys()) {
        if (locationKey !== 0)
            locs.push(locationKey);
    }
    const permutatedLocs = permutator(locs);
    // out(permutatedLocs);

    let minPathL = Number.MAX_SAFE_INTEGER;
    for (const permutatedLoc of permutatedLocs) {

        // out("Path:", 0, permutatedLoc);
        let pathL = 0;
        let prevLoc = 0;
        for (let i = 0; i < permutatedLoc.length; i++) {
            const loc = permutatedLoc[i];
            if (loc < prevLoc) // Keys sind kleinerer Wert zuerst
                pathL += shortestPathsLength.get(loc + '-' + prevLoc);
            else
                pathL += shortestPathsLength.get(prevLoc + '-' + loc);
            prevLoc = loc;
        }

        if (pathL < minPathL)
            minPathL = pathL;
    }

    out("minPathLength", minPathL);

    // part 2

    minPathL = Number.MAX_SAFE_INTEGER;
    let minPathPermutation = null;
    for (const permutatedLoc of permutatedLocs) {
        const permutatedLocBackTo0 = [...permutatedLoc, 0];
        // out("Path:", 0, permutatedLocBackTo0);
        let pathL = 0;
        let prevLoc = 0;
        for (let i = 0; i < permutatedLocBackTo0.length; i++) {
            const loc = permutatedLocBackTo0[i];
            if (loc < prevLoc) // Keys sind kleinerer Wert zuerst
                pathL += shortestPathsLength.get(loc + '-' + prevLoc);
            else
                pathL += shortestPathsLength.get(prevLoc + '-' + loc);
            prevLoc = loc;
        }

        if (pathL < minPathL) {
            minPathL = pathL;
            minPathPermutation = [...permutatedLocBackTo0];
        }
    }

    out("minPathLength (part 2)", minPathL);
    out("minPath (part 2)", minPathPermutation);

    let pathThroughMaze = [];
    let prevLoc = 0;
    for (let i = 0; i < minPathPermutation.length; i++) {
        const loc = minPathPermutation[i];

        let keyCombo = [prevLoc, loc];
        keyCombo.sort();
        const combo = keyCombo[0] + '-' + keyCombo[1];

        const minStepsPath = [...shortestPaths.get(combo)];
        if (keyCombo[0] === loc)
            minStepsPath.reverse();

        pathThroughMaze = [...pathThroughMaze, ...minStepsPath];

        prevLoc = loc;
    }

    printMaze(pathThroughMaze);
};

function findMin(actPos, target, global, soFarSteps, soFarPath) {
    if (soFarSteps > global.minSteps)
        return Number.MAX_SAFE_INTEGER;

    if (soFarSteps + actPos.manhattenDist(target) > global.minSteps)
        return Number.MAX_SAFE_INTEGER;

    if (actPos.equals(target)) {
        if (soFarSteps < global.minSteps) {
            global.minSteps = soFarSteps;
            global.minStepsPath = soFarPath;
        }
        return soFarSteps;
    }

    let min = Number.MAX_SAFE_INTEGER;
    const nextPositions = getNextPossibilities(actPos, target);
    for (const nextPosition of nextPositions) {
        const alreadyVisited = global.visited.get(nextPosition.toString())
        if (typeof alreadyVisited === "number") {
            if (alreadyVisited <= soFarSteps + 1)
                continue;
        } else
            global.visitedPositions.push(nextPosition);
        global.visited.set(nextPosition.toString(), soFarSteps + 1);
        const m = findMin(nextPosition, target, global, soFarSteps + 1, [...soFarPath, nextPosition]);
        if (m < min)
            min = m;
    }
    return min;
}

function getNextPossibilities(actPos, targetPos) {
    const next = [];
    for (const direction of directions) {
        const testPos = actPos.addPosToNew(direction);
        if (!maze.has(testPos.toString()))
            next.push(testPos);
    }
    // if (next.length > 1)
    //     next.sort((a, b) => b.manhattenDist(targetPos) - a.manhattenDist(targetPos));
    return next;
}

function printMaze(path) {
    const svg = new Svg(8);
    for (const pos of maze.values()) {
        svg.add(pos, '#888');
    }
    for (const pos of locations.values()) {
        svg.add(pos, '#f00');
    }
    if (path) {
        for (const pathElement of path) {
            svg.add(pathElement);
        }
    }
    document.getElementById('out').innerHTML = svg.toSVGStringAged();
}


task1();