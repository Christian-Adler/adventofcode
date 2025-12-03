const res = await fetch('./input_large2.txt');
const text = await res.text();
// console.log(text);
export const input0 = text;
export const input1 = '';