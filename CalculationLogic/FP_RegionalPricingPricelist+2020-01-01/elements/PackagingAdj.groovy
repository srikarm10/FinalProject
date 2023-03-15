//PackagingAdj Matrix type - provide attributeName to fetch and key to filter record
def packageAdjPct = api.vLookup("PackagingAdj", "PackagingAdj", api.product("Size"))
api.trace("product size", api.product("Size"))
api.trace("packageAdj", packageAdjPct)
if(null in [out.BasePrice, packageAdjPct]){
    return null
}
def packageAdjAmt =  out.BasePrice * packageAdjPct

if(out.Region == "America" || out.Region == "Americas"){
    packageAdjAmt = libs.MoneyUtils.ExchangeRate.convertMoney(packageAdjAmt, "EUR", out.Currency)
}
return packageAdjAmt