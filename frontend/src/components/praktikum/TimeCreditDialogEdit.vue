<template>
  <v-dialog
    v-model="dialog"
    max-width="500"
    persistent
  >
    <v-card rounded="xl">
      <v-card-title class="pa-6 pb-2"> Zeitgutschrift bearbeiten </v-card-title>

      <v-card-text class="pa-6 pt-3">
        <v-form @submit.prevent="updateZeitgutschrift">
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
              Speichern
            </v-btn>
          </div>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import type {
  SimpleZeitgutschriftDTO,
  ZeitgutschriftUpdateDTO,
} from "@/api/generated/api-spec/models";

import { computed, ref, watch } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { ZeitgutschriftControllerApi } from "@/api/generated/api-spec";
import { useValidationStore } from "@/stores/validation";
import { toDateString } from "@/util/formatter";

const dialog = defineModel<boolean>({
  default: false,
});

const props = defineProps<{
  praktikumId: number;
  zeitgutschrift: SimpleZeitgutschriftDTO | undefined;
}>();

const emit = defineEmits<{
  updated: [];
}>();

const zeitgutschriftApi = ApiFactory.getInstance(ZeitgutschriftControllerApi);

const validationStore = useValidationStore();

const minuten = ref(0);
const grund = ref("");

watch(
  () => props.zeitgutschrift,
  (zeitgutschrift) => {
    if (!zeitgutschrift) {
      return;
    }

    minuten.value = zeitgutschrift.mengeMinuten ?? 0;
    grund.value = zeitgutschrift.grund ?? "";
  },
  {
    immediate: true,
  }
);

const formattedDate = computed(() => {
  const tag = props.zeitgutschrift?.tag;

  return tag ? toDateString(tag) : "";
});

async function updateZeitgutschrift() {
  const zeitgutschrift = props.zeitgutschrift;

  if (
    !zeitgutschrift ||
    zeitgutschrift.id === undefined ||
    !zeitgutschrift.tag
  ) {
    return;
  }

  const mengeMinuten = Number(minuten.value);

  if (!Number.isFinite(mengeMinuten)) {
    return;
  }

  const request: ZeitgutschriftUpdateDTO = {
    tag: zeitgutschrift.tag,
    mengeMinuten,
    grund: grund.value.trim(),
    praktikumID: props.praktikumId,
    zeitgutschriftID: zeitgutschrift.id,
  };

  await zeitgutschriftApi.updateZeitgutschrift(request);

  emit("updated");
  close();
}

function close() {
  dialog.value = false;
}
</script>
