//Attribute Adj - Matrix type
//Get Attribute adjustment based on Product Lifecycle and then apply it on base price
def productLifecycle = api.product("ProductLifeCycle")
api.trace("productlife cycle", productLifecycle)
def attrAdjPct = api.vLookup("AttributeAdj", "AttributeAdj", productLifecycle)
//def attrAdjPct = api.findLookupTableValues("AttributeAdj", Filter.equal("name", productLifecycle))?.getAt(0)?.attribute1
api.trace("attr adj pct", attrAdjPct)
if(null in [out.BasePrice, attrAdjPct]){
    return null
}
def attributeAdjAmt = out.BasePrice * attrAdjPct
if(out.Region == "America" || out.Region == "Americas"){
    marginAdjAmt = libs.MoneyUtils.ExchangeRate.convertMoney(attributeAdjAmt, "EUR", out.Currency)
}
return attributeAdjAmt