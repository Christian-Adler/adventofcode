import {out} from "./util.js";
import {input0, input1} from "./input.js";

const useExample = 1;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task1 = () => {
    let level = 0;
    for (let i = 0; i < input.length; i++) {
        const char = input[i];
        if (char === '(')
            level++;
        else
            level--;
    }
    out('level: ', level);
};
const task2 = () => {
    let level = 0;
    for (let i = 0; i < input.length; i++) {
        const char = input[i];
        if (char === '(')
            level++;
        else
            level--;
        if (level === -1) {
            out('basement at pos ', i + 1);
            return;
        }
    }
};

task1();
task2();
