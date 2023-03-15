//Get Quote total invoice
def totalInvoicePrice = quote?.outputs?.each{
    it.resultName == "TotalInvoicePrice"
}?.result?.getAt(0)

api.trace("TotalInvoice for Quote", totalInvoicePrice)

//Get customer region
def customer = quote?.inputs?.each{
    it.name == "Customer"
}?.value?.getAt(0)
api.trace("Customer on quote", customer)
def region = api.customer("Region", customer)
api.trace("Customer region on quote", region)


if(region == null){
    region = ""
}
//Get approval threshold values from Price Parameter table sorted by Approval Level, Filtered by Region and Revenue Threshold
/**
def filters = [
        Filter.equal("key2", region),
        Filter.lessThan("attribute2", totalInvoicePrice)
]

def approvals = api.findLookupTableValues("ApprovalThreshold", ['attribute1'], "key1", *filters)
api.trace("approvals", approvals)
*/
def approvalLevels = api.findLookupTableValues("ApprovalThreshold",
        Filter.equal("key2", region),
        Filter.lessThan("attribute2", totalInvoicePrice)
)?.sort{a,b -> a.key1 <=> b.key1}

api.trace("thresholdValues for Quote", approvalLevels)

approvalLevels.each{ level->
    def group = level.attribute1
    def revenueThreshold = level.attribute2
    workflow.addApprovalStep(group)
    .withUserGroupApprovers(group)
    .withReasons("${group} must approval quote because Total Invoice price is over ${revenueThreshold}")
}

return null