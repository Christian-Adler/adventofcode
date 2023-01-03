import {out} from "./util.js";
import {input0, input1} from "./input.js";
import {MD5} from "./md5.js";

const useExample =0;

const input = (useExample ? input0 : input1);

// out(string2Array(input));

const task = (find) => {
 let count = 1;
 while(true){
  const checkStr = input+count;
  const res = MD5(checkStr);
  if(res.startsWith(find))
  {out(res);
   break;
  }
  count++;
 }

 out( count);
};

const task1 = () => {
task("00000")
};
const task2 = () => {
 task("000000")
};

// task1();
task2();
