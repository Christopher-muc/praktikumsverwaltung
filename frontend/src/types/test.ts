try {
  throw new TypeError("Falscher Typ");
} catch (e) {
  if (e instanceof TypeError) {
    console.log("Es war ein TypeError");
  } else if (e instanceof Error) {
    console.log("Anderer Error");
  }
}
