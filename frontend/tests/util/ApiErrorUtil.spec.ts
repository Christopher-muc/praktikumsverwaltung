import { describe, expect, it } from "vitest";

import { ResponseError } from "@/api/generated/api-spec/runtime";
import { isNotFoundError } from "@/util/ApiErrorUtil.ts";

describe("ApiErrorUtils", () => {
  it("liefert true bei einem 404 ResponseError", () => {
    const response = new Response(null, {
      status: 404,
    });

    const error = new ResponseError(response, "Not Found");

    expect(isNotFoundError(error)).toBe(true);
  });

  it("liefert false bei einem anderen ResponseError", () => {
    const response = new Response(null, {
      status: 500,
    });

    const error = new ResponseError(response, "Internal Server Error");

    expect(isNotFoundError(error)).toBe(false);
  });

  it("liefert false bei einem normalen Error", () => {
    const error = new Error("Fehler");

    expect(isNotFoundError(error)).toBe(false);
  });
});
