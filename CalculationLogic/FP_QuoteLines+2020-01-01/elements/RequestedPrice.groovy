if(api.isSyntaxCheck()){
    return api.inputBuilderFactory()
            .createIntegerUserEntry("RequestedPrice")
            .getInput()
}else{
    return input.RequestedPrice
}