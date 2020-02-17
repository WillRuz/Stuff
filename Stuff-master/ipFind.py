import requests

def netmask_to_cidr(m_netmask):
    return(sum([ bin(int(bits)).count("1") for bits in m_netmask.split(".") ]))

ip = '129.15.225.47'
networkinfo = []
success = True

# Starting a Requests object
ip = str(ip)
with requests.Session() as c:
    url = 'https://numbers.ou.edu/vlansearch.php?id='
    requestdata = url + ip  # This builds the query for our submission

    # This sends and recieves the query response.
    request = c.get(requestdata)

    # Splitting response string into an array.
    result = request.content
    results = result.split(',')

    # Parse the returned information.
    networkinfo = results
    print(networkinfo)
# Make sure the response isn't empty.
if networkinfo[0] == '':
    success = False
    networkinfo = ['False'] * 7
else:
    if networkinfo[0] == 'N':
        networkinfo[0] = "Non-PCI"
    elif networkinfo[0] == 'Y':
        networkinfo[0] = "PCI Zone."
    else:
        networkinfo[0] = "PCI status not recorded."

    # calculate CIDR representation of netmask
    cidr = netmask_to_cidr(networkinfo[2])
    networkinfo[1] += "/" + str(cidr)
    networkinfo[6] = networkinfo[6].replace("<br>", "")

    i = 0
    for element in networkinfo:
        if element == '':
            networkinfo[i] = "Not Reported"
        i += 1
# define array objects for readability
Classification = networkinfo[0]
CIDR = networkinfo[1]
Nmask = networkinfo[2]
Gateway = networkinfo[3]
VLAN = networkinfo[4]
Description = networkinfo[5]
DHCP = networkinfo[6]
                                                                                                                                                                ''Description' : networkinfo[5]}
print(networkinfo)
