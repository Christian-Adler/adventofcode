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

/**
 * e.g. permutator(['c','a','t']);
 * @param inputArr
 * @returns {*[]}
 */
export const permutator = (inputArr) => {
    let result = [];

    const permute = (arr, m = []) => {
        if (arr.length === 0) {
            result.push(m)
        } else {
            for (let i = 0; i < arr.length; i++) {
                let curr = arr.slice();
                let next = curr.splice(i, 1);
                permute(curr.slice(), m.concat(next))
            }
        }
    }

    permute(inputArr)

    return result;
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