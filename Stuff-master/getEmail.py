import imaplib

email_user = 'sec_wruzicka@ou.edu'
email_pass = input('Password: ')

M = imaplib.IMAP4_SSL('imap-mail.outlook.com', 993)
M.login(email_user, email_pass)
M.select('Test')

rv, data = M.search(None, 'ALL')

#get the actual email
for num in data[0].split():
    rv, data = M.fetch(num, '(RFC822)')

    #decode the bytes to string to be able to write into a file
    thing = data[0][1].decode("utf-8")
    #open file to write
    eggs = open('ok.eml','w')
    eggs.write(thing)
    eggs.close()
