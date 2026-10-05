import { ResponseError } from "@/api/generated/api-spec/runtime";

export function isNotFoundError(error: unknown): boolean {
  return error instanceof ResponseError && error.response.status === 404;
}
