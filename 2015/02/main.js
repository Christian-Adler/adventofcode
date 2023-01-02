import {out, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task1 = () => {
    let total = 0;
    let totalRibbon = 0;
    const arr = string2SplitLinesArray(input);
    for (let i = 0; i < arr.length; i++) {
        const dim = arr[i];
        const lwh = dim.split('x').map(s => parseInt(s));
        lwh.sort(function (a, b) {
            return a - b
        });
        const l = lwh[0];
        const w = lwh[1];
        const h = lwh[2];
        const lw = l * w;
        const lh = l * h;
        const wh = w * h;

        const surface = 2 * lw + 2 * lh + 2 * wh;
        const totalForPresent = surface + Math.min(lw, lh, wh);

        const ribbon = l + l + w + w + l * w * h;

        total += totalForPresent;
        totalRibbon += ribbon;
    }
    out('total (task1): ', total);
    out('totalRibbon (task2): ', totalRibbon);
};
const task2 = () => {
};

task1();
task2();
