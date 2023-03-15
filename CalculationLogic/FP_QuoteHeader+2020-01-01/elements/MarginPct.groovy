//Total Margin
def quoteMargin = 0.0
quoteProcessor.quoteView.lineItems.each{ line->
    if(!line.folder){
        def marginAmt = line?.outputs?.find{it.resultName == "Margin"}.result
        def qty = line?.outputs?.find{it.resultName == "Quantity"}.result
        def totalInvoice = line?.outputs?.find{it.resultName == "TotalInvoicePrice"}.result
        def lineTotalMargin = (marginAmt * qty)/totalInvoice
        if(lineTotalMargin != null){
            quoteMargin = quoteMargin + lineTotalMargin
        }
    }

}
def marginPctOutput = [
                "resultName" : "MarginPct",
               "resultLabel": "Margin %",
               "result"     : quoteMargin,
               "formatType" : "PERCENT",
               "resultType" : "SIMPLE"]

quoteProcessor.addOrUpdateOutput(marginPctOutput)
api.trace("Total margin ", quoteMargin)
return null