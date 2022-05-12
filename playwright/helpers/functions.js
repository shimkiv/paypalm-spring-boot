// Remove everything except numbers
export function leaveNumbersOnly(el) {
  return el.replace(/\D+/g, '');
}

export function removeSpaces(el) {
  return el.replace(/ /g, '');
}

export function replaceUsdSign(el) {
  return el.replace(/\$/g, '');
}
