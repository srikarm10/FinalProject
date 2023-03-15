//Get Margin Adjustment Simple parameter table by product group
def productGroup = api.currentItem()?.attribute1
//def marginAdjPct = api.vLookup("MarginAdj", productGroup, "name") //not necessary to use 3 param method for Simple table type
def marginAdjPct = api.vLookup("MarginAdj", productGroup)
api.trace("marginPct", marginAdjPct)

if(null in [out.BasePrice, marginAdjPct]){
    return null
}

def marginAdjAmt = out.BasePrice * marginAdjPct
if(out.Region == "America" || out.Region == "Americas"){
    marginAdjAmt = libs.MoneyUtils.ExchangeRate.convertMoney(marginAdjAmt, "EUR", out.Currency)
}
return marginAdjAmt
