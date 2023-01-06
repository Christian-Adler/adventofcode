import {out, string2SplitLinesArray} from "./util.js";
import {input0, inputRK} from "./input.js";

const useExample = 0;

const input = (useExample ? input0 : inputRK);

function findRegEx(value, regex) {
    let m;
    let count = 0;

    while ((m = regex.exec(value)) !== null) {
        // This is necessary to avoid infinite loops with zero-width matches
        if (m.index === regex.lastIndex)
            regex.lastIndex++;
        count += 1;
    }
    return count;
}

// function backslashCounter(value) {
//     return findRegEx(value, /(\\[^x])/gm);
// }

function xCodeCounter(value) {
    return findRegEx(value, /(\\x[a-f\d][a-f\d])/gm);
}

function tickMark(value) {
    return findRegEx(value, /(")/gm);
}


const task1 = () => {
};
const task2 = () => {
    const lines = string2SplitLinesArray(input);

    let result = 0;

    for (const line of lines) {
        result += xCodeCounter(line);
        const replItem = line.replace(/(\\x[a-f\d][a-f\d])/gm, '#');
        result += tickMark(replItem) + 2;
        result += findRegEx(replItem, /(\\)/gm);
    }

    out(result);
};

task1();
task2();
