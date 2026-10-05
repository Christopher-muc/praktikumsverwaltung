# StudiengangControllerApi

All URIs are relative to *http://localhost:8086*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createStudiengang**](StudiengangControllerApi.md#createstudiengang) | **POST** /studiengaenge |  |
| [**deleteStudiengang**](StudiengangControllerApi.md#deletestudiengang) | **DELETE** /studiengaenge/{id} |  |
| [**getStudiengaenge**](StudiengangControllerApi.md#getstudiengaenge) | **GET** /studiengaenge |  |



## createStudiengang

> number createStudiengang(studiengangCreationDTO)



### Example

```ts
import {
  Configuration,
  StudiengangControllerApi,
} from '';
import type { CreateStudiengangRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudiengangControllerApi();

  const body = {
    // StudiengangCreationDTO
    studiengangCreationDTO: ...,
  } satisfies CreateStudiengangRequest;

  try {
    const data = await api.createStudiengang(body);
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
| **studiengangCreationDTO** | [StudiengangCreationDTO](StudiengangCreationDTO.md) |  | |

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


## deleteStudiengang

> deleteStudiengang(id)



### Example

```ts
import {
  Configuration,
  StudiengangControllerApi,
} from '';
import type { DeleteStudiengangRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudiengangControllerApi();

  const body = {
    // number
    id: 56,
  } satisfies DeleteStudiengangRequest;

  try {
    const data = await api.deleteStudiengang(body);
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


## getStudiengaenge

> Array&lt;StudiengangDTO&gt; getStudiengaenge()



### Example

```ts
import {
  Configuration,
  StudiengangControllerApi,
} from '';
import type { GetStudiengaengeRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new StudiengangControllerApi();

  try {
    const data = await api.getStudiengaenge();
    console.log(data);
  } catch (error) {
    console.error(error);
  }
}

// Run the test
example().catch(console.error);
```

### Parameters

This endpoint does not need any parameter.

### Return type

[**Array&lt;StudiengangDTO&gt;**](StudiengangDTO.md)

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

