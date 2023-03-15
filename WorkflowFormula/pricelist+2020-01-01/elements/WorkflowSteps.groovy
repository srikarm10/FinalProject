//final marginUtil = libs.MarginCalculation.GetMargin
def pricelistApprovers = api.findLookupTableValues("PriceListApprovalLevels").collectEntries() { row ->
    [(row.name): row.attribute1]
}
api.trace("approvers list", pricelistApprovers)

def pid = pricelist.id
api.trace("pricelist id ", pid)

def pricelistItemData = api.find("PLI", 0, api.getMaxFindResultsLimit(), "attribute2", Filter.equal("pricelistId", pid))

def reasons = []
pricelistItemData.each {
    def businessUnit = api.product("attribute2", it.sku)
    api.trace("business unit", businessUnit)
    //BigDecimal basePrice = it.attribute2
    BigDecimal basePrice = it.attribute3
    //BigDecimal listPrice = it.attribute7
    BigDecimal listPrice = it.attribute8
    BigDecimal marginPct = null
    if(null in [basePrice, listPrice]){
        api.addWarning("Base price or List price is null")
        reasons << "Base price or List price is null"
    }else if(listPrice == 0){
        api.addWarning("List price is 0, divide by 0 error")
        reasons << "List price is 0"
    }else{
        marginPct = (listPrice - basePrice)/listPrice
    }
    def pricelistApprovalKey = pricelistApprovers.get(businessUnit)
    api.trace("pricelistApprovers.businessUnit =", pricelistApprovalKey)
    api.trace(" margin compare ", pricelistApprovalKey  +" Vs "+ marginPct)
    if(marginPct < pricelistApprovalKey){
          reasons << "Margin for sku "+it.sku+" is "+marginPct+" which is less than approved margin of "+pricelistApprovalKey
    }
    //BigDecimal marginPct = GetMargin.getMarginPct(it)
    api.trace("Margin Percent", marginPct)
}

api.trace("reasons ", reasons)

workflow.addApprovalStep("Product Manager - Approval")
.withUserGroupApprovers("ProductManager")
.withMinApprovalsNeeded(2)
.withReasons(reasons.join("\n"))

workflow.addApprovalStep("VP Sales - Approval")
        .withUserGroupApprovers("VPSales")
        .withMinApprovalsNeeded(1)
        .withReasons(reasons.join("\n"))

workflow.addWatcherStep("Pricing Manager - Watcher")
.withReasons(reasons.join(","))
.withUserGroupWatchers("PricingManager")