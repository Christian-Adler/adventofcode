import {out} from "./util.js";
import {input0, input1} from "./input.js";
import {MD5} from "./md5.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task1 = () => {
    let count = 0;
    let eightCharacterPassword = '';
    while (eightCharacterPassword.length < 8) {
        const checkStr = input + count;
        const res = MD5(checkStr);
        if (res.startsWith('00000')) {
            out(res);
            eightCharacterPassword += res.substring(5, 6);
            out('eightCharacterPassword so far: ', eightCharacterPassword);
        }
        count++;
    }

    out(count);
    out('eightCharacterPassword: ', eightCharacterPassword);
};

const task2 = () => {
    let count = 0;

    let eightCharacterPassword = 0;
    const password = {};
    while (eightCharacterPassword < 8) {
        const checkStr = input + count;
        const res = MD5(checkStr);
        if (res.startsWith('00000')) {
            out(res);
            const strIdx = res.substring(5, 6);
            const idx = parseInt(strIdx, 10);
            if (idx >= 0 && idx < 8) {
                if (!password[strIdx]) {
                    password[strIdx] = res.substring(6, 7);
                    eightCharacterPassword++;
                    out('eightCharacterPassword so far: ', password);
                }
            }
        }
        count++;
    }

    out(count);

    let pw = '';
    for (let i = 0; i < 8; i++) {
        pw += password['' + i];
    }

    out('eightCharacterPassword: ', pw);

};

// task1();
task2();
