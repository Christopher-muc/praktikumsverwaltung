# PraktikumControllerApi

All URIs are relative to *http://localhost:8086*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createPraktikum**](PraktikumControllerApi.md#createpraktikum) | **POST** /praktikum |  |
| [**deletePraktikum**](PraktikumControllerApi.md#deletepraktikum) | **DELETE** /praktikum/{id} |  |
| [**getPraktikum**](PraktikumControllerApi.md#getpraktikum) | **GET** /praktikum/{id} |  |
| [**updatePraktikum**](PraktikumControllerApi.md#updatepraktikum) | **PUT** /praktikum/{id} |  |



## createPraktikum

> number createPraktikum(praktikumDTO)



### Example

```ts
import {
  Configuration,
  PraktikumControllerApi,
} from '';
import type { CreatePraktikumRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new PraktikumControllerApi();

  const body = {
    // PraktikumDTO
    praktikumDTO: ...,
  } satisfies CreatePraktikumRequest;

  try {
    const data = await api.createPraktikum(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **praktikumDTO** | [PraktikumDTO](PraktikumDTO.md) |  | |

### Return type

**number**

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## deletePraktikum

> deletePraktikum(id)



### Example

```ts
import {
  Configuration,
  PraktikumControllerApi,
} from '';
import type { DeletePraktikumRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new PraktikumControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies DeletePraktikumRequest;

  try {
    const data = await api.deletePraktikum(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## getPraktikum

> FullPraktikumDTO getPraktikum(id)



### Example

```ts
import {
  Configuration,
  PraktikumControllerApi,
} from '';
import type { GetPraktikumRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new PraktikumControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies GetPraktikumRequest;

  try {
    const data = await api.getPraktikum(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |

### Return type

[**FullPraktikumDTO**](FullPraktikumDTO.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: Not defined
- **Accept**: `*/*`


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## updatePraktikum

> updatePraktikum(id, praktikumUpdateDTO)



### Example

```ts
import {
  Configuration,
  PraktikumControllerApi,
} from '';
import type { UpdatePraktikumRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new PraktikumControllerApi();

  const body = {
    // number
    id: 56,
    // PraktikumUpdateDTO
    praktikumUpdateDTO: ...,
  } satisfies UpdatePraktikumRequest;

  try {
    const data = await api.updatePraktikum(body);
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters


| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **id** | `number` |  | [Defaults to `undefined`] |
| **praktikumUpdateDTO** | [PraktikumUpdateDTO](PraktikumUpdateDTO.md) |  | |

### Return type

`void` (Empty response body)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: `application/json`
- **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)

