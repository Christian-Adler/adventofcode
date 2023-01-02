export const out = (...msg) => {
    console.log(...msg);
};

export const string2Array = (input) => {
    // https://www.samanthaming.com/tidbits/83-4-ways-to-convert-string-to-character-array/
    return [...input];
};

export const string2SplitLinesArray = (input) => {
    return input.split('\n');
};