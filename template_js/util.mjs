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
