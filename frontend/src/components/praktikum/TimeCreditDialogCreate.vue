<template>
  <v-dialog
    v-model="dialog"
    max-width="500"
    persistent
  >
    <v-card rounded="xl">
      <v-card-title class="pa-6 pb-2"> Zeitgutschrift hinzufügen </v-card-title>

      <v-card-text class="pa-6 pt-3">
        <v-form @submit.prevent="createZeitgutschrift">
          <v-text-field
            :model-value="formattedDate"
            label="Datum"
            variant="outlined"
            readonly
            :error-messages="validationStore.getFieldErrors('tag')"
            class="mb-2"
          />

          <v-text-field
            v-model.number="minuten"
            label="Minuten"
            type="number"
            variant="outlined"
            suffix="min"
            :error-messages="validationStore.getFieldErrors('mengeMinuten')"
            class="mb-2"
          />

          <v-text-field
            v-model="grund"
            label="Grund"
            variant="outlined"
            :error-messages="validationStore.getFieldErrors('grund')"
            class="mb-2"
          />

          <div class="d-flex justify-end ga-2 mt-4">
            <v-btn
              variant="text"
              @click="close"
            >
              Abbrechen
            </v-btn>

            <v-btn
              color="primary"
              type="submit"
            >
              Zeitgutschrift anlegen
            </v-btn>
          </div>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type { ZeitgutschriftCreateDTO } from "@/api/generated/api-spec/models";

import { computed, ref } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { ZeitgutschriftControllerApi } from "@/api/generated/api-spec";
import { useValidationStore } from "@/stores/validation";
import { toDateString } from "@/util/formatter";

const dialog = defineModel<boolean>({
  default: false,
});

const props = defineProps<{
  praktikumId: number;
  selectedDate?: Date;
}>();

const emit = defineEmits<{
  created: [];
}>();

const zeitgutschriftApi = ApiFactory.getInstance(ZeitgutschriftControllerApi);

const validationStore = useValidationStore();

const minuten = ref(0);
const grund = ref("");

const formattedDate = computed(() => {
  if (!props.selectedDate) {
    return "";
  }

  return toDateString(props.selectedDate);
});

async function createZeitgutschrift() {
  if (!props.selectedDate) {
    return;
  }

  const request: ZeitgutschriftCreateDTO = {
    tag: props.selectedDate,
    mengeMinuten: minuten.value,
    grund: grund.value.trim(),
    praktikumID: props.praktikumId,
  };

  await zeitgutschriftApi.createZeitgutschrift(request);

  emit("created");
  close();
}

function close() {
  dialog.value = false;
  resetForm();
}

function resetForm() {
  minuten.value = 0;
  grund.value = "";
}
</script>
