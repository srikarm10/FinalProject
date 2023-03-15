//Get historical quantity from transaction
def dmctx = api.getDatamartContext()
def table = "Transactions"
def tableData = dmctx.getDatamart(table)
api.trace("table data", tableData)
def query = dmctx.newQuery(tableData, true)
query.select("SUM(Quantity)", "Quantity")
.where(Filter.equal("ProductId", api.product("sku")))
def result = dmctx.executeQuery(query)
api.trace(query)
def qty = null
result.getData().each{
  qty = it.Quantity
}
return qty
