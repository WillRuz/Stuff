#Code by Emily Knox Version 1????
# This code takes the csv file provided by IntSights for a 'credential leak including clear text passwords' alert.
# It checks if passwords meet OU requirements and filters out entries that don't - which is usually a lot.
# A new file is created that contains a list of the credentials with potentially valid passwords.
#################################################################################################
# Current (Fall 2018) OU Password Requirements:                                                 #
#   Cannot be OU 4x4. Must be at least 8 characters long, and not longer than 32 characters.    #
#   If shorter than 16 characters, must also have:                                              #
#    -At least one uppercase letter                                                             #
#    -At least one lowercase letter                                                             #
#    -At least EITHER one number OR one of the following special characters:                    #
#      ~ ! $ % ^ & * ( ) _ - = { } [ ] \ , . < > ; '                                            #
#   If at least 16 characters long, there are no other password requirements.                   #
#   Passwords cannot include the following characters:                                          #
#      @ # : ? +                                                                                #
#################################################################################################

import csv
# List of illegal characters that can't be in passwords
bad_chars = ['@', '#', ':', '?', '+']
# List of optional special characters that can be in passwords
good_chars = ['~', '!', '$', '%', '^', '&', '*', '(', ')', '_', '-', '=',
              '{', '}', '[', ']', '\\', ',', '.', '<', '>', ';', '\'']

# First open the IntSights file to read the credentials
with open('NAME_OF_INTSIGHTS_FILE.csv', 'rb') as IntSights:
    reader = csv.reader(IntSights, delimiter=',')

    # Then open a file to write the filtered credentials to
    with open("OUPassword_Req_Met.csv", "wb") as ouPass:
        wtr = csv.writer(ouPass, delimiter=',')

        # Iterate through the IntSights file.
        # Emails are in the first 'column' and passwords are in the third 'column' of the csv file.
        for row in reader:
            email = row[0]
            password = row[2]
            length = len(password)

            # Keeps track of if the password has an illegal character.
            has_bad_char = False

            # If password is too short or too long, skip it.
            if length < 8 or length > 32 :
                continue

            # Iterate through the password and check if any characters are illegal.
            for x in password:
                for c in bad_chars:
                    if x == c:
                        # No need to check the rest after first bad character
                        has_bad_char = True
                        break
            # If password has an illegal character, skip it.
            if has_bad_char is True:
                continue

            if 16 <= length <= 32:
                # Password meets all requirements, so write it to the new csv.
                wtr.writerow((email, password))
                continue

            if 8 <= length < 16:
                # Checks if the password has an uppercase and lowercase letter
                if any(x.isupper() for x in password) and any(x.islower() for x in password):
                    # Checks if the password has a digit.
                    if any(x.isdigit() for x in password):
                        # Password meets all requirements, so write it to the new csv.
                        wtr.writerow((email, password))

                    # If the password doesn't have a digit, it needs a special character
                    else:
                        # Iterate through the password and check for a special character
                        has_special_char = False
                        for x in row[2]:
                           for c in good_chars:
                               if x == c:
                                   has_special_char = True

                        if has_special_char is True:
                            # Password meets all requirements, so write it to the new csv.
                            wtr.writerow((email, password))

