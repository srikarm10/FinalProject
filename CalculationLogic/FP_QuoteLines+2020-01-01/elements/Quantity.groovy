if(api.isSyntaxCheck()){
    return api.inputBuilderFactory()
    .createIntegerUserEntry("Quantity")
    .setRequired(true)
    .getInput()
}else{
    return input.Quantity
}