<template>
  <v-dialog
    v-model="dialog"
    max-width="500"
    persistent
  >
    <v-card rounded="xl">
      <v-card-title class="pa-6 pb-2"> Studiengang hinzufügen </v-card-title>

      <v-card-text class="pa-6">
        <v-form @submit.prevent="save">
          <v-text-field
            v-model="name"
            label="Name"
            variant="outlined"
            :error-messages="validationStore.getFieldErrors('name')"
            autofocus
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
              variant="flat"
              type="submit"
            >
              Anlegen
            </v-btn>
          </div>
        </v-form>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>

<script setup lang="ts">
import { ref } from "vue";

import { ApiFactory } from "@/api/ApiFactory";
import { StudiengangControllerApi } from "@/api/generated/api-spec/apis";
import { useValidationStore } from "@/stores/validation";

const dialog = defineModel<boolean>({
  required: true,
});

const emit = defineEmits<{
  created: [];
}>();

const api = ApiFactory.getInstance(StudiengangControllerApi);

const validationStore = useValidationStore();

const name = ref("");

async function save() {
  await api.createStudiengang({
    name: name.value.trim(),
  });

  emit("created");
  close();
}

function close() {
  dialog.value = false;
  name.value = "";
}
</script>
