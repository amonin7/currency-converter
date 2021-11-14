# currency-converter
## Summary
This is a reactive REST endpoint to handle the currency conversion.

## Example

Assuming a POST request to `/currency/convert` with the following body:
```json
{
  "from": "EUR",
  "to": "USD",
  "amount": 123.45
}
```
and a conversion rate of `1.1` from EUR to USD (obtained from the external provider of currency rates), the result should be:
```json
{
  "from": "EUR",
  "to": "USD",
  "amount": 123.45,
  "converted": 135.78
}
```
where `135.78` is the result of `123.45 * 1.1`, amount times the conversion rate.