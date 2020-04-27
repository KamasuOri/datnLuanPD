
#!/usr/bin/python
# -*- coding: utf8 -*-

import sys
import subprocess as sub
import subprocess
from subprocess import call

def execCMD(command):
	try:
	    p = sub.Popen(command,shell = True,stdout =sub.PIPE,stderr = sub.STDOUT)
	    output,errors = p.communicate()  
	except:
		print "fail when execCMD :"+command
	return output
def getFullpathOfZoneFile():

	f = open("zone.txt","r").read().split("Directory of ")
	f.remove(f[0])
	output = open("retZoneFile.txt","w")
	count=1
	for dirPath in f:
		dirPath = dirPath.replace("\r","").split("\n")
		tmpPath = dirPath[0]
		for line in dirPath:
			if "Zone.Identifier" in line:
				print count
				line =line.strip().replace(":$DATA","")
				cmd ="type -LiteralPath  'Microsoft.PowerShell.Core\\FileSystem::"+tmpPath+"\\"+line[line.find(" ")+1:]+"'"
				cmd = 'powershell -command "' +cmd+'"'

				output.write(tmpPath+"\\"+line[line.find(" ")+1:]+"\n")

				ret = execCMD(cmd).replace("\r","").split("\n")
				for a1 in ret:
					if "ZoneTransfer" not in a1:
						output.write(a1+"\n")

				output.write("\n----------------------------------------\n")
				count +=1

	output.close()


getFullpathOfZoneFile()