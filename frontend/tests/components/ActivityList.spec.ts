import type { FullPraktikumDTO } from "@/api/generated/api-spec/models";

import { mount } from "@vue/test-utils";
import { describe, expect, it } from "vitest";
import { createVuetify } from "vuetify";

import ActivityList from "@/components/praktikum/ActivityList.vue";

const vuetify = createVuetify();

function mountActivityList(
  praktikum?: FullPraktikumDTO,
  selectedDate?: Date,
  canWrite = true
) {
  return mount(ActivityList, {
    props: {
      studentId: 102,
      praktikum,
      selectedDate,
      canWrite,
    },
    global: {
      plugins: [vuetify],
      stubs: {
        ActivityCreate: true,
        ActivityEdit: true,
      },
    },
  });
}

describe("ActivityList", () => {
  it("shows message when no date is selected", () => {
    const wrapper = mountActivityList();

    expect(wrapper.text()).toContain("Kein Datum ausgewählt.");
  });

  it("shows message when no activities exist for selected date", () => {
    const praktikum = {
      taetigkeiten: [],
    } as FullPraktikumDTO;

    const wrapper = mountActivityList(praktikum, new Date(2026, 8, 24));

    expect(wrapper.text()).toContain("Keine Tätigkeitsblöcke hinterlegt.");
  });

  it("shows activities for selected date", () => {
    const praktikum = {
      taetigkeiten: [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date(2026, 8, 24),
            beginnZeit: "08:00:00",
            endeZeit: "10:00:00",
          },
          homeoffice: true,
        },
      ],
    } as FullPraktikumDTO;

    const wrapper = mountActivityList(praktikum, new Date(2026, 8, 24));

    expect(wrapper.text()).toContain("08:00");
    expect(wrapper.text()).toContain("10:00");
    expect(wrapper.text()).toContain("Homeoffice");
  });

  it("shows Präsenz for non-homeoffice activity", () => {
    const praktikum = {
      taetigkeiten: [
        {
          taetigkeitenblockID: {
            studentId: 102,
            tag: new Date(2026, 8, 24),
            beginnZeit: "08:00:00",
            endeZeit: "10:00:00",
          },
          homeoffice: false,
        },
      ],
    } as FullPraktikumDTO;

    const wrapper = mountActivityList(praktikum, new Date(2026, 8, 24));

    expect(wrapper.text()).toContain("Präsenz");
  });

  it("shows create button when user can write", () => {
    const praktikum = {
      taetigkeiten: [],
    } as FullPraktikumDTO;

    const wrapper = mountActivityList(praktikum, new Date(2026, 8, 24), true);

    expect(wrapper.text()).toContain("+");
  });

  it("does not show create button when user cannot write", () => {
    const praktikum = {
      taetigkeiten: [],
    } as FullPraktikumDTO;

    const wrapper = mountActivityList(praktikum, new Date(2026, 8, 24), false);

    expect(wrapper.text()).not.toContain("+");
  });
});
