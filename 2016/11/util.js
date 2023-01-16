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

export const downloadText = (filename, data) => {
    const blob = new Blob([data], {type: 'text/csv'});
    if (window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveBlob(blob, filename);
    } else {
        const elem = window.document.createElement('a');
        const url = window.URL.createObjectURL(blob);
        elem.href = url
        elem.download = filename;
        document.body.appendChild(elem);
        elem.click();
        document.body.removeChild(elem);
        window.URL.revokeObjectURL(url);
    }
}

const hue2rgb = function hue2rgb(p, q, t) {
    if (t < 0) t += 1;
    if (t > 1) t -= 1;
    if (t < 1 / 6) return p + (q - p) * 6 * t;
    if (t < 1 / 2) return q;
    if (t < 2 / 3) return p + (q - p) * (2 / 3 - t) * 6;
    return p;
}

/**
 * Converts an HSL color value to RGB. Conversion formula
 * adapted from http://en.wikipedia.org/wiki/HSL_color_space.
 * Assumes h, s, and l are contained in the set [0, 1] and
 * returns r, g, and b in the set [0, 255].
 *
 * @param   {number}  h       The hue
 * @param   {number}  s       The saturation
 * @param   {number}  l       The lightness
 * @return  {Array}           The RGB representation
 */
export const hslToRgb = (h, s, l) => {
    var r, g, b;

    if (s === 0) {
        r = g = b = l; // achromatic
    } else {
        let q = l < 0.5 ? l * (1 + s) : l + s - l * s;
        let p = 2 * l - q;
        r = hue2rgb(p, q, h + 1 / 3);
        g = hue2rgb(p, q, h);
        b = hue2rgb(p, q, h - 1 / 3);
    }

    return [Math.round(r * 255), Math.round(g * 255), Math.round(b * 255)];
}
/**
 * Converts an HSL color value to RGB.
 * returns hex color string.
 *
 * @param   {number}  h       The hue [0-360]
 * @param   {number}  s       The saturation [0-100]
 * @param   {number}  l       The lightness [0-100]
 * @returns {string} hex color
 */
export const hslToRgbStr = (h, s, l) => {
    let res = '#';
    const rgb = hslToRgb(h / 360, s / 100, l / 100);

    for (let i = 0; i < 3; i++) {
        let cVal = rgb[i].toString(16);
        if (cVal.length < 2)
            cVal = '0' + cVal;
        res += cVal;
    }
    return res;
}

export const deepClone = (obj) => {
    if (!obj || true === obj) //this also handles boolean as true and false
        return obj;
    const objType = typeof (obj);
    if ("number" === objType || "string" === objType) // add your immutables here
        return obj;
    const result = Array.isArray(obj) ? [] : !obj.constructor ? {} : new obj.constructor();
    if (obj instanceof Map)
        for (const key of obj.keys())
            result.set(key, deepClone(obj.get(key)));
    for (const key in obj)
        if (obj.hasOwnProperty(key))
            result[key] = deepClone(obj[key]);
    return result;
}

/**
 * Liefert alle Kombinationen
 * @param valuesArray
 * @returns {*[]}
 */
export function getAllCombinations(valuesArray) {
    const combi = [];
    let temp = [];
    const slent = Math.pow(2, valuesArray.length);

    for (var i = 0; i < slent; i++) {
        temp = [];
        for (let j = 0; j < valuesArray.length; j++) {
            if ((i & Math.pow(2, j))) {
                temp.push(valuesArray[j]);
            }
        }
        if (temp.length > 0) {
            combi.push(temp);
        }
    }

    combi.sort((a, b) => a.length - b.length);
    return combi;
}

/**
 * Liefert IterableIterator ueber alle Kombinationen der uebergebenen Laenge
 *
 * for (const combo of combinations([1,2,3], 2)) {
 *     console.log(combo)
 * }
 *
 * @param array
 * @param length
 * @returns {Generator<*[], void, *>}
 */
export function* combinations(array, length) {
    for (let i = 0; i < array.length; i++) {
        if (length === 1) {
            yield [array[i]];
        } else {
            const remaining = combinations(array.slice(i + 1, array.length), length - 1);
            for (let next of remaining) {
                yield [array[i], ...next];
            }
        }
    }
}

/**
 * https://github.com/bryc/code/blob/master/jshash/experimental/cyrb53.js
 * @param str
 * @param seed
 * @returns {number}
 */
export const cyrb53 = (str, seed = 0) => {
    let h1 = 0xdeadbeef ^ seed,
        h2 = 0x41c6ce57 ^ seed;
    for (let i = 0, ch; i < str.length; i++) {
        ch = str.charCodeAt(i);
        h1 = Math.imul(h1 ^ ch, 2654435761);
        h2 = Math.imul(h2 ^ ch, 1597334677);
    }

    h1 = Math.imul(h1 ^ (h1 >>> 16), 2246822507) ^ Math.imul(h2 ^ (h2 >>> 13), 3266489909);
    h2 = Math.imul(h2 ^ (h2 >>> 16), 2246822507) ^ Math.imul(h1 ^ (h1 >>> 13), 3266489909);

    return 4294967296 * (2097151 & h2) + (h1 >>> 0);
};