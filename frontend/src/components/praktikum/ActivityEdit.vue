<template>
  <v-dialog
    v-model="dialog"
    max-width="500"
    persistent
  >
    <v-card rounded="xl">
      <v-card-title class="pa-6 pb-2">
        Tätigkeitsblock bearbeiten
      </v-card-title>

      <v-card-text class="pa-6">
        <v-form @submit.prevent="save">
          <v-text-field
            v-model="beginnZeit"
            label="Beginn"
            type="time"
            variant="outlined"
            :error-messages="validationStore.getFieldErrors('beginnZeit')"
            class="mb-2"
          />

          <v-text-field
            v-model="endeZeit"
            label="Ende"
            type="time"
            variant="outlined"
            :error-messages="validationStore.getFieldErrors('endeZeit')"
            class="mb-2"
          />

          <v-switch
            v-model="homeoffice"
            label="Homeoffice"
            color="primary"
            :error-messages="validationStore.getFieldErrors('homeoffice')"
          />

          <div class="d-flex justify-end ga-2 mt-6">
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
  TaetigkeitenblockCreationDTO,
  TaetigkeitenblockDTO,
} from "@/api/generated/api-spec/models";

import { ref, watch } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { TaetigkeitenblockControllerApi } from "@/api/generated/api-spec/apis";
import { useValidationStore } from "@/stores/validation";

const dialog = defineModel<boolean>({
  default: false,
});

const props = defineProps<{
  studentId: number;
  activity: TaetigkeitenblockDTO | null;
}>();

const emit = defineEmits<{
  updated: [];
}>();

const taetigkeitenblockApi = ApiFactory.getInstance(
  TaetigkeitenblockControllerApi
);

const validationStore = useValidationStore();

const beginnZeit = ref("");
const endeZeit = ref("");
const homeoffice = ref(false);

watch(
  () => props.activity,
  (activity) => {
    if (!activity) {
      return;
    }

    beginnZeit.value =
      activity.taetigkeitenblockID?.beginnZeit ?? "";

    endeZeit.value =
      activity.taetigkeitenblockID?.endeZeit ?? "";

    homeoffice.value =
      activity.homeoffice ?? false;
  },
  {
    immediate: true,
  }
);

async function save() {
  const activity = props.activity;
  const id = activity?.taetigkeitenblockID;

  if (
    !activity ||
    id?.studentId === undefined ||
    id.tag === undefined ||
    id.beginnZeit === undefined ||
    id.endeZeit === undefined
  ) {
    return;
  }

  const request: TaetigkeitenblockCreationDTO = {
    studentId: props.studentId,
    tag: id.tag,
    beginnZeit: beginnZeit.value,
    endeZeit: endeZeit.value,
    homeoffice: homeoffice.value,
  };

  await taetigkeitenblockApi.updateTaetigkeitenblock(
    id.studentId,
    id.beginnZeit,
    id.endeZeit,
    id.tag,
    request
  );

  emit("updated");
  close();
}

function close() {
  dialog.value = false;
}
</script>