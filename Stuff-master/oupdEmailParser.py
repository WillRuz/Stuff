#open file
with open('ok.eml') as data:
    #turn file into a stirng variable
    eggs = data.read()
    #these variables will be useful in parsing information; start/end of html as well as some strings to use in find()
    start = eggs.find("<html>")
    end = eggs.find("</html>")
    blnkspc = "&nbsp;&nbsp;&nbsp;"
    br = "<br>"
    #find the location of each selected field in the email
    caseIDLocation = eggs.find("OUPD Case ID No.")
    dateStolenLocation = eggs.find("Date Stolen")
    reportingUserLocation = eggs.find("Reporting User First/Last Name")
    fourByFourLocation = eggs.find("Reporting User 4x4")
    macLocation = eggs.find("Physical Address (MAC)")
    deviceLocation = eggs.find("Device type")
    deviceDescLocation = eggs.find("Device Description (Model / OS)")
    netRegLocation = eggs.find("Registered With NetReg? (Net-reg.ou.edu)")
    otherInfoLocation = eggs.find("Other important information")

    #start with the document, and trim it from your date point and onward
    caseHtml = eggs[caseIDLocation:end]
    #find the first case of our triple blankspace, and our info should be between the spaces and the <br>
    caseIDPlace = caseHtml.find(blnkspc)
    #We add 18 characters so we dont have "&nbsp;&nbsp;&nbsp;" in each of our fields
    caseID = caseHtml[caseIDPlace + 18: caseHtml.find(br)]

    dateHtml = eggs[dateStolenLocation:end]
    datePlace = dateHtml.find(blnkspc)
    dateStolen = dateHtml[datePlace + 18: dateHtml.find(br)]

    reportHtml = eggs[reportingUserLocation:end]
    reportPlace = reportHtml.find(blnkspc)
    reportingUser = reportHtml[reportPlace + 18: reportHtml.find(br)]

    fourHtml = eggs[fourByFourLocation:end]
    fourPlace = fourHtml.find(blnkspc)
    fourByFour = fourHtml[fourPlace + 18: fourHtml.find(br)]

    macHtml = eggs[macLocation:end]
    macPlace = macHtml.find(blnkspc)
    macAddress = macHtml[macPlace + 18: macHtml.find(br)]

    deviceHtml = eggs[deviceLocation:end]
    devicePlace = deviceHtml.find(blnkspc)
    device = deviceHtml[devicePlace + 18: deviceHtml.find(br)]

    deviceDescHtml = eggs[deviceDescLocation:end]
    deviceDescPlace = deviceDescHtml.find(blnkspc)
    deviceDescription = deviceDescHtml[deviceDescPlace + 18: deviceDescHtml.find(br)]

    netHtml = eggs[netRegLocation + 46:end]
    netregRegistration = netHtml[0: netHtml.find(br)]

    otherHtml = eggs[otherInfoLocation + 32:end]
    otherPlace = otherHtml.find(blnkspc)
    otherInfo = otherHtml[otherPlace + 18: otherHtml.find(br)]

    print(caseID)
    print(dateStolen)
    print(reportingUser)
    print(fourByFour)
    print(macAddress)
    print(device)
    print(deviceDescription)
    print(netregRegistration)
    print(otherInfo)
data.close()