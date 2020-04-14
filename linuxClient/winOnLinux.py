# -*- coding: utf-8 -*- 
import os
import commands
import numpy

import api

userAndSID=['']
userList=['']
outputDir=''
inputPath=''

def getUserAndSID():
	check =0
	try:
		a = open(outputDir+"tmpFolder/reg/config/SAMparse","r").read()

		if len(a)<50:
			return 1
		a = a.split("Group Membership Information")[0].split("\n")
		tmpMem=''
		userAndSID.remove(userAndSID[0])
		userList.remove(userList[0])
		for line in a:
			if "Username" in line and check ==0 :
				tmpMem=line.split(":")[1].split("[")[0]
				check =1
				continue

			if "SID" in line and check ==1 :
				userList.append(tmpMem.strip().replace(" ",""))
				tmpMem=tmpMem+"<-->"+line.split(":")[1]
				tmpMem = tmpMem.strip().replace(" ","")
				userAndSID.append(tmpMem)
				tmpMem=''
				check =0
				continue
	except:
		print "loi getSysReg"
	return 0

def getRoughData():	

	# get reg file and some thing we want to use
	try:
		api.copyFile(inputPath+"Windows/System32/config",outputDir+"tmpFolder/reg")		#registry
		api.copyFile(inputPath+"Windows/System32/winevt/Logs",outputDir+"tmpFolder/winLog")#winlog

		api.retCmd("rip.pl -r "+outputDir+"tmpFolder/reg/config/SAM -p samparse > "+outputDir+"tmpFolder/reg/config/SAMparse")
	except:
		print "loi getRoughData phase 1"

	try:
		getUserAndSID()

		for userName in userList:
			cacheStore = outputDir+"tmpFolder/reg/userReg/"+userName
			api.retCmd("mkdir -p "+cacheStore)
			api.copyFile(inputPath+"Users/"+userName+"/NTUSER.DAT",cacheStore)		#user registry
	except:
		print "loi getRoughData phase 2"
	return 0

def getBrowserCache():
	for userName in userList:
		cacheStore = outputDir+"tmpFolder/browserCache/"+userName
		api.retCmd("mkdir -p "+cacheStore)

		# -------------------------------------------- test chay binh thuong --------------------------------------------
		chromeCache = inputPath+"Users/"+userName+"/AppData/Local/Google/Chrome/User Data/Default/Cache"
		if os.path.exists(chromeCache):
			api.copyFile(chromeCache,cacheStore+"/chrome")

		coccocCache = inputPath+"Users/"+userName+"/AppData/Local/CocCoc/Browser/User Data/Default/Cache"
		if os.path.exists(coccocCache):
			api.copyFile(coccocCache,cacheStore+"/coccoc")

		ieCache = inputPath+"Users/"+userName+"/AppData/Local/Microsoft/Windows/INetCache/IE"
		if os.path.exists(ieCache):
			api.copyFile(ieCache,cacheStore+"/IE1")
		ieCache = inputPath+"Users/"+userName+"/AppData/Local/Microsoft/Windows/Caches"
		if os.path.exists(ieCache):
			api.copyFile(ieCache,cacheStore+"/IE2")
		ieCache = inputPath+"Users/"+userName+"/AppData/Local/Microsoft/Windows/Profiles/INetCache/IE"
		if os.path.exists(ieCache):
			api.copyFile(ieCache,cacheStore+"/IE3")
		# ---------------------------------------------------------------------------------------



		operaCache = inputPath+"Users/"+userName+"AppData/Local/Opera Software/Opera Stable"
		if os.path.exists(operaCache):
			api.copyFile(operaCache,cacheStore+"/opera")
	
		firefoxCache = inputPath+"Users/"+userName+"/AppData/Local/Mozilla/Firefox/Profiles"
		if os.path.exists(firefoxCache):
			api.copyFile(firefoxCache,cacheStore+"/firefox")
		# ---------------------------------------------------------------------------------------
		
