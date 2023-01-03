import {out, string2Array, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const vowels = string2Array('aeiou');
const disallowed = ['ab', 'cd', 'pq', 'xy'];

const isNiceString1 = (str) => {
    let countVowels = 0;
    for (let i = 0; i < vowels.length; i++) {
        const vowel = vowels[i];
        let searchIdx = 0;
        while (searchIdx >= 0) {
            searchIdx = str.indexOf(vowel, searchIdx);
            if (searchIdx < 0)
                break;
            searchIdx++;
            countVowels++;

            if (countVowels >= 3)
                break;
        }
    }
    if (countVowels < 3) return false;

    for (let i = 0; i < disallowed.length; i++) {
        const disallowedElement = disallowed[i];
        if (str.includes(disallowedElement))
            return false;
    }

    // doppelt?
    let prev = '';
    const chars = string2Array(str);
    for (let i = 0; i < chars.length; i++) {
        const char = chars[i];
        if (prev === char)
            return true;
        prev = char;
    }

    return false;
};

const isNiceString2 = (str) => {
    let containsPair = false;
    let containsRepeat = false;
    let prev = ['0', '1', '2', '3'];
    const chars = string2Array(str);
    for (let i = 0; i < chars.length; i++) {
        const char = chars[i];
        prev = prev.slice(1); // erstes entfernen
        prev.push(char);

        if (!containsPair && i < chars.length - 2) {
            let searchElement = prev[2] + prev[3];
            if (str.indexOf(searchElement, i + 1) >= 0)
                containsPair = true;
        }

        if (!containsRepeat && prev[1] === prev[3])
            containsRepeat = true;

        if (containsRepeat && containsPair) return true;
    }

    return false;
};

const task1 = () => {
    let countNiceStrings = 0;
    let lines = string2SplitLinesArray(input);
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i];
        countNiceStrings += (isNiceString1(line) ? 1 : 0);
    }
    out("countNiceStrings: ", countNiceStrings);
};

const task2 = () => {
    let countNiceStrings = 0;
    let lines = string2SplitLinesArray(input);
    for (let i = 0; i < lines.length; i++) {
        const line = lines[i];
        countNiceStrings += (isNiceString2(line) ? 1 : 0);
    }
    out("countNiceStrings2: ", countNiceStrings);
};

// task1();
task2();
