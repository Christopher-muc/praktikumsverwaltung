
# PraktikumDTO


## Properties

Name | Type
------------ | -------------
`beginnDatum` | Date
`endeDatum` | Date
`studentId` | number
`wochenarbeitszeit` | number
`benoetigteWochen` | number

## Example

```typescript
import type { PraktikumDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "beginnDatum": null,
  "endeDatum": null,
  "studentId": null,
  "wochenarbeitszeit": null,
  "benoetigteWochen": null,
} satisfies PraktikumDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as PraktikumDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