def getUserLoginHistory():
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/MicrosoftWindowsUserProfileService")
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/retUserLoginHistory")
	listLog = api.retCmd("ls "+outputDir+"tmpFolder/winLog/Logs").split("\n")
	userEvent="1"
	for list1 in listLog:
		if "Microsoft-Windows-User Profile Service" in list1:
			userEvent = list1
			break
	if userEvent == "1":
		print "Have no user Event check the exist of 'Microsoft-Windows-User Profile Service' in folder "+outputDir+"tmpFolder/winLog/Logs"
		return 1
	api.retCmd(api.toolDir+"/evtx_dump -f "+outputDir+"tmpFolder/winLog/MicrosoftWindowsUserProfileService -o json "+outputDir+"tmpFolder/winLog/Logs/"+api.checkPath(userEvent))
	ulAll = open(outputDir+"tmpFolder/winLog/MicrosoftWindowsUserProfileService","rb").read().split("Record ")
	retUlAll= open(outputDir+"tmpFolder/winLog/retUserLoginHistory","wb")
	for ul in ulAll:
		tmp = ul.replace(" ","")
		if '"EventID":2' in tmp:
			tmp = tmp.split("\n")
			userName =''
			for a12 in tmp:
				if "UserID" in a12:
					userName=a12
			userName = userName.replace('"UserID":"',"").replace('"','')
			for uN in userAndSID:
				if userName in uN:
					userName = uN.split("<-->")[0]
					break
			retUlAll.write("User::::::::"+userName+"::::::::login\n Record "+ul)
			retUlAll.write("--------------------------------------------------------------------------------------------------------\n")

		elif '"EventID":4' in tmp :
			tmp = tmp.split("\n")
			userName =''
			for a12 in tmp:
				if "UserID" in a12:
					userName=a12
			userName = userName.replace('"UserID":"',"").replace('"','')
			for uN in userAndSID:
				if userName in uN:
					userName = uN.split("<-->")[0]
					break
			retUlAll.write("User::::::::"+userName+"::::::::logoff\n Record"+ul)
			retUlAll.write("--------------------------------------------------------------------------------------------------------\n")
	retUlAll.close()
	return 0

def getNetworkConfig():
	retFile = open(outputDir+"tmpFolder/network/status.txt","a")
	if not os.path.exists(outputDir+"tmpFolder/reg/config/SYSTEM"):
		retFile.write("Can't find SYSTEM file !")
		retFile.close()
		print "Can't find SYSTEM file !"
		return 0
	try:
		retFile.write(api.retCmd("ifconfig -a"))
		retFile.write("\n")
		tmpSysRegData = api.retCmd("rip.pl -r "+outputDir+"tmpFolder/reg/config/SYSTEM -f system").split("----------------------------------------")
		for block in tmpSysRegData:
			if "Gets NIC info from System hive" in block:
				tmp1 = block.split("\n")
				for line in tmp1:
					if "Adapter" in line:
						retFile.write("+++++\n")
						retFile.write(line)
						retFile.write("\n")
					if "LastWrite Time:" in line:
						retFile.write(line)
						retFile.write("\n")
					if "EnableDHCP" in line:
						retFile.write(line)
						retFile.write("\n")
					if "SubnetMask" in line:
						retFile.write(line)
						retFile.write("\n")
					if "DhcpServer" in line:
						retFile.write(line)
						retFile.write("\n")
					if "LeaseObtainedTime" in line:
						retFile.write(line)
						retFile.write("\n")
					if "T1" in line:
						retFile.write(line)
						retFile.write("\n")
					if "T2" in line:
						retFile.write(line)
						retFile.write("\n")
					if "RegistrationEnabled" in line:
						retFile.write(line)
						retFile.write("\n")
				retFile.write("----------------------------------------")
				retFile.write("\n")
		retFile.close()
	except:
		print "fail in getNetworkConfig"

def start(inPath,retDir):
	global inputPath
	global outputDir
	outputDir = retDir
	inputPath = inPath
	if inputPath[-1] != "/":
		inputPath=inputPath+"/"
	if outputDir[-1] != "/":
		outputDir=outputDir+"/"

	# getRoughData()

	# getBrowserCache()
	# getUserLoginHistory()

	getNetworkConfig()




start("/mnt/cDrive","./")