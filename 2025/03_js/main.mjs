import {input0, input1} from "./input.mjs";
import {out, string2Array, string2SplitLinesArray} from "./util.mjs";

const useExample = 1;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task1 = () => {
  // console.log(input.length);
  const t1 = new Date().getTime();
  const lines = string2SplitLinesArray(input);

  let jolatgeSum12 = BigInt(0);
  for (const line of lines) {
    const values12 = [];
    const values = string2Array(line.trim()).map(Number);

    let actMaxValIdx = -1;
    for (let n = 1; n <= 12; n++) {
      let maxVal = -1;
      for (let i = actMaxValIdx + 1; i < values.length - (12 - n); i++) {
        let val = values[i];
        if (val > maxVal) {
          maxVal = val;
          actMaxValIdx = i;
          if (val === 9) break;
        }
      }
      if (maxVal === -1) throw new Error();
      values12.push(maxVal);
    }

    let result = BigInt(0);
    for (const value of values12) {
      result *= BigInt(10);
      result += BigInt(value);
    }
    jolatgeSum12 += result;
  }

  out(jolatgeSum12);
  const t2 = new Date().getTime();
  out(t2 - t1 + "ms");
};
const task2 = () => {

};

task1();
task2();
