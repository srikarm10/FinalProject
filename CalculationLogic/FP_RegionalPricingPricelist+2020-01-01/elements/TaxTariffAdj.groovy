//Tax adj based on region- Simple table type, so query by Region as value
def taxAdjPct = api.vLookup("TaxAdj", out.Region)
api.trace("tax adj pct", taxAdjPct)
if(null in [out.BasePrice, taxAdjPct]){
    return null
}
def taxAdjAmt = taxAdjPct * out.BasePrice

if(out.Region == "America" || out.Region == "Americas"){
    taxAdjAmt = libs.MoneyUtils.ExchangeRate.convertMoney(taxAdjAmt, "EUR", out.Currency)
}

return taxAdjAmt
