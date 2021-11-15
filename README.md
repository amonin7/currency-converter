# Currency converter
## Summary
This is a reactive REST endpoint to handle the currency conversion.

## Details
The REST endpoint responds to a POST request to the URI `/currency/convert` with a body of type `ConversionRequest`, that will contain three fields: `from`, `to`, `amount`.

The fields `from` and `to` are three-letter strings representing the currencies, like USD or EUR and `amount` field contains the decimal number, representing the quantity to be converted, e.g. 123.45.

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