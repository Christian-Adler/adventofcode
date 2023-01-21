import {input0, input1} from "./input.js";
import {out} from "./util.js";
import {Svg} from "./svg.js";
import {Pos} from "./pos.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

const task = (numRows) => {
    const svg = new Svg(10);
    let actRow = input;
    let noTraps = (actRow.split(".").length - 1);
    for (let i = 0; i < numRows - 1; i++) {
        // out(actRow);
        let tmp = '';

        for (let j = 0; j < actRow.length; j++) {
            if (isTrap(actRow, j)) {
                tmp += '^';
                if (numRows === 40)
                    svg.add(new Pos(j, i));
            } else {
                tmp += '.';
            }
        }

        actRow = tmp;
        noTraps += (actRow.split(".").length - 1);
    }
    // out(actRow);

    out("noTraps:", noTraps);

    if (numRows === 40)
        document.getElementById('out').innerHTML = svg.toSVGStringAged();
};

function isTrap(str, idx) {
    let left = false, right = false, center;
    if (idx > 0)
        left = str.charAt(idx - 1) === '^';
    if (idx < str.length)
        right = str.charAt(idx + 1) === '^';
    center = str.charAt(idx) === '^';

    return left && center && !right || !left && center && right || !left && !center && right || left && !center && !right;
}

task(40);
task(400000);
