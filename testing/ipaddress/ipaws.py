from __future__ import print_function # Python 2/3 compatibility
#import boto3
import json
import decimal
import socket
import fcntl
import struct
import time
import ssl


# Helper class to convert a DynamoDB item to JSON.
class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, decimal.Decimal):
            if o % 1 > 0:
                return float(o)
            else:
                return int(o)
        return super(DecimalEncoder, self).default(o)

def get_ip_address(ifname):
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    return socket.inet_ntoa(fcntl.ioctl(
        s.fileno(),
        0x8915,  # SIOCGIFADDR
        struct.pack('256s', ifname[:15])
    )[20:24])

def call_aws(message):
	dynamodb = boto3.resource('dynamodb', region_name='us-east-1', endpoint_url="https://dynamodb.us-east-1.amazonaws.com")
	table = dynamodb.Table('devicesinfo')
	id = 1
	# your logic here...
	try:
		print("Trying to print " + message)
		response = table.update_item(
		Key={
		     'deviceid' : id,
		     'ipaddr' : message,
		    }
		)
		print("PutItem succeeded:")
		return 1
	except Exception, e:
		print (e)
		fol = open("awserror.txt", "wb")
		fol.write(str(e))
		fol.close()
		return e

#print(json.dumps(response, indent=4, cls=DecimalEncoder))

def lambda_handler(event, context):
    """ Route the incoming request based on type (LaunchRequest, IntentRequest,
    etc.) The JSON body of the request is provided in the event parameter.
    """
def main():
	i = 0
	ssl._create_default_https_context = ssl._create_unverified_context
	time.sleep(10)
	fo = open("aws.txt", "wb")
	fo.write( "starting app!\n");
	while(i != 5): 
		try:
                        ip = get_ip_address('wlan0')
			fo.write(ip);			
	                fo.write( "\n");
			#e = call_aws(ip)
			#fo.write(e);			
			i = 5	
		except Exception, e:
			try:			
				ip = get_ip_address('enx00e08f0026d9')
				fo.write(ip);			
				#e = call_aws(ip)		
				fo.write(e);				
			except Exception, e:
				print ("Exception2")
				i = i + 1
				fo.write( " Exception!\n");
				time.sleep(5)
	# Close opend file
	fo.close()


if __name__ == "__main__":
    main()
    
