//Sales Discount %
def salesDiscountAmt = out.ListPrice - out.InvoicePrice
def salesDiscountPct = null
if(out.ListPrice !=null && out.ListPrice > 0){
    salesDiscountPct = salesDiscountAmt/out.ListPrice
}
return salesDiscountPct
