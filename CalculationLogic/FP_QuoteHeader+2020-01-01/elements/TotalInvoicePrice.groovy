if(quoteProcessor.prePhase){
    return
}
def quoteView = quoteProcessor.quoteView
api.trace(quoteView)
def totalQuoteInvoice = 0.0
quoteView.lineItems.each{ line->
    if(!line.folder){
        def thisLineTotalInvoice = line?.outputs?.find{it.resultName == "TotalInvoicePrice"}?.result
        api.trace("thisLineTotalInvoice", thisLineTotalInvoice)
        if(thisLineTotalInvoice == null){
            api.redAlert("one of the line items List/ Invoice price is null")
            return null
        }
        totalQuoteInvoice = totalQuoteInvoice + thisLineTotalInvoice
    }
}
api.trace("Total quote invoice", totalQuoteInvoice)
def totalInvoiceOutput = [
        "resultName" : "TotalInvoicePrice",
        "resultLabel": "Total Invoice Price",
        "result" : totalQuoteInvoice,
        "formatType": "MONEY",
        "resultType": "SIMPLE"
]

quoteProcessor.addOrUpdateOutput(totalInvoiceOutput)
return null
