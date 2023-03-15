
if(api.isSyntaxCheck()){
    //Get regions from the Region table
    def regions = api.findLookupTableValues("Region")?.collect(){it.name}?.sort()
    return api.inputBuilderFactory()
    .createOptionEntry("Region")
    .setOptions(regions)
    .getInput()
}else{
    input.Region
}