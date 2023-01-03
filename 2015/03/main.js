import {downloadText, out, string2Array} from "./util.js";
import {input0, input1} from "./input.js";
import {Pos} from "./pos.js";
import {Svg} from "./svg.js";

const useExample = 0;

const downloadSVG = 0;

const input = (useExample ? input0 : input1);

const task1 = () => {
    const svg = new Svg();

    const visited = new Set();
    const actPos = new Pos(0, 0);
    visited.add(actPos.toString());
    svg.add(actPos, '#ff0000');

    const route = string2Array(input);
    for (let i = 0; i < route.length; i++) {
        const routeElement = route[i];
        if (routeElement === '^')
            actPos.add(0, -1);
        else if (routeElement === 'v')
            actPos.add(0, 1);
        else if (routeElement === '<')
            actPos.add(-1, 0);
        else if (routeElement === '>')
            actPos.add(1, 0);
        if (!visited.has(actPos.toString())) {
            visited.add(actPos.toString());
            svg.add(actPos, '#ff0000');
        }
    }

    out(actPos);
    out('visited: ', visited.size);

    // out(svg.toSVGString());
    if (downloadSVG)
        downloadText('aoc.svg', svg.toSVGString());
    document.getElementById('out').innerHTML = svg.toSVGStringAged();
};
const task2 = () => {
    const svg = new Svg();

    const visited = new Set();
    const actPos = new Pos(0, 0);
    const actPosRobot = new Pos(0, 0);
    visited.add(actPos.toString());
    svg.add(actPos, '#ff0000');

    const route = string2Array(input);
    for (let i = 0; i < route.length; i++) {
        const p = i % 2 === 0 ? actPos : actPosRobot;
        const routeElement = route[i];
        if (routeElement === '^')
            p.add(0, -1);
        else if (routeElement === 'v')
            p.add(0, 1);
        else if (routeElement === '<')
            p.add(-1, 0);
        else if (routeElement === '>')
            p.add(1, 0);
        if (!visited.has(p.toString())) {
            visited.add(p.toString());
            svg.add(p, '#ff0000');
        }
    }

    out(actPos);
    out(actPosRobot);
    out('visited: ', visited.size);

    // out(svg.toSVGString());
    if (downloadSVG)
        downloadText('aoc2.svg', svg.toSVGString());
    document.getElementById('out2').innerHTML = svg.toSVGStringAged();
};

task1();
task2();
