import {combinations, deepClone, out, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";
import {Device} from "./device.js";
import {Svg} from "./svg.js";
import {Pos} from "./pos.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

const regex = /([a-z-]+ generator)|([a-z-]+ microchip)/gm;

// Alternative syntax using RegExp constructor
// const regex = new RegExp('([a-z-]+ g)|([a-z-]+ m)', 'gm')

const MICROCHIP = "microchip";
const GENERATOR = "generator";

// out(floors);
let elements = [];
// out(elements);
const colors = ['#f00', '#0f0', '#00f', '#f0f', '#ff0', '#0ff', '#fff'];
const element2Color = new Map();

const allDeviceMap = new Map();

let minSteps = Number.MAX_SAFE_INTEGER;
let minStepsPath = [];
let maxSoFarMoves = 0;

let workList = [];
const alreadyInWork = new Set();
let workItemCounter = 0;

const task = (part2) => {
    let elevator = 1;
    const floors = new Map();
    floors.set(1, new Map());
    floors.set(2, new Map());
    floors.set(3, new Map());
    floors.set(4, new Map());

    const elementsSet = new Set();


    const lines = string2SplitLinesArray(input);
    for (const line of lines) {
        const floor = line.includes('first') ? 1 : (line.includes('second') ? 2 : (line.includes('third') ? 3 : 4));
        // out('floor', floor);

        let m;
        while ((m = regex.exec(line)) !== null) {
            // This is necessary to avoid infinite loops with zero-width matches
            if (m.index === regex.lastIndex) {
                regex.lastIndex++;
            }

            const matchParts = m[0].split(" ");
            const type = matchParts[1];
            const element = (type === MICROCHIP) ? matchParts[0].replace("-compatible", "") : matchParts[0];
            // out(element, type);
            elementsSet.add(element);

            const device = new Device(element, type);
            floors.get(floor).set(device.getMapKey(), device);
            allDeviceMap.set(device.getMapKey(), device);
        }
    }


    if (part2) {
        function addToFloor(flr, device) {
            flr.set(device.getMapKey(), device);
            allDeviceMap.set(device.getMapKey(), device);
        }

        const firstFloor = floors.get(1);
        elementsSet.add('elerium');
        elementsSet.add('dilithium');
        let device = new Device('elerium', MICROCHIP);
        addToFloor(firstFloor, device);
        device = new Device('elerium', GENERATOR);
        addToFloor(firstFloor, device);
        device = new Device('dilithium', MICROCHIP);
        addToFloor(firstFloor, device);
        device = new Device('dilithium', GENERATOR);
        addToFloor(firstFloor, device);
    }

    printFloors(floors, 1);

    elements = [...elementsSet];
    // out(elements);
    for (let i = 0; i < elements.length; i++) {
        element2Color.set(elements[i], colors[i]);
    }

    printSVG(elevator, floors);

    workList.push({elevator, floors: deepClone(floors), soFarMoves: 0, prevSteps: []});
    let turns = 0;
    while (workList.length > 0) {
        turns++;

        if (turns % 10000 === 0)
            out(turns, ' len:', workList.length, 'maxSoFarMoves', maxSoFarMoves);
        const workItem = workList.splice(0, 1)[0];
        minMoves(workItem);
    }
    out(minSteps);
    // out(minStepsPath);
    if (minStepsPath.length === 0) {
        out('found no solution!');
        return;
    }

    let actStep = 0;
    printStep(actStep);
    setTimeout(() => {
        let timer = setInterval(() => {
            if (actStep > minSteps)
                clearInterval(timer);
            else {
                printStep(actStep);
                actStep++;
            }
        }, 300);
    }, 2000);
    // for (let i = 0; i <= minSteps; i++) {
    //     printStep(i);
    // }
};

function printStep(step) {
    const actPath = minStepsPath[step];
    const parts = actPath.split(', ');
    // out(parts);

    const elevator = parseInt(parts[0]);

    const floors = new Map();
    floors.set(1, new Map());
    floors.set(2, new Map());
    floors.set(3, new Map());
    floors.set(4, new Map());

    let actFloorNo = 0;
    let actFloor = null;
    for (let i = 0; i < parts.length; i++) {
        const part = parts[i];
        if (part.length === 0 || i === 0) continue;
        if (part.includes("Floor")) {
            actFloorNo++;
            actFloor = floors.get(actFloorNo);
        } else {
            actFloor.set(part, allDeviceMap.get(part));
        }
    }

    printSVG(elevator, floors);
}

function minMoves({elevator, floors, soFarMoves, prevSteps}) {
    if (minSteps <= soFarMoves)
        return Number.MAX_SAFE_INTEGER;

    // if (!checkFloorValidity(floors)) // wird schon beim Hinzufügen geprueft.
    //     return Number.MAX_SAFE_INTEGER;

    let floorAsString = floorToString(floors, elevator);
    // if (prevSteps.includes(floorAsString))
    //     return Number.MAX_SAFE_INTEGER;
    const newPrevSteps = [...prevSteps, floorAsString];

    // check for finish
    if (floors.get(1).size === 0 && floors.get(2).size === 0 && floors.get(3).size === 0) {
        out("found solution:", soFarMoves);
        if (soFarMoves < minSteps) {
            minSteps = soFarMoves;
            minStepsPath = newPrevSteps;
            printSVG(elevator, floors);
        }
        return soFarMoves;
    }

    maxSoFarMoves = Math.max(maxSoFarMoves, soFarMoves);

    printSVG(elevator, floors);

    const possibleElevatorMoves = [];
    if (elevator < 4) possibleElevatorMoves.push(elevator + 1);
    if (elevator > 1) possibleElevatorMoves.push(elevator - 1);

    const actFloor = floors.get(elevator);
    const devicesOnFloor = Array.from(actFloor.values());
    const deviceCombinationsOnFloor = [];
    for (const combo of combinations(devicesOnFloor, 2)) {
        deviceCombinationsOnFloor.push(combo);
    }
    for (const device of devicesOnFloor) {
        deviceCombinationsOnFloor.push([device])
    }


    for (const deviceCombination of deviceCombinationsOnFloor) {
        for (const elevatorMove of possibleElevatorMoves) {
            const cloneFloors = deepClone(floors);
            const floorSoFar = cloneFloors.get(elevator);
            const floorTarget = cloneFloors.get(elevatorMove);

            for (const device of deviceCombination) {
                floorSoFar.delete(device.getMapKey());
                floorTarget.set(device.getMapKey(), device);
            }

            let nextItem = {
                elevator: elevatorMove,
                floors: cloneFloors,
                soFarMoves: soFarMoves + 1,
                prevSteps: newPrevSteps
            };

            if (checkFloorValidity(nextItem.floors)) {
                const itemStrHash = floorHash(nextItem.floors, nextItem.elevator);
                if (!alreadyInWork.has(itemStrHash)) {
                    alreadyInWork.add(itemStrHash);
                    // printFloors(nextItem.floors, elevatorMove);
                    workList.push(nextItem);
                }
            }
        }
    }
}

function checkFloorValidity(floors) {
    for (let i = 1; i <= floors.size; i++) {
        const floor = floors.get(i);

        for (const element of elements) {
            const chip = element + '-' + MICROCHIP;
            if (floor.has(chip)) {
                const matchingGenerator = element + '-' + GENERATOR;
                if (floor.has(matchingGenerator))
                    continue;

                for (const el of elements) {
                    if (el === element) continue;

                    const notMatchingGenerator = el + '-' + GENERATOR;
                    if (floor.has(notMatchingGenerator))
                        return false;
                }
            }
        }
    }
    return true;
}

function floorToString(floors, elevator) {
    let res = '' + elevator;
    for (let i = 1; i <= floors.size; i++) {
        res += ", Floor " + i;
        const floor = floors.get(i);

        for (const element of elements) {
            const chip = element + '-' + MICROCHIP;
            if (floor.has(chip))
                res += ', ' + chip;

            const matchingGenerator = element + '-' + GENERATOR;
            if (floor.has(matchingGenerator))
                res += ', ' + matchingGenerator;
        }
    }
    return res;
}

/**
 * Hint from Internet.
 * Nur die Verteilung pro Zeile spielt eine Rolle - nicht die eigentlichen Elemente. Welches Paerchen auf welchem Flur liegt ist egal.
 *
 * @param floors
 * @param elevator
 * @returns {string}
 */
function floorHash(floors, elevator) {
    let res = '' + elevator;
    for (let i = 1; i <= floors.size; i++) {
        res += "_F" + i + '_';
        const floor = floors.get(i);

        let countMicro = 0;
        let countGenerator = 0;
        for (const element of elements) {
            const chip = element + '-' + MICROCHIP;
            if (floor.has(chip))
                countMicro++;
            const matchingGenerator = element + '-' + GENERATOR;
            if (floor.has(matchingGenerator))
                countGenerator++;
        }
        res += countMicro + '-' + countGenerator;
    }
    return res;
}


function printFloors(floors, elevator) {
    out('(' + (++workItemCounter) + ') --------')

    for (let i = floors.size; i >= 1; i--) {
        let res = i + " ";
        if (elevator === i) res += "E ";
        else res += "  ";

        const floor = floors.get(i);

        let res2 = ''
        for (const element of elements) {
            const chip = element + '-' + MICROCHIP;
            if (floor.has(chip))
                res2 += (res2.length > 0 ? ', ' : '') + chip;

            const matchingGenerator = element + '-' + GENERATOR;
            if (floor.has(matchingGenerator))
                res2 += (res2.length > 0 ? ', ' : '') + matchingGenerator;
        }

        out(res + res2);
    }
}

function printSVG(elevator, floors) {
    const svg = new Svg(20);
    svg.add(new Pos(2, 4 * 2 - elevator * 2, '#555'));

    for (let i = 1; i <= 4; i++) {
        svg.add(new Pos(0, 4 * 2 - i * 2, '#b0b0b0')); // Floor

        const floor = floors.get(i);
        const devices = Array.from(floor.values());
        for (const device of devices) {
            const idx = elements.indexOf(device.element);
            const isGenerator = device.type === 'generator';
            svg.add(new Pos(4 + idx * 3 + (isGenerator ? 0 : 1), 4 * 2 - i * 2, element2Color.get(device.element))); // Floor
        }
    }

    document.getElementById('out').innerHTML = svg.toSVGString();
}

const t1 = new Date();
// task();
task(true);
const t2 = new Date();
out(t2.getTime() - t1.getTime(), 'ms');
