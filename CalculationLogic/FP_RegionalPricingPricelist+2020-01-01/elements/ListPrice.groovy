import java.math.RoundingMode

BigDecimal listPrice = 0.0
if(out.BasePrice != null){
    listPrice = out.BasePrice
}
if(out.MarginAdj != null){
    listPrice+= out.MarginAdj
}
if(out.AttributeAdj != null){
    listPrice+= out.AttributeAdj
}
if(out.PackagingAdj != null){
    listPrice+= out.PackagingAdj
}
if(out.TaxTariffAdj != null){
    listPrice+= out.TaxTariffAdj
}

if(out.Region == "America" || out.Region == "Americas"){
    listPrice = libs.MoneyUtils.ExchangeRate.convertMoney(listPrice, "EUR", out.Currency)
}
listPrice = listPrice.setScale(2, RoundingMode.HALF_UP)
return listPrice
