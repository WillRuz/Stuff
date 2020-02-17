 Code by Will Ruzicka Ver 1.0,  Takes a 2 row csv (email password) and tests
# for authentication via the mail server listed in code.
import csv
import imaplib
import time

# List of illegal characters that can't be in passwords
bad_chars = ['@', '#', ':', '?', '+']

# List of optional special characters that can be in passwords
good_chars = ['~', '!', '$', '%', '^', '&', '*', '(', ')', '_', '-', '=',
              '{', '}', '[', ']', '\\', ',', '.', '<', '>', ';', '\'']
#list of emails we have already seen
seen = []

#num of emails checked. starts at -1 because it increments to 0 before checking the first one
num = -1

#this function tries to login with a username and a password, if it works, checks for uniquness if unique adds to file
def tryLogin(u,p):
    # login to imap
    mail = imaplib.IMAP4_SSL('outlook.office365.com', 993)
    try:
        #login to imap with credentials
        mail.login(u, p)
        print("-------------------------------------------------------------------------Logged in as %r !" % u)
        # if it successfully logs in, write the username/password into our new file
        # if the row already exists in our list 'seen' we simply skip putting it in again
        if row not in seen:
            # if the row doesn't necessarily appear, then we add it to our csv file and seen[]
            seen.append(row)
            wtr.writerow(row)

        # if the credentials dont work, we throw this error
    except imaplib.IMAP4.error:
        print("login failed for %r" % u)
    mail.logout()

#for time keeping purposes
start = time.time()


# First open the csv file to read the credentials
with open('ehh.csv', 'r') as IntSights:
    reader = csv.reader(IntSights, delimiter=',')

    # Then open a file to write the filtered credentials to
    with open("Accounts_to_lock.csv", "w") as ouPass:
        wtr = csv.writer(ouPass, delimiter=',', lineterminator = '\n')

        # Iterate through the csv file.
        # Emails are in the first 'column' and passwords are in the third 'column' of the csv file.
        for row in reader:
            email = row[0]
            password = row[1]

            #num is solely to keep track of how many emails have been run
            num += 1
            if num % 50 == 0:
                print("%r emails have been checked" % num)
            length = len(password)

            # Keeps track of if the password has an illegal character.
            has_bad_char = False

            # If password is too short or too long, skip it.
            if length < 8 or length > 32 :
                print("%r has a bad password" % email)
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
                print("%r has a bad password" % email)
                continue

            if 16 <= length <= 32:
                # Password meets all requirements,try login.
                tryLogin(email, password)
                continue

            if 8 <= length < 16:
                # Checks if the password has an uppercase and lowercase letter
                if any(x.isupper() for x in password) and any(x.islower() for x in password):
                    # Checks if the password has a digit.
                    if any(x.isdigit() for x in password):
                        # Password meets all requirements, try login.
                        tryLogin(email, password)

                    # If the password doesn't have a digit, it needs a special character
                    else:
                        # Iterate through the password and check for a special character
                        has_special_char = False
                        for x in row[1]:
                           for c in good_chars:
                               if x == c:
                                   has_special_char = True

                        if has_special_char is True:
                            # Password meets all requirements, try login.
                            tryLogin(email, password)
                        else:
                            print("%r has a bad password" % email)
                else:
                    print("%r has a bad password" % email)
# print out how long it took
end = time.time()
print (end - start)
