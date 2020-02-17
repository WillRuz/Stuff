import csv
import imaplib
import time

#for time keeping purposes
start = time.time()

#open email list.... change name of file as needed
with open('8-15SpyCloudRecords.csv') as cred:
    cred_reader = csv.reader(cred, delimiter=';')

#this is the file where we write what emails work
    with open("Accounts_to_lock.csv", "wb") as lock:
        wtr = csv.writer(lock, delimiter=';')

        #for each row in our file
        for row in cred_reader:
            username = row[0]
            password = row[1]
        #login to imap
            mail = imaplib.IMAP4_SSL('outlook.office365.com', 993)
        #try each credential
            try:
                mail.login(username, password)
                print("--------------------Logged in as %r !" % username)
        #if it successfully logs in, write the username/password into our new file
                wtr.writerow((row[0], row[1]))
        # if the credentials dont work, we throw this error
            except imaplib.IMAP4.error:
                print("login failed for %r" %username)
            mail.logout()
# print out how long it took
end = time.time()
print (end - start)