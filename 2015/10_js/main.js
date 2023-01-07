import {out} from "./util.js";
import {input0, input1} from "./input.js";

const useExample = 0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task1 = () => {
   let repeatCounter =40;
        let lookAndSaySequences =input;
        for (let repeatIdx = 0; repeatIdx < repeatCounter; repeatIdx += 1) {
            const lookAndSaySequencesArray = lookAndSaySequences.split('');
            let sayCounter = 0;
            let lastNumber = parseInt(lookAndSaySequencesArray[0], 10);

            lookAndSaySequences = '';
            for (let idx = 0; idx < lookAndSaySequencesArray.length; idx += 1) {
                const num = parseInt(lookAndSaySequencesArray[idx], 10);
                if (num === lastNumber)
                    sayCounter += 1;
                else {
                    lookAndSaySequences += `${sayCounter}${lastNumber}`;
                    sayCounter = 1;
                    lastNumber = num;
                }
            }
            lookAndSaySequences += `${sayCounter}${lastNumber}`;
        }

       // out(lookAndSaySequences);
        out(lookAndSaySequences.length);
};
const task2 = () => {
   
};

task1();
task2();
