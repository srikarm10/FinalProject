def region = api.customer("Region")
if(api.isDebugMode()){
    region = "America"
}
//Get price from region specific pricelist
def listPrice = api.pricelist(region)
api.trace("pricelist price", listPrice)
if(listPrice == null){
    api.redAlert("List price is not available for this "+api.product("sku"))
    return null
}
def plistCurrency = api.pricelist(region, "Currency")
api.trace("price list is in currency", plistCurrency)
listPrice = libs.MoneyUtils.ExchangeRate.convertMoney(listPrice, plistCurrency, out.Currency)
return listPrice