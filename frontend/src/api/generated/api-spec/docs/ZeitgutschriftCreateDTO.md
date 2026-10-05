
# ZeitgutschriftCreateDTO


## Properties

Name | Type
------------ | -------------
`tag` | Date
`mengeMinuten` | number
`grund` | string
`praktikumID` | number

## Example

```typescript
import type { ZeitgutschriftCreateDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "tag": null,
  "mengeMinuten": null,
  "grund": null,
  "praktikumID": null,
} satisfies ZeitgutschriftCreateDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as ZeitgutschriftCreateDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


