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
	getUserAndSID()
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
	getUserAndSID()
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/MicrosoftWindowsUserProfileService")
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/retUserLoginHistory")
	listLog = api.retCmd("ls "+outputDir+"tmpFolder/winLog/Logs").split("\n")
	print listLog
	userEvent="1"
	for list1 in listLog:
		if "Microsoft-Windows-User Profile Service" in list1:
			userEvent = list1
			break
	if userEvent == "1":
		print "Have no user Event check the exist of 'Microsoft-Windows-User Profile Service' in folder "+outputDir+"tmpFolder/winLog/Logs"
		return 1
	api.retCmd("linuxTool/"+api.toolDir+"/evtx_dump -f "+outputDir+"tmpFolder/winLog/MicrosoftWindowsUserProfileService -o json "+outputDir+"tmpFolder/winLog/Logs/"+api.checkPath(userEvent))
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
			retUlAll.write("User::::::::"+userName+"::::::::login\n")
			for line in ul.split("\n"):
				if "Guid" in line or "UserID" in line or "SystemTime" in line:
					line = line.replace(" ","")
					line="   "+line
					retUlAll.write(line+"\n")
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
			retUlAll.write("User::::::::"+userName+"::::::::logoff\n")
			for line in ul.split("\n"):
				if "Guid" in line or "UserID" in line or "SystemTime" in line:
					line = line.replace(" ","")
					line="   "+line
					retUlAll.write(line+"\n")
			retUlAll.write("--------------------------------------------------------------------------------------------------------\n")

	retUlAll.close()
	return 0

def getRDPHistory():
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/LocalSessionManagerOperational")
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/RDPHistory")
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/RemoteConnectionManagerOperational")
	api.retCmd("rm "+outputDir+"tmpFolder/winLog/Security")
	allIp = ''
	userEvent1="1"			#eventid 1149 Applications and Services Logs -> Microsoft -> Windows -> Terminal-Services-RemoteConnectionManager > Operational	
	userEvent2="1"			#eventid 4624  4625  	Windows -> Security
	userEvent3="1"			#eventid 21 23 	Applications and Services Logs -> Microsoft -> Windows -> TerminalServices-LocalSessionManager -> Operational
	listLog = api.retCmd("ls "+outputDir+"tmpFolder/winLog/Logs").split("\n")
	retFile = open(outputDir+"tmpFolder/winLog/RDPHistory","a")
	for list1 in listLog:
		if "RemoteConnectionManager" in list1 and "Operational" in list1:
			userEvent1=list1
		if "Security.evtx" in list1:
			userEvent2 = list1
		if "LocalSessionManager" in list1 and "Operational" in list1:
			userEvent3=list1 
	if userEvent1 !=1:
		
		api.retCmd("linuxTool/"+api.toolDir+"/evtx_dump -f "+outputDir+"tmpFolder/winLog/RemoteConnectionManagerOperational -o json "+outputDir+"tmpFolder/winLog/Logs/"+api.checkPath(userEvent1))
		tmpdata = open(outputDir+"tmpFolder/winLog/RemoteConnectionManagerOperational","r").read().split("Record ")
		for a in tmpdata:
			if '"EventID": 1149' in a and '"UserData"' in a:
				retFile.write("Remote Desktop Services: User authentication succeeded\n")
				a=a.split("\n")
				for line in a:
					if "Param" in line or "SystemTime" in line or "Computer" in line or "EventRecordID" in line or "Guid" in line or "UserID" in line :
						line=line.replace(" ","")
						line = "   "+line
						retFile.write(line+"\n")	
					if 'Param3' in line:
						line = line.replace(" ","").replace('"Param3":"',"").replace('"',"").replace(',',"")
						if line not in allIp:
							allIp += line +"\n"
					
				retFile.write("\n<-->\n")
	else:
		retFile.write("Missing file tmpFolder/winLog/Logs/Microsoft-Windows-TerminalServices-RemoteConnectionManager%4Operational.evtx")

	retFile.write("----------------------------------------\n")

	if userEvent2 !=1:
		api.retCmd("linuxTool/"+api.toolDir+"/evtx_dump -f "+outputDir+"tmpFolder/winLog/Security -o json "+outputDir+"tmpFolder/winLog/Logs/"+api.checkPath(userEvent2))
		tmpdata = open(outputDir+"tmpFolder/winLog/Security","r").read().split("Record ")
		for a in tmpdata:
			if '"EventID": 4624' in a and '"IpAddress": "-"' not in a:
				retFile.write("An account was successfully logged on\n")
				a=a.split("\n")
				for line in a:
					if "AuthenticationPackageName" in line or "IpAddress" in line or "IpPort" in line or "LogonProcessName" in line or "LogonType" in line or "ProcessName" in line or "SubjectDomainName" in line or "SubjectUserName" in line or "TargetDomainName" in line or "TargetUserSid" in line or "AuthenticationPackageName" in line or "WorkstationName" in line or "Computer" in line or "SystemTime" in line:
						line=line.replace(" ","")
						line = "   "+line
						retFile.write(line+"\n")
					if 'IpAddress' in line:
						line = line.replace(" ","").replace('"IpAddress":"',"").replace('"',"").replace(',',"")
						if line not in allIp:
							allIp += line +"\n"
				retFile.write("\n<-->\n")

	else:
		retFile.write("Missing file tmpFolder/winLog/Logs/Security.evtx")

	retFile.write("----------------------------------------\n")

	if userEvent3 !=1:
		api.retCmd("linuxTool/"+api.toolDir+"/evtx_dump -f "+outputDir+"tmpFolder/winLog/LocalSessionManagerOperational -o json "+outputDir+"tmpFolder/winLog/Logs/"+api.checkPath(userEvent2))
		tmpdata = open(outputDir+"tmpFolder/winLog/LocalSessionManagerOperational","r").read().split("Record ")
		for a in tmpdata:
			if '"EventID": 21' in a:
				retFile.write("Remote Desktop Services: Session logon succeeded\n")
				retFile.write("Record "+a)
				retFile.write("\n<-->\n")
			if '"EventID": 23' in a:
				retFile.write("Remote Desktop Services: Session logoff succeeded\n")
				retFile.write("Record "+a)
				retFile.write("\n<-->\n")
	else:
		retFile.write("Missing file tmpFolder/winLog/Logs/Microsoft-Windows-TerminalServices-LocalSessionManager%4Operational.evtx")

	retFile.write("----------------------------------------\n")
	retFile.write("All ip RDP to target PC\n"+allIp)
	retFile.close()

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

def copyChosenFile():
	f = open("FileNeedCopy.txt","r").read().split("\n")

	count=0
	for file in f:
		if len(file)>2:
			path = outputDir+"tmpFolder/fileCopyOption/"+str(count)
			api.retCmd("mkdir -p "+path)
			api.copyFile(inputPath+file,path)
			count +=1



def main(inPath,retDir,ioctl):
	global inputPath
	global outputDir
	outputDir = retDir
	inputPath = inPath
	if inputPath[-1] != "/":
		inputPath=inputPath+"/"
	if outputDir[-1] != "/":
		outputDir=outputDir+"/"


# ioctl part 
	if "getRoughData" in ioctl:
		getRoughData()
	if "getNetworkConfig" in ioctl:
		getNetworkConfig()
	if "getBrowserCache" in ioctl:
		getBrowserCache()
	if "getUserLoginHistory" in ioctl:
		getUserLoginHistory()
	if "getRDPHistory" in ioctl:
		getRDPHistory()
	if "copyChosenFile" in ioctl:
		copyChosenFile()


# start("/mnt/cDrive","./")
