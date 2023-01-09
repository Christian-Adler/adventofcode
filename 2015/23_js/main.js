import {out, string2SplitLinesArray} from "./util.js";
import {input0, input1} from "./input.js";

const useExample = 0;

const input =string2SplitLinesArray( (useExample ? input0 : input1));

const task1 = (aStart =0) => {
    let a= aStart;
    let b = 0;
    let index = 0;
let round = 0;
    do {
        console.log('round: ',++round,' idx:', index,' ', input[index], 'a: ',a,', b: ',b);
        const commandLine = input[index].split(' ');
        const cmd = commandLine[0];
        let registerOrValue = commandLine[1];

        if (cmd === 'inc') {
            if (registerOrValue === 'a')
                a += 1;
            else
                b += 1;
            index += 1;
        } else if (cmd === 'hlf') {
            if (registerOrValue === 'a')
                a *= 0.5;
            else
                b *= 0.5;
            index += 1;
        } else if (cmd === 'tpl') {
            if (registerOrValue === 'a')
                a *= 3;
            else
                b *= 3
            index += 1;
        } else if (cmd === 'jmp')
            index += parseInt(registerOrValue, 10);
        else {
            registerOrValue = registerOrValue.replace(',', '');
            const jumpDistance = parseInt(commandLine[2], 10);
            if (cmd === 'jie') {
                if (registerOrValue === 'a' && (a % 2 === 0))
                    index += jumpDistance;
                else if (registerOrValue === 'b' && (b % 2 === 0))
                    index += jumpDistance;
                else
                    index += 1;
            } else if (cmd === 'jio')
                if (registerOrValue === 'a' && a === 1)
                    index += jumpDistance;
                else if (registerOrValue === 'b' && b === 1)
                    index += jumpDistance;
                else
                    index += 1;
        }

    } while (input[index] !== undefined);

 console.log('a: ',a,', b: ',b);
};
const task2 = () => {
   task1(1);
};

// task1();
task2();
