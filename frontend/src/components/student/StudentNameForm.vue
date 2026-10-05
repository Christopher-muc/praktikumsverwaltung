<template>
  <div>
    <v-text-field
      v-model="firstName"
      label="Vorname"
      variant="outlined"
      :error-messages="validationStore.getFieldErrors('vorname')"
      class="mb-2"
    />

    <v-text-field
      v-model="lastName"
      label="Nachname"
      variant="outlined"
      :error-messages="validationStore.getFieldErrors('nachname')"
      class="mb-2"
    />
  </div>
</template>

<script setup lang="ts">
import { useValidationStore } from "@/stores/validation";

const validationStore = useValidationStore();

const firstName = defineModel<string>("firstName", {
  required: true,
});

const lastName = defineModel<string>("lastName", {
  required: true,
});
</script>
