import csv
import time

# This section formats the AzureActivity file before being merged with the LocalActivity file
with open('AzureActivity-colon.csv', 'rb') as azure:
    azure_reader = csv.reader(azure, delimiter=';')

    with open("AzureActivity.csv", "wb") as Azure:
        wtr = csv.writer(Azure, delimiter=';')

        for row in azure_reader:
            # Checks if the row is the header row to write the new header row
            if azure_reader.line_num == 1:
                # Writes the new header row, excluding the license columns and adding new 'Most Recent' column
                wtr.writerow((row[0], row[1], row[8], row[9], row[10], row[11], row[12], row[13],
                              "Most Recent Activity Date"))
                continue
            # Get the recent dates and determine the most recent activity and make another column
            OGDates = [row[8], row[9], row[10], row[11], row[12], row[13]]
            NewDates = []
            for date in OGDates:
                if date != '':
                    NewDates.append(time.strptime(date, '%m/%d/%Y'))
                mostRecentDate = 0  # This will remain zero only if this User has no recorded activity
            for date in NewDates:
                if date > mostRecentDate:
                    mostRecentDate = date
            # If the User has no record of activity, indicate that in the 'Most Recent' column
            if mostRecentDate == 0:
                fmtDate = "No Previous Activity"
            else:
                fmtDate = str(mostRecentDate[1]) + '/' + str(mostRecentDate[2]) + '/' + str(mostRecentDate[0])
            # Excludes the license columns from the new file and adds new 'Most Recent' column
            wtr.writerow((row[0], row[1], row[8], row[9], row[10], row[11], row[12], row[13], fmtDate))

# The next section of code is to check each entry in the LocalActivity file for a corresponding entry in the
# AzureActivity file and then merge the two as one row in the new merged file. If a LocalActivity entry has no match,
# then it is added to the merged file with blank Azure data entries, and the message 'No Azure Record'.
with open('LocalActivity-General-colon.csv', 'rb') as local:
    lclRdr = csv.reader(local, delimiter=';')

    with open("mergedFinally.csv", "wb") as merge:
        mrgWtr = csv.writer(merge, delimiter=';')

        for lcR in lclRdr:
            # has_match is used to check if a LocalActivity entry matches with an entry in AzureActivity
            has_match = False
            with open("AzureActivity.csv", "rb") as AZURE:
                azrRdr = csv.reader(AZURE, delimiter=';')

                for azR in azrRdr:

                    if azrRdr.line_num == 1 and lclRdr.line_num == 1:
                        has_match = True
                        mrgWtr.writerow((lcR[0], lcR[2], lcR[3], lcR[4], lcR[5], lcR[6], lcR[7], lcR[8], azR[1], azR[2],
                                        azR[3], azR[4], azR[5], azR[6], azR[7], azR[8]))
                        break

                    if azR[0].lower() == lcR[0].lower():
                        has_match = True
                        mrgWtr.writerow((lcR[0], lcR[2], lcR[3], lcR[4], lcR[5], lcR[6], lcR[7], lcR[8], azR[1], azR[2],
                                         azR[3], azR[4], azR[5], azR[6], azR[7], azR[8]))
                        break

                if has_match is False:
                    mrgWtr.writerow((lcR[0], lcR[2], lcR[3], lcR[4], lcR[5], lcR[6], lcR[7], lcR[8],
                                     '', '', '', '', '', '', '', 'No Azure Record'))

# The next section of code is to double check that all records from the AzureActivity file,
# even those without a corresponding entry in the LocalActivity file, have been added to the Merged file.
with open("AzureActivity.csv", "rb") as AZURE:
    azrRdr = csv.reader(AZURE, delimiter=';')

    for azR in azrRdr:

        has_match = False
        if azrRdr.line_num == 1:
            has_match = True
        with open("mergedFinally.csv", "rb") as Merge:
            mrgRdr = csv.reader(Merge, delimiter=';')
            for mgR in mrgRdr:

                if mgR[0].lower() == azR[0].lower():
                    has_match = True
                    break

            if has_match is False:
                with open("mergedFinally.csv", 'a') as merge:
                    mrgWtr = csv.writer(merge, delimiter=';', lineterminator='\n')
                    mrgWtr.writerow((azR[0], '', '', 'No Local Record', '', '', '', '',
                                     azR[1], azR[2], azR[3], azR[4], azR[5], azR[6], azR[7], azR[8]))




