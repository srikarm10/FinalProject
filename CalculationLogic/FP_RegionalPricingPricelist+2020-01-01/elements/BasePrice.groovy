//Defines average cost of producing the product in EUR currency utilizing existing Product Cost CX table
def sku = api.product("sku")
def productCost = api.productExtension("ProductCost", Filter.equal("sku", sku))?.getAt(0)?.attribute1
api.trace("productCost", productCost)
if(out.Region == "America" || out.Region == "Americas"){
    api.trace("Region is ", out.Region)
    productCost = libs.MoneyUtils.ExchangeRate.convertMoney(productCost, "EUR", out.Currency)
}
return productCost