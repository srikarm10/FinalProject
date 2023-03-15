def productCost = api.productExtension("ProductCost", Filter.equal("sku", api.product("sku")))?.getAt(0)?.attribute1
api.trace("product cost", productCost)
if(productCost == null){
    api.redAlert("Product cost is null for "+api.product("sku"))
    return null
}
//Convert product cost from EUR to customer currency
productCost = libs.MoneyUtils.ExchangeRate.convertMoney(productCost, "EUR", out.Currency)
return productCost
