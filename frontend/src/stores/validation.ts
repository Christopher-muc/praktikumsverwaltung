import { defineStore } from "pinia";
import { ref } from "vue";

export const useValidationStore = defineStore("validation", () => {
  const fieldErrors = ref<Record<string, string[]>>({});
  const globalErrors = ref<string[]>([]);

  function setFieldErrors(errors: Record<string, string[]>) {
    fieldErrors.value = errors;
  }

  function setGlobalErrors(errors: string[]) {
    globalErrors.value = errors;
  }

  function getFieldErrors(field: string): string[] {
    return fieldErrors.value[field] ?? [];
  }

  function clearFieldErrors() {
    fieldErrors.value = {};
  }

  function clearGlobalErrors() {
    globalErrors.value = [];
  }

  function clearErrors() {
    fieldErrors.value = {};
    globalErrors.value = [];
  }

  return {
    fieldErrors,
    globalErrors,
    setFieldErrors,
    setGlobalErrors,
    getFieldErrors,
    clearFieldErrors,
    clearGlobalErrors,
    clearErrors,
  };
});