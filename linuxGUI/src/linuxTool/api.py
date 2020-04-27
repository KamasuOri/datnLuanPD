import os
import commands

toolDir = "tool"

def retCmd(cmd):
	return commands.getstatusoutput(cmd)[1]

def checkPath(path):
	if " " in path:
		return "'"+path+"'"
	else:
		return path
def fillPath(path):
	if " " in path:
		return "'"+path+"'"
	return path
	
def copyFile(dest,src):
	# print dest
	if  os.path.isfile(dest):
		cmd = "cp "+checkPath(dest)+" "+checkPath(src)
		retCmd(cmd)
	elif os.path.isdir(dest):
		if  os.path.isfile(dest):
			return 1
		cmd = "cp -r "+checkPath(dest)+" "+checkPath(src)
		retCmd(cmd)
	else:
		print "Cant check file:"+dest
		return 1
	return 0

