# TaetigkeitenblockControllerApi

All URIs are relative to *http://localhost:8086*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**createTaetigkeitenblock**](TaetigkeitenblockControllerApi.md#createtaetigkeitenblock) | **POST** /taetigkeitenblock |  |
| [**deleteTaetigkeitenblock**](TaetigkeitenblockControllerApi.md#deletetaetigkeitenblock) | **DELETE** /taetigkeitenblock |  |
| [**getTaetigkeitbyDay**](TaetigkeitenblockControllerApi.md#gettaetigkeitbyday) | **GET** /taetigkeitenblock/{studentid}/{tag} |  |
| [**updateTaetigkeitenblock**](TaetigkeitenblockControllerApi.md#updatetaetigkeitenblock) | **PUT** /taetigkeitenblock |  |



## createTaetigkeitenblock

> createTaetigkeitenblock(taetigkeitenblockCreationDTO)



### Example

```ts
import {
  Configuration,
  TaetigkeitenblockControllerApi,
} from '';
import type { CreateTaetigkeitenblockRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TaetigkeitenblockControllerApi();

  const body = {
    // TaetigkeitenblockCreationDTO
    taetigkeitenblockCreationDTO: ...,
  } satisfies CreateTaetigkeitenblockRequest;

  try {
    const data = await api.createTaetigkeitenblock(body);
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
| **taetigkeitenblockCreationDTO** | [TaetigkeitenblockCreationDTO](TaetigkeitenblockCreationDTO.md) |  | |

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
| **201** | Created |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


## deleteTaetigkeitenblock

> deleteTaetigkeitenblock(studentId, beginnZeit, endeZeit, tag)



### Example

```ts
import {
  Configuration,
  TaetigkeitenblockControllerApi,
} from '';
import type { DeleteTaetigkeitenblockRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TaetigkeitenblockControllerApi();

  const body = {
    // number
    studentId: 56,
    // string
    beginnZeit: beginnZeit_example,
    // string
    endeZeit: endeZeit_example,
    // Date
    tag: 2013-10-20,
  } satisfies DeleteTaetigkeitenblockRequest;

  try {
    const data = await api.deleteTaetigkeitenblock(body);
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
| **studentId** | `number` |  | [Defaults to `undefined`] |
| **beginnZeit** | `string` |  | [Defaults to `undefined`] |
| **endeZeit** | `string` |  | [Defaults to `undefined`] |
| **tag** | `Date` |  | [Defaults to `undefined`] |

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


## getTaetigkeitbyDay

> Array&lt;TaetigkeitenblockDTO&gt; getTaetigkeitbyDay(studentid, tag)



### Example

```ts
import {
  Configuration,
  TaetigkeitenblockControllerApi,
} from '';
import type { GetTaetigkeitbyDayRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TaetigkeitenblockControllerApi();

  const body = {
    // number
    studentid: 56,
    // Date
    tag: 2013-10-20,
  } satisfies GetTaetigkeitbyDayRequest;

  try {
    const data = await api.getTaetigkeitbyDay(body);
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
| **studentid** | `number` |  | [Defaults to `undefined`] |
| **tag** | `Date` |  | [Defaults to `undefined`] |

### Return type

[**Array&lt;TaetigkeitenblockDTO&gt;**](TaetigkeitenblockDTO.md)

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


## updateTaetigkeitenblock

> TaetigkeitenblockDTO updateTaetigkeitenblock(studentId, beginnZeit, endeZeit, tag, taetigkeitenblockCreationDTO)



### Example

```ts
import {
  Configuration,
  TaetigkeitenblockControllerApi,
} from '';
import type { UpdateTaetigkeitenblockRequest } from '';

async function example() {
  console.log("🚀 Testing  SDK...");
  const api = new TaetigkeitenblockControllerApi();

  const body = {
    // number
    studentId: 56,
    // string
    beginnZeit: beginnZeit_example,
    // string
    endeZeit: endeZeit_example,
    // Date
    tag: 2013-10-20,
    // TaetigkeitenblockCreationDTO
    taetigkeitenblockCreationDTO: ...,
  } satisfies UpdateTaetigkeitenblockRequest;

  try {
    const data = await api.updateTaetigkeitenblock(body);
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
| **studentId** | `number` |  | [Defaults to `undefined`] |
| **beginnZeit** | `string` |  | [Defaults to `undefined`] |
| **endeZeit** | `string` |  | [Defaults to `undefined`] |
| **tag** | `Date` |  | [Defaults to `undefined`] |
| **taetigkeitenblockCreationDTO** | [TaetigkeitenblockCreationDTO](TaetigkeitenblockCreationDTO.md) |  | |

### Return type

[**TaetigkeitenblockDTO**](TaetigkeitenblockDTO.md)

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

