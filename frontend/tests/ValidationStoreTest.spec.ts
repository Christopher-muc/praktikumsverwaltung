import { createPinia, setActivePinia } from "pinia";
import { beforeEach, describe, expect, it } from "vitest";

import { useValidationStore } from "@/stores/validation";

describe("ValidationStore", () => {
  beforeEach(() => {
    setActivePinia(createPinia());
  });

  it("ist initial leer", () => {
    const store = useValidationStore();

    expect(store.fieldErrors).toEqual({});
    expect(store.globalErrors).toEqual([]);
  });

  it("speichert FieldErrors", () => {
    const store = useValidationStore();

    store.setFieldErrors({
      vorname: ["Vorname darf nicht leer sein"],
      nachname: ["Nachname darf nicht leer sein"],
    });

    expect(store.getFieldErrors("vorname")).toEqual([
      "Vorname darf nicht leer sein",
    ]);

    expect(store.getFieldErrors("nachname")).toEqual([
      "Nachname darf nicht leer sein",
    ]);
  });

  it("liefert bei unbekanntem Feld ein leeres Array", () => {
    const store = useValidationStore();

    expect(store.getFieldErrors("unbekannt")).toEqual([]);
  });

  it("speichert mehrere Fehler für ein Feld", () => {
    const store = useValidationStore();

    store.setFieldErrors({
      vorname: ["Vorname darf nicht leer sein", "Vorname ist ungültig"],
    });

    expect(store.getFieldErrors("vorname")).toEqual([
      "Vorname darf nicht leer sein",
      "Vorname ist ungültig",
    ]);
  });

  it("speichert GlobalErrors", () => {
    const store = useValidationStore();

    store.setGlobalErrors([
      "Student konnte nicht gespeichert werden",
      "Unbekannter Fehler",
    ]);

    expect(store.globalErrors).toEqual([
      "Student konnte nicht gespeichert werden",
      "Unbekannter Fehler",
    ]);
  });

  it("löscht nur FieldErrors", () => {
    const store = useValidationStore();

    store.setFieldErrors({
      vorname: ["Fehler"],
    });

    store.setGlobalErrors(["Globaler Fehler"]);

    store.clearFieldErrors();

    expect(store.fieldErrors).toEqual({});
    expect(store.globalErrors).toEqual(["Globaler Fehler"]);
  });

  it("löscht nur GlobalErrors", () => {
    const store = useValidationStore();

    store.setFieldErrors({
      vorname: ["Fehler"],
    });

    store.setGlobalErrors(["Globaler Fehler"]);

    store.clearGlobalErrors();

    expect(store.fieldErrors).toEqual({
      vorname: ["Fehler"],
    });

    expect(store.globalErrors).toEqual([]);
  });

  it("löscht mit clearErrors alle Fehler", () => {
    const store = useValidationStore();

    store.setFieldErrors({
      vorname: ["Fehler"],
      nachname: ["Noch ein Fehler"],
    });

    store.setGlobalErrors(["Globaler Fehler"]);

    store.clearErrors();

    expect(store.fieldErrors).toEqual({});
    expect(store.globalErrors).toEqual([]);
  });
});
