import {out, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

const task1 = () => {
    const wire2Value = new Map();
    const wire2Gates = new Map();
    let workList = []; // wires die abzuarbeiten sind

    const wireCalculatedHandler = (wire, value) => {
        wire2Value.set(wire, value);
        if (!workList.includes(wire))
            workList.push(wire);
    };

    const addGate = (wire, gate) => {
        let gates = wire2Gates.get(wire);
        if (!gates) {
            gates = [];
            wire2Gates.set(wire, gates);
        }
        gates.push(gate);
    };

    const lines = string2SplitLinesArray(input);
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i];
        const parts = line.split(" ");
        if (line.includes('AND')) {
            const src1 = parts[0], src2 = parts[2], target = parts[4];

            // src1 Zahl?
            const src1AsNumber = parseInt(src1);
            if (isFinite(src1AsNumber)) {
                const gate = () => {
                    const src2Value = wire2Value.get(src2);
                    if (src2Value === undefined) return;
                    const calculatedValue = src1AsNumber & src2Value;
                    wireCalculatedHandler(target, calculatedValue);
                }
                addGate(src2, gate);
            } else {
                const gate = () => {
                    const src1Value = wire2Value.get(src1), src2Value = wire2Value.get(src2);
                    if (src1Value === undefined || src2Value === undefined) return;
                    const calculatedValue = src1Value & src2Value;
                    wireCalculatedHandler(target, calculatedValue);
                }
                addGate(src1, gate);
                addGate(src2, gate);
            }
        } else if (line.includes('OR')) {
            const src1 = parts[0], src2 = parts[2], target = parts[4];
            const gate = () => {
                const src1Value = wire2Value.get(src1), src2Value = wire2Value.get(src2);
                if (src1Value === undefined || src2Value === undefined) return;
                const calculatedValue = src1Value | src2Value;
                wireCalculatedHandler(target, calculatedValue);
            }
            addGate(src1, gate);
            addGate(src2, gate);
        } else if (line.includes('LSHIFT')) {
            const src1 = parts[0], target = parts[4];
            const shift = parseInt(parts[2]);
            const gate = () => {
                const src1Value = wire2Value.get(src1);
                if (src1Value === undefined) return;
                const calculatedValue = src1Value << shift;
                wireCalculatedHandler(target, calculatedValue);
            }
            addGate(src1, gate);
        } else if (line.includes('RSHIFT')) {
            const src1 = parts[0], target = parts[4];
            const shift = parseInt(parts[2]);
            const gate = () => {
                const src1Value = wire2Value.get(src1);
                if (src1Value === undefined) return;
                const calculatedValue = src1Value >> shift;
                wireCalculatedHandler(target, calculatedValue);
            }
            addGate(src1, gate);
        } else if (line.includes('NOT')) {
            const src1 = parts[1], target = parts[3];
            const gate = () => {
                const src1Value = wire2Value.get(src1);
                if (src1Value === undefined) return;
                const calculatedValue = 65535 ^ src1Value;
                wireCalculatedHandler(target, calculatedValue);
            }
            addGate(src1, gate);
        } else { // einfache Zuweisung
            const wire = parts[2];
            const value = parseInt(parts[0]);
            if (isFinite(value)) // Zahl Variable zuweisen
                wireCalculatedHandler(wire, value);
            else { // Variable einer anderen zuweisen
                const src1 = parts[0], target = wire;
                const gate = () => {
                    const src1Value = wire2Value.get(src1);
                    if (src1Value === undefined) return;
                    const calculatedValue = src1Value;
                    wireCalculatedHandler(target, calculatedValue);
                }
                addGate(src1, gate);
            }
        }
    }

    out(wire2Value);

    const calcWorkList = () => {
        while (workList.length > 0) {
            const wire = workList[0];
            workList = workList.slice(1);
            const gates = wire2Gates.get(wire);
            if (!gates) continue;
            for (let i = 0; i < gates.length; i++) {
                const gate = gates[i];
                gate();
            }
        }
    }

    calcWorkList();

    out(wire2Value);
    let valueA = wire2Value.get('a');
    out('task1 a: ', valueA);

    wire2Value.clear();
    // Zurueck setzen
    wireCalculatedHandler('c', 0);
    wireCalculatedHandler('b', valueA);

    calcWorkList();

    valueA = wire2Value.get('a');
    out('task2 a: ', valueA);
};

task1();