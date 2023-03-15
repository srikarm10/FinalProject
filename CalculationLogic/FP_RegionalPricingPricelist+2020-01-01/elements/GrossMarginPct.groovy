if(null in [out.BasePrice, out.ListPrice]){
    return null
}else if(out.ListPrice == 0){
    api.redAlert("List Price is 0, divide by 0 error")
    return null
}
def marginPct = (out.ListPrice - out.BasePrice)/out.ListPrice
api.trace("marginPct", marginPct)
//Get Pricing Strategy value from PriceStrategy Matrix table
def businessUnit = api.product("BusinessUnit")
def priceStrategyPct = api.vLookup("PriceStrategy", "RedAlert", businessUnit)
if(marginPct < priceStrategyPct){
    api.redAlert("Margin is less than the defined margin of "+priceStrategyPct * 100)
}
api.trace("price strategy", priceStrategyPct)
return marginPct
